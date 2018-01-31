package www.diandianxing.com.diandianxing.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Sharebean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class UmshareActivity extends BaseActivity {

    private static Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected static void SharebyQQ(Activity context) {
        mContext = context;
        new ShareAction(mContext)
                .withMedia(new UMImage(mContext,R.drawable.img_motou))
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText("hello")//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();
    }

    protected static void SharebyQzon(Activity context) {
        mContext = context;
        new ShareAction(mContext)
                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                .withText("hello")//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();
    }

    protected static void SharebyWeixin(Activity context) {
        mContext = context;
        new ShareAction(mContext)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .withText("hello")//分享内容

                .setCallback(umShareListener)//回调监听器
                .share();
    }
    protected static void SharebyWeixincenter(Activity context) {
        mContext = context;
        new ShareAction(mContext)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withText("hello")//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();
    }
    public static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult( final  SHARE_MEDIA platform) {
            //            Log.d("plat", "platform" + platform);
             /*
               成功调用接口
              */
            String type = "";
            if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                type = "1";

            } else if(platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
                type="2";

            }
            else if (platform.equals(SHARE_MEDIA.QQ)) {
                type = "3";
            } else if (platform.equals(SHARE_MEDIA.QZONE)) {
                type = "4";
            }
             //网络请求
            Map<String,String> map=new HashMap<>();
              map.put("uid", SpUtils.getString(mContext,"userid",null));
             map.put("type",type);
            map.put("token",SpUtils.getString(mContext,"token",null));
              map.put("platform",2+"");
            RetrofitManager.post(MyContants.BASEURL + "s=User/share", map, new BaseObserver1<Sharebean>("") {
                @Override
                public void onSuccess(Sharebean result, String tag) {
                    if(result.getCode()==200){
                        Toast.makeText(Myapplication.getGloableContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed(int code,String data) {

                }
            });

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(Myapplication.getGloableContext(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                //                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(Myapplication.getGloableContext(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
