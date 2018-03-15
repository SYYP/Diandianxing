package www.diandianxing.com.diandianxing.server;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import www.diandianxing.com.diandianxing.IBackserver;
import www.diandianxing.com.diandianxing.base.Myapplication;

public class BackService extends Service {
    private static final String TAG = "BackService";
    /** 心跳检测时间  */
    private static final long HEART_BEAT_RATE = 3 * 1000;
    /** 主机IP地址  */
    //private static final String HOST = "114.215.83.139";
    private static final String HOST = "47.93.45.38";
    /** 端口号  */
    public static final int PORT = 4323;
    /** 消息广播  */
    public static final String MESSAGE_ACTION = "org.feng.message_ACTION";
    /** 心跳广播  */
    public static final String HEART_BEAT_ACTION = "org.feng.heart_beat_ACTION";

    private long sendTime = 0L;

    /** 弱引用 在引用对象的同时允许对垃圾对象进行回收  */
    private WeakReference<Socket> mSocket;

    private ReadThread mReadThread;

    private String string="\r\n";
    private Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();
    }
  private  IBackserver.Stub iBackService= new IBackserver.Stub() {
      @Override
      public boolean sendMessage(String message) throws RemoteException {
          boolean abool=sendMsg(message);
          Log.d("taga",abool+"--------");
          return abool;
      }

      @Override
      public void shibie()  {
         sss();
      }

      @Override
      public void qidong() {
          try {
              initSocket();
          } catch (IOException e) {
              e.printStackTrace();
          }
          new InitSocketThread().start();
      }
  };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tagah","---------------------");
        return START_NOT_STICKY;
    }

    public void sss() {
       mReadThread.release();
     }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) iBackService;
    }

    // 发送心跳包


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("tag---","解绑");
        return super.onUnbind(intent);
    }

    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                boolean isSuccess = sendMsg(string);// 就发送一个\r\n过去, 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSocketThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    public boolean sendMsg(String msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        Socket soc = mSocket.get();
        try {
            if (!soc.isClosed() && !soc.isOutputShutdown()) {
                OutputStream os = soc.getOutputStream();
                String message = msg ;
                os.write(message.getBytes());
                os.flush();
                sendTime = System.currentTimeMillis();// 每次发送成功数据，就改一下最后成功发送的时间，节省心跳间隔时间
                Log.i(TAG, "发送成功的时间：" + sendTime);
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 初始化socket
    private void initSocket() throws UnknownHostException, IOException {
        socket = new Socket(HOST, PORT);
        mSocket = new WeakReference<Socket>(socket);
        mReadThread = new ReadThread(socket);
        mReadThread.start();
//		mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);// 初始化成功后，就准备发送心跳包
    }

    // 释放socket
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CQ(String string){
        this.string=string;
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                initSocket();
            } catch (final UnknownHostException e) {
                Log.e("TAG", ""+"UnknownHostException绑定IP错误:"+e.getMessage().toString());
                e.printStackTrace();
            } catch (final IOException e) {
                Log.e("TAG", ""+"IOException绑定IP错误:"+e.getMessage().toString());
                e.printStackTrace();
            }
        }
    }

    public class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;


        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @SuppressLint("NewApi")
        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    if(is!=null){
                        while (!socket.isClosed() && !socket.isInputShutdown()
                                && isStart && ((length = is.read(buffer)) != -1)) {
                            if (length > 0) {
                                String message = new String(Arrays.copyOf(buffer,
                                        length)).trim();
                                Log.i(TAG, "收到服务器发送来的消息："+message);
                                // 收到服务器过来的消息，就通过Broadcast发送出去
                                Log.d("mess",message+"");

                                // 其他消息回复
                                Intent intent = new Intent(MESSAGE_ACTION);
                                intent.putExtra("message", message);
                                LocalBroadcastManager.getInstance(Myapplication.getApplication()).sendBroadcast(intent);

                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
