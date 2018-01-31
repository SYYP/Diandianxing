package www.diandianxing.com.diandianxing.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.bean.Dingdanbean;
import www.diandianxing.com.diandianxing.bean.PayResult;
import www.diandianxing.com.diandianxing.bean.Payailbean;
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

    public PayUtils(Context context, int type, int lei, String money) {
        this.context = context;
        this.type = type;
        this.lei = lei;
        this.money = money;
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    Toast.makeText(context, " " + payResult.getResultStatus(), Toast.LENGTH_SHORT).show();
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
                          Map<String ,String> map=new HashMap<>();
                            map.put("uid",SpUtils.getString(context,"userid",null));
                             map.put("token",SpUtils.getString(context,"token",null));
                              map.put("ordersn",ordersn+"");
                        RetrofitManager.post(MyContants.BASEURL + "s=User/ordersnSearch", map, new BaseObserver1<Dingdanbean>("") {
                            @Override
                            public void onSuccess(Dingdanbean result, String tag) {

                                if(result.getCode()==200){
                                    Dingdanbean.DatasBean datas = result.getDatas();
                                    String balance = datas.getBalance();//余额
                                    String securityDeposit = datas.getSecurityDeposit();//押金
                                    Log.d("balance",balance+"-------");
                                    Log.d("secur",securityDeposit+"----");
                                    SpUtils.putString(context,"yajin",securityDeposit);
                                    SpUtils.putString(context,"yue",balance);
                                    EventMessage eventbus=new EventMessage("dingdan");
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



       public void goAlipay(){

           Map<String,String> map =new HashMap<>();
            map.put("uid",SpUtils.getString(context,"userid",null));
            map.put("token",SpUtils.getString(context,"token",null));
            map.put("type",type+"");
            map.put("pay_type",lei+"");
            map.put("gold",money+"");
           RetrofitManager.post(MyContants.BASEURL + "s=Payment/pay", map, new BaseObserver1<Payailbean>("") {


               private String sign;

               @Override
               public void onSuccess(Payailbean result, String tag) {

                  if(result.getCode()==200)  {
                      Payailbean.DatasBean datas = result.getDatas();
                      sign = datas.getSign();
                      ordersn = datas.getOrdersn();

                      Runnable payRunnable = new Runnable() {

                          @Override
                          public void run() {
                              PayTask alipay = new PayTask((Activity)context);
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
}
