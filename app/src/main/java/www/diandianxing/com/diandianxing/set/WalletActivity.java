package www.diandianxing.com.diandianxing.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.my.BalanceActivity;
import www.diandianxing.com.diandianxing.my.CashpayActivity;
import www.diandianxing.com.diandianxing.my.MingxiActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView wallet_next;
    private TextView wallet_yue;
    private ImageView wallet_next1;
    private TextView wallet_yanjin;
    private TextView look_mingxi;
    private TextView wallet_ok;
    private RelativeLayout real_yanjin,real_yue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_mywallet);
        initView();
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        wallet_next = (ImageView) findViewById(R.id.wallet_next);
        wallet_yue = (TextView) findViewById(R.id.wallet_yue);
        wallet_next1 = (ImageView) findViewById(R.id.wallet_next1);
        wallet_yanjin = (TextView) findViewById(R.id.wallet_yanjin);
        look_mingxi = (TextView) findViewById(R.id.look_mingxi);
        wallet_ok = (TextView) findViewById(R.id.wallet_ok);
        real_yanjin = (RelativeLayout) findViewById(R.id.real_yanjin);
        real_yue = (RelativeLayout) findViewById(R.id.real_yue);
        iv_callback.setOnClickListener(this);
        real_yanjin.setOnClickListener(this);
        look_mingxi.setOnClickListener(this);
        wallet_ok.setOnClickListener(this);
        real_yue.setOnClickListener(this);
        zhong.setText("我的钱包");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.real_yanjin:
                if(wallet_yanjin.getText().toString().equals("0.00元")){
                 //跳转到叫押金
                    Intent intent=new Intent(this, CashpayActivity.class);
                    startActivity(intent);
                }
                else {
                    //退换押金
                    showphotoDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                }
                break;
            case R.id.real_yue:
                Intent intent=new Intent(this, BalanceActivity.class);
                startActivity(intent);
                break;
            case R.id.look_mingxi:
                Intent intent2=new Intent(this, MingxiActivity.class);
                startActivity(intent2);
                break;
            case R.id.wallet_ok:
                Intent intent1=new Intent(this, BalanceActivity.class);
                startActivity(intent1);
                break;
        }
    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_yajin)
                //设置dialogpadding
                .setPaddingdp(30, 0, 30, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.getView(R.id.tuihuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //押金退换

                dialog.dismiss();
            }
        });
        //取消
        dialog.getView(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}