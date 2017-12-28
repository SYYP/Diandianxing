package www.diandianxing.com.diandianxing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.bean.Guzhangbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Guzhangadapter extends RecyclerView.Adapter<Guzhangadapter.Myviewholder> {
         private Context context;
        private List<Guzhangbean> list=new ArrayList<>();

    public Guzhangadapter(Context context) {
        this.context = context;
        data();
    }

    private void data() {
         Guzhangbean guzhang=new Guzhangbean();
        guzhang.setCount("车太重，骑不动");
        list.add(guzhang);
        Guzhangbean guzhang1=new Guzhangbean();
        guzhang1.setCount("二维码脱落");
        list.add(guzhang1);
        Guzhangbean guzhang2=new Guzhangbean();
        guzhang2.setCount("把套歪了");
        list.add(guzhang2);
        Guzhangbean guzhang3=new Guzhangbean();
        guzhang3.setCount("车铃丢了");
        list.add(guzhang3);
        Guzhangbean guzhang4=new Guzhangbean();
        guzhang4.setCount("踏板坏了");
        list.add(guzhang4);
        Guzhangbean guzhang5=new Guzhangbean();
        guzhang5.setCount("车铃丢了");
        list.add(guzhang5);
        Guzhangbean guzhang6=new Guzhangbean();
        guzhang3.setCount("锁不了车");
        list.add(guzhang6);
        Guzhangbean guzhang7=new Guzhangbean();
        guzhang7.setCount("踏板坏了");
        list.add(guzhang7);
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Myviewholder myviewholder=new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_guzhang,parent,false));
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(final Myviewholder holder, final int position) {
         holder.text_count.setText(list.get(position).getCount());
          holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                  holder.checkBox.setText(list.get(position).getaBoolean()+"");


              }
          });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final TextView text_count;

        public Myviewholder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.guzhang_miaoshu);
            text_count = itemView.findViewById(R.id.text_count);
        }
    }


}
