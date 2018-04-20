package www.diandianxing.com.diandianxing.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.Map;

import www.diandianxing.com.diandianxing.DianDianActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Loginsuccess;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.ClickFilter;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.MyUtils;
import www.diandianxing.com.diandianxing.utils.SendSmsTimerUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class BandphoneActivity extends BaseActivity {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView logio;
    private RelativeLayout relative;
    private ImageView register_l;
    private EditText bound_phone;
    private ImageView login_lpwd;
    private EditText register_yanzhen;
    private TextView huoqu;
    private TextView login_sso;
    private String type;
    private String openid;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        MyContants.windows(this);
        setContentView(R.layout.activity_bound);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        logio = (ImageView) findViewById(R.id.logio);
        relative = (RelativeLayout) findViewById(R.id.relative);
        register_l = (ImageView) findViewById(R.id.register_l);
        bound_phone = (EditText) findViewById(R.id.bound_phone);
        login_lpwd = (ImageView) findViewById(R.id.login_lpwd);
        register_yanzhen = (EditText) findViewById(R.id.register_yanzhen);
        huoqu = (TextView) findViewById(R.id.huoqu);
        login_sso = (TextView) findViewById(R.id.login_sso);
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*
           得到数据
         */
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        openid = intent.getStringExtra("openid");
        name = intent.getStringExtra("name");
        //获取验证码
        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClickFilter.isFastClick()){
                    getcode();
                }

            }
        });
        //登录
        login_sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    submit();


            }
        });
    }
    public void getcode() {
        if (TextUtils.isEmpty(bound_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!MyUtils.isMobileNO(bound_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;

        }
        //进行网络请求
        HttpParams httpParams=new HttpParams();
        httpParams.put("mobile",bound_phone.getText().toString().trim());
        OkGo.<String>post(MyContants.BASEURL+"s=Sms/sendSms")
                .params(httpParams)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj=new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            String datas = jsonobj.getString("datas");
                            if(code==200){
                                Toast.makeText(BandphoneActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                SendSmsTimerUtils sendSmsTimerUtils=new SendSmsTimerUtils(60*1000,1000,huoqu);
                                sendSmsTimerUtils.start();
                            }
                            else if(code==404){
                             ToastUtils.showShort(BandphoneActivity.this,datas);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    private void submit() {
        if (TextUtils.isEmpty(bound_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MyUtils.isMobileNO(bound_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(register_yanzhen.getText().toString())) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something

        gomain();
    }
    private void gomain() {
        HttpParams harr=new HttpParams();
        String trim = register_yanzhen.getText().toString().trim();
        harr.put("mobile",bound_phone.getText().toString());
        harr.put("code",trim);
        OkGo.<String>post(MyContants.BASEURL+"s=Sms/verify")
                .params(harr)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonb=new JSONObject(body);
                            int code = jsonb.getInt("code");
                            String datas = jsonb.getString("datas");
                            if(code==200){
                                  /*
                       注册
                     */
                                Intent intent=new Intent(BandphoneActivity.this,AgreementActivity.class);
                                intent.putExtra("contact",bound_phone.getText().toString().trim());
                                intent.putExtra("password","");
                                intent.putExtra("register_or_bind","bind");
                                intent.putExtra("type",type);
                                intent.putExtra("openid",openid);
                                intent.putExtra("name",name);
                                startActivity(intent);
                            }
                            else if(code==404){
                               ToastUtils.showShort(BandphoneActivity.this,datas);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    private void bind() {

        Map<String,String> map=new ArrayMap<>();
        map.put("contact",bound_phone.getText().toString().trim());
        map.put("type",type);
        map.put("openid",openid);
        map.put("name",name);
        Log.d("TAGSS",bound_phone.getText().toString().trim()+"leixing:"+type
        +"id："+openid+"名字"+name);
        RetrofitManager.post(MyContants.BASEURL +"s=Login/threeRegister", map, new BaseObserver1<Loginsuccess>("") {

            @Override
            public void onSuccess(Loginsuccess result, String tag) {
                  if(result.getCode()==200){
                      SpUtils.putString(BandphoneActivity.this,"userid",result.getDatas().getId());
                      SpUtils.putString(BandphoneActivity.this,"token",result.getDatas().getToken());
                      SpUtils.putInt(BandphoneActivity.this, "guid", 1);
                      //绑定成功跳登录
                       Intent intent=new Intent(BandphoneActivity.this, DianDianActivity.class);
                        startActivity(intent);

                  }
                else if(result.getCode()==404){
                      ToastUtils.show(BandphoneActivity.this,"手机号已注册",1);
                  }

            }

            @Override
            public void onFailed(int code,String data) {

               ToastUtils.show(BandphoneActivity.this,data,1);
            }
        });

    }
}
