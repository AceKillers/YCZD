package zz.zept.yczd.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.ChannelBean.DataBean;

public class TrendChannelLVApapter extends BaseAdapter {
	public TrendChannelLVApapter(Context context, List<DataBean> data) {
		super();
		this.context = context;
		this.data = data;
	}

	Context context;
	private List<DataBean> data;

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		TextView tv_outfit = null;
		if (convertView == null) {
			view = View.inflate(context, R.layout.item_channel, null);
		} else {
			view = convertView;
		}

		tv_outfit = (TextView) view.findViewById(R.id.tv_name);
		tv_outfit.setText(data.get(position).getName());
//		if (position == 0) {
//			view.setBackgroundResource(R.drawable.item2);
//		} else {
//			if (position % 2 == 0) {
//				view.setBackgroundResource(R.drawable.item2);
//			} else {
//				view.setBackgroundResource(R.drawable.item);
//			}
//		}
		return view;
	}
}
