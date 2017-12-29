package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.adapter.Messageadapter;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Messagebean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MessageActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ListView list_message;
    private List<Messagebean.DatasBean> datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_message);
        initView();
        /*
           网络请求
         */
           network();
    }

    private void network() {

        RetrofitManager.get(MyContants.BASEURL + "s=Login/newsList", new BaseObserver1<Messagebean>("") {



            @Override
            public void onSuccess(Messagebean result, String tag) {
                if(result.getCode()==200){
                    datas = result.getDatas();
                    Messageadapter messageadapter=new Messageadapter(MessageActivity.this, datas);
                    list_message.setAdapter(messageadapter);
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        list_message = (ListView) findViewById(R.id.list_message);
         iv_callback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
          zhong.setText("我的消息");

        list_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MessageActivity.this,MessagedetailActivity.class);
                 intent.putExtra("mes_id",datas.get(i).getId());
                 startActivity(intent);
            }
        });
    }

}
