package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.set.SetActivity;
import www.diandianxing.com.diandianxing.set.WalletActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.CircleImageView;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyActivityActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private ImageView you;
    private RelativeLayout rela;
    private CircleImageView my_photo;
    private TextView diandianxing;
    private TextView my_wallet;
    private RelativeLayout real_myallet;
    private TextView my_car;
    private RelativeLayout real_car;
    private TextView my_yaoqing;
    private RelativeLayout real_yaoqing;
    private TextView my_kefu;
    private RelativeLayout real_kefu;
    private TextView my_xingyong;
    private String nickname;
    private String photo;
    private String fenshu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        EventBus.getDefault().register(this);
        MyContants.windows(this);
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_my);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (ImageView) findViewById(R.id.you);
        rela = (RelativeLayout) findViewById(R.id.rela);
        my_photo = (CircleImageView) findViewById(R.id.my_photo);
        diandianxing = (TextView) findViewById(R.id.diandianxing);
        my_wallet = (TextView) findViewById(R.id.my_wallet);
        real_myallet = (RelativeLayout) findViewById(R.id.real_myallet);
        my_car = (TextView) findViewById(R.id.my_car);
        real_car = (RelativeLayout) findViewById(R.id.real_car);
        my_yaoqing = (TextView) findViewById(R.id.my_yaoqing);
        real_yaoqing = (RelativeLayout) findViewById(R.id.real_yaoqing);
        my_kefu = (TextView) findViewById(R.id.my_kefu);
        real_kefu = (RelativeLayout) findViewById(R.id.real_kefu);
        my_xingyong = (TextView) findViewById(R.id.my_xinyong);
        my_photo.setOnClickListener(this);
        real_myallet.setOnClickListener(this);
        real_car.setOnClickListener(this);
        real_yaoqing.setOnClickListener(this);
        real_kefu.setOnClickListener(this);
        iv_callback.setOnClickListener(this);
        my_xingyong.setOnClickListener(this);
        you.setOnClickListener(this);
        Intent intent = getIntent();
        fenshu = intent.getStringExtra("fenshu");
        String paiphoto = SpUtils.getString(this, "paiphoto", null);
        String nickname = SpUtils.getString(this, "nickname", null);
        Glide.with(this).load(paiphoto).into(my_photo);
        if(nickname.length()>0&&nickname!=null) {
            diandianxing.setText(nickname);
        }
        my_xingyong.setText("信用分 "+fenshu);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventMessage eventMessage) {
        if (eventMessage.getMsg().equals("myname")) {
            String name = SpUtils.getString(this, "nickname", null);
            diandianxing.setText(name);
        }
        else if(eventMessage.getMsg().equals("personphoto")){
            String photos = SpUtils.getString(this, "paiphoto", null);
            Glide.with(this).load(photos).into(my_photo);


        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回
            case R.id.iv_callback:
                finish();
                break;
            //设置
            case R.id.you:
                Intent intent=new Intent(this, SetActivity.class);
                startActivity(intent);
                break;
            //照片
            case R.id.my_photo:
                Intent intent1=new Intent(this,PersonActivity.class);
                startActivity(intent1);
                break;
            //信用
            case R.id.my_xinyong:
                Intent intent2=new Intent(this,CredibilityActivity.class);
                startActivity(intent2);
                break;
            //我的钱包
            case R.id.real_myallet:
                Intent intent3=new Intent(this,WalletActivity.class);
                startActivity(intent3);
                break;
            //我的邀请
            case R.id.real_yaoqing:
                Intent intent4=new Intent(this,ShareActivity.class);
                startActivity(intent4);
                break;
            //我的客服
            case R.id.real_kefu:
                showphotoDialog(Gravity.BOTTOM,R.style.Bottom_Top_aniamtion);
                break;
            //我的行程
            case R.id.real_car:
                Intent intent5=new Intent(this,JourActivity.class);
                startActivity(intent5);
                break;


        }
    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_kefu)
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
        //拍照
        dialog.getView(R.id.text_kefu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机选取

                dialog.dismiss();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "010-1234563245"));
                //跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });

        dialog.show();
    }
     /*
       监听返回键
      */

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
