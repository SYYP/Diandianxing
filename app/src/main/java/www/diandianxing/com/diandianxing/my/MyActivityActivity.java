package www.diandianxing.com.diandianxing.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import www.diandianxing.com.diandianxing.Login.BangdingActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.set.SetActivity;
import www.diandianxing.com.diandianxing.set.WalletActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.ImageViewPuls;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyActivityActivity extends BangdingActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private ImageView you;
    private RelativeLayout rela;
    private ImageViewPuls my_photo;
    private TextView diandianxing;
    private TextView my_wallet;
    private RelativeLayout real_myallet;
    private TextView my_car;
    private RelativeLayout real_car;
    private TextView my_yaoqing;
    private RelativeLayout real_yaoqing;
    private TextView my_kefu;
    private RelativeLayout real_qq,real_weichat;
    private TextView my_xingyong;
    private String nickname;
    private String photo;
    private String fenshu;
    boolean bool;
    private TextView qq_text;
    private TextView wei_text;
    private RelativeLayout real_kefu;

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
        real_kefu= (RelativeLayout) findViewById(R.id.real_kefu);
        my_photo = (ImageViewPuls) findViewById(R.id.my_photo);
        diandianxing = (TextView) findViewById(R.id.diandianxing);
        my_wallet = (TextView) findViewById(R.id.my_wallet);
        real_myallet = (RelativeLayout) findViewById(R.id.real_myallet);
        my_car = (TextView) findViewById(R.id.my_car);
        real_car = (RelativeLayout) findViewById(R.id.real_car);
        my_yaoqing = (TextView) findViewById(R.id.my_yaoqing);
        real_yaoqing = (RelativeLayout) findViewById(R.id.real_yaoqing);
