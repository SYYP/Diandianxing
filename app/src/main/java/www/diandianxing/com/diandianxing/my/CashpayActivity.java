package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Yajinbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.PayUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class CashpayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView img_xuanze;
    private ImageView img_xuanze1;
    private TextView chongzhi_ok;
     int i=1;
    private TextView chong_money;
    private String yajin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_yajinchongzhi);
        initView();
        network();
    }

    private void network() {

        Map<String,String> map=new HashMap<>();
          map.put("uid", SpUtils.getString(this,"userid",null));
          map.put("token",SpUtils.getString(this,"token",null));
        RetrofitManager.post(MyContants.BASEURL + "s=User/depositGet", map, new BaseObserver1<Yajinbean>("") {



            @Override
            public void onSuccess(Yajinbean result, String tag) {
                if(result.getCode()==200){
                    yajin = result.getDatas().toString();
                    chong_money.setText("¥ "+result.getDatas().toString());
                }
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        img_xuanze = (ImageView) findViewById(R.id.img_xuanze);
        img_xuanze1 = (ImageView) findViewById(R.id.img_xuanze1);
        chongzhi_ok = (TextView) findViewById(R.id.chongzhi_ok);
        chong_money = (TextView) findViewById(R.id.chong_money);
        iv_callback.setOnClickListener(this);
        img_xuanze.setOnClickListener(this);
        img_xuanze1.setOnClickListener(this);
        chongzhi_ok.setOnClickListener(this);
        zhong.setText("押金充值");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.img_xuanze:
                     img_xuanze.setImageResource(R.drawable.select_true);
                      img_xuanze1.setImageResource(R.drawable.select_false);
                    i=1;

                break;
            case R.id.img_xuanze1:
                img_xuanze.setImageResource(R.drawable.select_false);
                img_xuanze1.setImageResource(R.drawable.select_true);
                  i=2;
                break;
            case R.id.chongzhi_ok:
                  if(i==1){
                     //微信
                      ToastUtils.show(CashpayActivity.this,"微信",0);
                  }
                else  if(i==2){
                      PayUtils payUtils=new PayUtils(this,2,2,yajin+"");
                        payUtils.goAlipay();
                      //支付宝
                      ToastUtils.show(CashpayActivity.this,"支付宝",0);
                  }
                break;
        }
    }
}
