package www.diandianxing.com.diandianxing.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import www.diandianxing.com.diandianxing.DianDianActivity;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Loginsuccess;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * Created by lxk on 2017/7/3.
 */

public class UMLoginActivity extends BaseActivity {

    private static Activity mContext;

    /*
            * 授权中只是能拿到uid，openid，token这些授权信息，想获取用户名和用户资料，需要使用这个接口
            * 其中umAuthListener为授权回调，构建如下，其中授权成功会回调onComplete，取消授权回调onCancel，
            * 授权错误回调onError，对应的错误信息可以用过onError的Throwable参数来打印
            * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected static void loginBySina(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.SINA, umAuthListener);
    }

    protected static void loginByWeiXin(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    protected static void loginByQQ(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, umAuthListener);
    }

    private static UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            //            Toast.makeText(MyApplication.getGloableContext(), "授权开始回调", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            /*
        * 登录成功后，第三方平台会将用户资料传回， 全部会在Map data中返回 ，由于各个平台对于用户资料的标识不同，
        * 因此为了便于开发者使用，我们将一些常用的字段做了统一封装，开发者可以直接获取，
        * 不再需要对不同平台的不同字段名做转换，这里列出我们封装的字段及含义
        * */
            Log.d("TSs",data.toString());
            //            Toast.makeText(MyApplication.getGloableContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            final String username = data.get("name");
            final String userhead = data.get("iconurl");
            final String uid = data.get("openid");
            Log.d("opend",uid+"```````");
            SpUtils.putInt(Myapplication.getApplication(), "guid", 1);
            //            SpUtils.putString(MyApplication.getGloableContext(), "threeid", uid);
            //            SpUtils.putString(MyApplication.getGloableContext(), "logintype", "three");
            String type = "";
            if (platform.equals(SHARE_MEDIA.QQ)) {
                type = "2";
            } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                type = "1";
            } else if (platform.equals(SHARE_MEDIA.SINA)) {
                type = "3";
            }
            HttpParams httpParams=new HttpParams();
            httpParams.put("openid", uid);
            httpParams.put("type", type);
            final String finalType = type;
            OkGo.<String>post(MyContants.BASEURL+"s=Login/threeLogin")
                    .tag(this)
                    .params(httpParams)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            try {
                                JSONObject jsonobj=new JSONObject(body);
                                int code = jsonobj.getInt("code");
                                String msg = jsonobj.getString("msg");
                                if(code==200){
                                    if(msg.equals("failed")){
                                        Intent intent = new Intent(mContext, BandphoneActivity.class);
                                        intent.putExtra("type", finalType);
                                        intent.putExtra("openid", uid);
                                        intent.putExtra("name",username);
                                        mContext.startActivity(intent);
                                    }
                                    else {
                                        Gson gson = new Gson();
                                        Loginsuccess loginsuccess = gson.fromJson(body, Loginsuccess.class);
                                        SpUtils.putString(mContext, "userid", loginsuccess.getDatas().getId() + "");
                                        SpUtils.putString(mContext, "token", loginsuccess.getDatas().getToken() + "");
                                        mContext.startActivity(new Intent(mContext, DianDianActivity.class));
                                        mContext.finish();

                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(Myapplication.getGloableContext(), "登陆失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(Myapplication.getGloableContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };

    /*
    * 最后在登录所在的Activity里复写onActivityResult方法,注意不可在fragment中实现，如果在fragment中调用登录，
    * 就在fragment依赖的Activity中实现，如果不实现onActivityResult方法，会导致登录或回调无法正常进行
    * */
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
