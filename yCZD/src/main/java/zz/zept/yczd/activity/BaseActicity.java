package zz.zept.yczd.activity;

import com.yolanda.nohttp.NoHttp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View.OnClickListener;
import zz.zept.yczd.res.MyRes;

abstract class BaseActicity extends Activity implements OnClickListener {

	public SharedPreferences sp;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		NoHttp.newRequestQueue().cancelAll();
		NoHttp.setDefaultConnectTimeout(20*1000);
		NoHttp.setDefaultReadTimeout(60*1000);
		NoHttp.setDefaultDownloadThreadSize(4);
		NoHttp.setDefaultRequestThreadSize(10);
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
