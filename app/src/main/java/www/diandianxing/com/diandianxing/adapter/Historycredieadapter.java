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
import www.diandianxing.com.diandianxing.bean.Xinyongdetailbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Historycredieadapter extends BaseAdapter {
     private Context context;
    private List<String> list=new ArrayList<>();
    private List<Xinyongdetailbean.DatasBean> datas;
    public Historycredieadapter(Context context,List<Xinyongdetailbean.DatasBean> datas) {
        this.context = context;
        this.datas=datas;
       // data();
    }

    private void data() {
        for (int i = 0; i <5 ; i++) {
            list.add("注册完成");
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
          Myholder myholder;
        if(view==null){
           myholder=new Myholder();
            view= LayoutInflater.from(context).inflate(R.layout.item_credit,null);
            myholder.finish=view.findViewById(R.id.text_fininsh);
            myholder.text_date=view.findViewById(R.id.text_date);
            myholder.text_fen=view.findViewById(R.id.text_fen);
            view.setTag(myholder);
        }
        else {
            myholder= (Myholder) view.getTag();
        }
         myholder.finish.setText(datas.get(i).getReason());
        myholder.text_date.setText("评估时间"+datas.get(i).getAdd_time());
        String num = datas.get(i).getNum();
        int i1 = Integer.parseInt(num);
        if(i1>0){
            myholder.text_fen.setText("+"+datas.get(i).getNum());
          }
        else {
            myholder.text_fen.setText(datas.get(i).getNum());
        }

        return view;
    }

      class Myholder {
        TextView finish,text_date,text_fen;
    }
}
