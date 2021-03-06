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
import zz.zept.yczd.bean.ZongheInfo;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class ZongheAdapter extends BaseAdapter {
    private Context context;
    private List<ZongheInfo> list;

    public ZongheAdapter(Context context,List<ZongheInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_zonghe, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ZongheInfo item = list.get(i);
        holder.time.setText(item.getTime());
        holder.content.setText(item.getText());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
