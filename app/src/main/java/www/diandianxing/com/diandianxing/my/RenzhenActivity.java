package www.diandianxing.com.diandianxing.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Altername;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.PhotoUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;
import www.diandianxing.com.diandianxing.utils.UploadUtil;

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
    private Uri imageUri;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private Uri cropImageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private Handler handler=new Handler();
    private RelativeLayout ren_real;
    private ImageView pho_idcard;
    private TextView text_miaoshu;
    private String phoss;

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
        ren_real = (RelativeLayout) findViewById(R.id.ren_real);
        pho_idcard = (ImageView) findViewById(R.id.pho_idcard);
        text_miaoshu = (TextView) findViewById(R.id.text_miaoshu);
        zhong.setText("实名认证");
        iv_callback.setOnClickListener(this);
        yangzheng_ok.setOnClickListener(this);
        paizhao.setOnClickListener(this);
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
                photowork(phoss);
                break;
            case R.id.paizhao:
                showphotoDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
        }
    }
   /*
     选择照片上传方式
    */
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
        dialog.getView(R.id.tv_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选取
                autoObtainStoragePermission();
                dialog.dismiss();
            }
        });
        //取消
        dialog.getView(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

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
                    imageUri = FileProvider.getUriForFile(RenzhenActivity.this, "com.xykj.customview.fileproviders", fileUri);//通过FileProvider创建一个content类型的Uri
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
                            imageUri = FileProvider.getUriForFile(RenzhenActivity.this, "com.xykj.customview.fileproviders", fileUri);//通过FileProvider创建一个content类型的Uri
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


                //    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));

                        if (Build.VERSION.SDK_INT >=24)
                            newUri = FileProvider.getUriForFile(this, "com.xykj.customview.fileproviders", new File(newUri.getPath()));
                       // PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                   // Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    phoss = cropImageUri.toString();
//                    //sp存头像
//                    SpUtils.putString(RenzhenActivity.this,"photos",s);
//                    EventMessage eventMessage = new EventMessage("personphoto");
//                    EventBus.getDefault().postSticky(eventMessage);
//                    KLog.a(cropImageUri);
//                    //将URl上传到服务器

                    //     ToastUtils.showShort(PersoninforActivity.this,cropImageUri.toString());
//                    if (bitmap != null) {
//
//                        showImages(bitmap);
//                    }
                    paizhao.setVisibility(View.GONE);
                    pho_idcard.setVisibility(View.VISIBLE);
                    text_miaoshu.setVisibility(View.GONE);
                    Glide.with(RenzhenActivity.this).load(phoss).into(pho_idcard);

                    break;
            }
        }
    }
    private void photowork(String s) {

        final Thread thred = new Thread(new Runnable() {


            private String s;

            @Override
            public void run() {
                Map<String,File > files = new HashMap<>();
                files.put("file", fileCropUri);
                Map<String,String> map=new HashMap<>();
                map.put("uid",SpUtils.getString(Myapplication.getApplication(),"userid",null));
                map.put("token",SpUtils.getString(RenzhenActivity.this,"token",null));
                map.put("uname",yanzhneg_name.getText().toString().trim());
                map.put("ident_num",yanzhneg_hao.getText().toString().trim());
                /*
                  图片上传
                 */
                s = UploadUtil.uploadFile(map,files, MyContants.BASEURL + "s=User/updateHeadImg");
                Gson gson = new Gson();
                final Altername altername = gson.fromJson(s, Altername.class);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        KLog.a("Tag", s);
                        // ToastUtils.showShort(PersoninforActivity.this, s);
                    }
                });

            }
        });
        thred.start();
    }

}
