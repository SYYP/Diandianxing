package www.diandianxing.com.diandianxing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.diandianxing.com.diandianxing.Login.LoginActivity;
import www.diandianxing.com.diandianxing.bean.BikeBean;
import www.diandianxing.com.diandianxing.bean.Shouyebean;
import www.diandianxing.com.diandianxing.main.GuZhangActivity;
import www.diandianxing.com.diandianxing.main.KefuActivity;
import www.diandianxing.com.diandianxing.main.OtherActivity;
import www.diandianxing.com.diandianxing.main.ZiXingActivity;
import www.diandianxing.com.diandianxing.my.CashpayActivity;
import www.diandianxing.com.diandianxing.my.MessageActivity;
import www.diandianxing.com.diandianxing.my.MyActivityActivity;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.overlay.WalkRouteOverlay;
import www.diandianxing.com.diandianxing.sousuo.SearchActivity;
import www.diandianxing.com.diandianxing.utils.AMapUtil;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

import static www.diandianxing.com.diandianxing.R.id.map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AMap.OnMyLocationChangeListener
        ,AMap.OnMapTouchListener,RouteSearch.OnRouteSearchListener,AMap.OnMapClickListener {

    private ImageView img_my;
    private ImageView img_message;
    private RelativeLayout activity_main;
    private RelativeLayout relativeLayout;
    private ImageView iv_search;
    private ImageView iv_kefu;
    private ImageView iv_refresh;
    private ImageView iv_address;
    private LinearLayout linearLayout3;
    private ImageView iv_lock;
    private ImageView img_che;
    private LinearLayout real_gongxiang;
    private ImageView img_wei;
    private LinearLayout real_dianzi;
    private RelativeLayout main_liner;
    private TextView text_jiaona;
    MapView mMapView = null;
    AMap aMap;
    private Location locations;
    private double lat;
    private double lon;
    private boolean followMove = true;
    private UiSettings mUiSettings;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private ArrayList<LatLng> list;
    MyLocationStyle myLocationStyle;
    private ArrayList<LatLng> list1;
    private MarkerOptions markerOption;
    Marker tempMark;
    private LatLonPoint mStartPoint = null;//起点，116.335891,39.942295
    private LatLonPoint mEndPoint = null;//终点，116.481288,39.995576
    private double latitude;
    private double longitude;
    private final int ROUTE_TYPE_WALK = 3;
    private RouteSearch mRouteSearch;
    private Marker marker;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private WalkRouteResult mWalkRouteResult;
    private LatLng latLng;
    private boolean isClickIdentification = false;
    private WalkRouteOverlay walkRouteOverlay;
    private BaseDialog dialog;
    private String [] time;
    private String distance;
    private TextView tv_address;
    private TextView tv_time;
    private TextView tv_chewei;
    private TextView miao_juli;
    private LinearLayout liner_mark;
    private TextView miao_shengyu;
    private TextView miao_time;
    private String des;
    Handler handler=new Handler();
    private TextView tv_juli;
    private LinearLayout liner_linshi;
    private TextView text_tingche;
    private LinearLayout liner_jishi;
    private  String formatAddress;
    boolean bool;
    private CameraUpdate mUpdata;
    private Shouyebean.DatasBean datas;
    private TextView bike_number;
    private TextView qi_time;
    private TextView qi_juli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        /*
           网络请求
         */
//加载uI
        initView();
        dataview();

        datamap();

        //加载点点行自行车
        addmark();
        //首先页面初始化时加载的是点点行车页面，保存当前状态
        SpUtils.putString(this, "address", "gongxiang");
        //data加载数据
        network();
        //改变数据
         intdata();
    }

    private void dataview() {

    }

    private void intdata() {
         /*
           是否显示押金
          */

    }

    private void network() {

        Map<String,String> map=new HashMap<>();
         map.put("uid",SpUtils.getString(this,"userid",null));
         map.put("token",SpUtils.getString(this,"token",null));
        RetrofitManager.get(MyContants.BASEURL + "s=Bike/myInfo", map, new BaseObserver1<Shouyebean>("") {




            @Override
            public void onSuccess(Shouyebean result, String tag) {
                int code = result.getCode();
                if(code==200){
                    datas = result.getDatas();
                    Log.d("ssss",datas.getRidingState());
                    int ride = Integer.parseInt(datas.getRidingState());
                    int istemp = Integer.parseInt(datas.getIsTemporary());
                    SpUtils.putString(MainActivity.this,"token", datas.getToken());
                     SpUtils.putString(MainActivity.this,"paiphoto",MyContants.PHOTO+datas.getHeadImageUrl()+"");
                     SpUtils.putString(MainActivity.this,"nickname",datas.getNickName()+"");
                     SpUtils.putString(MainActivity.this,"yajin",datas.getSecurityDeposit());//押金
                     SpUtils.putString(MainActivity.this,"yue",datas.getBalance());//余额
                    // intent.putExtra("photo",datas.getHeadImageUrl());
                    if(datas.getSecurityDeposit()!=null&&!("0".equals(datas.getSecurityDeposit()))){
                        main_liner.setVisibility(View.GONE);
                    }
                    else if(datas.getBikeNumber()!=null){
                         bike_number.setText(datas.getBikeNumber());
                    }
                    else if(datas.getBikeDistance()!=null){

                          qi_juli.setText(datas.getBikeDistance());
                    }
                    else if(datas.getBikeTime()!=null){
                         qi_time.setText(datas.getBikeTime());
                    }
                    Log.d("ff",ride+"");
                     if(ride==1){
                        Log.d("ss","sssssssssssssssssssss");
                        real_gongxiang.setVisibility(View.GONE);
                        real_dianzi.setVisibility(View.GONE);
                        iv_lock.setVisibility(View.GONE);
                        liner_linshi.setVisibility(View.VISIBLE);
                        iv_refresh.setVisibility(View.GONE);
                        iv_search.setVisibility(View.GONE);
                        liner_jishi.setVisibility(View.VISIBLE);
                        main_liner.setVisibility(View.GONE);

                    }
                     if(istemp==0){

                         text_tingche.setText("继续骑行");
                     }
                    else if(istemp==1){
                         text_tingche.setText("临时停车");
                     }

                }

            }

            @Override
            public void onFailed(int code) {

                 if(code==404){
                     ToastUtils.showShort(MainActivity.this,"账号已过期，请重新登录");
                      Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                     startActivity(intent);

                 }
            }
        });

    }

    /*
       加载数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventMessage eventMessage) {
         //扫码成功后刷新主页面
        if (eventMessage.getMsg().equals("zxing")) {
          //  liner_mark.setVisibility(View.GONE);
            SpUtils.putString(this, "address", "dianzi");
            img_che.setImageResource(R.drawable.img_gongxiangfalse);
            img_wei.setImageResource(R.drawable.img_dian_true);
            aMap.clear();
            datamap();
            adddianzimark();
            network();
//            real_gongxiang.setVisibility(View.GONE);
//            real_dianzi.setVisibility(View.GONE);
//            iv_lock.setVisibility(View.GONE);
//            liner_linshi.setVisibility(View.VISIBLE);
//            iv_refresh.setVisibility(View.GONE);
//            iv_search.setVisibility(View.GONE);
//            liner_jishi.setVisibility(View.VISIBLE);
//            main_liner.setVisibility(View.GONE);


        }
        else if(eventMessage.getMsg().equals("myposition")){
             datamap();


        }
        else if(eventMessage.getMsg().equals("lishi")){
            //常用地址
            //修改地图的中心点位置
            String lat = SpUtils.getString(this, "lat", null);
            String lon = SpUtils.getString(this, "lon", null);
            double v = Double.valueOf(lat).doubleValue();
            double v1 = Double.valueOf(lon).doubleValue();
            Log.d("vvv",v+"dddd"+v1+"");
//            mUiSettings = aMap.getUiSettings();
//            mUiSettings.setZoomControlsEnabled(false);
//            mUiSettings.setCompassEnabled(true);
            mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
                    new CameraPosition(new LatLng(v,v1), 15, 0, 30));//这是地理位置，就是经纬度。
            aMap.moveCamera(mUpdata);//定位的方法

        }
        else if(eventMessage.getMsg().equals("sousou")){
            //常用地址
            //修改地图的中心点位置
            String lat = SpUtils.getString(this, "lat", null);
            String lon = SpUtils.getString(this, "lon", null);
            double v = Double.valueOf(lat).doubleValue();
            double v1 = Double.valueOf(lon).doubleValue();
            Log.d("vvv",v+"dddd"+v1+"");
//            mUiSettings = aMap.getUiSettings();
//            mUiSettings.setZoomControlsEnabled(false);
//            mUiSettings.setCompassEnabled(true);
            mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
                    new CameraPosition(new LatLng(v,v1), 15, 0, 30));//这是地理位置，就是经纬度。
            aMap.moveCamera(mUpdata);//定位的方法

        }
        else if(eventMessage.getMsg().equals("changyong")){
            //常用地址
            //修改地图的中心点位置
            String lat = SpUtils.getString(this, "lat", null);
            String lon = SpUtils.getString(this, "lon", null);
            double v = Double.valueOf(lat).doubleValue();
            double v1 = Double.valueOf(lon).doubleValue();
            Log.d("vvv",v+"dddd"+v1+"");
//            mUiSettings = aMap.getUiSettings();
//            mUiSettings.setZoomControlsEnabled(false);
//            mUiSettings.setCompassEnabled(true);
            mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
             new CameraPosition(new LatLng(v,v1), 15, 0, 30));//这是地理位置，就是经纬度。
             aMap.moveCamera(mUpdata);//定位的方法
         //   myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
          //  aMap.setMyLocationStyle(myLocationStyle);

           // aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

//                  myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//          //  myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
////aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//            myLocationStyle.showMyLocation(false);
//            aMap.setMyLocationEnabled(true);//

//
//            //初始化定位服务
//            initLocationService();
        }
    }

    private void data() {
        niaddress();
           //通过状态值判断是自行车，还是电子围栏


    }

    /*
      初始化地图
     */
    private void datamap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            //地图初始化时设置的可见比例
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        }
        //设置logo位置
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
        mUiSettings.setZoomControlsEnabled(false);

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
     //   myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loation_locin);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapTouchListener(this);
        aMap.setOnMapClickListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void initView() {
        img_my = (ImageView) findViewById(R.id.img_my);
        img_message = (ImageView) findViewById(R.id.img_message);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        img_my.setOnClickListener(this);
        img_message.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(this);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        iv_kefu = (ImageView) findViewById(R.id.iv_kefu);
        iv_kefu.setOnClickListener(this);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        iv_refresh.setOnClickListener(this);
        iv_address = (ImageView) findViewById(R.id.iv_address);
        iv_address.setOnClickListener(this);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setOnClickListener(this);
        iv_lock = (ImageView) findViewById(R.id.iv_lock);
        iv_lock.setOnClickListener(this);
        img_che = (ImageView) findViewById(R.id.img_che);
        img_che.setOnClickListener(this);
        real_gongxiang = (LinearLayout) findViewById(R.id.real_gongxiang);
        real_gongxiang.setOnClickListener(this);
        img_wei = (ImageView) findViewById(R.id.img_wei);
        img_wei.setOnClickListener(this);
        main_liner = (RelativeLayout) findViewById(R.id.main_liner);
        text_jiaona = (TextView) findViewById(R.id.jiaona);
        real_dianzi = (LinearLayout) findViewById(R.id.real_dianzi);
        tv_address = (TextView) findViewById(R.id.infor_address);
        tv_time = (TextView) findViewById(R.id.infor_time);
        tv_juli = (TextView) findViewById(R.id.infor_juli);
        tv_chewei = (TextView) findViewById(R.id.infor_chewei);
        miao_juli = (TextView) findViewById(R.id.miao_juli);
        miao_shengyu = (TextView) findViewById(R.id.miao_shengyu);
        miao_time = (TextView) findViewById(R.id.miao_time);
        liner_mark = (LinearLayout) findViewById(R.id.liner_mark);
        liner_linshi = (LinearLayout) findViewById(R.id.btn_linshi);
        text_tingche = (TextView) findViewById(R.id.text_tingche);
        liner_linshi.setOnClickListener(this);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        real_dianzi.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_address.setOnClickListener(this);
        iv_kefu.setOnClickListener(this);
        iv_lock.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        real_gongxiang.setOnClickListener(this);
        real_dianzi.setOnClickListener(this);
        text_jiaona.setOnClickListener(this);
        bike_number = (TextView) findViewById(R.id.bike_number);
        qi_time = (TextView) findViewById(R.id.qi_time);
        qi_juli = (TextView) findViewById(R.id.qi_juli);
        liner_jishi = (LinearLayout) findViewById(R.id.liner_jishi);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linshi:

                if(bool){
                    //临时停车,再次点击变为继续骑行
                    text_tingche.setText("临时停车");
                    showxingDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                }
                else {
                    //临时停车,再次点击变为继续骑行
                    text_tingche.setText("继续骑行");
                    showqixingDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                }
                bool=!bool;

                break;
            //我的
            case R.id.img_my:
                Intent intent = new Intent(this, MyActivityActivity.class);

                 intent.putExtra("fenshu",datas.getCredit()+"");
                startActivity(intent);
                break;
            //消息
            case R.id.img_message:
                Intent intent1 = new Intent(this, MessageActivity.class);
                startActivity(intent1);
                break;
            //搜索
            case R.id.iv_search:
                //跳到搜索页面
                Intent search=new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.iv_kefu:
                //客服
                showphotoDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
            case R.id.iv_lock:
                Intent intent2 = new Intent(this, ZiXingActivity.class);
                startActivity(intent2);
                //扫码
                break;
            case R.id.iv_address:
                datamap();
                String address = SpUtils.getString(this, "address", null);
                if (address.equals("gongxiang")) {
                    addmark();

                } else if (address.equals("dianzi")) {
                    adddianzimark();

                }
                //定位
                break;
            case R.id.iv_refresh:
                //刷新
                datamap();
                break;
            case R.id.real_gongxiang:
                liner_mark.setVisibility(View.GONE);
                SpUtils.putString(this, "address", "gongxiang");
                aMap.clear();
                addmark();
                img_che.setImageResource(R.drawable.img_gongbick);
                img_wei.setImageResource(R.drawable.img_dianziwei);
                break;
            case R.id.real_dianzi:
                liner_mark.setVisibility(View.GONE);
                SpUtils.putString(this, "address", "dianzi");
                img_che.setImageResource(R.drawable.img_gongxiangfalse);
                img_wei.setImageResource(R.drawable.img_dian_true);
                aMap.clear();
                adddianzimark();
                break;
            case R.id.jiaona:
                Intent intent3 = new Intent(this, CashpayActivity.class);
                startActivity(intent3);
                break;
        }

    }

    private void showphotoDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_shouke)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        //违停
        dialog.getView(R.id.real_weiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KefuActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //故障
        dialog.getView(R.id.real_guzhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuZhangActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });
        //取消
        dialog.getView(R.id.real_qita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //其他
                Intent otherActivity = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(otherActivity);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showqixingDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_anquantishi)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        //wozhidao
        dialog.getView(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }
    private void showxingDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_anquantishis)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        //wozhidao
        dialog.getView(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //跳扫码
                Intent intent=new Intent(MainActivity.this,ZiXingActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
    @Override
    public void onMyLocationChange(Location location) {
        //locations = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
        Log.d("local", latitude + "" + longitude + "");

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        myLocationStyle.myLocationType((MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
        aMap.setMyLocationStyle(myLocationStyle);

    }
    /*
       电子围栏
     */
    public void adddianzimark() {
        list1 = new ArrayList<>();
        LatLng lat = new LatLng(39.5427, 116.2317);
        list1.add(lat);
        LatLng lat1 = new LatLng(39.4698041, 116.27);
        list1.add(lat1);
        LatLng lat2 = new LatLng(39.96, 116.3445);
        list1.add(lat2);
        for (int i = 0; i < list1.size(); i++) {
            Log.d("list", list1.size() + "");

            markerOption = new MarkerOptions();
            markerOption.position(list1.get(i));
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.dianziwei)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(false);//设置marker平贴地图效果
            marker = aMap.addMarker(markerOption);
            marker.setObject(new BikeBean("--i--" + i, list1.get(i).latitude, list1.get(i).longitude));
            marker.showInfoWindow();
        }
        //mark点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (null != walkRouteOverlay) {
                    walkRouteOverlay.removeFromMap();
                }
                final BikeBean bikebean = (BikeBean) marker.getObject();
                mStartPoint = new LatLonPoint(latitude, longitude);
                mEndPoint = new LatLonPoint(bikebean.getLat(), bikebean.getLon());
                searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                SpUtils.putString(MainActivity.this,"xuanze","dianziwei");
                return true;
            }
        });
    }

    public void addmark() {
        list1 = new ArrayList<>();
        LatLng lat = new LatLng(39.5427, 116.2317);
        list1.add(lat);
        LatLng lat1 = new LatLng(39.4698041, 116.27);
        list1.add(lat1);
        LatLng lat2 = new LatLng(39.96, 116.3445);
        list1.add(lat2);
        for (int i = 0; i < list1.size(); i++) {
            Log.d("list", list1.size() + "");
            markerOption = new MarkerOptions();
            markerOption.position(list1.get(i));
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.chebiao)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(false);//设置marker平贴地图效果
            marker = aMap.addMarker(markerOption);
            marker.setObject(new BikeBean("--i--" + i, list1.get(i).latitude, list1.get(i).longitude));
           // marker.showInfoWindow();
        }
        //mark点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (null != walkRouteOverlay) {
                    walkRouteOverlay.removeFromMap();
                }
                isClickIdentification = true;
                final BikeBean bikebean = (BikeBean) marker.getObject();
                mStartPoint = new LatLonPoint(latitude, longitude);
                mEndPoint = new LatLonPoint(bikebean.getLat(), bikebean.getLon());
                searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                 SpUtils.putString(MainActivity.this,"xuanze","diandianxing");
                return true;
            }
        });
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
       // Toast.makeText(this, "...............", Toast.LENGTH_SHORT).show();

        if (mStartPoint == null) {
            ToastUtils.show(MainActivity.this, "定位中，稍后再试...", 0);
            return;
        }
        if (mEndPoint == null) {
            ToastUtils.show(MainActivity.this, "终点未设置", 0);

        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {


        dissmissProgressDialog();
        if (i == 1000) {
            //添加的方式都一样，只是Overlay不一样；
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(final WalkRouteResult result, final int errorCode) {

        new Thread(new Runnable() {


            @Override
            public void run() {
                dissmissProgressDialog();
                if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getPaths() != null) {
                        if (result.getPaths().size() > 0) {
                            mWalkRouteResult = result;
                            final WalkPath walkPath = mWalkRouteResult.getPaths()
                                    .get(0);
                            walkRouteOverlay = new WalkRouteOverlay(
                                    MainActivity.this, aMap, walkPath,
                                    mWalkRouteResult.getStartPos(),
                                    mWalkRouteResult.getTargetPos());
                            walkRouteOverlay.removeFromMap();
                            walkRouteOverlay.addToMap();
                            walkRouteOverlay.zoomToSpan();

                             int dis = (int) walkPath.getDistance();
                              int dur = (int) walkPath.getDuration();
                            Log.e("dur",dur+"");
                          time = AMapUtil.getFriendlyTimeArray(dur);
                            distance = AMapUtil.getFriendlyLength(dis);
                            des = AMapUtil.getFriendlyTime(dur);
                            Log.d("time",time+"");
                            Log.d("distance",distance+"");

                             handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     data();
                                 }
                             });
//                            marker.setTitle(des);
//                            marker.showInfoWindow();

                        } else if (result != null && result.getPaths() == null) {
//                              ToastUtil.show(mContext, R.string.no_result);
                        }
                    } else {
//                          ToastUtils.show(MainActivity.this, R.string.no_result,);
                    }
                } else {
//                      ToastUtil.showerror(this.getApplicationContext(), errorCode);
                }

            }
        }).start();

        liner_mark.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        clickMap();
        liner_mark.setVisibility(View.GONE);

    }

    private void clickMap() {
        clickInitInfo();
//        if (latLng != null) {
//            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
//                    latLng, 17f);
//            aMap.animateCamera(cameraUpate);
//
//        }
    }

    private void clickInitInfo() {
        if (null != marker) {
            //  tempMark.setIcon(smallIdentificationBitmap);
            marker.hideInfoWindow();
        }
        if (null != walkRouteOverlay) {
            walkRouteOverlay.removeFromMap();
        }
    }

  /*
     逆地理编码
   */
    public void niaddress(){
      GeocodeSearch  geocoderSearch = new GeocodeSearch(this);
     //   geocoderSearch.setOnGeocodeSearchListener(this);//和上面一样
// 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(mEndPoint,200,GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
       geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

           @Override
           public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
               formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                   Log.v("ttt", formatAddress +"");

//               List<PoiItem> pois = regeocodeResult.getRegeocodeAddress().getPois();
//                     for (PoiItem poiItem : pois) {
//                         Log.v("zhangzida", poiItem.getTitle());//输出周边poi的信息
//
//
//                 }

               String xuanze = SpUtils.getString(MainActivity.this, "xuanze", null);
               // String address = SpUtils.getString(this, "ads", null);

               if(xuanze!=null) {
                   if (xuanze.equals("diandianxing")) {
                /*
                  点点行页面
                 */
                       tv_chewei.setText(des+"");
                       miao_juli.setText("每30分钟");
                       miao_time.setText("距离起始位置");
                       miao_shengyu.setText("步行可到达");
                       tv_time.setText(distance+"");
                            tv_address.setText(formatAddress);


                   }
                   else if(xuanze.equals("dianziwei")){
                       miao_juli.setText("距离你的位置");
                       miao_time.setText("步行时间");
                       miao_shengyu.setText("剩余停车位");
                       tv_time.setText(des+"");
                       tv_juli.setText(distance+"");
                       tv_chewei.setText(5+"辆");
                          tv_address.setText(formatAddress);
                   }
               }
           }

           @Override
           public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

           }
       });
    }
}