package www.diandianxing.com.diandianxing.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.CircleImageView;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class JourdetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private MapView map;
    private CircleImageView circleImageView;
    private LinearLayout liner_qi;
    private View view;
    private TextView textView2;
    private LinearLayout linearLayout4;
    private ImageView imageView3;
     AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        /*
          禁止弹出虚拟键盘
         */
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        setContentView(R.layout.activity_jourdetail);
        initView();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        map.onCreate(savedInstanceState);
        datamap();
    }

    private void datamap() {
        if (aMap == null) {
            aMap = map.getMap();
            //地图初始化时设置的可见比例
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        }
    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        map = (MapView) findViewById(R.id.map);
        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        liner_qi = (LinearLayout) findViewById(R.id.liner_qi);
        view = (View) findViewById(R.id.view);
        textView2 = (TextView) findViewById(R.id.textView2);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        zhong.setText("行程详情");
        iv_callback.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
      map.onSaveInstanceState(outState);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
        }
    }
}
