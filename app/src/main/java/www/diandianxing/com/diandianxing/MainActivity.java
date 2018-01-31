package www.diandianxing.com.diandianxing;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
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
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.diandianxing.com.diandianxing.Login.LoginActivity;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.BikeBean;
import www.diandianxing.com.diandianxing.bean.Changbackbean;
import www.diandianxing.com.diandianxing.bean.Kailockbean;
import www.diandianxing.com.diandianxing.bean.MainBikebean;
import www.diandianxing.com.diandianxing.bean.Shangchuanbean;
import www.diandianxing.com.diandianxing.bean.Shouyebean;
import www.diandianxing.com.diandianxing.bean.Zhanshi;
import www.diandianxing.com.diandianxing.bean.backbean;
import www.diandianxing.com.diandianxing.main.GuZhangActivity;
import www.diandianxing.com.diandianxing.main.KefuActivity;
import www.diandianxing.com.diandianxing.main.OtherActivity;
import www.diandianxing.com.diandianxing.main.ZiXingActivity;
import www.diandianxing.com.diandianxing.my.BalanceActivity;
import www.diandianxing.com.diandianxing.my.CashpayActivity;
import www.diandianxing.com.diandianxing.my.JieshuActivity;
import www.diandianxing.com.diandianxing.my.MessageActivity;
import www.diandianxing.com.diandianxing.my.MyActivityActivity;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.overlay.WalkRouteOverlay;
import www.diandianxing.com.diandianxing.server.BackService;
import www.diandianxing.com.diandianxing.sousuo.SearchActivity;
import www.diandianxing.com.diandianxing.utils.AMapUtil;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.CustomProgressDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SoftKeyboardTool;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

import static www.diandianxing.com.diandianxing.R.id.map;

public class MainActivity extends BaseActivity implements View.OnClickListener,AMap.OnMyLocationChangeListener
        ,AMap.OnMapTouchListener,RouteSearch.OnRouteSearchListener,AMap.OnMapClickListener,LocationSource,
        AMapLocationListener {

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
    private long preTime;
    private Location locations;
    private double lat;
    private double lon;
    private boolean followMove = true;
    private UiSettings mUiSettings;
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
    // 处理定位更新
    private OnLocationChangedListener mListener;
    // 定位
    private AMapLocationClient mlocationClient;

    private AMapLocationClientOption mLocationOption;
    private List<MainBikebean.DatasBean.EnclosureBean> enclosure;
    private List<MainBikebean.DatasBean.BikeBean> bike;
    private RelativeLayout real_shou;
    private ImageView img_delete;
    private EditText ed_shuru;
    private Intent mServiceIntent;
    private IBackserver iBackService;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                ToastUtils.show(MainActivity.this,"开锁失败",1);
            }
            super.handleMessage(msg);
        }
    };

    private static ProgressDialog mDialog;
    boolean abool;
    TimerTask timerTask;
     int i=0,j=0;
    Timer mtimer;
    private TextView bike_sso;
    private String tripld;
     Timer mtimers;
    TimerTask timerTasks;
    boolean aBoolean=true;
    boolean suo=false;
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
        mServiceIntent = new Intent(this, BackService.class);
        startService(mServiceIntent);
        Log.d("TAG","Create===============================");

        /*
           网络请求
         */
//加载uI
        initView();

        datamap();
        //data加载数据
        network();
