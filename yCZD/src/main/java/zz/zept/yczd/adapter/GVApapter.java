package zz.zept.yczd.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.activity.MainActivity;

public class GVApapter extends BaseAdapter {
	Context context;
	private String[] texts;
	private int[] images;

	public GVApapter(Context context, String[] texts, int[] images) {
		super();
		this.context = context;
		this.texts = texts;
		this.images = images;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return texts.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return texts.length;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		TextView tv;
		ImageView iv;
		if (convertView == null) {
			view = View.inflate(context, R.layout.item_gv_main, null);
		} else {
			view = convertView;
		}
		tv = (TextView) view.findViewById(R.id.tv_grid_text);
		iv = (ImageView) view.findViewById(R.id.iv_grid_icon);
		tv.setText(texts[position]);
		iv.setImageResource(images[position]);
		return view;

	}

}
