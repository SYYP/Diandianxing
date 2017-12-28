package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.adapter.Messageadapter;
import www.diandianxing.com.diandianxing.base.BaseActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_message);
        initView();
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
        Messageadapter messageadapter=new Messageadapter(this);
        list_message.setAdapter(messageadapter);
        list_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MessageActivity.this,MessagedetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
