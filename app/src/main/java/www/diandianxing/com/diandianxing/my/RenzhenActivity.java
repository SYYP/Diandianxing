package www.diandianxing.com.diandianxing.my;

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

public class RenzhenActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView paizhao;
    private TextView set_lpwd;
    private EditText yanzhneg_name;
    private TextView set_lpwds;
    private EditText yanzhneg_hao;
    private TextView yangzheng_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_certification);
        initView();
    }
    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        paizhao = (ImageView) findViewById(R.id.paizhao);
        set_lpwd = (TextView) findViewById(R.id.set_lpwd);
        yanzhneg_name = (EditText) findViewById(R.id.yanzhneg_name);
        set_lpwds = (TextView) findViewById(R.id.set_lpwds);
        yanzhneg_hao = (EditText) findViewById(R.id.yanzhneg_hao);
        yangzheng_ok = (TextView) findViewById(R.id.yangzheng_ok);
        zhong.setText("实名认证");
        iv_callback.setOnClickListener(this);
        yangzheng_ok.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String name = yanzhneg_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入名字", Toast.LENGTH_SHORT).show();
            return;
        }

        String hao = yanzhneg_hao.getText().toString().trim();
        if (TextUtils.isEmpty(hao)) {
            Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //保存
            case R.id.yangzheng_ok:
                break;
        }
    }
}
