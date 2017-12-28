package www.diandianxing.com.diandianxing.sousuo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Addressbean;

public class SearchPopAdapter extends BaseAdapter {

	private Context context;
	
	private List<Addressbean> list;

	public SearchPopAdapter(Context context, List<Addressbean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHodler hodler;
		
		if(arg1==null){
			arg1=View.inflate(context, R.layout.searchpop_view, null);
			hodler = new ViewHodler();
			hodler.tv_changyong=arg1.findViewById(R.id.tv_changyong);
			arg1.setTag(hodler);
		}else{
			hodler=(ViewHodler) arg1.getTag();
		}
		hodler.tv_changyong.setText(list.get(arg0).getName());
		return arg1;
	}

	class ViewHodler{
		TextView tv_changyong;
	}
	
}
