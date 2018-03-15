package www.diandianxing.com.diandianxing.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivity extends UMLoginActivity implements View.OnClickListener{

    private ImageView logio;
    private RelativeLayout relative;
    private TextView login_sso;
    private TextView register_ok;
    private LinearLayout liner;
    private RelativeLayout rel_liner;
    private TextView iv_qq;
    private TextView iv_weixin;
    private TextView iv_weibo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        MyContants.windows(this);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        logio = (ImageView) findViewById(R.id.logio);
        relative = (RelativeLayout) findViewById(R.id.relative);
        login_sso = (TextView) findViewById(R.id.login_sso);
        register_ok = (TextView) findViewById(R.id.register_ok);
        liner = (LinearLayout) findViewById(R.id.liner);
        rel_liner = (RelativeLayout) findViewById(R.id.rel_liner);
        iv_qq = (TextView) findViewById(R.id.iv_qq);
        iv_weixin = (TextView) findViewById(R.id.iv_weixin);
        iv_weibo = (TextView) findViewById(R.id.iv_weibo);
        login_sso.setOnClickListener(this);
        register_ok.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_sso:
                Intent intent=new Intent(this,LoginActivitys.class);
                startActivity(intent);
                break;
            case R.id.register_ok:
                Intent intent1=new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_qq:
                loginByQQ(this);
                break;
            case R.id.iv_weixin:
                loginByWeiXin(this);
                break;
            case R.id.iv_weibo:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
