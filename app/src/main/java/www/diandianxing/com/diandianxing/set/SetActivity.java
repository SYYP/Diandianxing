package www.diandianxing.com.diandianxing.set;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;

import util.DownloadAppUtils;
import util.UpdateAppUtils;
import www.diandianxing.com.diandianxing.Login.BandphoneActivity;
import www.diandianxing.com.diandianxing.Login.LoginActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.my.ProtocolActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.CacheDataManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class SetActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView set_pwd;
    private RelativeLayout real_pwd;
    private TextView set_gengxin;
    private RelativeLayout real_gengxin;
    private TextView set_xieyi;
    private RelativeLayout real_xieyi;
    private TextView set_aboutwe;
    private RelativeLayout real_aboutwe;
    private TextView set_huancun;
    private RelativeLayout real_huancun;
    private TextView set_yijian;
    private RelativeLayout real_yijian;
    private TextView text_exit;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    Toast.makeText(SetActivity.this,"清理完成",Toast.LENGTH_SHORT).show();
                    try {
                     //   clean_huan.setText(CacheDataManager.getTotalCacheSize(SetActivity.this));

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

            }

        };

    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
      setContentView(R.layout.activity_set);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("aaaa");
        registerReceiver(broadcastReceiver,intentFilter);
        initView();
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("aaaa")){

                Bundle bundle = intent.getBundleExtra("data");
                int progress = bundle.getInt("progress");
                if (progress == 100) {
//                    install(DownloadAppUtils.downloadUpdateApkFilePath);

                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setDataAndType(Uri.fromFile(new File(DownloadAppUtils.downloadUpdateApkFilePath)), "application/vnd.android.package-archive");
                context.startActivity(intent1);
                }


            }

        }
    };

    private void install(String path) {
        Uri uri;
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0以上的系统，要使用FileProvider的方式构建Uri
            uri = FileProvider.getUriForFile(this, "com.hm.retrofitrxjavademo.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        set_pwd = (TextView) findViewById(R.id.set_pwd);
        real_pwd = (RelativeLayout) findViewById(R.id.real_pwd);
        set_gengxin = (TextView) findViewById(R.id.set_gengxin);
        real_gengxin = (RelativeLayout) findViewById(R.id.real_gengxin);
        set_xieyi = (TextView) findViewById(R.id.set_xieyi);
        real_xieyi = (RelativeLayout) findViewById(R.id.real_xieyi);
        set_aboutwe = (TextView) findViewById(R.id.set_aboutwe);
        real_aboutwe = (RelativeLayout) findViewById(R.id.real_aboutwe);
        set_huancun = (TextView) findViewById(R.id.set_huancun);
        real_huancun = (RelativeLayout) findViewById(R.id.real_huancun);
        set_yijian = (TextView) findViewById(R.id.set_yijian);
        real_yijian = (RelativeLayout) findViewById(R.id.real_yijian);
        text_exit = (TextView) findViewById(R.id.exit);
        zhong.setText("设置");
        real_pwd.setOnClickListener(this);
        real_gengxin.setOnClickListener(this);
        real_xieyi.setOnClickListener(this);
        real_aboutwe.setOnClickListener(this);
        real_huancun.setOnClickListener(this);
        real_yijian.setOnClickListener(this);
        text_exit.setOnClickListener(this);
        iv_callback.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //修改密码
            case R.id.real_pwd:
                Intent intent=new Intent(this,AlterpwdActivity.class);
                startActivity(intent);
                break;
            //更新
            case R.id.real_gengxin:
                networkBB();
                break;
            //协议
            case R.id.real_xieyi:
                Intent it=new Intent(this, ProtocolActivity.class);
                startActivity(it);
                break;
            //关于我们
            case R.id.real_aboutwe:
                  Intent intent1=new Intent(this,AboutweActivity.class);
                startActivity(intent1);
                break;
            //清除缓存
            case R.id.real_huancun:
                cleanDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                break;
            //意见反馈
            case R.id.real_yijian:
                Intent intent2=new Intent(this,Feedback.class);
                startActivity(intent2);
                break;
            //退出登录
            case R.id.exit:
                tuichuDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                break;
        }
    }
    //更新
    private void update(String code,String name,String url){

                    UpdateAppUtils.from(SetActivity.this)
                            .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //更新检测方式，默认为VersionCode
                            .serverVersionCode(Integer.parseInt(code)) //服务器穿过来的
                            .serverVersionName(name)//服务器传过来的
                            .apkPath(url)//更新的连接
                            .showNotification(true) //是否显示下载进度到通知栏，默认为true
                            // .updateInfo()  //更新日志信息 String
                            .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
                            .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                            .update();
                }

    private void networkBB() {
        HttpParams params = new HttpParams();
        params.put("uid", SpUtils.getString(this, "userid", null));
        params.put("token", SpUtils.getString(this, "token", null));
        OkGo.<String>post(MyContants.BASEURL +"s=LockBalance/getEditionSave")
                .tag(this)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String body = response.body();
                        Log.d("TAG","版本"+body);
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            String datas = jsonobj.getString("datas");
                            if (code == 200) {

                                JSONObject jo = new JSONObject(datas);

                                String bbcode=jo.getString("EditionNum");
                                String name = jo.getString("randNum");
                                String url = jo.getString("appUrl");

                                update(bbcode,name,url);

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
    }


    //退出登录
    private void tuichuDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_phone)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
       TextView tv_clean= dialog.getView(R.id.tv_clean);
        tv_clean.setVisibility(View.GONE);
        TextView tv_content = dialog.getView(R.id.tv_content);
        tv_content.setText("            是否退出登录             ");
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
                SpUtils.putInt(SetActivity.this, "guid", 2);

                //更新页面
                dialog.dismiss();
                realBack();
                Intent intent3=new Intent(SetActivity.this,LoginActivity.class);
                startActivity(intent3);




            }
        });
    }

//清除缓存
    private void cleanDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_phone)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        TextView tv_content = dialog.getView(R.id.tv_content);
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
                //更新页面
                new Thread(new clearCache()).start();
                dialog.dismiss();
            }
        });
    }
    //创建内部类用于清除缓存
    class clearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheDataManager.clearAllCache(SetActivity.this);
                Thread.sleep(3000);
                if (CacheDataManager.getTotalCacheSize(SetActivity.this).startsWith("0")) {
                    handler.sendEmptyMessage(0);
                }

            } catch (Exception e) {

                return;

            }

        }

    }

}
