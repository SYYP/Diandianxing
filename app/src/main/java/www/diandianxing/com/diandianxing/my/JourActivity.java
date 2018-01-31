package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import www.diandianxing.com.diandianxing.adapter.xingchengadapter;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Jourbean;
import www.diandianxing.com.diandianxing.bean.Xingchengbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class JourActivity extends BaseActivity {
    private List<Jourbean> list = new ArrayList<>();
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private RecyclerView jour_recycle;
    private TextView tex_all;
    private TextView text_allday;
    private SpringView springView;
    private www.diandianxing.com.diandianxing.adapter.xingchengadapter xingchengadapter;

    int i=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_journey);
        initView();
          network();
        data();

    }

    private void network() {

        Map<String,String> map=new HashMap<>();
         map.put("token",SpUtils.getString(this,"token",null));
          map.put("uid", SpUtils.getString(this,"userid",null));
          map.put("page",i+"");
        RetrofitManager.post(MyContants.BASEURL + "s=LockBalance/tripLists", map, new BaseObserver1<Xingchengbean>("") {



            @Override
            public void onSuccess(Xingchengbean result, String tag) {
                      if(result.getCode()==200){
                          List<Xingchengbean.DatasBean.ListBean> list = result.getDatas().getList();
                          Xingchengbean.DatasBean.UinfoBean uinfo = result.getDatas().getUinfo();

                           //加载数据
                          String mileage = uinfo.getMileage();
                          double v = Double.valueOf(mileage).doubleValue();//距离
                          String mile = String.valueOf(v);
                          tex_all.setText(mile);
                          String dayNum = uinfo.getDayNum();//天数
                          text_allday.setText(dayNum);
                          xingchengadapter = new xingchengadapter(JourActivity.this,list);
                          jour_recycle.setLayoutManager(new LinearLayoutManager(JourActivity.this));
                          jour_recycle.setAdapter(xingchengadapter);

                      }
            }

            @Override
            public void onFailed(int code, String data) {

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

                        xingchengadapter.notifyDataSetChanged();
                    }
                }, 5000);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       i++;
                        network();

                    }
                }, 5000);
                springView.onFinishFreshAndLoad();
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
