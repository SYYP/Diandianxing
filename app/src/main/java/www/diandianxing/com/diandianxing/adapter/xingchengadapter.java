package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Jourbeans;
import www.diandianxing.com.diandianxing.bean.Xingchengbean;
import www.diandianxing.com.diandianxing.bean.databean;
import www.diandianxing.com.diandianxing.my.JourdetailActivity;
import www.diandianxing.com.diandianxing.utils.DividerItemDecoration;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class xingchengadapter extends RecyclerView.Adapter<xingchengadapter.MyviewHolder>{
      private Context context;
    List<Xingchengbean.DatasBean.ListBean> lists;
    private OnItemClickListener mOnItemClickListener = null;
    public List<databean> listdate;
    Map<String,List<databean>> stringMap;
    private List<databean> value;
    List<Jourbeans> listjou;
    private List<databean> listda;

    public xingchengadapter(Context context, List<Xingchengbean.DatasBean.ListBean> lists) {
        this.context = context;
        this.lists = lists;
    }



    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyviewHolder myViewHolder = new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.item_jour1, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, int position) {

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(  new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int layoutPosition = holder.getLayoutPosition();
                    Log.d("POS",layoutPosition+"---layoutPosition-");
                    mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
                }
            });
        }

            if(position==0){
                holder.v1.setVisibility(View.GONE);
                holder.text_date.setText(lists.get(0).getMonth()+"-"+lists.get(0).getDay());
                //分钟
                String lengthOfTime = lists.get(0).getLengthOfTime();
                int i = Integer.parseInt(lengthOfTime);
                int min = i / 60;

                //钱
                String money = lists.get(0).getMoney();
                double v = Double.valueOf(money).doubleValue();
                String moneys= String.valueOf(v);
                holder.text_heji.setText(min+"分钟");
                holder.text_money.setText(moneys+"元");
                holder.text_time.setText(lists.get(0).getHours());
                holder.v2.setVisibility(View.GONE);


            }
        else {
                    String s = lists.get(position).getMonth() + "-" + lists.get(position).getDay();
                    String s1 = lists.get(position-1).getMonth() + "-" + lists.get(position-1).getDay();
                    Log.e("TAG", "lists[ "+position+"]==="+s1+"====="+s);
                    if(s.equals(s1)){
                        holder.text_date.setVisibility(View.GONE);

                        holder.v2.setVisibility(View.GONE);
                        //分钟

                        String lengthOfTime = lists.get(position).getLengthOfTime();
                        int i = Integer.parseInt(lengthOfTime);
                        int min = i / 60;

                        //钱
                        String money = lists.get(position).getMoney();
                        double v = Double.valueOf(money).doubleValue();
                        String moneys= String.valueOf(v);
                        holder.text_heji.setText(min+"分钟");
                        holder.text_money.setText(moneys+"元");
                        holder.text_time.setText(lists.get(position).getHours());

                    }
                    else {
                        holder.v2.setVisibility(View.VISIBLE);
                        holder.text_date.setVisibility(View.VISIBLE);
                        holder.v1.setVisibility(View.VISIBLE);
                        holder.text_date.setText(lists.get(position).getMonth()+"-"+lists.get(position).getDay());
                        //分钟
                        String lengthOfTime = lists.get(position).getLengthOfTime();
                        int i = Integer.parseInt(lengthOfTime);
                        int min = i / 60;

                        //钱
                        String money = lists.get(position).getMoney();
                        double v = Double.valueOf(money).doubleValue();
                        String moneys= String.valueOf(v);
                        holder.text_heji.setText(min+"分钟");
                        holder.text_money.setText(moneys+"元");
                        holder.text_time.setText(lists.get(position).getHours());



                }


            }

//        Iterator<Map.Entry<String,List<databean>>> it = stringMap.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry<String, List<databean>> entry = it.next();
//                    Log.d("TAg", "key= " + entry.getKey() + " and value= " + entry.getValue());
//                    value = entry.getValue();
//
////        listda = listjou.get(position).getList();
//        holder.text_date.setText(entry.getKey() + "");
//                    holder.listview.setLayoutManager(new LinearLayoutManager(context));
//                    Xingadapter xingadapter = new Xingadapter(context, value);
//                    holder.listview.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL_LIST));
//                    holder.listview.setAdapter(xingadapter);
//                    xingadapter.setOnItemClickListener(new Xingadapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            //保存行程id
//                            String log_id = value.get(position).getLog_id();
//                            Log.d("TAS", log_id + "");
//                            SpUtils.putString(context, "triped", log_id);
//                            //跳转到行程详情
//                            Intent intent = new Intent(context, JourdetailActivity.class);
//                            intent.putExtra("xicheng", "xing");//根据不同的页面返回不同的页面
//                            context.startActivity(intent);
//                        }
//
//                    });
            //   }

    }

    @Override
    public int getItemCount() {
        return lists!=null?lists.size():0;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {

        private final TextView text_date;
        private final TextView text_time,text_money,text_heji;
        View v1,v2;

        public MyviewHolder(View itemView) {
            super(itemView);
            text_date = itemView.findViewById(R.id.text_date);
            text_time = itemView.findViewById(R.id.text_time);
            text_money=itemView.findViewById(R.id.text_money);
            text_heji= itemView.findViewById(R.id.text_heji);
            v1= itemView.findViewById(R.id.v1);
            v2=  itemView.findViewById(R.id.v2);

        }
    }
    /*
        接口回调用来item点击事件
       */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
