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
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.adapter.xingchengadapter;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Jourbean;
import www.diandianxing.com.diandianxing.utils.MyContants;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_journey);
        initView();
        data();
        xingchengadapter xingchengadapter=new xingchengadapter(this,list);
        jour_recycle.setLayoutManager(new LinearLayoutManager(this));
        jour_recycle.setAdapter(xingchengadapter);
    }

    private void data() {

        Jourbean jourbean = new Jourbean();
        jourbean.setAll("1361.1");
        jourbean.setAllday("121");
        jourbean.setHeji("10");
        jourbean.setMoney("1.00");
        jourbean.setTime("9:36");
        list.add(jourbean);
        Jourbean jourbean1 = new Jourbean();
        jourbean1.setHeji("10");
        jourbean1.setMoney("1.00");
        jourbean1.setTime("9:20");
        list.add(jourbean1);
        Jourbean jourbean2 = new Jourbean();
        jourbean2.setHeji("10");
        jourbean2.setMoney("1.00");
        jourbean2.setTime("9:20");
        list.add(jourbean2);
        Jourbean jourbean3 = new Jourbean();
        jourbean3.setHeji("10");
        jourbean3.setMoney("1.00");
        jourbean3.setTime("9:36");
        list.add(jourbean3);
        tex_all.setText(list.get(0).getAll());
        text_allday.setText(list.get(0).getAllday());
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        quwanadapter.notifyDataSetChanged();

                    }
                }, 5000);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      //  Toast.makeText(getActivity(), "ahha", Toast.LENGTH_SHORT).show();

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
