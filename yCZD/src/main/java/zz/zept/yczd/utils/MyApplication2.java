package zz.zept.yczd.utils;

import com.yolanda.nohttp.NoHttp;

import android.app.Application;
import config.AppConfig;

public class MyApplication2 extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		NoHttp.initialize(this);
		AppConfig.getInstance();
	}
}