//        my_kefu = (TextView) findViewById(R.id.my_kefu);
        real_qq = (RelativeLayout) findViewById(R.id.real_qq);
        real_weichat = (RelativeLayout) findViewById(R.id.real_wei);
        my_xingyong = (TextView) findViewById(R.id.my_xinyong);
        qq_text = (TextView) findViewById(R.id.qq_text);
        wei_text = (TextView) findViewById(R.id.wei_text);
        my_photo.setOnClickListener(this);
        real_myallet.setOnClickListener(this);
        real_car.setOnClickListener(this);
        real_yaoqing.setOnClickListener(this);
        real_kefu.setOnClickListener(this);
        iv_callback.setOnClickListener(this);
        my_xingyong.setOnClickListener(this);
        you.setOnClickListener(this);
        real_qq.setOnClickListener(this);
        real_weichat.setOnClickListener(this);
        Intent intent = getIntent();
        fenshu = intent.getStringExtra("fenshu");
        String paiphoto = SpUtils.getString(this, "paiphoto", null);
        String nickname = SpUtils.getString(this, "nickname", null);
        if(paiphoto!=null&&paiphoto.length()>0) {
            //加载图片
        RequestOptions options = new RequestOptions()
                .error(R.drawable.img_motou)
                .priority(Priority.NORMAL);
        Glide.with(this).load(MyContants.PHOTO+paiphoto).apply(options).into(my_photo);
    }

    if(nickname!=null) {
        diandianxing.setText(nickname);
    }
        my_xingyong.setText("信用分 "+fenshu);
        //添加数据
        String qq_zhi = SpUtils.getString(MyActivityActivity.this, "qq_zhi", "");
        Log.d("Tags",qq_zhi);
        String wei_zhi = SpUtils.getString(MyActivityActivity.this, "wei_zhi", "");
         if(!(TextUtils.isEmpty(qq_zhi))){
             qq_text.setText("已绑定");
             qq_text.setTextColor(getResources().getColor(R.color.red));
         }
            if(!(TextUtils.isEmpty(wei_zhi))){
             wei_text.setText("已绑定");
             wei_text.setTextColor(getResources().getColor(R.color.red));
         }



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
        else if(eventMessage.getMsg().equals("sucess")){
            String types = SpUtils.getString(MyActivityActivity.this, "types", null);
            if(types.equals("1")){
                wei_text.setText("已绑定");
                wei_text.setTextColor(getResources().getColor(R.color.red));
            }
            else if(types.equals("2")){
                qq_text.setText("已绑定");
                qq_text.setTextColor(getResources().getColor(R.color.red));
            }



        }
        else if(eventMessage.getMsg().equals("personphoto")){
            String photos = SpUtils.getString(this, "paiphoto", null);
            if(photos.length()>0&&photos!=null) {
                //加载图片
                RequestOptions options = new RequestOptions()
                        .error(R.drawable.img_motou)
                        .priority(Priority.NORMAL);
                Glide.with(this).load(MyContants.PHOTO+photos).apply(options).into(my_photo);
            }


        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回
            case R.id.iv_callback:
//                //通知首页刷新数据
//                EventMessage eventMessage=new EventMessage("mainetwork");
//                 EventBus.getDefault().postSticky(eventMessage);
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
//            //我的客服
            case R.id.real_kefu:

                networkmoney();


                break;
            //我的行程
            case R.id.real_car:
                Intent intent5=new Intent(this,JourActivity.class);
                startActivity(intent5);
                break;
            case R.id.real_qq://绑定QQ
                if(qq_text.getText().toString().equals("未绑定")){
                    BangByQQ(this);
                }
                else if(qq_text.getText().toString().equals("已绑定")){
                    jiebangDialog(Gravity.CENTER,R.style.Alpah_aniamtion,2);
                }
                break;
            case R.id.real_wei://绑定微信
                if(wei_text.getText().toString().equals("未绑定")){
                    BangWeiXin(this);
                }
                else if(wei_text.getText().toString().equals("已绑定")){
                    jiebangDialog(Gravity.CENTER,R.style.Alpah_aniamtion,1);
                }
                break;


        }
    }

    private void networkmoney() {
        OkGo.<String>get(MyContants.BASEURL +"s=Login/aboutContact")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String body = response.body();
                        Log.d("TAG","关于"+body);
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            String datas = jsonobj.getString("datas");
                            if (code == 200) {

                                JSONObject jo = new JSONObject(datas);

                                String tel=jo.getString("service_tel");
                                showphotoDialog(Gravity.BOTTOM,R.style.Bottom_Top_aniamtion,tel);


                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
//        Map<String,String> map=new HashMap<>();
//         map.put("uid",SpUtils.getString(this,"userid",null));
//         map.put("token",SpUtils.getString(this,"token",null));
//        RetrofitManager.post(MyContants.BASEURL+"s=Payment/refundDeposit", map, new BaseObserver1<Tuikuanbean>("") {
//            @Override
//            public void onSuccess(Tuikuanbean result, String tag) {
//                if(result.getCode()==200){
//
//                }
//            }
//
//            @Override
//            public void onFailed(int code, String data) {
//
//            }
//        });
//    }
    }

    private void jiebangDialog(int grary, int animationStyle, final int type) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_jilu)
                //设置dialogpadding
                .setPaddingdp(0, 10, 0, 10)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        TextView tv_canel = dialog.getView(R.id.tv_canel);
        tv_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.close();
            }
        });
        TextView tv_yes = dialog.getView(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //网络请求上传数据
                jiechu(type);
                dialog.dismiss();

            }


        });
    }
       //解除三方绑定
     public void jiechu(final int tye){
         HttpParams httpParams=new HttpParams();
         httpParams.put("uid",SpUtils.getString(this,"userid",null));
         httpParams.put("token",SpUtils.getString(this,"token",null));
         httpParams.put("type",tye);
         OkGo.<String>post(MyContants.BASEURL+"s=User/threeRemove")
                 .tag(this)
                 .params(httpParams)
                 .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         String body = response.body();
                         try {
                             JSONObject jsobobj=new JSONObject(body);
                             int code = jsobobj.getInt("code");
                             if(code==200){
                                 ToastUtils.showShort(MyActivityActivity.this,"解绑成功");
                                  if(tye==1){
                                    wei_text.setText("未绑定");
                                      wei_text.setTextColor(getResources().getColor(R.color.black_san));

                                  }
                                 else if(tye==2){
                                      qq_text.setText("未绑定");
                                      qq_text.setTextColor(getResources().getColor(R.color.black_san));
                                  }

                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 });
     }
    private void showphotoDialog(int grary, int animationStyle, final String tel) {
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


        TextView text_kefu=dialog.getView(R.id.text_kefu);
        text_kefu.setText(tel);
        //拍照
        dialog.getView(R.id.text_kefu).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //相机选取

                dialog.dismiss();
                if(checkSelfPermission("android.permission.CALL_PHONE")!= PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);

                }

                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){

            for (int grant : grantResults) {
                // 判断是否所有的权限都已经授予了
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyActivityActivity.this,"请在设置里面设置权限",Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
//        EventMessage eventMessage=new EventMessage("mainetwork");
//        EventBus.getDefault().postSticky(eventMessage);
        super.onBackPressed();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
