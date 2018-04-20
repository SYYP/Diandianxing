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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.adapter.Historycredieadapter;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Xinyongdetailbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class HistorycreditActivity extends BaseActivity {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ListView list_history;
    private SpringView spring_view;
    int i=1;
    private List<Xinyongdetailbean.DatasBean> datas;
    private List<Xinyongdetailbean.DatasBean> data=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_historycredit);
        //加载布局
        initView();
        networkmore();

        data();//刷新

    }

    private void data() {


        spring_view.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        i=1;
                        networkmore();

                    }
                }, 5000);

            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         i++;
                        networkmore();

                    }
                }, 5000);

            }
        });
        spring_view.setFooter(new DefaultFooter(this));
        spring_view.setHeader(new DefaultHeader(this));

    }


    private void networkmore() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("page", i );
        OkGo.<String>post(MyContants.BASEURL + "s=User/creditList")
                .params(httpParams)
                .tag(this)
                .execute(new StringCallback() {



                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            if (code == 200) {
                                spring_view.onFinishFreshAndLoad();
                                Gson gson = new Gson();
                                Xinyongdetailbean xinyongdetailbean = gson.fromJson(body, Xinyongdetailbean.class);
                                datas = xinyongdetailbean.getDatas();
                                  data.addAll(datas);

                               /*
                          加载数据
                       */
                                if (HistorycreditActivity.this.data != null && HistorycreditActivity.this.data.size() > 0) {
                                    Historycredieadapter historycredieadapter = new Historycredieadapter(HistorycreditActivity.this, data);
                                    list_history.setAdapter(historycredieadapter);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void network() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("page", i + "");
        OkGo.<String>post(MyContants.BASEURL + "s=User/creditList")
                .params(httpParams)
                .tag(this)
                .execute(new StringCallback() {



                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            if (code == 200) {
                                Gson gson = new Gson();
                                Xinyongdetailbean xinyongdetailbean = gson.fromJson(body, Xinyongdetailbean.class);
                                datas = xinyongdetailbean.getDatas();

                               /*
                          加载数据
                       */
                                if (HistorycreditActivity.this.datas != null && HistorycreditActivity.this.datas.size() > 0) {
                                    Historycredieadapter historycredieadapter = new Historycredieadapter(HistorycreditActivity.this, datas);
                                    list_history.setAdapter(historycredieadapter);
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
        list_history = (ListView) findViewById(R.id.list_history);
        spring_view = (SpringView) findViewById(R.id.spring_view);
        zhong.setText("信用记录");
        spring_view.setType(SpringView.Type.FOLLOW);

        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
