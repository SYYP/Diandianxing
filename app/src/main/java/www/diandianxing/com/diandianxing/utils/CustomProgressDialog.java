package www.diandianxing.com.diandianxing.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;


/**
 * Created by Administrator on 2017/10/11.
 */

public class CustomProgressDialog extends ProgressDialog {

    private TextView tv_load_dialog;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) { //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.myprogressdialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        tv_load_dialog= (TextView) findViewById(R.id.tv_load_dialog);
    }

    @Override
    public void show() {
        super.show();
//        try {
//            super.show();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

    }

}
