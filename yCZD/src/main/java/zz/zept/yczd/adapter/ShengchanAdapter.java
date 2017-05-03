package zz.zept.yczd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.ShengchanList;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class ShengchanAdapter extends BaseAdapter {
    private Context context;
    private List<ShengchanList> list;

    public ShengchanAdapter(Context context,List<ShengchanList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shengchan, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShengchanList item = list.get(i);
        holder.company.setText(item.getCompany());
        holder.time.setText("更新时间："+item.getTime());
        holder.num.setText(item.getNum());
        holder.dw.setText(item.getDw());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.dw)
        TextView dw;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
