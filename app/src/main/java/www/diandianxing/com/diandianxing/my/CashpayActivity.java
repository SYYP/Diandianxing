package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;
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
     int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_yajinchongzhi);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        img_xuanze = (ImageView) findViewById(R.id.img_xuanze);
        img_xuanze1 = (ImageView) findViewById(R.id.img_xuanze1);
        chongzhi_ok = (TextView) findViewById(R.id.chongzhi_ok);
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
                      ToastUtils.show(CashpayActivity.this,"微信",1);
                  }
                else  if(i==2){
                      //支付宝
                      ToastUtils.show(CashpayActivity.this,"支付宝",1);
                  }
                break;
        }
    }
}
