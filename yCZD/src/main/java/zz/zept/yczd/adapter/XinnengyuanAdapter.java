package zz.zept.yczd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.XinnengyuanInfo;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class XinnengyuanAdapter extends BaseAdapter {
    private Context context;
    private List<XinnengyuanInfo> list;
    private int[] colors;

    public XinnengyuanAdapter(Context context, List<XinnengyuanInfo> list,int[] colors) {
        this.context = context;
        this.list = list;
        this.colors = colors;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_xinnengyuan, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        XinnengyuanInfo item = list.get(i);
        holder.color.setBackgroundColor(colors[i]);
        holder.name.setText(item.getInfo());
        holder.num.setText(item.getYx());
        holder.percent.setText(item.getPercent()+"");

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.color)
        ImageView color;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.percent)
        TextView percent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
