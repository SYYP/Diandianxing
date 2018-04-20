package www.diandianxing.com.diandianxing.my;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.DianDianActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.bean.xingbean;
import www.diandianxing.com.diandianxing.network.BaseObserver1;
import www.diandianxing.com.diandianxing.network.RetrofitManager;
import www.diandianxing.com.diandianxing.overlay.WalkRouteOverlay;
import www.diandianxing.com.diandianxing.utils.CircleImageView;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class JourdetailActivity extends BaseActivity implements View.OnClickListener,RouteSearch.OnRouteSearchListener {

    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private MapView map;
    private CircleImageView circleImageView;
    private LinearLayout liner_qi;
    private View view;
    private TextView text_bian;
    private LinearLayout linearLayout4;
    private ImageView imageView3;
     AMap aMap;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private final int ROUTE_TYPE_WALK = 3;
    private RouteSearch mRouteSearch;
    private WalkRouteResult mWalkRouteResult;
    private WalkRouteOverlay walkRouteOverlay;
    private CameraUpdate mUpdata;
    private MarkerOptions markerOption;
    private Marker marker;
    private TextView qi_j;
    private TextView qi_jl;
    private TextView qi_time;
    private String xicheng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);

        setContentView(R.layout.activity_jourdetail);
        initView();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        map.onCreate(savedInstanceState);
        datamap();
        //计算骑行路线
      //  intnetwork();
       network();
        Intent intent = getIntent();
        xicheng = intent.getStringExtra("xicheng");
    }

    private void network() {

        Map<String,String> map=new HashMap<>();
         map.put("uid", SpUtils.getString(this,"userid",null));
        map.put("token",SpUtils.getString(this,"token",null));
        map .put("log_id",SpUtils.getString(this,"triped",null));
        Log.d("TAS",SpUtils.getString(this,"triped",null)+"");
        RetrofitManager.post(MyContants.BASEURL + "s=Bike/tripInfo", map, new BaseObserver1<xingbean>("") {
            @Override
            public void onSuccess(xingbean result, String tag) {
                if(result.getCode()==200){
                    xingbean.DatasBean datas = result.getDatas();
                    List<xingbean.DatasBean.RouteArrayBean> routeArray1 = result.getDatas().getRouteArray();
                    //自行车编号
                    text_bian.setText("自行车编号"+datas.getBikenumber());
                    //骑行距离
                    String distance = datas.getDistance();
                    Log.d("Tags",distance+"");
                    double v4 = Double.valueOf(distance).doubleValue();
                    Log.d("TS",v4+"");
                     int v5 = (int) (v4 * 1000);
                    Log.d("TS",v5+"");
                    String s = String.valueOf(v5);
                    qi_j.setText(s+"");
                    qi_jl.setText(s+"");
                    //骑行时间
                    String time = datas.getTime();
                    Log.d("TGS",time+"");
                    int i2 = Integer.parseInt(time);
                    int i1 = i2 / 60;
                    qi_time.setText(i1+"");

                    //  mlocationClient.stopLocation();
                    //常用地址
                    //修改地图的中心点位置
                    double v = Double.valueOf(routeArray1.get(0).getLat()).doubleValue();
                    double v1 = Double.valueOf(routeArray1.get(0).getLog()).doubleValue();
                    Log.d("vvv",v+"dddd"+v1+"");

                    LatLng lat = new LatLng(v,v1);;
                    markerOption = new MarkerOptions();
                    markerOption.position(lat);
                    markerOption.draggable(true);//设置Marker可拖动
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.amap_start)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(false);//设置marker平贴地图效果
                    marker = aMap.addMarker(markerOption);
                    //终点位置
                    double v2 = Double.valueOf(routeArray1.get(routeArray1.size()-1).getLat()).doubleValue();
                    double v3 = Double.valueOf(routeArray1.get(routeArray1.size()-1).getLog()).doubleValue();
                    LatLng lats = new LatLng(v2,v3);
                    markerOption = new MarkerOptions();
                    markerOption.position(lats);
                    markerOption.draggable(true);//设置Marker可拖动
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.amap_end)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(false);//设置marker平贴地图效果
                    marker = aMap.addMarker(markerOption);
                    //设置定位的方法
                    mUpdata = CameraUpdateFactory.newCameraPosition(
//15是缩放比例，0是倾斜度，30显示比例
                            new CameraPosition(new LatLng(v2,v3), 10, 0, 10));//这是地理位置，就是经纬度。
                    aMap.moveCamera(mUpdata);//定位的方法
                    for (int i = 1; i <routeArray1.size() ; i++) {
                        mStartPoint = new LatLonPoint(Double.valueOf(routeArray1.get(i-1).getLat()), Double.valueOf(routeArray1.get(i-1).getLog()));
                        mEndPoint = new LatLonPoint(Double.valueOf(routeArray1.get(i).getLat()), Double.valueOf(routeArray1.get(i).getLog()));
                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                    }
                }
            }

            @Override
            public void onFailed(int code,String data) {

            }
        });
    }

    private void intnetwork() {


    }
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        // Toast.makeText(this, "...............", Toast.LENGTH_SHORT).show();

        if (mStartPoint == null) {
            ToastUtils.show(JourdetailActivity.this, "定位中，稍后再试...", 0);
            return;
        }
        if (mEndPoint == null) {
            ToastUtils.show(JourdetailActivity.this, "终点未设置", 0);

        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }

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
        text_bian = (TextView) findViewById(R.id.text_bian);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        zhong.setText("行程详情");
        qi_j = (TextView) findViewById(R.id.qi_ju);
        qi_jl = (TextView) findViewById(R.id.qi_jul);
        qi_time = (TextView) findViewById(R.id.qi_time);
        iv_callback.setOnClickListener(this);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
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
                if("xing".equals(xicheng)){
                 finish();

                }
                else {
                    //调用eventbus刷新主页面
                    //调用Eventbus
                    EventMessage eventMessage = new EventMessage("xiangqing");
                    EventBus.getDefault().postSticky(eventMessage);
//                    Intent intent = new Intent(this, DianDianActivity.class);
//                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("xing".equals(xicheng)){
            finish();

        }
        else {
            //调用eventbus刷新主页面
            //调用Eventbus
                EventMessage eventMessage = new EventMessage("xiangqing");
                EventBus.getDefault().postSticky(eventMessage);
            Intent intent = new Intent(this, DianDianActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }
    @Override
    public void onWalkRouteSearched(final WalkRouteResult result, final int errorCode) {

        new Thread(new Runnable() {


            @Override
            public void run() {
                if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getPaths() != null) {
                        if (result.getPaths().size() > 0) {
                            mWalkRouteResult = result;
                            final WalkPath walkPath = mWalkRouteResult.getPaths()
                                    .get(0);
                            walkRouteOverlay = new WalkRouteOverlay(
                                    JourdetailActivity.this, aMap, walkPath,
                                    mWalkRouteResult.getStartPos(),
                                    mWalkRouteResult.getTargetPos());
                            walkRouteOverlay.removeFromMap();
                            walkRouteOverlay.addToMap();
                            walkRouteOverlay.zoomToSpan();

                            int dis = (int) walkPath.getDistance();
                            int dur = (int) walkPath.getDuration();
                            Log.e("dur",dur+"");
//                           ") time = AMapUtil.getFriendlyTimeArray(dur);
//                            distance = AMapUtil.getFriendlyLength(dis);
//                            des = AMapUtil.getFriendlyTime(dur);
//                            Log.d("time",time+"");
//                            Log.d("distance",distance+";

//                            marker.setTitle(des);
//                            marker.showInfoWindow();

                        } else if (result != null && result.getPaths() == null) {
//                              ToastUtil.show(mContext, R.string.no_result);
                        }
                    } else {
//                          ToastUtils.show(DianDianActivity.this, R.string.no_result,);
                    }
                } else {
//                      ToastUtil.showerror(this.getApplicationContext(), errorCode);
                }

            }
        }).start();


    }
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
