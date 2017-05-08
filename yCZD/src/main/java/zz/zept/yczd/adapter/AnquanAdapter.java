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
import zz.zept.yczd.bean.AnquanInfo;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class AnquanAdapter extends BaseAdapter {
    private Context context;
    private List<AnquanInfo> list;

    public AnquanAdapter(Context context, List<AnquanInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_anquan, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AnquanInfo item = list.get(i);
        holder.company.setText(item.getJz());
        if (item.getType().contains("1")){
            holder.key1.setText("检修容量");
            holder.key2.setText("检修类别");
            holder.key6.setText("检修小时数");
        }else if (item.getType().contains("2")){
            holder.key1.setText("装机容量");
            holder.key2.setText("停运类型");
            holder.key6.setText("备用小时数");
        }else if (item.getType().contains("3")){
            holder.key1.setText("装机容量");
            holder.key2.setText("停运原因");
            holder.key6.setText("停运小时数");
        }
        holder.value1.setText(item.getRl());
        holder.value2.setText(item.getCause());
        holder.value3.setText(item.getTime_start());
        holder.value4.setText(item.getTime_end());
        holder.value5.setText(item.getProperty());
        holder.value6.setText(item.getSpare());
        holder.value7.setText(item.getImpact());
        holder.value8.setText(item.getPlan_time());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.key1)
        TextView key1;
        @BindView(R.id.value1)
        TextView value1;
        @BindView(R.id.unit1)
        TextView unit1;
        @BindView(R.id.key2)
        TextView key2;
        @BindView(R.id.value2)
        TextView value2;
        @BindView(R.id.unit2)
        TextView unit2;
        @BindView(R.id.value3)
        TextView value3;
        @BindView(R.id.unit3)
        TextView unit3;
        @BindView(R.id.value4)
        TextView value4;
        @BindView(R.id.unit4)
        TextView unit4;
        @BindView(R.id.value5)
        TextView value5;
        @BindView(R.id.unit5)
        TextView unit5;
        @BindView(R.id.key6)
        TextView key6;
        @BindView(R.id.value6)
        TextView value6;
        @BindView(R.id.unit6)
        TextView unit6;
        @BindView(R.id.value7)
        TextView value7;
        @BindView(R.id.unit7)
        TextView unit7;
        @BindView(R.id.value8)
        TextView value8;
        @BindView(R.id.unit8)
        TextView unit8;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
