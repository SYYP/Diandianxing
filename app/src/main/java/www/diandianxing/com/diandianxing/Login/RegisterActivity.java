package www.diandianxing.com.diandianxing.Login;

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

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Successbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.MyUtils;
import www.diandianxing.com.diandianxing.utils.SendSmsTimerUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView logio;
    private RelativeLayout relative;
    private ImageView set_lpwd;
    private EditText register_phone;
    private ImageView register_l;
    private EditText register_pwd;
    private ImageView login_lpwd;
    private EditText register_yanzhen;
    private TextView huoqu;
    private TextView login_sso;
    private TextView zhu;
    private RelativeLayout rel_liner;
    private TextView iv_qq;
    private TextView iv_weixin;
    private TextView iv_weibo;
    private TextView text_diandian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        MyContants.windows(this);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        logio = (ImageView) findViewById(R.id.logio);
        relative = (RelativeLayout) findViewById(R.id.relative);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register_l = (ImageView) findViewById(R.id.register_l);
        register_pwd = (EditText) findViewById(R.id.register_pwd);
        login_lpwd = (ImageView) findViewById(R.id.login_lpwd);
        register_yanzhen = (EditText) findViewById(R.id.register_yanzhen);
        huoqu = (TextView) findViewById(R.id.huoqu);
        login_sso = (TextView) findViewById(R.id.register_sso);
        zhu = (TextView) findViewById(R.id.zhu);
        rel_liner = (RelativeLayout) findViewById(R.id.rel_liner);
        text_diandian = (TextView) findViewById(R.id.text_diandian);
        iv_qq = (TextView) findViewById(R.id.iv_qq);
        iv_weixin = (TextView) findViewById(R.id.iv_weixin);
        iv_weibo = (TextView) findViewById(R.id.iv_weibo);
        iv_callback.setOnClickListener(this);
        login_sso.setOnClickListener(this);
        huoqu.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        text_diandian.setOnClickListener(this);
    }
    private void submit() {
        if (TextUtils.isEmpty(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MyUtils.isMobileNO(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(register_yanzhen.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(register_pwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (register_pwd.getText().toString().length() < 5 || register_pwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }
         gomain();

    }

    private void gomain() {
                ArrayMap map=new ArrayMap<String,String>();
             //先验证手机号是否正确，然后在注册
                 map.put("mobile",register_phone.getText().toString());
                 map.put("code",register_yanzhen.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL + "s=Sms/verify", map, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) {

                if(result.getCode()==200){
                     /*
                       注册
                     */
                      register();
                }
            }
            @Override
            public void onFailed(int code) {
                  ToastUtils.show(RegisterActivity.this,"验证码错误",1);
            }
        });

    }
    private void register() {
        Map<String,String>map=new ArrayMap<>();
         map.put("contact",register_phone.getText().toString().trim());
         map.put("password",register_pwd.getText().toString().trim());
         RetrofitManager.post(MyContants.BASEURL + "s=Login/register", map, new BaseObserver1<Successbean>("") {
             @Override
             public void onSuccess(Successbean result, String tag) {
                 if(result.getCode()==200){
                     ToastUtils.show(RegisterActivity.this,"注册成功",1);
                      finish();

                 }
             }

             @Override
             public void onFailed(int code) {

                 ToastUtils.show(RegisterActivity.this,"手机号或密码错误",1);
             }
         });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_callback:
                finish();
                break;
            case R.id.register_sso:

                submit();
                finish();
                break;
            case R.id.huoqu:
                getcode();
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_weixin:
                break;
            case R.id.iv_weibo:
                break;
            //点点行协议
            case R.id.text_diandian:
                Intent intent=new Intent(this,XieyiActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void getcode() {
        if (TextUtils.isEmpty(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!MyUtils.isMobileNO(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;

        }
         //进行网络请求
        ArrayMap arrayMap=new ArrayMap<String,String>();
        arrayMap.put("mobile",register_phone.getText().toString().trim());
        RetrofitManager.get(MyContants.BASEURL + "s=Sms/sendSms", arrayMap, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) {
                if (result.getCode() == 200) {
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    SendSmsTimerUtils sendSmsTimerUtils=new SendSmsTimerUtils(60*1000,1000,huoqu);
                    sendSmsTimerUtils.start();
                } else {
                    Toast.makeText(RegisterActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }
}
