package www.diandianxing.com.diandianxing.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Forgetbean;
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

public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private EditText forget_phone;
    private EditText forget_yanzheng;
    private EditText forget_newpwd;
    private EditText forget_okpwd;
    private TextView forget_ok;
    private TextView forget_huoqu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_forgetpwd);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        forget_phone = (EditText) findViewById(R.id.forget_phone);
        forget_yanzheng = (EditText) findViewById(R.id.forget_yanzheng);
        forget_newpwd = (EditText) findViewById(R.id.forget_newpwd);
        forget_okpwd = (EditText) findViewById(R.id.forget_okpwd);
        forget_ok = (TextView) findViewById(R.id.forget_ok);
        forget_huoqu = (TextView) findViewById(R.id.forget_huoqu);
        iv_callback.setOnClickListener(this);
        forget_ok.setOnClickListener(this);
        forget_huoqu.setOnClickListener(this);
        zhong.setText("忘记密码");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.forget_ok:
                submit();
                break;
            case R.id.forget_huoqu:
                getcode();
                break;
        }
    }
    public void getcode() {
        if (TextUtils.isEmpty(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!MyUtils.isMobileNO(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;

        }
        //进行网络请求
        ArrayMap arrayMap=new ArrayMap<String,String>();
        arrayMap.put("mobile",forget_phone.getText().toString().trim());
        RetrofitManager.get(MyContants.BASEURL + "s=Sms/sendSms", arrayMap, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) {
                if (result.getCode() == 200) {
                    Toast.makeText(ForgetActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    SendSmsTimerUtils sendSmsTimerUtils=new SendSmsTimerUtils(60*1000,1000,forget_huoqu);
                    sendSmsTimerUtils.start();
                } else {
                    Toast.makeText(ForgetActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }
    private void submit() {
        if (TextUtils.isEmpty(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MyUtils.isMobileNO(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(forget_yanzheng.getText().toString())) {
            Toast.makeText(ForgetActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(forget_newpwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (forget_newpwd.getText().toString().length() < 5 || forget_newpwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }
         if(!(forget_newpwd.getText().toString().equals(forget_okpwd.getText().toString()))){
             ToastUtils.showShort(ForgetActivity.this,"两次密码不一致");
                return;
         }
        gomain();

    }

    private void gomain() {
        ArrayMap map=new ArrayMap<String,String>();
        //先验证手机号是否正确，然后在注册
        map.put("mobile",forget_phone.getText().toString());
        map.put("code",forget_yanzheng.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL + "s=Sms/verify", map, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) {

                if(result.getCode()==200){
                     /*
                       重新申请
                     */
                    forgetpwd();
                }
            }



            @Override
            public void onFailed(int code) {
                ToastUtils.show(ForgetActivity.this,"验证码错误",1);
            }
        });

    }
    private void forgetpwd() {

        Map<String,String> map=new HashMap<>();
        map.put("contact",forget_phone.getText().toString().trim());
        map.put("password",forget_newpwd.getText().toString().trim());

        RetrofitManager.post(MyContants.BASEURL + "s=Login/forgetPassword", map, new BaseObserver1<Forgetbean>("") {

            @Override
            public void onSuccess(Forgetbean result, String tag) {
                if(result.getCode()==200){
                    ToastUtils.showShort(ForgetActivity.this,"修改成功");
                    finish();
                }

            }

            @Override
            public void onFailed(int code) {

            }
        });
    }
}
