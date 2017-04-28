package zz.zept.yczd.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.Date;

import zz.zept.yczd.R;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.HttpResponseListener.HttpListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.TipsToast;

public class LoginActivity extends BaseActicity {
	ProgressBar pb;
	String baseurl;
	EditText et_login_name, et_login_password;
	Button btn_login;
	private String login_name;
	private String login_password;
	// 联网返回的标记 看帐号密码是不是对照
	String code = "success";
	// 登录成功的毫秒值
	private int keep_time;
	String url;
	private Date date;
	private Intent intent;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
			Date date = new Date();
			long time = date.getTime();
			sp.edit().putLong("time", time).commit();
			startActivity(intent);
			finish();
//			Date date = new Date();
//			long time = date.getTime();
			sp.edit().putLong("time", time).commit();
			//	Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			//	startActivity(intent);
			//	finish();
			if(et_login_name.getText().toString().trim().length()<=0||et_login_password.getText().toString().trim().length()<=0){
				TipsToast.show(LoginActivity.this, "请输入用户名和密码");
				return;
			}
			CallServer callServer = CallServer.getRequestInstance();
			Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
			request.add("username_", et_login_name.getText().toString().trim());
			request.add("password_", et_login_password.getText().toString().trim());
			request.setTag(this);
			HttpListener<String> callback = new HttpListener<String>() {

				@Override
				public void onSucceed(int what, Response<String> response) {
					String json = response.get();
					Utils.closeWaiting();
					if (json.equals("\"success\"")) {
						login_name = et_login_name.getText().toString().trim();
						login_password = et_login_password.getText().toString().trim();
						sp.edit().putString(MyRes.LOGIN_NAME, login_name).commit();
						sp.edit().putString(MyRes.LOGIN_PSW, login_password).commit();
						Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
						Date date = new Date();
						long time = date.getTime();
						sp.edit().putLong("time", time).commit();
						startActivity(intent);
						finish();
					} else {
						TipsToast.show(LoginActivity.this, "请检查帐号密码");
					}
				}

				@Override
				public void onFailed(int what, Response<String> response) {
					// TODO Auto-generated method stub
					Utils.closeWaiting();
					TipsToast.show(LoginActivity.this, "服务器繁忙,稍后再试");
				}
			};
			Utils.showWaiting(LoginActivity.this);
			callServer.add(12312, request, callback);
			break;
		default:
			break;
		}
	}

	void init() {
		setContentView(R.layout.activity_login);
		StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
	}

	void initListener() {
		btn_login.setOnClickListener(this);
	}

	void initView() {
		btn_login = (Button) findViewById(R.id.btn_login);
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		String username=sp.getString(MyRes.LOGIN_NAME, "");
		String pasword=sp.getString(MyRes.LOGIN_PSW, "");
		if(username.length()>0){
			et_login_name.setText(username);
			if(pasword.length()>0){
				et_login_password.setText(pasword);
			}
		}

	}

	void initData() {
		url = MyRes.BASE_URL + "security_check_.action";
		// long oldTime = sp.getLong("time", 11111);
		// Date date = new Date();
		// long nowTime = date.getTime();
		// if (nowTime - oldTime > 60 * 60000) {
		// ToastUtils.showToast(this, "请登陆");
		//
		// } else {
		// intent = new Intent(this, MainActivity.class);
		// startActivity(intent);
		// finish();
		// }
	}

}
