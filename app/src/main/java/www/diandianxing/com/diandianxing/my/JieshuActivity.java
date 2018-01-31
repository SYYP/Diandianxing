package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.MainActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.xingbean;
import www.diandianxing.com.diandianxing.bean.xiusuccess;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class JieshuActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private TextView fei_yuan;
    private TextView zhifu_ok;
    private BaseDialog dialog;
    private RelativeLayout real_back;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                dialog.close();
                zhifu_ok.setText("已支付");
                real_back.setBackground(getResources().getDrawable(R.drawable.shape_backhuidaradioline));

            }
        }
    };
    private TextView zong_money;
    private TextView zong_time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_jieshu);
        initView();
        /*
          请求网络
         */
          networks();
    }

    private void networks() {
         Map<String ,String> map=new HashMap<>();
            map.put("uid",SpUtils.getString(this,"userid",null));
           map.put("token",SpUtils.getString(this,"token",null));
           map.put("log_id",SpUtils.getString(this,"ripedt",null));
        RetrofitManager.post(MyContants.BASEURL + "s=Bike/tripInfo", map, new BaseObserver1<xingbean>("") {
            @Override
            public void onSuccess(xingbean result, String tag) {
                if(result.getCode()==200) {
                    xingbean.DatasBean datas = result.getDatas();

                    /*
                       添加数据

                     */
                    fei_yuan.setText("¥ "+datas.getMoney());
                    zong_money.setText(datas.getMoney()+"元");
                    String time = datas.getTime();
                    int times = Integer.parseInt(time);
                    zong_time.setText(times/60+"分钟");

                }
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });



    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        fei_yuan = (TextView) findViewById(R.id.fei_yuan);
        real_back = (RelativeLayout) findViewById(R.id.real_back);
        zhifu_ok = (TextView) findViewById(R.id.zhifu_ok);
        zong_money = (TextView) findViewById(R.id.zong_money);
        zong_time = (TextView) findViewById(R.id.zong_time);
        zhong.setText("行程结束");
        you.setText("故障");
        zhifu_ok.setOnClickListener(this);
        iv_callback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                  Intent intent=new Intent(this, MainActivity.class);
                  startActivity(intent);
                  finish();
                break;
            case R.id.you://跳故障
                break;
            case R.id.zhifu_ok:
                network();
//                showDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                //支付
                break;
        }
    }

    private void network() {

        Map<String,String> map=new HashMap<>();
        map.put("uid", SpUtils.getString(this,"userid",null));
        map.put("token",SpUtils.getString(this,"token",null));
        RetrofitManager.get(MyContants.BASEURL + "s=Bike/bikeStatus", map, new BaseObserver1<xiusuccess>("") {
            @Override
            public void onSuccess(xiusuccess result, String tag) {
                Intent intent=new Intent(JieshuActivity.this,JourdetailActivity.class);
                  startActivity(intent);
                  finish();
            }

            @Override
            public void onFailed(int code,String data) {

            }
        });
    }


    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        //设置dialogpadding
//设置显示位置
//设置动画
//设置dialog的宽高
//设置触摸dialog外围是否关闭
//设置监听事件
        dialog = builder.setViewId(R.layout.dialog_tishi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
           dialog.show();
  handler.sendEmptyMessageDelayed(1,3000);

    }

    @Override
    public void onBackPressed() {
        //如果按返回键调用eventbus刷新页面
        super.onBackPressed();
        EventMessage eventMessage = new EventMessage("xiangqing");
        EventBus.getDefault().postSticky(eventMessage);
    }
}
