package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<LocalMedia> selectList = new ArrayList<>();
    private String cutPath;
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
        if(file==null){
            ToastUtils.showShort(this,"请上传照片");
            return;
        }
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
                photoworks();
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
                requestCamera();
                dialog.dismiss();
            }
        });
        //相册
        dialog.getView(R.id.tv_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选取
                //相册中选择图片的action
                requestPhoto();
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
    /*
     调用相册
    */
    private void requestPhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(list)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    /*
    调用相机
   */
    private void requestCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
//                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /*
       图片上传
     */
      private void photoworks(){
          HttpParams httpParams=new HttpParams();
          Log.d("file",file+"");
          httpParams.put("file", file);
          httpParams.put("uid",SpUtils.getString(Myapplication.getApplication(),"userid",null));
          httpParams.put("token",SpUtils.getString(RenzhenActivity.this,"token",null));
          httpParams.put("uname",yanzhneg_name.getText().toString().trim());
          httpParams.put("ident_num",yanzhneg_hao.getText().toString().trim());
          OkGo
                  .<String>post(MyContants.BASEURL+"s=User/submitAuthInfo")
                  .params(httpParams)
                  .tag(this)
                  .execute(new StringCallback() {
                      @Override
                      public void onSuccess(Response<String> response) {
                          String body = response.body();
                          try {
                              JSONObject jsonobj=new JSONObject(body);
                              int code = jsonobj.getInt("code");
                              String datas = jsonobj.getString("datas");
                              if(code==200){
                                  ToastUtils.show(RenzhenActivity.this,"上传成功",1);
                       /*
                          记录认证状态
                        */
                                  Gson gson = new Gson();
                                  Photobean photobean = gson.fromJson(body, Photobean.class);
                                  SpUtils.putString(RenzhenActivity.this,"IDcard",photobean.getDatas().getRealType());
                                  //调用eventbus刷新状态
                                  EventMessage message=new EventMessage("idcard");
                                  EventBus.getDefault().postSticky(message);
                                  finish();
                              }
                              else {
                                  ToastUtils.show(RenzhenActivity.this,datas,0);
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  });
      }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    paizhao.setVisibility(View.GONE);
                    text_miaoshu.setVisibility(View.GONE);
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    cutPath = selectList.get(0).getPath();
                    file = new File(Environment.getExternalStorageDirectory()+"/sfz.jpg");
                    File cjfile= new File(cutPath);



                    Log.e("TAG","文件目录：："+cutPath);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        Bitmap bitmap=BitmapFactory.decodeStream(new FileInputStream(cjfile));
                        Log.e("TAG","bitmap对象：："+bitmap);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,70,fileOutputStream);
                        Glide.with(this).load(this.cutPath).into(pho_idcard);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
