package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Photobean;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.CropUtils;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.IdentityUtils;
import www.diandianxing.com.diandianxing.utils.MyContants;
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
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";
    private String basePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    private Uri cameraUri = Uri.fromFile(new File(CropUtils.getRandomFileName(basePath)));
    private File file;
    private RelativeLayout shenhe;
    private TextView shen_maioshu;
    private TextView shen_shen;
    private LinearLayout liner_s;
    private RelativeLayout real_bao;
    private RelativeLayout shen_chongxin;

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
        shenhe = (RelativeLayout) findViewById(R.id.shenke);
        shen_maioshu = (TextView) findViewById(R.id.shen_text_bai);
        shen_chongxin = (RelativeLayout) findViewById(R.id.shen_chongxin);
        shen_shen = (TextView) findViewById(R.id.shen_text);
        liner_s = (LinearLayout) findViewById(R.id.liner_s);
        real_bao = (RelativeLayout) findViewById(R.id.real_bao);
        zhong.setText("实名认证");
        iv_callback.setOnClickListener(this);
        yangzheng_ok.setOnClickListener(this);
        paizhao.setOnClickListener(this);
        ren_real.setOnClickListener(this);
        String iDcrad = SpUtils.getString(this, "IDcard", null);
        int ids= Integer.parseInt(iDcrad.trim());
        if(ids==0){
                shenhe.setVisibility(View.GONE);
            liner_s.setVisibility(View.VISIBLE);
            real_bao.setVisibility(View.VISIBLE);
         }
        else if(ids==1){
            shenhe.setVisibility(View.VISIBLE);
            liner_s.setVisibility(View.GONE);
            real_bao.setVisibility(View.GONE);
            shen_shen.setText("审核中");
            shen_maioshu.setVisibility(View.GONE);
            shen_chongxin.setVisibility(View.GONE);


        }
        else if(ids==2){
            shenhe.setVisibility(View.VISIBLE);
            liner_s.setVisibility(View.GONE);
            real_bao.setVisibility(View.GONE);
            shen_shen.setText("审核失败");
            shen_maioshu.setVisibility(View.VISIBLE);
            shen_chongxin.setVisibility(View.VISIBLE);
            shen_chongxin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shenhe.setVisibility(View.GONE);
                    liner_s.setVisibility(View.VISIBLE);
                    real_bao.setVisibility(View.VISIBLE);
                }
            });

        }

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
        boolean b = IdentityUtils.checkIDCard(yanzhneg_hao.getText().toString().trim());
        if(b==false){
              ToastUtils.show(this,"身份证号码错误",1);
            return;
        }
        photowork(phoss);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //保存
            case R.id.yangzheng_ok:

                submit();

                break;
            case R.id.ren_real:
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
                // 指定调用相机拍照后照片的储存路径
                CropUtils.startCamera(RenzhenActivity.this, cameraUri);
                dialog.dismiss();
            }
        });
        //相册
        dialog.getView(R.id.tv_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选取
                //相册中选择图片的action
                CropUtils.startAlbum(RenzhenActivity.this);
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
    private void photowork(String s) {
        final Thread thred = new Thread(new Runnable() {
            private String s;

            @Override
            public void run() {
                Map<String,File > files = new HashMap<>();
                 Log.d("file",file+"");
                files.put("file", file);
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
                Photobean photobean = gson.fromJson(s, Photobean.class);
                if(photobean.getCode()==200){
                    ToastUtils.show(RenzhenActivity.this,"上传成功",1);
                       /*
                          记录认证状态
                        */
                       SpUtils.putString(RenzhenActivity.this,"IDcard",photobean.getDatas().getRealType());
                    //调用eventbus刷新状态
                    EventMessage message=new EventMessage("idcard");
                    EventBus.getDefault().postSticky(message);
                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = CropUtils.onActivityResult(requestCode, resultCode, data, cameraUri, this);
        Log.d("sda",cameraUri+"");
        if (bitmap != null) {
            pho_idcard.setImageBitmap(bitmap);
            paizhao.setVisibility(View.GONE);
            text_miaoshu.setVisibility(View.GONE);
            file = saveBitmapFile(bitmap, FILE_PATH);
        }


    }
    public static File saveBitmapFile(Bitmap bitmap, String filepath){
        File file=new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
