package www.diandianxing.com.diandianxing.guidance;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import www.diandianxing.com.diandianxing.MainActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.SpUtils;


/**
 * Created by Administrator on 2017/8/24.
 */

public class LancherActivity extends BaseActivity {

    private int time = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                time--;
                if (time == 0) {
                    boolean isguide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
                    if (isguide) {
                        startActivity(new Intent(LancherActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LancherActivity.this, GuidePageActivity.class));
                    }
                    finish();
                } else {
                    Message message = mHandler.obtainMessage(1);
                    mHandler.sendMessageDelayed(message, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mHandler.postDelayed(
                new Runnable() {
            @Override
            public void run() {
                boolean isguide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
                if (isguide) {
                    startActivity(new Intent(LancherActivity.this,MainActivity.class));
                } else {
                    startActivity(new Intent(LancherActivity.this, GuidePageActivity.class));
                }
                finish();
            }
        }, 1500);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
