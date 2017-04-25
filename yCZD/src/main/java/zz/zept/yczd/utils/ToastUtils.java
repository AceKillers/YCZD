package zz.zept.yczd.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private static Toast toast;

	/**
	 * 静态toast
	 *
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {
		if (toast == null)
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

		toast.setText(text);
		toast.show();
	}

}
