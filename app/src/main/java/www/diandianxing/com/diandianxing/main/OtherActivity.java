package www.diandianxing.com.diandianxing.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Gubackbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.set.Feedback;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class OtherActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private EditText feel_edtext;
    private TextView login_sso;
    private TextView tv_shengyu;

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
        feel_edtext = (EditText) findViewById(R.id.feel_edtext);
        login_sso = (TextView) findViewById(R.id.login_sso);
        tv_shengyu = (TextView) findViewById(R.id.tv_shengyu);
        zhong.setText("其他");

        feel_edtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                int length = trim.length();
                int i = 140 - length;
                tv_shengyu.setText(i+"/140");
            }
        });
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login_sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();


            }


        });


    }
    private void network() {
        HttpParams httpar=new HttpParams();
        httpar.put("uid", SpUtils.getString(this, "userid", null));
        httpar.put("token", SpUtils.getString(this, "token", null));
        httpar.put("type",3);
        httpar.put("content", feel_edtext.getText().toString());

        OkGo.<String>post(MyContants.BASEURL+"s=Bike/feedback")
                .tag(this)
                .params(httpar)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj=new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            if(code==200){
                                ToastUtils.showShort(OtherActivity.this,"提交成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void submit() {
        // validate
        String edtext = feel_edtext.getText().toString().trim();
        if (TextUtils.isEmpty(edtext)) {
            Toast.makeText(this, "请输入反馈消息", Toast.LENGTH_SHORT).show();
            return;
        }
        network();
        // TODO validate success, do something


    }
}
