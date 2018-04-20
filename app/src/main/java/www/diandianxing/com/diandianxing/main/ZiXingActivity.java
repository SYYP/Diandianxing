package www.diandianxing.com.diandianxing.main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import www.diandianxing.com.diandianxing.IBackserver;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.Changbackbean;
import www.diandianxing.com.diandianxing.bean.Kailockbean;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.CameraManager;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.CaptureActivityHandler;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.InactivityTimer;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.ViewfinderView;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.server.BackService;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ZiXingActivity extends BaseActivity implements SurfaceHolder.Callback {
    private LinearLayout flash_btn;
    private CaptureActivityHandler handler;// 消息中心
    private ViewfinderView viewfinderView;// 绘制扫描区域
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔
    private boolean isTorchOn = true;
    private static final long VIBRATE_DURATION = 200L;
    private RelativeLayout rl_smq;
    private ProgressBar pb_jd;
    private LinearLayout ll_pro;
    private TextView tv_jd;
    private TextView tv_kaisuo;
    private String token, sid = "", rid = "";
    private BluetoothAdapter mBluetoothAdapter;
    int pro = 0;
    private static final int REQUEST_ENABLE_BT = 1;
    private int time = 300;
    private ImageView sm_fanhui;
    private boolean iswh;//维护过来
    private LinearLayout flash_shou;
    //创建一个Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理消息
        }
    };
    private TextView user_zhinan;
    private long sendTime = 0L;
    private String string = "\r\n";
    /**
     * 心跳检测时间
     */
    private static final long HEART_BEAT_RATE = 3 * 1000;
    // 发送心跳包
    private BackService.ReadThread mReadThread;
    private Handler handlers = new Handler();
    private IBackserver iBackserver;
    private Intent mServiceIntent;
    private boolean abool;
    private TextView text_shou;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_qrcode_capture_layout);
        mServiceIntent = new Intent(this, BackService.class);
        //  String bian = SpUtils.getString(this, "bian", null);

        CameraManager.init(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        rl_smq = (RelativeLayout) findViewById(R.id.rl_smq);
        flash_btn = (LinearLayout) findViewById(R.id.flash_btn);
        flash_shou = (LinearLayout) findViewById(R.id.flash_shou);


        ll_pro = (LinearLayout) findViewById(R.id.ll_pro);
        pb_jd = (ProgressBar) findViewById(R.id.pb_jd);
        tv_kaisuo = (TextView) findViewById(R.id.tv_kaisuo);
        tv_jd = (TextView) findViewById(R.id.tv_jd);
        user_zhinan = (TextView) findViewById(R.id.user_zhinan);
        text_shou = (TextView) findViewById(R.id.text_shou);
        text_shou.setText("手动开锁");
        Intent intent = getIntent();
        abool = intent.getBooleanExtra("abool", false);
        if (abool) {
            user_zhinan.setVisibility(View.GONE);
            text_shou.setText("手动输入");
        }

        user_zhinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ZiXingActivity.this, zhinanActivity.class);
                startActivity(intent1);
            }
        });
        sm_fanhui = (ImageView) findViewById(R.id.sm_fanhui);
//		sweep_code_view=findViewById(R.id.sweep_code_view);
//		sweep_code_view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getStatusHeight(SweepCodeActivity.this)));
//		sweep_code_view.setBackgroundResource(R.color.black);
        inactivityTimer = new InactivityTimer(this);

        iswh = getIntent().getBooleanExtra("iswh", false);

        rl_smq.setVisibility(View.VISIBLE);
        ll_pro.setVisibility(View.GONE);
        //链接
