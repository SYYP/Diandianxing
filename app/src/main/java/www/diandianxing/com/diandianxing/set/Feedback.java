package www.diandianxing.com.diandianxing.set;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Feedback extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView login_sso;
    private EditText feel_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_fuwu);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        feel_text = (EditText) findViewById(R.id.feel_edtext);
        login_sso = (TextView) findViewById(R.id.login_sso);
        zhong.setText("客户服务");
        iv_callback.setOnClickListener(this);
        login_sso.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.login_sso:
                finish();
                break;
        }
    }
}
