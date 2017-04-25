package zz.zept.yczd.warn_activity;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.activity.MainActivity;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;

public class WarnActivity extends Activity {
	TextView tv1, tv2;
	String url1, url2;
	Button btn_back;
	LinearLayout ll1, ll2;
	private Intent intent;
	private int loadNum=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warn);
		SharedPreferences sp = getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		btn_back = (Button) findViewById(R.id.btn_back);
		url1 = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=01&msgOrigin_=1";
		url2 = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=01&msgOrigin_=2";
		
//		http://192.168.66.13:8090/SPIC/todomessage.action?username_=guest&msgState_=01&msgOrigin_=2
		listener();
		getServiceDatas();
	}
	
	

	private void listener() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(WarnActivity.this, MainActivity.class);

				startActivity(intent);
				finish();
			}
		});
		ll1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(WarnActivity.this, WarnOneActivity.class);
				startActivity(intent);
//				finish();
			}
		});
		ll2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(WarnActivity.this, WarnTwoActivity.class);
				startActivity(intent);
//				finish();
			}
		});
	}

	private void getServiceDatas() {
		Utils.showWaiting(WarnActivity.this);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				System.out.println(response.get());
				tv1.setText(response.get().replace("\"", ""));
				loadNum++;
				isLoadFinish();
			}

			@Override
			public void onStart(int what) {

			}

			@Override
			public void onFinish(int what) {
//				loadNum++;
//				isLoadFinish();
			}

			@Override
			public void onFailed(int what, Response<String> response) {
		ToastUtils.showToast(WarnActivity.this, "联网失败,请检查网络");
		loadNum++;
		isLoadFinish();
			}
		};
		NoHttp.newRequestQueue().add(123231, NoHttp.createStringRequest(url1), responseListener);

		OnResponseListener<String> responseListener2 = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				System.out.println(response.get());
				tv2.setText(response.get().replace("\"", ""));
				loadNum++;
				isLoadFinish();
			}

			@Override
			public void onStart(int what) {

			}

			@Override
			public void onFinish(int what) {
//				loadNum++;
//				isLoadFinish();

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				loadNum++;
				isLoadFinish();
			}
		};
	
		NoHttp.newRequestQueue().add(123231, NoHttp.createStringRequest(url2), responseListener2);

	}
	
	private void isLoadFinish(){
		if(loadNum==2){
			Utils.closeWaiting();
		}
		
	}
}
