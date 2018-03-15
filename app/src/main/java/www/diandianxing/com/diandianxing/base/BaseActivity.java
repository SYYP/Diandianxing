package www.diandianxing.com.diandianxing.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lxk on 2017/6/10.
 */

public class BaseActivity extends AppCompatActivity {
    private static List<Activity> activityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里判断是否token是否存在、是否过期之类的
        if (activityList != null)
            activityList.add(this);


    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //        overridePendingTransition(R.anim.start_activity_in, R.anim.start_activity_out);
    }

    @Override
    public void finish() {
        super.finish();
        //        overridePendingTransition(R.anim.finish_activity_in, R.anim.finish_activity_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null)
            activityList.remove(this);
    }

    /*
    * 必须调用 MobclickAgent.onResume() 和 MobclickAgent.onPause()方法，
    * 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
    * 这两个方法是用来统计应用时长的(也就是Session时长,当然还包括一些其他功能)
    * MobclickAgent.onPageStart() 和 MobclickAgent.onPageEnd() 方法是用来统计页面跳转的
    * 在仅有Activity的应用中，SDK 自动帮助开发者调用了上面的方法，并把Activity 类名作为页面名称统计。
    * 但是在包含fragment的程序中我们希望统计更详细的页面，所以需要自己调用方法做更详细的统计。
    * */
    @Override
    protected void onResume() {
        super.onResume();
       // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  MobclickAgent.onPause(this);
    }

    public static List<Activity> getAllActivitys() {
        return activityList;
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            //            activityList = null;
        }
    }

    public static void realBack() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            activityList = null;
        }
    }
//    private void UMPush() {
//        UmengMessageHandler umengMessageHandler=new UmengMessageHandler(){
//            @Override
//            public Notification getNotification(Context context, UMessage uMessage) {
//                EventMessage eventMessage=new EventMessage("tuisong");
//                EventBus.getDefault().postSticky(eventMessage);
//                return super.getNotification(context, uMessage);
//
//            }
//        };
//
//
//
//    }


}
