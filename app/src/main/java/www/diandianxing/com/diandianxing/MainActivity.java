package www.diandianxing.com.diandianxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import www.diandianxing.com.diandianxing.main.KefuActivity;
import www.diandianxing.com.diandianxing.my.MessageActivity;
import www.diandianxing.com.diandianxing.my.MyActivityActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_my;
    private ImageView img_message;
    private RelativeLayout activity_main;
    private RelativeLayout relativeLayout;
    private ImageView iv_search;
    private ImageView iv_kefu;
    private ImageView iv_refresh;
    private ImageView iv_address;
    private LinearLayout linearLayout3;
    private ImageView iv_lock;
    private ImageView img_che;
    private LinearLayout real_gongxiang;
    private ImageView img_wei;
    private LinearLayout real_dianzi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        img_my = (ImageView) findViewById(R.id.img_my);
        img_message = (ImageView) findViewById(R.id.img_message);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        img_my.setOnClickListener(this);
        img_message.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(this);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        iv_kefu = (ImageView) findViewById(R.id.iv_kefu);
        iv_kefu.setOnClickListener(this);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        iv_refresh.setOnClickListener(this);
        iv_address = (ImageView) findViewById(R.id.iv_address);
        iv_address.setOnClickListener(this);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setOnClickListener(this);
        iv_lock = (ImageView) findViewById(R.id.iv_lock);
        iv_lock.setOnClickListener(this);
        img_che = (ImageView) findViewById(R.id.img_che);
        img_che.setOnClickListener(this);
        real_gongxiang = (LinearLayout) findViewById(R.id.real_gongxiang);
        real_gongxiang.setOnClickListener(this);
        img_wei = (ImageView) findViewById(R.id.img_wei);
        img_wei.setOnClickListener(this);
        real_dianzi = (LinearLayout) findViewById(R.id.real_dianzi);
        real_dianzi.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_address.setOnClickListener(this);
        iv_kefu.setOnClickListener(this);
        iv_lock.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        real_gongxiang.setOnClickListener(this);
        real_dianzi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //我的
            case R.id.img_my:
                Intent intent = new Intent(this, MyActivityActivity.class);
                startActivity(intent);
                break;
            //消息
            case R.id.img_message:
                Intent intent1 = new Intent(this, MessageActivity.class);
                startActivity(intent1);
                break;
            //搜索
            case R.id.iv_search:
                break;
            case R.id.iv_kefu:
                //客服
                showphotoDialog(Gravity.BOTTOM,R.style.Bottom_Top_aniamtion);
                break;
            case R.id.iv_lock:
                //扫码
                break;
            case R.id.iv_address:
                //定位
                break;
            case R.id.iv_refresh:
                //刷新
                break;
            case R.id.real_gongxiang:
                  img_che.setImageResource(R.drawable.img_gongbick);
                  img_wei.setImageResource(R.drawable.img_dianziwei);
                break;
            case R.id.real_dianzi:
                img_che.setImageResource(R.drawable.img_gongxiangfalse);
                img_wei.setImageResource(R.drawable.img_dian_true);
                break;
        }

    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_shouke)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        //违停
        dialog.getView(R.id.real_weiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent=new Intent(MainActivity.this, KefuActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //故障
        dialog.getView(R.id.real_guzhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        //取消
        dialog.getView(R.id.real_qita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //其他
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
