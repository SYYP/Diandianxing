package www.diandianxing.com.diandianxing.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Gubackbean;
import www.diandianxing.com.diandianxing.bean.Guzhangbean;
import www.diandianxing.com.diandianxing.bean.Guzhanxuanbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;
import www.diandianxing.com.diandianxing.utils.UploadUtil;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class GuZhangActivity extends BaseActivity {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private RecyclerView recycler_guzhang;
    private Context context;
    private List<Guzhangbean> list=new ArrayList<>();
    Guzhangadapter guzhangadapter;
    private TextView text_zishu;
    private EditText fu_edtext;
    private ImageView img_create;
    private RelativeLayout real_ok;
    private Uri imageUri;
    private RelativeLayout real_renzheng;
    private List<Guzhanxuanbean.DatasBean> datas;
    private Handler handler=new Handler();
    private EditText fu_edtext1;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String cutPath;
    private File file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_guzhang);
        initView();
        network();

       // deleteCollect();
        /*
           network
         */


    }

    private void network() {
        Map<String,String> map=new HashMap<>();
         map.put("uid", SpUtils.getString(this,"userid",null));
         map.put("token",SpUtils.getString(this,"token",null));
        RetrofitManager.post(MyContants.BASEURL +"s=Bike/feedbackType", map, new BaseObserver1<Guzhanxuanbean>("") {
            @Override
            public void onSuccess(Guzhanxuanbean result, String tag) {
                   if(result.getCode()==200){
                       datas = result.getDatas();
                       guzhangadapter=new Guzhangadapter(R.layout.item_guzhang,datas);
                       recycler_guzhang.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                       recycler_guzhang.setAdapter(guzhangadapter);
                       data();


                   }

            }

            @Override
            public void onFailed(int code,String data) {

            }
        });

    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        real_ok = (RelativeLayout) findViewById(R.id.real_ok);
        fu_edtext1 = (EditText) findViewById(R.id.fu_edtext);
        recycler_guzhang = (RecyclerView) findViewById(R.id.recycler_guzhang);
        zhong.setText("故障申报");
        recycler_guzhang.setNestedScrollingEnabled(false);
        img_create = (ImageView) findViewById(R.id.img_increate);
        real_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                           fankui();

            }


        });


        img_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showphotoDialog(Gravity.BOTTOM,R.style.Bottom_Top_aniamtion);
            }
        });
        fu_edtext1 = (EditText) findViewById(R.id.fu_edtext);
        fu_edtext1.addTextChangedListener(new TextWatcher() {
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
                text_zishu.setText(i+"/140");
            }
        });
        text_zishu = (TextView) findViewById(R.id.zishu);

        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /*
        网络请求
     */

    private void fankui() {

            final Thread thred = new Thread(new Runnable() {
                private Gubackbean gubackbean;
                private String s1;

                @Override
                public void run() {

                    Map<String,String>map=new HashMap<>();
                    map.put("uid",SpUtils.getString(GuZhangActivity.this,"userid",null));
                    map.put("token",SpUtils.getString(GuZhangActivity.this,"token",null));
                    map.put("type",2+"");
                    map.put("content",fu_edtext1.getText().toString().trim());
                    for (int i = 0; i <datas.size() ; i++) {
                        map.put("label",datas.get(i).getName()+" ");
                    }
                    Map<String,File> maps=new HashMap<>();
                    if(file!=null){
                        maps.put("file",file) ;
                    }
                    s1 = UploadUtil.uploadFile(map, maps, MyContants.BASEURL +"s=Bike/feedback");
                    Gson gson=new Gson();
                    gubackbean = gson.fromJson(s1, Gubackbean.class);


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            KLog.a("Tag", s1);
                            if(gubackbean.getCode()==200){
                                ToastUtils.show(GuZhangActivity.this,"提交成功",1);
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





    private void data() {

        guzhangadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("pos",position+"");
                datas.get(position).setaBoolean(!datas.get(position).isaBoolean());
                guzhangadapter.notifyDataSetChanged();
                Log.d("ppp",datas.get(position).isaBoolean()+"");
                Log.d("sss",datas.get(position).getName());


            }
        });
    }

    class Guzhangadapter extends BaseQuickAdapter<Guzhanxuanbean.DatasBean, BaseViewHolder> {


        public Guzhangadapter(@LayoutRes int layoutResId, @Nullable List<Guzhanxuanbean.DatasBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Guzhanxuanbean.DatasBean item) {
            helper.setIsRecyclable(false);
            helper.setText(R.id.text_count, item.getName())
                    .setChecked(R.id.guzhang_miaoshu, item.isaBoolean());
        }
    }
    private void requestCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
//                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
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
                requestCamera();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    cutPath = selectList.get(0).getPath();
                    file = new File(cutPath);
                    Glide.with(this).load(cutPath).into(img_create);
                    Log.d("files",file+"");
                    break;
            }
        }


    }
}
