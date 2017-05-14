package zz.zept.yczd.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import config.AppConfig;

public class MyApplication extends Application {

	private static Application _instance;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;

		NoHttp.setDefaultConnectTimeout(120000);
		NoHttp.setDefaultReadTimeout(120000);
		NoHttp.initialize(this);


		Logger.setTag("sld");
		Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

		AppConfig.getInstance();
	}

	public static Application getInstance() {
		return _instance;
	}

	public String getVersion() {
		try {
			PackageManager pm = getPackageManager();
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
}
