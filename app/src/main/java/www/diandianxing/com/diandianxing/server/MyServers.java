package www.diandianxing.com.diandianxing.server;

import android.content.Context;
import android.content.Intent;

import com.umeng.message.UmengBaseIntentService;

import www.diandianxing.com.diandianxing.DianDianActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyServers extends UmengBaseIntentService {
    @Override
    protected void onMessage(Context context, Intent intent) {
        super.onMessage(context, intent);
        {
            super.onMessage(context, intent);
            try
            {
                Intent data = new Intent(intent);
                data.setClass(context, DianDianActivity.class);
                data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//需为Intent添加Flag：Intent.FLAG_ACTIVITY_NEW_TASK，否则无法启动Activity。
                context.startActivity(data);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
