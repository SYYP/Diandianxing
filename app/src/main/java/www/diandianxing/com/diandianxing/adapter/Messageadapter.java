package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Messagebean;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Messageadapter extends BaseAdapter {
       private Context context;
      private List<String> list=new ArrayList<>();
    List<Messagebean.DatasBean> datas;
    public Messageadapter(Context context,List<Messagebean.DatasBean> datas) {
        this.context = context;
        this.datas=datas;
      //  date();
    }

    private void date() {

        for (int i = 0; i <5; i++) {
            list.add("喜迎圣诞，单车活动享不停，免费骑行三天");
        }
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
             view= LayoutInflater.from(context).inflate(R.layout.item_message,null);
           holder.text_date=  view.findViewById(R.id.mesg_date);
           holder.img_pho=  view.findViewById(R.id.mesg_pho);
           holder.text_title=  view.findViewById(R.id.mesg_title);
             view.setTag(holder);
         }
        else {
            holder= (ViewHolder) view.getTag();
         }
        holder.text_date.setText(datas.get(i).getAdd_time());
        holder.text_title.setText(datas.get(i).getTitle());
        Glide.with(context).load(MyContants.PHOTO+datas.get(i).getPic()).error(R.drawable.aaa).into(holder.img_pho);
        return view;
    }

       class ViewHolder{
           TextView text_date,text_title;
           ImageView img_pho;
       }
}
