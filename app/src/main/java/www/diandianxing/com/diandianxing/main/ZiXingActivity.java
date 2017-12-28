package www.diandianxing.com.diandianxing.main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.ArrayList;
import java.util.Vector;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.CameraManager;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.CaptureActivityHandler;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.InactivityTimer;
import www.diandianxing.com.diandianxing.bjxf.ms.qr.ViewfinderView;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ZiXingActivity extends BaseActivity implements SurfaceHolder.Callback{
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
    private String token,sid="",rid="";
    private BluetoothAdapter mBluetoothAdapter;
    int pro=0;
    private static final int REQUEST_ENABLE_BT = 1;
    // 10s后停止扫描
    private static final long SCAN_PERIOD = 10000;
    private boolean mScanning;
    private final static String TAG = "TAG";
    private String address;
    private Message msg;

    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private int mGroupPostion;
    private int mchildPostion;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private String lat,lng;

    private BluetoothDevice bluetoothDevice;

    //写数据
    private BluetoothGattCharacteristic characteristic;
    private BluetoothGattService mnotyGattService;

    private int time=300;

    //读数据
//    private BluetoothGattCharacteristic readCharacteristic;
//    private BluetoothGattService readMnotyGattService;
//    byte[] WriteBytes={0x28,(byte) 0xa5, 0x01, 0x00, 0x0a, 0x00, 0x02, (byte) 0xaf, (byte) 0x87, 0x16, (byte) 0xb8, 0x14, (byte) 0xf1, (byte) 0xf4, 0x6d, 0x5a, 0x29};
    byte[] WriteBytes;

    //    \x02\x00\x0A\x00\x02\x43\x16\xCC\x4B\x0B\x52
    byte[] ID={0x01, 0x00, 0x0a, 0x00, 0x02,(byte) 0xaf, (byte) 0x87, 0x16, (byte) 0xb8, 0x14, (byte) 0xf1};
    //    byte[] ID={0x02, 0x00, 0x0a, 0x00, 0x02,(byte) 0x43, (byte) 0x16, (byte) 0xcc, (byte) 0x4b, 0x0b, (byte) 0x52};
    private String head="28 A5";

    private String locksetID1="AF 87 16 B8 14 F1";
    private String locksetID2="43 16 cc 4b 0b 52";
    private String BS="01 00 0a 00 02";

    private String state="01 00 0a 00 04";//状态查询

    private String crc;

    private String ending="5A 29";

    private int amount=-1;//总金额

    private boolean istiao=false;
    //	public IBackService iBackService;
    private Intent mServiceIntent;
    public String json_str;

    private int errorCode;
    private int isdy=1;
    private int codeju;
    private boolean isyc;
    private ImageView sm_fanhui;
    private boolean iswh;//维护过来
    private View sweep_code_view;
    //创建一个Handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理消息
        }
    };
    private TextView user_zhinan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_qrcode_capture_layout);

        final Intent intent = getIntent();
        lat=intent.getStringExtra("lat");
        lng=intent.getStringExtra("lng");

        CameraManager.init(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        rl_smq=(RelativeLayout) findViewById(R.id.rl_smq);
        flash_btn=(LinearLayout) findViewById(R.id.flash_btn);
        ll_pro=(LinearLayout) findViewById(R.id.ll_pro);
        pb_jd=(ProgressBar) findViewById(R.id.pb_jd);
        tv_kaisuo=(TextView) findViewById(R.id.tv_kaisuo);
        tv_jd=(TextView) findViewById(R.id.tv_jd);
        user_zhinan = (TextView) findViewById(R.id.user_zhinan);
        user_zhinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ZiXingActivity.this,zhinanActivity.class);
                startActivity(intent1);
            }
        });
        sm_fanhui=(ImageView) findViewById(R.id.sm_fanhui);
//		sweep_code_view=findViewById(R.id.sweep_code_view);
//		sweep_code_view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getStatusHeight(SweepCodeActivity.this)));
//		sweep_code_view.setBackgroundResource(R.color.black);
        inactivityTimer = new InactivityTimer(this);

        iswh=getIntent().getBooleanExtra("iswh", false);

        rl_smq.setVisibility(View.VISIBLE);
        ll_pro.setVisibility(View.GONE);
        //链接
