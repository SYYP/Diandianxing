package www.diandianxing.com.diandianxing.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.litepal.LitePalApplication;

import java.util.Iterator;
import java.util.List;


/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myapplication extends LitePalApplication {
    public static Myapplication application;



    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initUMShare();
        Config.DEBUG = true;
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

    }

    public static Myapplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public static Application getInstance() {
        return  application;
    }
    public static Context getGloableContext()    {
        return  application.getApplicationContext();
    }

    private void initUMShare() {
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx04eb0c6da77b669c", "f70f9f76294b47c2d107808a0ecb8d66");
        PlatformConfig.setQQZone("1106567631", "QRCByySV1Wqbc93c");
        PlatformConfig.setSinaWeibo("557964441", "b52b29e8a5393bd34e2315e509fb5842", "http://www.baidu.com");//回调地址要跟微博开放平台的一样
    }
}
