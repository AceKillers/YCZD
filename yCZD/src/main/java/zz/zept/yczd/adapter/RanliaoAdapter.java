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
import zz.zept.yczd.bean.RanliaoInfo2;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class RanliaoAdapter extends BaseAdapter {
    private Context context;
    private List<RanliaoInfo2> list;

    public RanliaoAdapter(Context context, List<RanliaoInfo2> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ranliao, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RanliaoInfo2 item = list.get(i);
        holder.company.setText(item.getDw());
        holder.value1.setText(item.getFrl());
        holder.unit1.setText(item.getFrl_unit());
        holder.value2.setText(item.getHff());
        holder.unit2.setText(item.getHff_unit());
        holder.value3.setText(item.getHf());
        holder.unit3.setText(item.getHf_unit());
        holder.value4.setText(item.getLf());
        holder.unit4.setText(item.getLf_unit());
        holder.value5.setText(item.getSf());
        holder.unit5.setText(item.getSf_unit());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.value1)
        TextView value1;
        @BindView(R.id.unit1)
        TextView unit1;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
