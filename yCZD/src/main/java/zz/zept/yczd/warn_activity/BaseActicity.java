package zz.zept.yczd.warn_activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View.OnClickListener;
import zz.zept.yczd.res.MyRes;

abstract class BaseActicity extends Activity implements OnClickListener {

	public SharedPreferences sp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
		init();
		initView();
		initListener();
		initData();

	}

	/**
	 * xml文件转换为view
	 *
	 */
	abstract void init();

	/**
	 * 注册监听
	 *
	 */
	abstract void initListener();

	/**
	 * findViewById
	 *
	 */
	abstract void initView();

	/**
	 * 数据的填充
	 *
	 */
	abstract void initData();

}