//加载点点行自行车
        addmark();
        //首先页面初始化时加载的是点点行车页面，保存当前状态
        SpUtils.putString(this, "address", "gongxiang");
        //网络请求车
        networkbike();
      //  mtimers.schedule(timerTasks,1000,1000);
    }
    /*
       根据经纬度搜索附近的车辆
     */
    private void networkbike() {
           Map<String,String> map=new HashMap<>();
        Log.d("lo",SpUtils.getString(this,"lo",null)+"cccccc");
        Location myLocation = aMap.getMyLocation();
        if(myLocation!=null) {
            Log.d("los", myLocation.getLatitude() + "cccccc");
        }
        Log.d("la",SpUtils.getString(this,"la",null)+"ccc");
             map.put("longitude",SpUtils.getString(this,"lo",null));
             map.put("latitude",SpUtils.getString(this,"la",null));
             map.put("uid",SpUtils.getString(this,"userid",null));
             map.put("token",SpUtils.getString(this,"token",null));
       RetrofitManager.get(MyContants.BASEURL +"s=Bike/indexInfo", map, new BaseObserver1<MainBikebean>("") {



           @Override
           public void onSuccess(MainBikebean result, String tag) {

                if(result.getCode()==200){
                    //电子围栏
                    enclosure = result.getDatas().getEnclosure();
                    //共享单车
                    bike = result.getDatas().getBike();
                    list1 = new ArrayList<>();
                    if(bike!=null&&bike.size()>0) {
                        for (int i = 0; i < bike.size(); i++) {
                            Log.d("list", list1.size() + "");
                            LatLng lat = new LatLng(Double.valueOf(bike.get(i).getLatitude().toString().trim()), Double.valueOf(bike.get(i).getLongitude().toString().trim()));
                            list1.add(lat);
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
           }

           @Override
           public void onFailed(int code,String data) {
               if(code==1001){
                   if(data.equals("token不正确")) {
                       ToastUtils.show(MainActivity.this, "账号已过期，请重新登录", 1);
                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                       startActivity(intent);
                       finish();
                   }
               }
           }
       });


    }


    private void network() {

        final Map<String,String> map=new HashMap<>();
         map.put("uid",SpUtils.getString(this,"userid",null));
         map.put("token",SpUtils.getString(this,"token",null));
        RetrofitManager.get(MyContants.BASEURL + "s=Bike/myInfo", map, new BaseObserver1<Shouyebean>("") {



            private int istemp;

            @Override
            public void onSuccess(Shouyebean result, String tag)  {
                int code = result.getCode();
                if(code==200){
                    datas = result.getDatas();
                    Log.d("ssss",datas.getRidingState());
                    int ride = Integer.parseInt(datas.getRidingState().trim());//骑行状态，0未骑行，1骑行中，2骑行结束
                    if(datas.getIsTemporary()!=null&&datas.getIsTemporary().length()>0){
                        istemp = Integer.parseInt(datas.getIsTemporary());//是否是临时停车 0不是，1是；
                    }

                    SpUtils.putString(MainActivity.this,"token", datas.getToken());
                     SpUtils.putString(MainActivity.this,"paiphoto",MyContants.PHOTO+datas.getHeadImageUrl()+"");
                     SpUtils.putString(MainActivity.this,"nickname",datas.getNickName()+"");
                     SpUtils.putString(MainActivity.this,"yajin",datas.getSecurityDeposit());//押金
                     SpUtils.putString(MainActivity.this,"yue",datas.getBalance());//余额
                     SpUtils.putString(MainActivity.this,"IDcard",datas.getRealType()+"");//认证状态，0未认证，1审核中，2审核不通过，3审核通过
                    Log.d("idcrd",datas.getRealType()+"");
                    tripld = datas.getTripId();
                    SpUtils.putString(MainActivity.this,"ripedt",datas.getTripId());
                   String securityDeposit = datas.getSecurityDeposit();
                    double mooey= Double.valueOf(securityDeposit).doubleValue();
                    Log.d("mooey",securityDeposit+"");
//                    int moo= Integer.valueOf(securityDeposit).intValue();
                    // intent.putExtra("photo",datas.getHeadImageUrl());
                    if(mooey!=0.00){
                       Log.d("tttt","ttttttttsstt");
                        main_liner.setVisibility(View.GONE);
                    } else if(mooey==0.00){
                        main_liner.setVisibility(View.VISIBLE);
                    }
                     if(datas.getBikeNumber()!=null){
                         bike_number.setText("正在用车"+datas.getBikeNumber());
                    }
                    String cartStatus = datas.getCartStatus();
                    Log.d("cart",cartStatus+"--------违规-------");
                    int iss = Integer.parseInt(cartStatus);
                    Log.d("carts",cartStatus+"--------违规停放-------");
                    if(iss==1){
                        weiguidailog(Gravity.CENTER,R.style.Alpah_aniamtion);
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
                         SpUtils.putString(MainActivity.this, "bian", datas.getBikeNumber());
                         tripld = datas.getTripId();
                         SpUtils.putString(MainActivity.this,"ripedt",datas.getTripId());
                           /*
                          建立长连接
                          */
                         if(aBoolean) {
                             Log.d("start","---------------");
                             netchang();
                         }
                          if(mtimers!=null){
                              mtimers.schedule(timerTasks,1000,1000);
                          }

                    }
                     else {
                         if (ride == 0) {
                             real_gongxiang.setVisibility(View.VISIBLE);
                             real_dianzi.setVisibility(View.VISIBLE);
                             iv_lock.setVisibility(View.VISIBLE);
                             liner_linshi.setVisibility(View.GONE);
                             iv_refresh.setVisibility(View.VISIBLE);
                             iv_search.setVisibility(View.VISIBLE);
                             liner_jishi.setVisibility(View.GONE);
                         } else {
                             if (ride == 2) {//骑行结束跳支付

                                 if (mtimers != null) {
                                     mtimers.cancel();
                                     mtimers = null;
                                 }
                                 if (timerTasks != null) {
                                     timerTasks.cancel();
                                     timerTasks = null;
                                 }
                                 tripld = datas.getTripId();
                                 //  netchangs();
                                 Log.d("tagss", tripld);
//                                 Intent intent_unbind = new Intent(MainActivity.this, BackService.class);
//                                 stopService(intent_unbind);

                                 //   SpUtils.putString(MainActivity.this,"triped",datas.getTripld());
                                 Intent intent = new Intent(MainActivity.this, JieshuActivity.class);
                                 startActivity(intent);
                                finish();

                                 //    netchangs();
                                 //保存骑行的id

                                 // finish();

                             }
                         }
                     }
                     if(istemp==0){

                         text_tingche.setText("临时停车");
                     }
                    else if(istemp==1){
                         text_tingche.setText("继续骑行");
                     }

                         Log.d("tripld",tripld+"-----------");
                         /*
                           开启定时器
                          */
                         if(tripld!=null&&tripld.length()>0) {
                           //  mtimers.schedule(timerTasks,1000,1000);

                     }

                }

            }

            @Override
            public void onFailed(int code,String data) {

                if(code==1001){
                    if(data.equals("token不正确")||data.equals("token已过期")) {
                        ToastUtils.show(MainActivity.this, "账号已过期，请重新登录", 1);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }



    private void lanetwork() {
        Map<String,String> map=new HashMap<String, String>();
        map.put("uid",SpUtils.getString(MainActivity.this,"userid",null));
        map.put("token",SpUtils.getString(MainActivity.this,"token",null));
        map.put("log_id",SpUtils.getString(MainActivity.this,"ripedt",null));
        String la = SpUtils.getString(MainActivity.this, "la", null);
        Log.d("la",la+"---------------");
        map.put("lat",SpUtils.getString(MainActivity.this,"la",null));
        map.put("lon",SpUtils.getString(MainActivity.this,"lo",null));
        RetrofitManager.post(MyContants.BASEURL + "s=LockBalance/distCalculation", map, new BaseObserver1<Shangchuanbean>("") {
            @Override
            public void onSuccess(Shangchuanbean result, String tag) {
                if(result.getCode()==200){
                    String addTime = result.getDatas().getAddTime();
                    int i = Integer.parseInt(addTime);
                    int i1 = i / 60;
                    Log.d("msd","成功");
                    qi_time.setText(i1+"分钟");

                    String route = result.getDatas().getRoute();
                    if(route!=null) {
                        double v = Double.valueOf(route).doubleValue();
                        String s = String.valueOf(v);
                        qi_juli.setText(s + "米");
                    }
                }
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });
    }

    /*
       加载数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventMessage eventMessage) {
        //支付完好跳到详情，看完后返回主界面
        if(eventMessage.getMsg().equals("xiangqing")){

              network();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            iBackService.qidong();
//                            network();
//
//                            Log.d("tagss",mReceiver+"----"+conn+"");
//
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();



//            bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
//            // 开始服务
//            registerReceiver();

            // 注销广播 最好在onPause上注销


        }
         //扫码成功后刷新主页面
        if (eventMessage.getMsg().equals("zxing")) {
            suo=true;

            if(suo) {

                bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
                // 开始服务
                registerReceiver();
                Log.d("tagsssssss","start-------------");

            }
          //  liner_mark.setVisibility(View.GONE);
           //扫码开锁
            Log.d("start1111111","---------------");
            mDialog.show();
            SpUtils.putString(this, "address", "dianzi");
            img_che.setImageResource(R.drawable.img_gongxiangfalse);
            img_wei.setImageResource(R.drawable.img_dian_true);
            aMap.clear();
            datamap();
            mtimer=new Timer();

            timerTask=new TimerTask() {
                @Override
                public void run() {
                    j++;
                   if(abool){
                       j=0;
                      mDialog.dismiss();
                       if(mtimer!=null) {
                           mtimer.cancel();
                           mtimer = null;
                       }
                       if(timerTask!=null) {
                           timerTask.cancel();
                           timerTask = null;
                       }


                   }
                     if(j==39){
                         j=0;
                          mDialog.dismiss();

                         handler.sendEmptyMessage(1);

                         if(mtimer!=null) {
                             mtimer.cancel();
                             mtimer = null;
                         }
                         if(timerTask!=null) {
                             timerTask.cancel();
                             timerTask = null;
                         }
                     }

                    Log.d("ssssddds",i+"");
                }

            };
            kaisuo();//短连接开锁

            adddianzimark();//电子围栏
            if(timerTask!=null) {
                mtimer.schedule(timerTask, 1000, 1000);
            }





        }
        else if(eventMessage.getMsg().equals("mainetwork")){
            if (mtimers != null) {
                mtimers.cancel();
                mtimers = null;
            }
            if (timerTasks != null) {
                timerTasks.cancel();
                timerTasks = null;
            }

            network();

        }
        else  if(eventMessage.getMsg().equals("dingdan")){
            network();

        }
        else if(eventMessage.getMsg().equals("shoushi")){
            real_shou.setVisibility(View.VISIBLE);
            SoftKeyboardTool.showSoftKeyboard(ed_shuru);

        }
        else if(eventMessage.getMsg().equals("myposition")){
             datamap();
            mlocationClient.startLocation();


        }
        else if(eventMessage.getMsg().equals("yincang")){
            real_shou.setVisibility(View.GONE);
            SoftKeyboardTool.closeKeyboard(ed_shuru);
        }
        else if(eventMessage.getMsg().equals("lishi")){
         //   mlocationClient.stopLocation();
            //常用地址
            //修改地图的中心点位置
            String lat = SpUtils.getString(this, "lat", null);
            String lon = SpUtils.getString(this, "lon", null);
            double v = Double.valueOf(lat).doubleValue();
            double v1 = Double.valueOf(lon).doubleValue();
            Log.d("vvv",v+"dddd"+v1+"");
            mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
                    new CameraPosition(new LatLng(v,v1), 15, 0, 30));//这是地理位置，就是经纬度。
            aMap.moveCamera(mUpdata);//定位的方法

        }
        else if(eventMessage.getMsg().equals("sousuo")){
          //  mlocationClient.stopLocation();
            //常用地址
            //修改地图的中心点位置
            String lat = SpUtils.getString(this, "lat", null);
            String lon = SpUtils.getString(this, "lon", null);
            double v = Double.valueOf(lat).doubleValue();
            double v1 = Double.valueOf(lon).doubleValue();
            Log.d("vvv",v+"dddd"+v1+"");
            mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
                    new CameraPosition(new LatLng(v,v1), 15, 0, 30));//这是地理位置，就是经纬度。
            aMap.moveCamera(mUpdata);//定位的方法

        }
        else if(eventMessage.getMsg().equals("changyong")){
          //  mlocationClient.stopLocation();
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
             aMap.moveCamera(mUpdata);//定位的方法;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播 最好在onPause上注销
    }

    private void netchang() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Log.i("tag", "是否为空：" + iBackService);
                    if (iBackService == null) {
                        Toast.makeText(getApplicationContext(),
                                "没有连接，可能是服务器已断开", Toast.LENGTH_SHORT).show();
                    } else {


                        String bikenum = SpUtils.getString(MainActivity.this, "bianhao", null);
                             Log.d("bian",bikenum+"");
                        if(bikenum!=null) {
                            final boolean isSend = iBackService.sendMessage(bikenum);
                            SpUtils.putString(MainActivity.this,"bianhao",null);
                            Log.d("isend",isSend+"--------");
                        }

                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
//                                Toast.makeText(MainActivity.this,
//                                        isSend ? "success" : "fail", Toast.LENGTH_SHORT)
//                                        .show();


                            }
                        });


                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void netchangs() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Log.i("tag", "是否为空：" + iBackService);
                    if (iBackService == null) {
                        Toast.makeText(getApplicationContext(),
                                "没有连接，可能是服务器已断开", Toast.LENGTH_SHORT).show();
                    } else {




                        final boolean isSend = iBackService.sendMessage("啥玩意");
                        Log.d("isend",isSend+"--------");
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Toast.makeText(MainActivity.this,
                                        isSend ? "success" : "fail", Toast.LENGTH_SHORT)
                                        .show();

                                if(!isSend){



                                }

                            }
                        });


                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();


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
        aMap.getUiSettings().setZoomControlsEnabled(false);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loation_locin);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapTouchListener(this);
        aMap.setOnMapClickListener(this);
        /*
         dingwei蓝点
         */
// 设置定位监听
        aMap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
// 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);

    }



    @Override
    protected void onDestroy() {
        if (conn != null && iBackService != null) {
            suo=false;
            // 注销广播 最好在onPause上注销
            LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mReceiver);
            // 注销服务
            unbindService(conn);
            Log.d("jj-----","注销");
              conn = null;
            iBackService = null;


        }

//        if(mtimers!=null){
//            mtimers.cancel();
//            mtimers=null;
//
//        }
//        if(timerTasks!=null){
//            timerTasks.cancel();
//            timerTasks=null;
//        }
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();



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
    protected void onStart() {
        super.onStart();
//        if(suo) {
//
//            bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
//            // 开始服务
//            registerReceiver();
//            Log.d("tagsssssss","start-------------");
//
//        }
        Log.d("ffffff","startssss-------------");



    }
    // 注册广播
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BackService.HEART_BEAT_ACTION);
        intentFilter.addAction(BackService.MESSAGE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter);

    }

      public void kaisuo(){
           /*
               建立长连接
              */
          final String bikenum = SpUtils.getString(MainActivity.this, "bianhao", null);
          Map<String, String> map = new HashMap<>();
          map.put("uid", SpUtils.getString(MainActivity.this, "userid", null));
          map.put("token", SpUtils.getString(MainActivity.this, "token", null));
          map.put("ident", bikenum);
          RetrofitManager.post(MyContants.BASEURL+"s=LockBalance/unlock", map, new BaseObserver1<Kailockbean>("") {

              @Override
              public void onSuccess(Kailockbean result, String tag) {
                  if(result.getCode()==200){
                      Log.d("bikenums",bikenum+"开锁成功");
                      netchang();//长连接
                  }

                  else{
                  if(result.getDatas().equals("token不正确")||result.getDatas().equals("token已过期")) {
                      ToastUtils.show(MainActivity.this, "账号已过期，请重新登录", 1);
                      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                      startActivity(intent);
                      finish();
                  }
                      if(result.getDatas().equals("车辆使用中")){
                          mDialog.dismiss();
                          ToastUtils.show(MainActivity.this, "车辆使用中", 1);
                      }
                      if(result.getDatas().equals("车俩失踪")){
                          mDialog.dismiss();
                          ToastUtils.show(MainActivity.this, "车俩失踪", 1);
                      }


                  }
              }

              @Override
              public void onFailed(int code,String data) {
                  if(code==1001){
                      if(data.equals("token不正确")||data.equals("token已过期")) {
                          ToastUtils.show(MainActivity.this, "账号已过期，请重新登录", 1);
                          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                          startActivity(intent);
                          finish();
                          if(data.equals("车辆使用中")){
                              mDialog.dismiss();
                              ToastUtils.show(MainActivity.this, "车辆使用中", 1);

                          }
                      }
                  }
              }
          });

      }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 消息广播
            if (action.equals(BackService.MESSAGE_ACTION)) {
                String stringExtra = intent.getStringExtra("message");
                if (stringExtra.equals("你是不是傻")) {
                    return;
                } else {
                    Gson gson = new Gson();
                    Log.d("asd", stringExtra + "");
                    backbean backbean = gson.fromJson(stringExtra, backbean.class);
                    int code = backbean.getCode();
                    Log.d("ddddss", code + "");

                    if (code == 93000) {
                        if (mtimers != null) {
                            mtimers.cancel();
                            mtimers = null;
                        }
                        if (timerTasks != null) {
                            timerTasks.cancel();
                            timerTasks = null;
                        }
                        network();

                    } else if (code == 96300) {

                        network();

//                     try {
//                         network();
//                         iBackService.shibie();
//
//
//                         //结算
//
//                     } catch (RemoteException e) {
//                         e.printStackTrace();
//                     }

                    } else if (code == 0) {

                        networkser(stringExtra);
                    }
                }
                 /*
                    解析chang连接返回的数据

                  */
                }else if (action.equals(BackService.HEART_BEAT_ACTION)) {// 心跳广播
                }

        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 未连接为空
            iBackService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 已连接
            iBackService = IBackserver.Stub.asInterface(service);
            Log.d("iback",iBackService+"-------------");
        }
    };

    public  void networkser(final String stringextra){
        Map<String,String> map=new HashMap<>();
        map.put("uid",SpUtils.getString(this,"userid",null));
        map.put("token",SpUtils.getString(this,"token",null));
        map.put("json",stringextra);
        RetrofitManager.post(MyContants.BASEURL + "s=LockBalance/equipOperation", map, new BaseObserver1<Changbackbean>("") {
            @Override
            public void onSuccess(Changbackbean result, String tag) {
                if(result.getCode()==200) {
                    abool=true;
                    mDialog.dismiss();
                    aBoolean=false;
                    network();
                }


            }

            @Override
            public void onFailed(int code,String data) {
                if(code==1001){
                    mDialog.dismiss();
                    ToastUtils.show(MainActivity.this,data,1);
                }
            }
        });
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
        real_shou = (RelativeLayout) findViewById(R.id.real_shoudong);
        img_delete = (ImageView) findViewById(R.id.img_delete);
        img_delete.setOnClickListener(this);
        ed_shuru = (EditText) findViewById(R.id.ed_shuru);
        mDialog = new CustomProgressDialog(this, R.style.myprogressdialog);
        //立即用车
        bike_sso = (TextView) findViewById(R.id.bike_sso);
        bike_sso.setOnClickListener(this);
        //开启定时器每三分钟穿数据
        mtimers=new Timer();
        timerTasks=new TimerTask() {
            @Override
            public void run() {
                i++;
                if(i==10){
                    i=0;
                    //上传数据库自己的定位
                    if(tripld!=null&&tripld.length()>0) {
                        lanetwork();
                    }
                    else {
                        mtimers.cancel();
                        mtimers=null;
                        timerTasks.cancel();
                        timerTasks=null;
                    }

                }

                Log.d("ssssddd",i+"");
            }



        };

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bike_sso://立即用车
                //获取edittext上的内容
                String trim = ed_shuru.getText().toString().trim();
                 SpUtils.putString(MainActivity.this,"bianhao",trim);
                EventMessage eventMessage = new EventMessage("zxing");
                EventBus.getDefault().postSticky(eventMessage);
                real_shou.setVisibility(View.GONE);
                SoftKeyboardTool.closeKeyboard(ed_shuru);
                break;
            case R.id.img_delete:
                real_shou.setVisibility(View.GONE);
                SoftKeyboardTool.closeKeyboard(ed_shuru);
                break;
            case R.id.btn_linshi:

                if(bool){
                    //临时停车,再次点击变为继续骑行
                    showxingDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                    text_tingche.setText("临时停车");
                }
                else {
                    showqixingDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                    //临时停车,再次点击变为继续骑行
                    netlinshi();

                }
                bool=!bool;
                break;
            //我的
            case R.id.img_my:Intent intent = new Intent(this, MyActivityActivity.class);
             if(datas!=null) {
                 intent.putExtra("fenshu", datas.getCredit() + "");
             }
                startActivity(intent);
                break;
            //消息
            case R.id.img_message:

               Intent intent1 = new Intent(this, MessageActivity.class);
               startActivity(intent1);
                break;
            //搜索
            case R.id.iv_search:
                mlocationClient.stopLocation();
                //跳到搜索页面
                Intent search=new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.iv_kefu:
                //客服
                showphotoDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
            case R.id.iv_lock:
                 /*
                   判断 当押金为零时，跳押金页面
                    当余额不足时跳充值余额
                  */

                String yajin = SpUtils.getString(this, "yajin", null);
                String yue = SpUtils.getString(this, "yue", null);
                double yues= Double.valueOf(yue).doubleValue();
                int yu=(int)yues;
                if(yajin==null||yajin.equals("0.00")||yajin.length()<0){
                    //跳充值押金
                    Intent cashpay=new Intent(this,CashpayActivity.class);
                    startActivity(cashpay);
                  }
                else if(yu<2){
                    Intent cashpays=new Intent(this,BalanceActivity.class);
                    startActivity(cashpays);
                }
                else {
                    Intent intent2 = new Intent(this, ZiXingActivity.class);
                    startActivity(intent2);
                }
                //扫码
                break;
            case R.id.iv_address:
                mlocationClient.startLocation();
                String address = SpUtils.getString(this, "address", null);
                if (address.equals("gongxiang")) {
                    addmark();

                } else if (address.equals("dianzi")) {
                    adddianzimark();

                }
                //定位
                break;
            case R.id.iv_refresh:
                mlocationClient.startLocation();
                //刷新
                addmark();
                break;
            case R.id.real_gongxiang:
                mlocationClient.startLocation();
                liner_mark.setVisibility(View.GONE);
                SpUtils.putString(this, "address", "gongxiang");
                aMap.clear();
                addmark();
                img_che.setImageResource(R.drawable.img_gongbick);
                img_wei.setImageResource(R.drawable.img_dianziwei);
                break;
            case R.id.real_dianzi:
                mlocationClient.startLocation();
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

    private void netlinshi() {

               Map<String,String> map=new HashMap<>();
               map.put("uid",SpUtils.getString(this,"userid",null));
               map.put("token",SpUtils.getString(this,"token",null));
               map.put("BikeNum",SpUtils.getString(this,"bian",null));
        RetrofitManager.post(MyContants.BASEURL + "s=LockBalance/suspend", map, new BaseObserver1<Zhanshi>("") {
            @Override
            public void onSuccess(Zhanshi result, String tag) {
                if(result.getCode()==200){
                     ToastUtils.show(MainActivity.this,result.getDatas().toString(),1);
                    text_tingche.setText("继续骑行");
                }
            }

            @Override
            public void onFailed(int code,String data) {

            }
        });
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
    private void weiguidailog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
              
        final BaseDialog dialog = builder.setViewId(R.layout.activity_weigui)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();
        //wozhidao
        TextView text_context= dialog.getView(R.id.text_context);
        text_context.setText("尊敬的用户，您当前车辆"+(datas.getBikeNumber()+"编号")+"属于违规停放，违规停放将会影响" +
                "您的骑行信用，请及时处理");
        dialog.getView(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mtimers != null) {
                    mtimers.cancel();
                    mtimers = null;
                }
                if (timerTasks != null) {
                    timerTasks.cancel();
                    timerTasks = null;
                }
                if (conn != null && iBackService != null) {
                    suo = false;
                    // 注销广播 最好在onPause上注销
                    LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mReceiver);
                    // 注销服务
                    unbindService(conn);
                }
                   Intent it=new Intent(MainActivity.this,ZiXingActivity.class);
                   startActivity(it);
                  dialog.dismiss();

            }
        });
        dialog.getView(R.id.lin_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mtimers != null) {
                    mtimers.cancel();
                    mtimers = null;
                }
                if (timerTasks != null) {
                    timerTasks.cancel();
                    timerTasks = null;
                }
                //联系客服
                dialog.dismiss();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "010-1234563245"));
                //跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
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
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
        Log.d("local", latitude + "" + longitude + "");
        SpUtils.putString(this,"la",latitude+"");
        SpUtils.putString(this,"lo",longitude+"");

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        mlocationClient.stopLocation();

    }
    /*
       电子围栏
     */
    public void adddianzimark() {
        list1 = new ArrayList<>();

        if(enclosure!=null&&enclosure.size()>0) {
            for (int i = 0; i < enclosure.size(); i++) {
                Log.d("list", "shulia" + enclosure.size() + "");
                LatLng lat = new LatLng(Double.valueOf(enclosure.get(i).getLatitude().toString().trim()), Double.valueOf(enclosure.get(i).getLongitude().toString().trim()));
                list1.add(lat);
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
                    SpUtils.putString(MainActivity.this, "xuanze", "dianziwei");
                    return true;
                }
            });
        }else {
            return;
        }
    }

    public void addmark() {
        list1 = new ArrayList<>();
        if(bike!=null&&bike.size()>0) {
            for (int i = 0; i < bike.size(); i++) {
                Log.d("list", list1.size() + "");
                LatLng lat = new LatLng(Double.valueOf(bike.get(i).getLatitude().toString().trim()), Double.valueOf(bike.get(i).getLongitude().toString().trim()));
                list1.add(lat);
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

               String xuanze = SpUtils.getString(MainActivity.this, "xuanze", null);

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



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                SpUtils.putString(this,"la",  amapLocation.getLatitude()+"");
                SpUtils.putString(this,"lo",amapLocation.getLongitude()+"");
             Log.d("ass", amapLocation.getLatitude()+"dddd"+amapLocation.getLongitude()+"") ;


            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    /*
       返回键退出程序
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > preTime + 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            preTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();//相当于finish()
            realBack();//删除所有引用
        }
    }
}