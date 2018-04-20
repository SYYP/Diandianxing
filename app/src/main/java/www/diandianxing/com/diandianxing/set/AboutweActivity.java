package www.diandianxing.com.diandianxing.set;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

import static android.R.attr.version;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AboutweActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView imageView2;
    private RelativeLayout real;
    private TextView about_wei;
    private TextView wei_num;
    private TextView about_call;
    private TextView call_num;
    private TextView about_email;
    private TextView email_num;
    private TextView about_guanwang;
    private TextView call_guanwang;
    private TextView tv_bb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_aboutwe);
        MyContants.windows(this);
        try {
            initView();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() throws PackageManager.NameNotFoundException {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        real = (RelativeLayout) findViewById(R.id.real);
        about_wei = (TextView) findViewById(R.id.about_wei);
        wei_num = (TextView) findViewById(R.id.wei_num);
        about_call = (TextView) findViewById(R.id.about_call);
        call_num = (TextView) findViewById(R.id.call_num);
        about_email = (TextView) findViewById(R.id.about_email);
        email_num = (TextView) findViewById(R.id.email_num);
        about_guanwang = (TextView) findViewById(R.id.about_guanwang);
        call_guanwang = (TextView) findViewById(R.id.call_guanwang);
        tv_bb= (TextView) findViewById(R.id.tv_bbh);

        PackageManager pm = getPackageManager();//context为当前Activity上下文
        PackageInfo pi = pm.getPackageInfo(AboutweActivity.this.getPackageName(), 0);
        String versionName = pi.versionName;
        tv_bb.setText("v"+versionName);

        networkmoney();
        zhong.setText("关于我们");
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

                                String email=jo.getString("email");

                                String http = jo.getString("http");

                                email_num.setText(email);
                                call_guanwang.setText(http);

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


}
