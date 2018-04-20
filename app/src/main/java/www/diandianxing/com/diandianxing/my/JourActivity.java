package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import www.diandianxing.com.diandianxing.adapter.xingchengadapter;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.base.JsonCallback;
import www.diandianxing.com.diandianxing.bean.Jourbean;
import www.diandianxing.com.diandianxing.bean.Jourbeans;
import www.diandianxing.com.diandianxing.bean.Joures;
import www.diandianxing.com.diandianxing.bean.Stringbean;
import www.diandianxing.com.diandianxing.bean.Xingchengbean;
import www.diandianxing.com.diandianxing.bean.databean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

import static www.diandianxing.com.diandianxing.R.id.map;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class JourActivity extends BaseActivity {
    //    private List<Jourbean> list = new ArrayList<>();
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private RecyclerView jour_recycle;
    private TextView tex_all;
    private TextView text_allday;
    private SpringView springView;
    boolean bool = true;

    private String s;
    private www.diandianxing.com.diandianxing.adapter.xingchengadapter xingchengadapter;
    List<Xingchengbean.DatasBean.ListBean> lists = new ArrayList<>();
    Map<String, List<databean>> stringMap = new HashMap<String, List<databean>>();
    List<databean> listdata = new ArrayList<>();
    List<Jourbeans> listjou = new ArrayList<>();
    List<Joures> listjous = new ArrayList<>();
    private List<Stringbean> liststring = new ArrayList<>();


    private List<Xingchengbean.DatasBean.ListBean> list;

    int i = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_journey);
        initView();
        networkmore();


        data();

    }

    private void network() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("page", i);
        OkGo.<String>post(MyContants.BASEURL + "s=LockBalance/tripLists")
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {


                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            if (code == 404) {
                                ToastUtils.showShort(JourActivity.this, "暂无行程记录");
                            }
                            if (code == 200) {
                                Gson gson = new Gson();
                                Xingchengbean xingchengbean = gson.fromJson(body, Xingchengbean.class);
                                list = xingchengbean.getDatas().getList();
                                Xingchengbean.DatasBean.UinfoBean uinfo = xingchengbean.getDatas().getUinfo();
                                lists.addAll(list);
                                //加载数据
                                String mileage = uinfo.getMileage();
                                double v = Double.valueOf(mileage).doubleValue();//距离
                                String mile = String.valueOf(v);
                                tex_all.setText(mile);
                                String dayNum = uinfo.getDayNum();//天数
                                text_allday.setText(dayNum);
                                xingchengadapter = new xingchengadapter(JourActivity.this, lists);
                                jour_recycle.setLayoutManager(new LinearLayoutManager(JourActivity.this));
                                jour_recycle.setAdapter(xingchengadapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private void networkmore() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SpUtils.getString(this, "token", null));
        httpParams.put("uid", SpUtils.getString(this, "userid", null));
        httpParams.put("page", i);
        OkGo.<String>post(MyContants.BASEURL + "s=LockBalance/tripLists")
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonobj = new JSONObject(body);
                            int code = jsonobj.getInt("code");
                            if (code == 404) {
                                ToastUtils.showShort(JourActivity.this, "暂无行程记录");
                            }
                            if (code == 200) {
                                springView.onFinishFreshAndLoad();
                                Gson gson = new Gson();
                                Xingchengbean xingchengbean = gson.fromJson(body, Xingchengbean.class);
                                list = xingchengbean.getDatas().getList();
                                Xingchengbean.DatasBean.UinfoBean uinfo = xingchengbean.getDatas().getUinfo();
                                lists.addAll(list);
                                //加载数据
                                String mileage = uinfo.getMileage();
                                double v = Double.valueOf(mileage).doubleValue();//距离
                                String mile = String.valueOf(v);
                                tex_all.setText(mile);
                                String dayNum = uinfo.getDayNum();//天数
                                text_allday.setText(dayNum);
                                Log.d("TAGS", lists.toString());


                            }
                            Log.d("TAG", listjou.toString());
                            xingchengadapter = new xingchengadapter(JourActivity.this, lists);

                            jour_recycle.setLayoutManager(new LinearLayoutManager(JourActivity.this));
                            jour_recycle.setAdapter(xingchengadapter);
                             xingchengadapter.setOnItemClickListener(new xingchengadapter.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(View view, int position) {
                                     //保存行程id
                            String log_id = lists.get(position).getLog_id();
                            Log.d("TAS", log_id + "");
                            SpUtils.putString(JourActivity.this, "triped", log_id);
                            //跳转到行程详情
                            Intent intent = new Intent(JourActivity.this, JourdetailActivity.class);
                            intent.putExtra("xicheng", "xing");//根据不同的页面返回不同的页面
                            startActivity(intent);
                                 }
                             });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private void data() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i = 1;
                        network();
                        springView.onFinishFreshAndLoad();
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
                }, 0);

            }
        });
        springView.setFooter(new DefaultFooter(this));
        springView.setHeader(new DefaultHeader(this));
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        tex_all = (TextView) findViewById(R.id.text_all);
        text_allday = (TextView) findViewById(R.id.text_allday);
        springView = (SpringView) findViewById(R.id.springview);
        jour_recycle = (RecyclerView) findViewById(R.id.jour_recycle);
        springView.setType(SpringView.Type.FOLLOW);
        zhong.setText("我的行程");
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
