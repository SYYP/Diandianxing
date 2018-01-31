package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Photobean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AlternameActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private EditText alter_name;
    private TextView name_ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_my_name);
        initView();
    }
    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        alter_name = (EditText) findViewById(R.id.alter_name);
        name_ok = (TextView) findViewById(R.id.name_ok);
        iv_callback.setOnClickListener(this);
        name_ok.setOnClickListener(this);
        zhong.setText("个人信息");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            //保存
            case R.id.name_ok:
                SpUtils.putString(this,"nickname",alter_name.getText().toString().trim());
                //调用Eventbus
                EventMessage eventMessage = new EventMessage("myname");
                EventBus.getDefault().postSticky(eventMessage);
                network();

                break;
        }
    }
    private void network() {
        Map<String,String> map=new ArrayMap<>();
          map.put("uid",SpUtils.getString(this,"userid",null));
          map.put("token",SpUtils.getString(this,"token",null));
          map.put("nickname",alter_name.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL + "s=User/updateNickname", map, new BaseObserver1<Photobean>("") {
            @Override
            public void onSuccess(Photobean result, String tag) {
                if(result.getCode()==200){

                    ToastUtils.show(AlternameActivity.this,"修改成功",1);
                    finish();
                }
            }

            @Override
            public void onFailed(int code,String data) {

            }
        });
    }
}
