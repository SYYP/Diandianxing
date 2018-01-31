package www.diandianxing.com.diandianxing.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.Login.LoginActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Alternamebean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AlterpwdActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private EditText alter_oldpwd;
    private EditText alter_pwd;
    private EditText alter_okpwd;
    private TextView alter_pause;
    private TextView alter_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_uppwd);
        initView();

    }

    private void network() {
        Map<String,String> map=new HashMap<>();
          map.put("uid", SpUtils.getString(this,"userid",null));
          map.put("token",SpUtils.getString(this,"token",null));
          map.put("old_password",alter_oldpwd.getText().toString().trim());
          map.put("password",alter_pwd.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL + "s=User/updatePwd", map, new BaseObserver1<Alternamebean>("") {
            @Override
            public void onSuccess(Alternamebean result, String tag) {
                if(result.getCode()==200){
                    ToastUtils.showShort(AlterpwdActivity.this,"修改成功,请重新登录");
                    Intent intent=new Intent(AlterpwdActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(int code,String data) {

            }
        });
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        alter_oldpwd = (EditText) findViewById(R.id.alter_oldpwd);
        alter_pwd = (EditText) findViewById(R.id.alter_pwd);
        alter_okpwd = (EditText) findViewById(R.id.alter_okpwd);
        alter_pause = (TextView) findViewById(R.id.alter_pause);
        alter_ok = (TextView) findViewById(R.id.alter_ok);
        iv_callback.setOnClickListener(this);
        alter_ok.setOnClickListener(this);
        alter_pause.setOnClickListener(this);
        zhong.setText("修改密码");
    }

    private void submit() {
        // validate
        String oldpwd = alter_oldpwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldpwd)) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = alter_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String okpwd = alter_okpwd.getText().toString().trim();
        if (TextUtils.isEmpty(okpwd)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(okpwd.equals(pwd))){
            ToastUtils.showShort(this,"两次密码不一致");
            return;
        }

        // TODO validate success, do something
        network();//加载网络请求

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //取消
            case R.id.alter_pause:
                finish();
                break;
            //确认
            case R.id.alter_ok:
                submit();

                break;
        }
    }
}
