package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class CredibilityActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView xinyong_fen;
    private RelativeLayout real;
    private ImageView shui;
    private TextView text_bian;
    private TextView text_date;
    private TextView text_fen;
    private TextView look_lishi;
    private TextView text_jiedu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinyong);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        xinyong_fen = (TextView) findViewById(R.id.xinyong_fen);
        real = (RelativeLayout) findViewById(R.id.real);
        shui = (ImageView) findViewById(R.id.shui);
        text_bian = (TextView) findViewById(R.id.text_bian);
        text_date = (TextView) findViewById(R.id.text_date);
        text_fen = (TextView) findViewById(R.id.text_fen);
        look_lishi = (TextView) findViewById(R.id.look_lishi);
        text_jiedu = (TextView) findViewById(R.id.text_jiedu);
        iv_callback.setOnClickListener(this);
        text_jiedu.setOnClickListener(this);
        look_lishi.setOnClickListener(this);
        zhong.setText("我的信用");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //规则解读
            case R.id.text_jiedu:
Intent intent1=new Intent(this,JiedudetailActivity.class);
                startActivity(intent1);
                break;
            //查看历史
            case R.id.look_lishi:
                Intent intent=new Intent(this, HistorycreditActivity.class);
                startActivity(intent);
                break;
        }
    }
}
