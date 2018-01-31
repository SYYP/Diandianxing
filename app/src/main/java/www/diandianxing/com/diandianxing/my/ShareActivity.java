package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.Login.UmshareActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ShareActivity extends UmshareActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView textView;
    private TextView share_wei;
    private TextView share_friend;
    private TextView share_qq;
    private TextView share_kongjian;
    private TextView look_xiangqing;
    private LinearLayout linearLayout2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_sharefriend);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        textView = (TextView) findViewById(R.id.textView);
        share_wei = (TextView) findViewById(R.id.share_wei);
        share_friend = (TextView) findViewById(R.id.share_friend);
        share_qq = (TextView) findViewById(R.id.share_qq);
        share_kongjian = (TextView) findViewById(R.id.share_kongjian);
        look_xiangqing = (TextView) findViewById(R.id.look_xiangqing);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        iv_callback.setOnClickListener(this);
        share_wei.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_kongjian.setOnClickListener(this);
        look_xiangqing.setOnClickListener(this);
        share_friend.setOnClickListener(this);
        zhong.setText("邀请好友");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.share_friend:
                SharebyWeixincenter(this);
                break;
            case R.id.share_kongjian:
                SharebyQzon(this);
                break;
            case R.id.share_wei:
                SharebyWeixin(this);
                break;
            case R.id.share_qq:
                SharebyQQ(this);
                break;
            case R.id.look_xiangqing:
                Intent intent=new Intent(this,XiangxiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