//    	Intent gattServiceIntent = new Intent(SweepCodeActivity.this, BluetoothLeService.class);
//    	bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        // Initializes list view adapter.
        sm_fanhui.setOnClickListener(listener);
        flash_btn.setOnClickListener(listener);
        flash_shou.setOnClickListener(listener);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!iswh) {
//			mServiceIntent = new Intent(this, BackService.class);
//	        // 开始服务
//	        registerReceiver();
//	        bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
//		}
            // 初始化相机画布
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            if (hasSurface) {
                initCamera(surfaceHolder);
            } else {
                surfaceHolder.addCallback(ZiXingActivity.this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }
            decodeFormats = null;
            characterSet = null;
            // 声音
            playBeep = true;
            // 初始化音频管理器
            AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                playBeep = false;
            }
            // initBeepSound();
            // 振动
            vibrate = true;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }







    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        // 停止相机扫描刷新timer
        inactivityTimer.shutdown();
        super.onDestroy();

// 		if(mBluetoothLeService!=null){
//			mBluetoothLeService.disconnect();
//			Log.e("TAG", "连接断开");
//		}
        mHandler.removeMessages(1);
        mHandler.removeCallbacks(runnable);
         if(mediaPlayer!=null){
             mediaPlayer.release();
             mediaPlayer=null;
         }

    }


    void restartCamera() {

        viewfinderView.setVisibility(View.VISIBLE);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        // 声音
        playBeep = true;
        // 初始化音频管理器
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // 振动
        vibrate = true;
    }


    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        //    inactivityTimer.onActivity();

        final String resultString = result.getText();
        Log.d("tag", "ma" + resultString + "");
          /*
             扫完后扫出单车编号
           */
        if (resultString != null) {
            //  String substring = resultString.substring(resultString.indexOf("="), resultString.length());
            String[] split = resultString.split("=");
            String substring = split[1];
            SpUtils.putString(this, "bianhao", substring);
              if(abool){
                  //调用Eventbus
                  EventMessage eventMessage = new EventMessage("weiting");
                  EventBus.getDefault().postSticky(eventMessage);
                  finish();
              }
            else {
                  //调用Eventbus
                  EventMessage eventMessage = new EventMessage("zxing");
                  EventBus.getDefault().postSticky(eventMessage);
                  Log.d("ress", substring + "");
                  finish();
              }

            //长连接
            //  initData(resultString);
        } else {//二维码正常
            Log.e("TAG", "码：" + resultString);

        }
    }


    private String fs;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        Log.e("TAG", "到这里了~~~");
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Log.e("TAG", "开始进度----3333333333");
            fs = "GPRS";


            startProgress();

            //用户点击蓝牙拒绝开启


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean pgss;

    //开启线程去执行进度
    private void startProgress() {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {


        @Override
        public void run() {
            int max = pb_jd.getMax();
            try {
                pgss = true;
                //子线程循环间隔消息
                while (pro < max) {
                    Log.e("TAG", "pro==" + pro + ",max=" + max);
                    pro += 1;
//                    if("GPRS".equals(fs)){
//                    	msg = new Message();
//                    	msg.what = 1;
//                    }else {
//                    	msg = new Message();
//                    	msg.what = 2;
//                    }

                    mHandler.sendEmptyMessage(1);
                    Thread.sleep(time);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            switch (arg0.getId()) {
                case R.id.flash_btn:
                    if (isTorchOn) {
                        isTorchOn = false;
                        CameraManager.start();
                    } else {
                        isTorchOn = true;
                        CameraManager.stop();
                    }
                    break;
                case R.id.sm_fanhui:
                    finish();
                    break;
                case R.id.flash_shou:
                    if(abool){
                        //调用Eventbus
                        EventMessage eventMessage = new EventMessage("shoush");
                        EventBus.getDefault().postSticky(eventMessage);
                        finish();
                    }
                    else {
                        //调用Eventbus
                        EventMessage eventMessage = new EventMessage("shoushi");
                        EventBus.getDefault().postSticky(eventMessage);
                        finish();
                    }

                    break;
            }
        }
    };


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        if (!hasSurface) {
            hasSurface = true;
            initCamera(arg0);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 声音设置
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();

            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * 结束后的声音
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}

