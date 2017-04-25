package zz.zept.yczd.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Telephony.Sms.Conversations;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.R.color;
import zz.zept.yczd.bean.OnePagerBean.DataBean;
import zz.zept.yczd.utils.ToastUtils;

@SuppressLint("ResourceAsColor")
public class LVApapter extends BaseAdapter {
	Context context;
	SeekBar sb;
	TextView tv1, tv2, tv3;
	private List<DataBean> datas;

	public LVApapter(Context context, List<DataBean> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_onepager_lv, null);
		}
		sb = (SeekBar) convertView.findViewById(R.id.sb);
		sb.setEnabled(false);
		tv1 = (TextView) convertView.findViewById(R.id.tv1);
		tv2 = (TextView) convertView.findViewById(R.id.tv2);
		tv3 = (TextView) convertView.findViewById(R.id.tv3);
		DataBean dataBean = datas.get(position);
		double loadValue = dataBean.getLoadValue();
		String text = dataBean.getFactoryName() + dataBean.getUnitName();
		String capacity = dataBean.getCapacity();
		String replace = capacity.replace("MW", "");
		int integer = Integer.valueOf(replace);

		BigDecimal b = new BigDecimal(loadValue);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		if (f1 < 10) {
			
			System.out.println("ssssssssss");
			// tv1.setTextColor(color.wheat);
			// tv2.setTextColor(color.wheat);
			// tv3.setTextColor(color.wheat);
		} else {
		System.out.println("llllllllll");
			// tv1.setTextColor(color.lll);
			// tv2.setTextColor(color.lll);
			// tv3.setTextColor(color.lll);
		}
		tv1.setText(text);
		tv3.setText(f1 + "");
		tv2.setText(dataBean.getCapacity());
		sb.setMax(integer);
		sb.setProgress((int) loadValue);
		return convertView;
	}

}
