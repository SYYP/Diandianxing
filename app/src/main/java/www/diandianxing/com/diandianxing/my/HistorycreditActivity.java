package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_historycredit);
        //加载布局
        initView();
        network();//网络请求

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
                        network();

                    }
                }, 5000);
                spring_view.onFinishFreshAndLoad();
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
                spring_view.onFinishFreshAndLoad();
            }
        });
        spring_view.setFooter(new DefaultFooter(this));
        spring_view.setHeader(new DefaultHeader(this));

    }


    private void network() {
        Map<String,String> map=new HashMap<>();
        map.put("uid", SpUtils.getString(this,"userid",null));
        map.put("token",SpUtils.getString(this,"token",null));
        map.put("page",i+"");
        RetrofitManager.get(MyContants.BASEURL + "s=User/creditList", map, new BaseObserver1<Xinyongdetailbean>("") {



            @Override
            public void onSuccess(Xinyongdetailbean result, String tag) {
                if(result.getCode()==200){
                    datas = result.getDatas();
                        /*
                          加载数据
                       */
                    if(datas!=null&&datas.size()>0) {
                        Historycredieadapter historycredieadapter = new Historycredieadapter(HistorycreditActivity.this, datas);
                        list_history.setAdapter(historycredieadapter);
                    }
                }
            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showShort(HistorycreditActivity.this,code+"");

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
