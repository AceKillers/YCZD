package zz.zept.yczd.view;

import zz.zept.yczd.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * 自定义对话框，用于网络请求的时候
 * @author Administrator
 *
 */
public class AlertDialog extends Dialog {
	ProgressBar xlistview_header_progressbar;

	/**
	 * 设置成自定义view，不可点击消失
	 * @param context
	 * @param theme
	 */
	public AlertDialog(Context context, int theme, boolean cancel) {
		super(context, theme);
		this.setContentView(R.layout.alert_dialog);
		this.setCanceledOnTouchOutside(cancel);
	}

}
