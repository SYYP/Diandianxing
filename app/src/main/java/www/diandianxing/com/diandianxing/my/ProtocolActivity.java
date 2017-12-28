package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ProtocolActivity extends BaseActivity {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_userxieyi);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         zhong.setText("用户协议");
    }

}
