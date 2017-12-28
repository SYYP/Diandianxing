package www.diandianxing.com.diandianxing.sousuo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.Myapplication;
import www.diandianxing.com.diandianxing.bean.Addressbean;
import www.diandianxing.com.diandianxing.bean.PoiAddressBean;
import www.diandianxing.com.diandianxing.utils.BaseDialog;
import www.diandianxing.com.diandianxing.utils.EventMessage;
import www.diandianxing.com.diandianxing.utils.ListDataSave;
import www.diandianxing.com.diandianxing.utils.MyContants;
import www.diandianxing.com.diandianxing.utils.MyListView;
import www.diandianxing.com.diandianxing.utils.SpUtils;
import www.diandianxing.com.diandianxing.utils.ToastUtils;

public class SearchActivity extends Activity implements PoiSearch.OnPoiSearchListener {


	private MyListView lv_ss_content;
	private LinearLayout ll_cydd;
	private ImageView tv_callback;
	private EditText searchedtext;
	private String keyWord = "";// 要输入的poi搜索关键字
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch       poiSearch;// POI搜索
	private SearchAdapter adapter;
	private ArrayList<PoiAddressBean> data;
	List<Addressbean> list;
	ListDataSave datasave;
	Context context;
  boolean bool;
    private BaseDialog dialog;
	private List<Addressbean> changlist;
	private LinearLayout myposition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MyContants.windows(this);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		lv_ss_content=findViewById(R.id.lv_ss_content);
		ll_cydd=findViewById(R.id.ll_cydd);
		tv_callback = findViewById(R.id.tv_callback);
		searchedtext = findViewById(R.id.search_edtext);
		ll_cydd.setOnClickListener(listener);
		myposition = findViewById(R.id.myposition);
		myposition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//调用eventbus刷新主页面，根据经纬度跳到该区域
				EventMessage eventMessage = new EventMessage("myposition");
				EventBus.getDefault().postSticky(eventMessage);
				finish();
			}
		});
		list=new ArrayList<>();
		context= Myapplication.getApplication();
		datasave=new ListDataSave(context,"dizhi");

        tv_callback.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 finish();
			 }
		 });
        /*
            首次进入显示历史计入
         */

		//输入框监听
		searchedtext.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				keyWord = String.valueOf(charSequence);
				if ("".equals(keyWord)) {
					//ToastUtils.show(SearchActivity.this,"请输入搜索关键字",1);
					return;
				} else {
					doSearchQuery();
				}




            }

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

        indata();
	}

    private void indata() {

        final List<Addressbean> adress = datasave.getDataList("adress");
            if (adress != null) {
//                Set<Addressbean> setData = new HashSet<Addressbean>();
//                final List<Addressbean> newlist=new ArrayList<>();
//                setData.addAll(adress);
//                for(Addressbean ad:setData){
//                       newlist.add(ad);
//                    Log.d("name",ad.getName());
//                }
                SearchAdapters s = new SearchAdapters(context, adress);
                lv_ss_content.setAdapter(s);
                lv_ss_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        //调用eventbus刷新主页面，根据经纬度跳到该区域
                        EventMessage eventMessage = new EventMessage("lishi");
                        EventBus.getDefault().postSticky(eventMessage);
                        String latitude = adress.get(i).getLatitude();
                        SpUtils.putString(context,"lat",latitude);
                        SpUtils.putString(context,"lon",adress.get(i).getLongitude());
                        //ToastUtils.show(SearchActivity.this,adress.get(i).getName()+""+adress.get(i).getLatitude(),1);
                        finish();
                    }
                });

            }

    }

    private void data(int position) {

        List<Addressbean> adress = datasave.getDataList("adress");
        if(adress!=null) {
            list = adress;
            Log.d("aa","-----");
        }
        Addressbean address=new Addressbean();
		address.setLatitude(data.get(position).getLatitude());
		address.setLongitude(data.get(position).getLongitude());
		address.setName(data.get(position).getDetailAddress());
        address.setCity(data.get(position).getCity());
        address.setNum(1);
        int j=0;
        if(list!=null&&list.size()>0) {

            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getName();
                String name1 = address.getName();
                if (name.equals(name1)) {
                    int num = list.get(i).getNum();
                    list.get(i).setNum(num + 1);
                    Log.d("tasss","----------------");
                    j++;
                }

            }

            if(j<=0){
                list.add(address);
            }

        }
        else {
            list.add(address);
        }


		datasave.setDataList("adress",list);
        //调用eventbus刷新主页面，根据经纬度跳到该区域
		String latitude = data.get(position).getLatitude();
		SpUtils.putString(context,"lat",latitude);
		SpUtils.putString(context,"lon",data.get(position).getLongitude());
        EventMessage eventMessage = new EventMessage("sousuo");
        EventBus.getDefault().postSticky(eventMessage);
        finish();

	}


	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		currentPage = 0;
		//不输入城市名称有些地方搜索不到
		query = new PoiSearch.Query(keyWord, "", "北京");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		//这里没有做分页加载了,默认给50条数据
		query.setPageSize(50);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页

		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}


	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getId()){
			case R.id.ll_cydd:
				changlist = new ArrayList<>();
				List<Addressbean> adress = datasave.getDataList("adress");
				if(adress!=null&&adress.size()>0) {
					Collections.sort(adress);
					for (int i = 0; i < adress.size(); i++) {
						Addressbean ad = new Addressbean();
						ad.setName(adress.get(i).getName());
						ad.setLongitude(adress.get(i).getLongitude());
						ad.setLatitude(adress.get(i).getLatitude());
						if(i<=2){
							changlist.add(ad);
						}
						else {
							break;
						}
					}
					if (changlist != null && changlist.size() > 0)
						showGenderDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
					else {
						ToastUtils.show(SearchActivity.this, "无常用地址", 1);
					}
				}
				else {
					ToastUtils.show(SearchActivity.this,"无常用地址",1);
				}
				break;
			}
		}
	};
	//常用位置
	public void showGenderDialog(int grary, int animationStyle) {
		BaseDialog.Builder builder = new BaseDialog.Builder(this);
        //设置dialogpadding
//设置显示位置
//设置动画
//设置dialog的宽高
//设置触摸dialog外围是否关闭
//设置监听事件
        dialog = builder.setViewId(R.layout.dialog_address)
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
       ListView listview = dialog.getView(R.id.pay_listview).findViewById(R.id.pay_listview);



        SearchPopAdapter sear=new SearchPopAdapter(this, changlist);
        listview.setAdapter(sear);
        sear.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //调用eventbus刷新主页面，根据经纬度跳到该区域
                EventMessage eventMessage = new EventMessage("changyong");
                EventBus.getDefault().postSticky(eventMessage);
                String latitude = changlist.get(i).getLatitude();
                SpUtils.putString(context,"lat",latitude);
                SpUtils.putString(context,"lon", changlist.get(i).getLongitude());
              //  ToastUtils.show(SearchActivity.this,changlist.get(i).getName()+""+changlist.get(i).getLatitude(),1);
                finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
	 * POI信息查询回调方法
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		if (rCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getQuery() != null) {  // 搜索poi的结果
				if (result.getQuery().equals(query)) {  // 是否是同一条
					poiResult = result;
					//自己创建的数据集合
					data = new ArrayList<PoiAddressBean>();
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					for(PoiItem item : poiItems){
						//获取经纬度对象
						LatLonPoint llp = item.getLatLonPoint();
						double lon = llp.getLongitude();
						double lat = llp.getLatitude();

						String title = item.getTitle();
						String text = item.getSnippet();
						String provinceName = item.getProvinceName();
						String cityName = item.getCityName();
						String adName = item.getAdName();
						data.add(new PoiAddressBean(String.valueOf(lon), String.valueOf(lat), title, text,provinceName,
								cityName,adName));
					}
					adapter = new SearchAdapter(SearchActivity.this, data);
					lv_ss_content.setAdapter(adapter);
                    /*
		   listview跳目点击事件
		  */
                    lv_ss_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           // ToastUtils.show(context,data.get(i).detailAddress,1);
                            data(i);



                        }
                    });
				}
			} else {
			}
		} else {
			ToastUtils.show(SearchActivity.this, rCode,1);
		}

	}

	@Override
	public void onPoiItemSearched(PoiItem poiItem, int i) {

	}
}
