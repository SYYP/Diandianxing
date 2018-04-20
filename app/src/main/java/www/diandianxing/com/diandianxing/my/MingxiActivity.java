package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.adapter.Mingxiadapter;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Mingxibean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MingxiActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private SpringView ming_springview;
    int i=1;
    List<Mingxibean.DatasBean> datas;
    List<Mingxibean.DatasBean> datass=new ArrayList<>();
    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_mingxi);
        initView();
        //网络请求
        network();
        data();//刷新，添加数据

    }

    private void data() {


        ming_springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        i=1;
                        network();
                        ming_springview.onFinishFreshAndLoad();

                    }
                }, 0);

            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        networkmore();


                    }
                }, 0);

            }
        });
        ming_springview.setFooter(new DefaultFooter(this));
        ming_springview.setHeader(new DefaultHeader(this));

    }
    private void network() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("page", i + "");
        OkGo
                .<String>post(MyContants.BASEURL + "s=Purse/consumerRecords")
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int anInt = jsonobj.getInt("code");
                            if (anInt == 200) {
                                Gson gson = new Gson();
                                Mingxibean mingxibean = gson.fromJson(body, Mingxibean.class);
                                datas = mingxibean.getDatas();

                                         /*
                          加载数据
                       */
                                if (datas != null && datas.size() > 0) {
                                    Mingxiadapter mingxiadapter = new Mingxiadapter(MingxiActivity.this, datas);
                                    listview.setAdapter(mingxiadapter);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void networkmore() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("page", i + "");
        OkGo
                .<String>post(MyContants.BASEURL + "s=Purse/consumerRecords")
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int anInt = jsonobj.getInt("code");
                            if (anInt == 200) {
                                ming_springview.onFinishFreshAndLoad();
                                Gson gson = new Gson();
                                Mingxibean mingxibean = gson.fromJson(body, Mingxibean.class);
                                datas = mingxibean.getDatas();
                                datass.addAll(datas);
                                         /*
                          加载数据
                       */
                                if (datass != null && datass.size() > 0) {
                                    Mingxiadapter mingxiadapter = new Mingxiadapter(MingxiActivity.this, datass);
                                    listview.setAdapter(mingxiadapter);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        listview = (ListView) findViewById(R.id.list_mingxi);
        ming_springview = (SpringView) findViewById(R.id.ming_springview);
        ming_springview.setType(SpringView.Type.FOLLOW);

        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         zhong.setText("明细");
    }
}
