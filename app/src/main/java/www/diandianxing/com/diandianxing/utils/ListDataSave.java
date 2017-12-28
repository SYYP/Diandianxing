package www.diandianxing.com.diandianxing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import www.diandianxing.com.diandianxing.bean.Addressbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ListDataSave {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return ;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        Log.d("strJson",strJson.toString());
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public  List<Addressbean> getDataList(String tag) {
       // List<T> datalist=new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
//        }.getType());
        List<Addressbean> retList = gson.fromJson(strJson,  new TypeToken<List<Addressbean>>() {  }.getType());
         Log.d("retlist",retList.toString());
        return retList;

    }
}
