package www.diandianxing.com.diandianxing.sousuo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.PoiAddressBean;

public class SearchAdapter extends BaseAdapter {

	
	private Context context;
	
	private List<PoiAddressBean> list;
	
	public SearchAdapter(Context context,List<PoiAddressBean> list){
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler viewHodler;
		if(arg1==null){
			arg1=View.inflate(context, R.layout.search_item_view, null);
			viewHodler = new ViewHodler();
			viewHodler.tv_title=arg1.findViewById(R.id.tv_title);
			viewHodler.tv_count=arg1.findViewById(R.id.tv_count);
			arg1.setTag(viewHodler);
		}else{
			viewHodler=(ViewHodler) arg1.getTag();
		}
		 viewHodler.tv_title.setText(list.get(arg0).getCity()+list.get(arg0).district);
		viewHodler.tv_count.setText(list.get(arg0).detailAddress);
		return arg1;
	}

	
	class ViewHodler{
		TextView tv_title,tv_count;
		
	}
	
}
