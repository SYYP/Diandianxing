package www.diandianxing.com.diandianxing.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.ultimatebar.UltimateBar;

import www.diandianxing.com.diandianxing.MainActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivitys extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView logio;
    private RelativeLayout relative;
    private ImageView set_lpwd;
    private EditText login_phone;
    private ImageView login_lpwd;
    private EditText login_pwd;
    private TextView login_sso;
    private RelativeLayout rel_liner;
    private TextView iv_qq;
    private TextView iv_weixin;
    private TextView iv_weibo;
    private ImageView ivcallback;
    private TextView forgetpwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        MyContants.windows(this);
        setContentView(R.layout.activity_startlogin);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        logio = (ImageView) findViewById(R.id.logio);
        relative = (RelativeLayout) findViewById(R.id.relative);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_lpwd = (ImageView) findViewById(R.id.login_lpwd);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        login_sso = (TextView) findViewById(R.id.login_sso);
        rel_liner = (RelativeLayout) findViewById(R.id.rel_liner);
        iv_qq = (TextView) findViewById(R.id.iv_qq);
        iv_weixin = (TextView) findViewById(R.id.iv_weixin);
        iv_weibo = (TextView) findViewById(R.id.iv_weibo);
        forgetpwd = (TextView) findViewById(R.id.forgetpwd);
        iv_callback.setOnClickListener(this);
        login_sso.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String phone = login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.login_sso:
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.forgetpwd:
                break;

        }
    }
}
