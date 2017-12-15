package www.diandianxing.com.diandianxing.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

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
        iv_callback.setOnClickListener(this);
        forget_ok.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String phone = forget_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String yanzheng = forget_yanzheng.getText().toString().trim();
        if (TextUtils.isEmpty(yanzheng)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newpwd = forget_newpwd.getText().toString().trim();
        if (TextUtils.isEmpty(newpwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String okpwd = forget_okpwd.getText().toString().trim();
        if (TextUtils.isEmpty(okpwd)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.forget_ok:
                break;
        }
    }
}
