package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.PayUtils;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class BalanceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private ImageView img_xuanze;
    private RelativeLayout wei;
    private ImageView img_xuanze1;
    private RelativeLayout zhi;
    private TextView chongzhi_ok;
    private TextView erbai,yibai,wushi,ershi,qita,shi;
     int i=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_chongziyu);
        initView();


    }



    private void initView() {
        erbai = (TextView) findViewById(R.id.erbai);
        yibai = (TextView) findViewById(R.id.yibai);
        wushi = (TextView) findViewById(R.id.wushi);
        ershi = (TextView) findViewById(R.id.ershi);
        qita = (TextView) findViewById(R.id.qita);
        shi = (TextView) findViewById(R.id.shi);
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        img_xuanze = (ImageView) findViewById(R.id.img_xuanze);
        wei = (RelativeLayout) findViewById(R.id.wei);
        img_xuanze1 = (ImageView) findViewById(R.id.img_xuanze1);
        zhi = (RelativeLayout) findViewById(R.id.zhi);
        chongzhi_ok = (TextView) findViewById(R.id.chongzhi_ok);
        zhong.setText("余额充值");
        erbai.setOnClickListener(this);
        yibai.setOnClickListener(this);
        wushi.setOnClickListener(this);
        ershi.setOnClickListener(this);
        qita.setOnClickListener(this);
        iv_callback.setOnClickListener(this);
        chongzhi_ok.setOnClickListener(this);
        wei.setOnClickListener(this);
        shi.setOnClickListener(this);
        zhi.setOnClickListener(this);
        SpUtils.putString(this,"banlacn",200+"");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.erbai:
                shi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                erbai.setBackgroundResource(R.drawable.shape_backorageradioline);
                yibai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(this,"banlacn",erbai.getText().toString());

                break;
            case R.id.shi:
                erbai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                shi.setBackgroundResource(R.drawable.shape_backorageradioline);
                yibai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(this,"banlacn",erbai.getText().toString());
                break;
            case R.id.yibai:
                shi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                yibai.setBackgroundResource(R.drawable.shape_backorageradioline);
                erbai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(this,"banlacn",yibai.getText().toString());
                break;
            case R.id.wushi:
                shi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backorageradioline);
                erbai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                yibai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(this,"banlacn",wushi.getText().toString());
                break;
            case R.id.ershi:
                shi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backorageradioline);
                erbai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                yibai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(this,"banlacn",ershi.getText().toString());
                break;
            case R.id.qita:
                 showphotoDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                break;
            case R.id.chongzhi_ok:
                String banlacn = SpUtils.getString(this, "banlacn", null);
                if(i==1){
                    if (banlacn!=null)
                    ToastUtils.show(this,"微信支付"+banlacn,1);
                 }
                else if(i==2){
                    if (banlacn!=null) {
                       // ToastUtils.show(this, "支付宝支付" + banlacn, 1);
                        PayUtils payutil = new PayUtils(this, 1, 2, "0.01");
                        payutil.goAlipay();
                    }

                }
                break;
            case R.id.wei:
                 img_xuanze.setImageResource(R.drawable.select_true);
                 img_xuanze1.setImageResource(R.drawable.select_false);
                i=1;
                break;
            case R.id.zhi:
                img_xuanze1.setImageResource(R.drawable.select_true);
                img_xuanze.setImageResource(R.drawable.select_false);
                i=2;
                break;
        }
    }
    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_balance)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 0)
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
        //确认
        View view = dialog.getView(R.id.ed_text);
         final EditText ed_text= view.findViewById(R.id.ed_text);
        dialog.getView(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qita.setText(ed_text.getText().toString());
                shi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                qita.setBackgroundResource(R.drawable.shape_backorageradioline);
                erbai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                wushi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                yibai.setBackgroundResource(R.drawable.shape_backhuiradioline);
                ershi.setBackgroundResource(R.drawable.shape_backhuiradioline);
                SpUtils.putString(BalanceActivity.this,"banlacn",qita.getText().toString());
                dialog.dismiss();
            }
        });
        //取消
        dialog.getView(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
