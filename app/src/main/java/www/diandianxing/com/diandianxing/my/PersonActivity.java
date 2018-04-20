package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Photobean;
import www.diandianxing.com.diandianxing.bean.Shouyebean;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.CircleImageView;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;
import www.diandianxing.com.diandianxing.utils.UploadUtil;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener {
    private Shouyebean.DatasBean datas;
     List<String> list=new ArrayList<>();
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView set_pho;
    private ImageView back;
    private RelativeLayout real_pho;
    private TextView set_name;
    private ImageView back1;
    private RelativeLayout real_name;
    private TextView set_renzheng;
    private ImageView back2;
    private RelativeLayout real_renzheng;
    private CircleImageView per_pho;
    private TextView alter_name;
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
    private String s;
    private TextView id_card;
    private List<String> cameraList;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String cutPath;
    private File file;
    private BaseDialog dialog;
    private File filess = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_personmessage);
        initView();
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
            alter_name.setText(name);
        }
         else if(eventMessage.getMsg().equals("idcard")){
            String iDcrad = SpUtils.getString(this, "IDcard", null);
            int idc = Integer.parseInt(iDcrad.trim());
            if(idc==0){
                id_card.setText("未认证");
            }
            else if(idc==1){
                id_card.setText("审核中");
            }
            else if(idc==2){
                id_card.setText("审核不通过");
            }
            else if(idc==3){
                id_card.setText("已认证");
            }


        }
    }
    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        set_pho = (TextView) findViewById(R.id.set_pho);
        back = (ImageView) findViewById(R.id.back);
        real_pho = (RelativeLayout) findViewById(R.id.real_pho);
        set_name = (TextView) findViewById(R.id.set_name);
        back1 = (ImageView) findViewById(R.id.back1);
        real_name = (RelativeLayout) findViewById(R.id.real_name);
        set_renzheng = (TextView) findViewById(R.id.set_renzheng);
        back2 = (ImageView) findViewById(R.id.back2);
        alter_name = (TextView) findViewById(R.id.alter_name);
        per_pho = (CircleImageView) findViewById(R.id.person_pho);
        real_renzheng = (RelativeLayout) findViewById(R.id.real_renzheng);
        id_card = (TextView) findViewById(R.id.idcard_zhuangtai);
        iv_callback.setOnClickListener(this);
        real_pho.setOnClickListener(this);
        zhong.setText("个人信息");
        real_name.setOnClickListener(this);
        real_renzheng.setOnClickListener(this);
        String paizhao = SpUtils.getString(this, "paiphoto", null);
        if(paizhao!=null&&paizhao.length()>0) {
            RequestOptions options = new RequestOptions()
                    .error(R.drawable.img_motou)
                    .priority(Priority.NORMAL);
            Glide.with(this).load(MyContants.PHOTO+paizhao).apply(options).into(per_pho);
        }
        String nickname = SpUtils.getString(this, "nickname", null);
        alter_name.setText(nickname);
        String iDcrad = SpUtils.getString(this, "IDcard",null);
        int idc = Integer.parseInt(iDcrad.trim());
        if(idc==0){
            id_card.setText("未认证");
            real_renzheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent its=new Intent(PersonActivity.this,RenzhenActivity.class);
                    startActivity(its);
                }
            });
        }
        else if(idc==1){
            id_card.setText("审核中");
            real_renzheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent its=new Intent(PersonActivity.this,RenzhenActivity.class);
                    startActivity(its);
                }
            });

        }
        else if(idc==2){
            id_card.setText("审核不通过");
            real_renzheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent its=new Intent(PersonActivity.this,RenzhenActivity.class);
                    startActivity(its);
                }
            });
        }
        else if(idc==3){
            id_card.setText("已认证");
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                EventMessage eventMessage = new EventMessage("personphoto");
                EventBus.getDefault().postSticky(eventMessage);
                finish();
                break;
            //修改头像
            case R.id.real_pho:
                showphotoDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
            //修改名字
            case R.id.real_name:
                Intent it=new Intent(this,AlternameActivity.class);
                startActivity(it);
                break;
            //实名认证
//            case R.id.real_renzheng:
//
//                break;

        }
    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        //设置dialogpadding
//设置显示位置
//设置动画
//设置dialog的宽高
//设置触摸dialog外围是否关闭
//设置监听事件
        dialog = builder.setViewId(R.layout.dialog_photo)
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
                requestCamera();
                dialog.dismiss();

            }
        });
        //相册
        dialog.getView(R.id.tv_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选取
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
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
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
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
               .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    cutPath = selectList.get(0).getCutPath();
                    file = new File(cutPath);
                    Log.d("TAg",cutPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    int byteCount = bitmap.getByteCount();
                    Log.d("TAG",byteCount+"-----size----"+file.length());
                    /*
                       质量压缩
                     */
                    FileOutputStream baos = null;
                    try {

                        baos = new FileOutputStream(filess);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 2, baos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    int quality =2;

                    list.clear();
                    list.add(cutPath);
                    Log.d("Tag",list.toString());
                    photoworks();
                    break;
            }
        }
    }


    public void photoworks(){
        HttpParams httpParams=new HttpParams();

        httpParams.put("file", filess);
        Log.d("ffff",file+"");
        httpParams.put("uid",SpUtils.getString(Myapplication.getApplication(),"userid",null));
        httpParams.put("token",SpUtils.getString(PersonActivity.this,"token",null));

            OkGo.<String>post(MyContants.BASEURL+"s=User/updateHeadImg")
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
                                    ToastUtils.showShort(PersonActivity.this,"上传成功");
                                    Gson gson = new Gson();
                                    Photobean photobean = gson.fromJson(body, Photobean.class);
                                    String headImageUrl = photobean.getDatas().getHeadImageUrl();
                                    Glide.with(PersonActivity.this).load(MyContants.PHOTO+headImageUrl).into(per_pho);
                                    //sp存头像
                                    SpUtils.putString(PersonActivity.this, "paiphoto",headImageUrl);
                                    Log.d("TAg",headImageUrl);



                                }
                                else {
                                    ToastUtils.showShort(PersonActivity.this,datas.toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventMessage eventMessage = new EventMessage("personphoto");
        EventBus.getDefault().postSticky(eventMessage);
    }
}
