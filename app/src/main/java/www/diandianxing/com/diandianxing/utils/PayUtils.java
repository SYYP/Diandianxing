package www.diandianxing.com.diandianxing.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.bean.Dingdanbean;
import www.diandianxing.com.diandianxing.bean.PayResult;
import www.diandianxing.com.diandianxing.bean.Payailbean;
import www.diandianxing.com.diandianxing.bean.Wetpaybean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class PayUtils {
    private static final int SDK_PAY_FLAG = 1;
    Context context;
    int type;
    int lei;
    String money;
    private String ordersn;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private String WX_APPID = "wx6d0a0a32729dfd2c";// 微信appid

    public PayUtils(Context context, int type, int lei, String money) {
        this.context = context;
        this.type = type;
        this.lei = lei;
        this.money = money;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, WX_APPID, false);
        // 将该app注册到微信
        api.registerApp(WX_APPID);
    }


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    //     Toast.makeText(context, " " + payResult.getResultStatus(), Toast.LENGTH_SHORT).show();
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
//                        /*
//                           根据订单号查询状态
//                         */
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", SpUtils.getString(context, "userid", null));
                        map.put("token", SpUtils.getString(context, "token", null));
                        map.put("ordersn", ordersn + "");
                        RetrofitManager.post(MyContants.BASEURL + "s=User/ordersnSearch", map, new BaseObserver1<Dingdanbean>("") {
                            @Override
                            public void onSuccess(Dingdanbean result, String tag) {

                                if (result.getCode() == 200) {
                                    Dingdanbean.DatasBean datas = result.getDatas();
                                    String balance = datas.getBalance();//余额
                                    String securityDeposit = datas.getSecurityDeposit();//押金
                                    Log.d("balance", balance + "-------");
                                    Log.d("secur", securityDeposit + "----");
                                    SpUtils.putString(context, "yajin", securityDeposit);
                                    SpUtils.putString(context, "yue", balance);
                                    EventMessage eventbus = new EventMessage("dingdan");
                                    EventBus.getDefault().postSticky(eventbus);
                                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                                    //自己写的跳转到自定义的支付宝支付成功界面
                                    ((Activity) context).finish();
                                }
                            }

                            @Override
                            public void onFailed(int code, String data) {

                            }
                        });

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        /*
                        "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        最终交易是否成功以服务端异步通知为准（小概率状态）
                         */
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };


    public void goAlipay() {

        Map<String, String> map = new HashMap<>();
        map.put("uid", SpUtils.getString(context, "userid", null));
        map.put("token", SpUtils.getString(context, "token", null));
        map.put("type", type + "");
        map.put("pay_type", lei + "");
        map.put("gold", money + "");
        RetrofitManager.post(MyContants.BASEURL + "s=Payment/pay", map, new BaseObserver1<Payailbean>("") {


            private String sign;

            @Override
            public void onSuccess(Payailbean result, String tag) {

                if (result.getCode() == 200) {
                    Payailbean.DatasBean datas = result.getDatas();
                    sign = datas.getSign();
                    ordersn = datas.getOrdersn();

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask((Activity) context);
                            String result = alipay.pay(sign, true);//调用支付接口，获取支付结果
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);

                        }
                    };

                    // 必须异步调用，支付或者授权的行为需要在独立的非ui线程中执行
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();

                }
            }


            @Override
            public void onFailed(int code, String data) {

            }
        });


}

     /*
       微信支付
      */

   public void weixinPay() {
//        HttpParams httpParams=new HttpParams();
//        httpParams.put("uid", SpUtils.getString(context, "userid", null));
//        httpParams.put("token", SpUtils.getString(context, "token", null));
//        httpParams.put("type", type + "");
//        httpParams.put("pay_type", lei + "");
//        httpParams.put("gold", money + "");
//        OkGo.<String>post(MyContants.BASEURL + "s=Payment/pay")
//                .tag(context)
//                .params(httpParams)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                    }
//                });
       Map<String, String> map = new HashMap<>();
       map.put("uid", SpUtils.getString(context, "userid", null));
       map.put("token", SpUtils.getString(context, "token", null));
       map.put("type", type + "");
       map.put("pay_type", lei + "");
       map.put("gold", money + "");
        RetrofitManager.get(MyContants.BASEURL + "s=Payment/pay", map, new BaseObserver1<Wetpaybean>("") {



            @Override
            public void onSuccess(Wetpaybean result, String tag) throws RemoteException {
                PayReq req = new PayReq();
                Wetpaybean.DatasBean datas = result.getDatas();
                String ordersn = datas.getOrdersn();
                SpUtils.putString(context,"ordersn",ordersn);
                Wetpaybean.DatasBean.SignBean sign = datas.getSign();
                req.appId = sign.getAppid();// 微信开放平台审核通过的应用APPID
                req.partnerId = sign.getPartnerid();// 微信支付分配的商户号
                req.prepayId = sign.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
                req.nonceStr = sign.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
                int timestamp = sign.getTimestamp();
                String s = String.valueOf(timestamp);
                req.timeStamp =s ;// 时间戳，app服务器小哥给出
                req.packageValue ="Sign=WXPay";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
              Log.d("Tag","sign的内容"+sign.getPackageX());
               req.sign = sign.getSign();// 签名，服务器小哥给出
                req.extData = "app data"; // optional
//                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);//调起支付
//                //微信支付的金额我用sp存的，之前用eventbus不管用
//                SpUtils.putString(context,"wxprice",datas.getPay_price());
//                EventMessage eventMessage = new EventMessage("pay");
//                EventBus.getDefault().postSticky(eventMessage);
//                EventMessage eventMessages = new EventMessage("renew");
//                EventBus.getDefault().postSticky(eventMessages);
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });
    }}