//    	Intent gattServiceIntent = new Intent(SweepCodeActivity.this, BluetoothLeService.class);
//    	bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        // Initializes list view adapter.
        sm_fanhui.setOnClickListener(listener);
        flash_btn.setOnClickListener(listener);

    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(!iswh){
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
            initBeepSound();
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
//        scanLeDevice(false);
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
//		unbindService(mServiceConnection);
//        mBluetoothLeService = null;
//        unregisterReceiver(receiver);
//        unregisterReceiver(mGattUpdateReceiver);


        //用户
//        if(mReceiver!=null&&conn!=null){
//        	// 注销广播 最好在onPause上注销
//        	unregisterReceiver(mReceiver);
//        	// 注销服务
//        	unbindService(conn);
//        }

    }



    void restartCamera(){

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
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if ("哈哈".equals(resultString)) {
              //扫码成功返回主页面
            /*
              调用evevnbus刷新主页面，切换布局
             */
            //调用Eventbus
            EventMessage eventMessage = new EventMessage("zxing");
            EventBus.getDefault().postSticky(eventMessage);
            SpUtils.putString(this,"bikenum","123456");
            finish();

        }


        else {//二维码正常
            Log.e("TAG", "码："+resultString);

//        	boolean contains = resultString.contains("http://www.moshroom.cn/q");
//        	if(contains){
//        		String neirong = resultString.substring(resultString.lastIndexOf("/")+1,resultString.length());
//
//        		String decode = Base64.decode(neirong);
//
//        		String uid=decode.substring(0, 12);
//        		String mac=decode.substring(12, decode.length());
//
//        		char[] chrCharArray; //创建一个字符数组chrCharArray
//        		chrCharArray = uid.toCharArray();
//
//        		StringBuffer uid_sb = new StringBuffer();
//
//        		for(int i=0;i<chrCharArray.length;i++){
//
//
//        			if(i%2==0){
//        				if(i==0){
//        					uid_sb.append(chrCharArray[i]);
//        				}else{
//        					uid_sb.append(" "+chrCharArray[i]);
//        				}
//        			}else{
//        				uid_sb.append(chrCharArray[i]);
//        			}
//        		}
//
//
//        		char[] macArray; //创建一个字符数组chrCharArray
//        		macArray = mac.toCharArray();
//
//        		StringBuffer mac_sb = new StringBuffer();
//
//        		for(int i=0;i<macArray.length;i++){
//
//
//        			if(i%2==0){
//        				if(i==0){
//        					mac_sb.append(macArray[i]);
//        				}else{
//        					mac_sb.append(":"+macArray[i]);
//        				}
//        			}else{
//        				mac_sb.append(macArray[i]);
//        			}
//        		}
//        		Log.e("TAG", "decode=="+uid_sb.toString()+",mac=="+mac_sb.toString());
//        		locksetID1=uid_sb.toString();
//
//        		address=mac_sb.toString();
//
//        		//开锁成功，需要连接长链接
//        		String[] split = locksetID1.split(" ");
//        		StringBuffer sb = new StringBuffer();
//        		for(int i=0;i<split.length;i++){
//        			int stringToAsciiString = StringToHex.StringToAsciiString(split[i]);
//        			if(split.length-1==i){
//        				sb.append(stringToAsciiString+"");
//        			}else{
//        				sb.append(stringToAsciiString+"-");
//        			}
//        		}
//        		sid=sb.toString();
//            	//长链接发送锁具ID
//
//        		MyThread.getInstance().execute(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						try {
//		    				Log.i(TAG, "是否为空：" + iBackService);
//		    				if (iBackService == null) {
//		    					Toast.makeText(getApplicationContext(),
//		    							"没有连接，可能是服务器已断开", Toast.LENGTH_SHORT).show();
//		    				} else {
//		    					boolean isSend = iBackService.sendMessage(sid);
//		    					if(!isSend){
//		    						handler.post(new Runnable() {
//
//										@Override
//										public void run() {
//											// TODO Auto-generated method stub
//											Toast.makeText(SweepCodeActivity.this, "开锁异常，请重试", 0).show();
//				    						finish();
//				    						return;
//										}
//									});
//
//		    					}
////		    					Toast.makeText(SweepCodeActivity.this,
////		    							isSend ? "success" : "fail", Toast.LENGTH_SHORT)
////		    							.show();
//		    					if(!isSend){
//		    						isSend = iBackService.sendMessage(sid);
//		    						if(!isSend){
//		    							handler.post(new Runnable() {
//
//											@Override
//											public void run() {
//												// TODO Auto-generated method stub
//												Toast.makeText(SweepCodeActivity.this, "开锁异常，请重试", 0).show();
//					    						finish();
//					    						return;
//											}
//										});
//		        					}
////		    						Toast.makeText(SweepCodeActivity.this,
////		    								isSend ? "success" : "fail", Toast.LENGTH_SHORT)
////		    								.show();
//
////		    						iBackService.QC(sid);
//		    					}
//		    				}
//		    			} catch (RemoteException e) {
//		    				e.printStackTrace();
//		    			}
//					}
//				});
//
//
//
//    			if(iswh){
//    	            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//    	                Toast.makeText(SweepCodeActivity.this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
//    	                finish();
//    	            }
//
//    	            final BluetoothManager bluetoothManager =
//    	                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//    	            mBluetoothAdapter = bluetoothManager.getAdapter();
//
//    	            if (mBluetoothAdapter == null) {
//    	                Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
//    	                finish();
//    	                return;
//    	            }
//    	            if (!mBluetoothAdapter.isEnabled()) {
//    	            	Log.e("TAG", "蓝牙未连接");
//    	                if (!mBluetoothAdapter.isEnabled()) {
//    	                	Log.e("TAG", "蓝牙未连接");
//    	                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//    	                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//    	                    rl_smq.setVisibility(View.INVISIBLE);
//    	            		ll_pro.setVisibility(View.VISIBLE);
//    	                }else{
//    	                	rl_smq.setVisibility(View.INVISIBLE);
//    	                	ll_pro.setVisibility(View.VISIBLE);
//    	                	Log.e("TAG", "开始进度----1111111111");
//    	                	startProgress();
//    	                }
//    	            }else{
//    	            	rl_smq.setVisibility(View.INVISIBLE);
//    	            	ll_pro.setVisibility(View.VISIBLE);
//    	            	Log.e("TAG", "开始进度----22222222222");
//    	            	startProgress();
//    	            }
//    	            mBluetoothLeService.connect(address);
//
//    			}else{
//    				getMyYue();
//    			}
//        	}else{
//        		Toast.makeText(SweepCodeActivity.this, "二维码格式不对", 0).show();
//        		finish();
//        	}
        }
    }



    private String fs;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        Log.e("TAG", "到这里了~~~");
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Log.e("TAG", "开始进度----3333333333");
            fs="GPRS";


            startProgress();

            //用户点击蓝牙拒绝开启


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean pgss;

    //开启线程去执行进度
    private void startProgress()
    {
        new Thread(runnable).start();
    }

    private Runnable runnable=new Runnable() {


        @Override
        public void run() {
            int max = pb_jd.getMax();
            try {
                pgss = true;
                //子线程循环间隔消息
                while (pro < max) {
                    Log.e("TAG", "pro=="+pro+",max="+max);
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
            switch(arg0.getId()){
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

//    private ServiceConnection conn = new ServiceConnection() {
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			// 未连接为空
//			iBackService = null;
//		}
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// 已连接
//			iBackService = IBackService.Stub.asInterface(service);
//			try {
//				if(iswh){
//					iBackService.QC("WHSweepCodeActivity");
//				}else{
//					iBackService.QC("SweepCodeActivity");
//				}
//
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//	};


//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
//            // TODO Auto-generated method stub
//            String action = arg1.getAction();
//            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
//                int state = arg1.getIntExtra(BluetoothAdapter.EXTRA_STATE,
//                        BluetoothAdapter.ERROR);
//                switch (state) {
//                    case BluetoothAdapter.STATE_OFF:
//                        Log.e("TAG", "STATE_OFF 手机蓝牙关闭");
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_OFF:
//                        Log.e("TAG", "STATE_TURNING_OFF 手机蓝牙正在关闭");
//                        break;
//                    case BluetoothAdapter.STATE_ON:
//                        Log.d("TAG", "STATE_ON 手机蓝牙开启");
//
//                        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
//                        // fire an intent to display a dialog asking the user to grant permission to enable it.
//                        if (!mBluetoothAdapter.isEnabled()) {
//                            if (!mBluetoothAdapter.isEnabled()) {
//                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//                            }
//                        }
//                        time=300;
//                        Log.e("TAG", "开始进度----444444444444");
//                        startProgress();
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_ON:
//                        Log.d("TAG", "STATE_TURNING_ON 手机蓝牙正在开启");
//                        break;
//                }
//            }
//
//        }
//    };

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}
