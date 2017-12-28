package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Jourbean;
import www.diandianxing.com.diandianxing.my.JourdetailActivity;
import www.diandianxing.com.diandianxing.utils.DividerItemDecoration;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class xingchengadapter extends RecyclerView.Adapter<xingchengadapter.MyviewHolder>{
      private Context context;
    private List<Jourbean> list;
    private List<String>titlelist=new ArrayList<>();

    public xingchengadapter(Context context, List<Jourbean> list) {
        this.context = context;
        this.list = list;
        data();
    }

    private void data() {
        for (int i = 0; i < 3; i++) {
            titlelist.add("11-20");
        }
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyviewHolder myViewHolder = new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.item_jour1, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
           holder.text_date.setText(titlelist.get(position));
          holder.listview.setLayoutManager(new LinearLayoutManager(context));
          Xingadapter xingadapter=new Xingadapter(context,list);
        holder.listview.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST));
          holder.listview.setAdapter(xingadapter);
        xingadapter.setOnItemClickListener(new Xingadapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                 //跳转到行程详情
                Intent intent=new Intent(context, JourdetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {

        private final TextView text_date;
        private final RecyclerView listview;

        public MyviewHolder(View itemView) {
            super(itemView);
            text_date = itemView.findViewById(R.id.text_date);
            listview = itemView.findViewById(R.id.lisview);

        }
    }
}
