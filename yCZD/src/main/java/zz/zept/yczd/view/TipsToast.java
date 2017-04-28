package zz.zept.yczd.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zz.zept.yczd.R;


/**
 * com.york.yorkbbs.widget.TipsToast
 * 
 * @author 王少雷 <br/>
 *         create at 2015年3月26日 下午2:06:54
 */
public class TipsToast extends Toast {

	public TipsToast(Context context) {
		super(context);
	}

	public static void show(Context context, CharSequence text) {
		TipsToast toast = new TipsToast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.layout_view_tips, null);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		toast.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);

		toast.show();
	}

	public static void show(Context context, int resId) throws Resources.NotFoundException {
		makeText(context, context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
	}
}
