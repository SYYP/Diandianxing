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
    private List<Mingxibean.DatasBean> datas;
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

                    }
                }, 5000);
                ming_springview.onFinishFreshAndLoad();
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
                ming_springview.onFinishFreshAndLoad();
            }
        });
        ming_springview.setFooter(new DefaultFooter(this));
        ming_springview.setHeader(new DefaultHeader(this));

    }
    private void network() {
        Map<String,String> map=new HashMap<>();
        map.put("uid", SpUtils.getString(this,"userid",null));
        map.put("token",SpUtils.getString(this,"token",null));
        map.put("page",i+"");
        RetrofitManager.get(MyContants.BASEURL + "s=Purse/consumerRecords", map, new BaseObserver1<Mingxibean>("") {



            @Override
            public void onSuccess(Mingxibean result, String tag) {
                if(result.getCode()==200){
                    datas = result.getDatas();
                        /*
                          加载数据
                       */
                    if(datas !=null&& datas.size()>0) {
                        Mingxiadapter mingxiadapter=new Mingxiadapter(MingxiActivity.this,datas);
                        listview.setAdapter(mingxiadapter);
                    }

            }

            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showShort(MingxiActivity.this,code+"");

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
