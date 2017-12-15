package www.diandianxing.com.diandianxing.set;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AboutweActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView imageView2;
    private RelativeLayout real;
    private TextView about_wei;
    private TextView wei_num;
    private TextView about_call;
    private TextView call_num;
    private TextView about_email;
    private TextView email_num;
    private TextView about_guanwang;
    private TextView call_guanwang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_aboutwe);
        MyContants.windows(this);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        real = (RelativeLayout) findViewById(R.id.real);
        about_wei = (TextView) findViewById(R.id.about_wei);
        wei_num = (TextView) findViewById(R.id.wei_num);
        about_call = (TextView) findViewById(R.id.about_call);
        call_num = (TextView) findViewById(R.id.call_num);
        about_email = (TextView) findViewById(R.id.about_email);
        email_num = (TextView) findViewById(R.id.email_num);
        about_guanwang = (TextView) findViewById(R.id.about_guanwang);
        call_guanwang = (TextView) findViewById(R.id.call_guanwang);
        zhong.setText("关于我们");
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
