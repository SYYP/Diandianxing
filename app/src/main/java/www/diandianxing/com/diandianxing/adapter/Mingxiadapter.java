package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Mingxibean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Mingxiadapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private List<Mingxibean.DatasBean> datas;
    public Mingxiadapter(Context context, List<Mingxibean.DatasBean> datas) {
        this.context = context;
        this.datas=datas;
        //data();
    }

    private void data() {
        list = new ArrayList<>();
         list.add("余额");
         list.add("支付宝");
        list.add("余额");


    }

    @Override
    public int getCount() {
        return datas!=null?datas.size():0;
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
          ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_mingxi,null);
            holder.tv_laiyuan= view.findViewById(R.id.tv_laiyuan);
            holder.tv_time=view.findViewById(R.id.tv_time);
            holder.tv_title= view.findViewById(R.id.tv_title);
            holder.tv_money= view.findViewById(R.id.tv_money);
            view.setTag(holder);
        }
        else {
           holder= (ViewHolder) view.getTag();
        }
        holder.tv_laiyuan.setText(datas.get(i).getPay_type());
        holder.tv_money.setText(datas.get(i).getGold()+"元");
        holder.tv_title.setText(datas.get(i).getType());
        holder.tv_time.setText(datas.get(i).getAdd_time());
        return view;
    }

      class ViewHolder{
          TextView tv_laiyuan,tv_title,tv_money,tv_time;
      }
}
