package www.diandianxing.com.diandianxing.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.Map;

import www.diandianxing.com.diandianxing.MainActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Loginbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.CustomProgressDialog;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.MyUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivitys extends UMLoginActivity implements View.OnClickListener {

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
    private static ProgressDialog mDialog;
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
        mDialog = new CustomProgressDialog(this, R.style.myprogressdialog);
        iv_callback.setOnClickListener(this);
        login_sso.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
    }
    private void submit() {
        if (TextUtils.isEmpty(login_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyUtils.isMobileNO(login_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(login_pwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_pwd.getText().toString().length() < 5||login_pwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }
            /*
               登录
             */
             Map<String,String> map=new ArrayMap<>();
             map.put("mobile",login_phone.getText().toString().trim());
             map.put("password",login_pwd.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL + "s=Login/login", map, new BaseObserver1<Loginbean>("") {
            @Override
            public void onSuccess(Loginbean result, String tag) {
                if(result.getCode()==200){
                    Loginbean.DatasBean datas = result.getDatas();
                    String token= datas.getToken();
                    String id = datas.getId();
                    //将 token与userid保存
                    SpUtils.putString(LoginActivitys.this,"token",token);
                    SpUtils.putString(LoginActivitys.this,"userid",id);
                    SpUtils.putInt(LoginActivitys.this, "guid", 1);
                    Intent intent=new Intent(LoginActivitys.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if(result.getCode()==404){
                    ToastUtils.show(LoginActivitys.this,"账号或密码错误",1);
                }
            }
            @Override
            public void onFailed(int code,String data) {
               ToastUtils.show(LoginActivitys.this,data,1);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.login_sso:

                  submit();

                break;
            case R.id.forgetpwd:
                Intent intent1=new Intent(this,ForgetActivity.class);
                   startActivity(intent1);
                break;
            case R.id.iv_qq:
                mDialog.show();
                loginByQQ(this);
                break;
            case R.id.iv_weixin:
              //  mDialog.show();
                loginByWeiXin(this);
                break;
            case R.id.iv_weibo:
                break;
        }
    }

    public static void closeDialog(){
        mDialog.dismiss();
        mDialog=null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
