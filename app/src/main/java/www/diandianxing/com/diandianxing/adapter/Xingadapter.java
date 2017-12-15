package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Jourbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Xingadapter extends RecyclerView.Adapter<Xingadapter.MyviewHolder> {
      private Context context;
    private List<Jourbean> list;

    public Xingadapter(Context context, List<Jourbean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyviewHolder myViewHolder = new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.item_jour2, parent, false));

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
       holder.text_heji.setText(list.get(position).getHeji()+"分钟");
        holder.text_money.setText(list.get(position).getMoney()+"元");
        holder.text_time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        private final TextView text_time,text_money,text_heji;

        public MyviewHolder(View itemView) {
            super(itemView);
            text_time = itemView.findViewById(R.id.text_time);
            text_money=itemView.findViewById(R.id.text_money);
            text_heji= itemView.findViewById(R.id.text_heji);

        }
    }
}
