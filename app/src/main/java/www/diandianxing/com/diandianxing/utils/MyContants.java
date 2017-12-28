package www.diandianxing.com.diandianxing.utils;

import android.app.Activity;
import android.view.View;

public class MyContants {
    public static String FILENAME = "config.xml";
    public static String BASEURL="http://172.16.9.148/danche/api.php?";
    public static String PHOTO="http://172.16.9.148";

    public static  void windows(Activity activity){

       activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}
