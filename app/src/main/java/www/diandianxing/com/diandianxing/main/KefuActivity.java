package www.diandianxing.com.diandianxing.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Gubackbean;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.PhotoUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;
import www.diandianxing.com.diandianxing.utils.UploadUtil;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class KefuActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView img_sao;
    private ImageView img_increate;
    private LinearLayout liner;
    private EditText fu_edtext;
    private LinearLayout liner1;
    private TextView fu_tijiao;
    private Uri imageUri;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private RelativeLayout real_renzheng;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private Uri cropImageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private TextView zishu;
    private TextView tv_bianhao;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyContants.windows(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        img_sao = (ImageView) findViewById(R.id.img_sao);
        img_increate = (ImageView) findViewById(R.id.img_increate);
        liner = (LinearLayout) findViewById(R.id.liner);
        fu_edtext = (EditText) findViewById(R.id.fu_edtext);
        liner1 = (LinearLayout) findViewById(R.id.liner1);
        fu_tijiao = (TextView) findViewById(R.id.fu_tijiao);
        zishu = (TextView) findViewById(R.id.zishu);
        tv_bianhao = (TextView) findViewById(R.id.tv_bianhao);
        zhong.setText("客户服务");
        img_sao.setOnClickListener(this);
        img_increate.setOnClickListener(this);
        iv_callback.setOnClickListener(this);
        fu_tijiao.setOnClickListener(this);
        fu_edtext.addTextChangedListener(new TextWatcher() {
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
                zishu.setText(i+"/140");
            }
        });
    }

    private void submit() {
        // validate
        String edtext = fu_edtext.getText().toString().trim();
        if (TextUtils.isEmpty(edtext)) {
            Toast.makeText(this, "edtext不能为空", Toast.LENGTH_SHORT).show();
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
            //添加照片
            case R.id.img_increate:
                showphotoDialog(Gravity.BOTTOM,R.style.Bottom_Top_aniamtion);
                break;
            //扫一扫
            case R.id.img_sao:
                /*
                  跳扫码
                 */
                Intent intent=new Intent(this,ZiXingActivity.class);
                startActivity(intent);



                break;
            //提交
            case R.id.fu_tijiao:
                network();
                finish();
                break;
        }
    }

    private void network() {


            final Thread thred = new Thread(new Runnable() {
                private Gubackbean gubackbean;
                private String s1;

                @Override
                public void run() {
                    Map<String,String> map=new HashMap<>();
                    map.put("uid", SpUtils.getString(KefuActivity.this,"userid",null));
                    map.put("token",SpUtils.getString(KefuActivity.this,"token",null));
                    map.put("identnum",tv_bianhao.getText().toString());
                    Map<String,File> maps=new HashMap<>();
                    if(fileCropUri!=null) {
                        maps.put("file", fileCropUri);
                    }
                    s1 = UploadUtil.uploadFile(map, maps, MyContants.BASEURL + "s=Bike/feedback");
                    Gson gson=new Gson();
                    gubackbean = gson.fromJson(s1, Gubackbean.class);


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            KLog.a("Tag", s1);
                            if(gubackbean.getCode()==200){
                                ToastUtils.show(KefuActivity.this,"提交成功",1);
                                finish();

                            }
                            // ToastUtils.showShort(PersoninforActivity.this, s);
                            //Glide.with(PersonActivity.this).load(MyContants.FILENAME+datas.getHeadImageUrl()).into(per_pho);
                        }
                    });

                }
            });
            thred.start();
        }





    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    //调用相机
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= 24)
                    imageUri = FileProvider.getUriForFile(this, "com.xykj.customview.fileproviders", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(this, "设备没有SD卡！");
            }


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= 24)
                            imageUri = FileProvider.getUriForFile(this, "com.xykj.customview.fileproviders", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }
    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);


                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    String s = cropImageUri.toString();
                    //sp存头像
//                    SpUtils.putString(PersoninforActivity.this,"photos",s);
//                    EventMessage eventMessage = new EventMessage("ppp");
//                    EventBus.getDefault().postSticky(eventMessage);
//                    KLog.a(cropImageUri);
//                    //将URl上传到服务器
//                    photowork();
                    //     ToastUtils.showShort(PersoninforActivity.this,cropImageUri.toString());
                    if (bitmap != null) {

                        showImages(bitmap);
                    }
                    break;
            }
        }
    }
    private void showImages(Bitmap bitmap) {
        //设置图片到页面
        img_increate.setImageBitmap(bitmap);
        //  String s = bitmaptoString(bitmap);

    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_photo)
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
        dialog.getView(R.id.tv_paizhao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机选取
                autoObtainCameraPermission();
                dialog.dismiss();
            }
        });
        //相册
        View view = dialog.getView(R.id.tv_local);
      TextView tv_loal=  view.findViewById(R.id.tv_local);
        tv_loal.setVisibility(View.GONE);
        View view1=dialog.getView(R.id.pho_view);
        View vi = view1.findViewById(R.id.pho_view);
        vi.setVisibility(View.GONE);
        //取消
        dialog.getView(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventMessage eventMessage) {
        //扫码成功后刷新主页面
        if (eventMessage.getMsg().equals("zxing")) {
            String bikenum = SpUtils.getString(this, "bikenum", null);
            tv_bianhao.setText(bikenum);
        }
    }
}
