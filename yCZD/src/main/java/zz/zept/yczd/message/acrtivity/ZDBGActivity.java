package zz.zept.yczd.message.acrtivity;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import config.AppConfig;
import zz.zept.yczd.R;
import zz.zept.yczd.activity.DailyActiviyt;
import zz.zept.yczd.bean.ZDBGBean;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.FileUtil;
import zz.zept.yczd.utils.ToastUtils;

public class ZDBGActivity extends BaseActicity {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13;
	Button btn_yes, btn_no;
	LinearLayout ll1, ll2, ll3;
	private String url;
	private String driID;
	private ZDBGBean zdbgBean;
	String urlFJ = "http://192.168.66.31:81/";

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_no:
			ToastUtils.showToast(this, "btn_no");
			postDatas("2");
			break;
		case R.id.btn_yes:
			ToastUtils.showToast(this, "btn_yes");
			postDatas("1");
			break;
		case R.id.ll1:
			String url1 = urlFJ + zdbgBean.getData().getAppends().get(0).getDRIA_APPENDPATH();
			downFJ(url1,"附件1.xls");

			break;
		case R.id.ll2:
			ToastUtils.showToast(this, "ll2");
			String url2 = urlFJ + zdbgBean.getData().getAppends().get(1).getDRIA_APPENDPATH();
			downFJ(url2,"附件2.xls");
			break;
		case R.id.ll3:
			ToastUtils.showToast(this, "ll3");
			String url3 = urlFJ + zdbgBean.getData().getAppends().get(2).getDRIA_APPENDPATH();
			downFJ(url3,"附件3.xls");
			break;
		default:
			break;
		}
	}

	private void downFJ(String url,final String  name) {
		DownloadRequest request = NoHttp.createDownloadRequest(url,
				AppConfig.getInstance().APP_PATH_ROOT, name, true, true);
		System.out.println(AppConfig.getInstance().APP_PATH_ROOT);
		Logger.setDebug(true);
		DownloadListener downloadListener = new DownloadListener() {

			@Override
			public void onStart(int arg0, boolean arg1, long arg2, Headers arg3, long arg4) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgress(int arg0, int arg1, long arg2) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(ZDBGActivity.this, "已下载:" + arg1 + "%");
			}

			@Override
			public void onFinish(int arg0, String arg1) {
				// TODO Auto-generated method stub

				FileUtil.openFile(ZDBGActivity.this, new File(AppConfig.getInstance().APP_PATH_ROOT + name));
			}

			@Override
			public void onDownloadError(int arg0, Exception arg1) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(ZDBGActivity.this, "下载失败,请稍后再试");
			}

			@Override
			public void onCancel(int arg0) {
				// TODO Auto-generated method stub

			}
		};
		NoHttp.newDownloadQueue().add(12312312, request, downloadListener);
	}

	private void postDatas(String tag) {
		Request<String> createStringRequest = NoHttp
				.createStringRequest(
						"http://192.168.66.31:81/TZ/Diag/DiagInstantFlow?DirID=" + driID + "&UserName="
								+ sp.getString(MyRes.LOGIN_NAME, "") + "&DirTitle=" + zdbgBean.getData().getDRI_TITLE()
								+ "&FlowOrder=" + zdbgBean.getData().getDRI_STATE() + "&FlowAction=" + tag,
						RequestMethod.POST);
		createStringRequest.setTag(this);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ZDBGActivity.this, MessageActivity.class);
				startActivity(intent);
			}

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(ZDBGActivity.this, "联网失败，请检查网络");
			}
		};
		NoHttp.newRequestQueue().add(3213, createStringRequest, responseListener);
	}

	@Override
	void init() {
		setContentView(R.layout.activity_message_zdbg);
		url = getIntent().getStringExtra("url");
		driID = getIntent().getStringExtra("driID");
	}

	@Override
	void initListener() {
		// TODO Auto-generated method stub
		btn_no.setOnClickListener(this);
		btn_yes.setOnClickListener(this);
		ll1.setOnClickListener(this);
		ll2.setOnClickListener(this);
		ll3.setOnClickListener(this);
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tv9 = (TextView) findViewById(R.id.tv9);
		tv10 = (TextView) findViewById(R.id.tv10);
		tv11 = (TextView) findViewById(R.id.tv11);
		tv12 = (TextView) findViewById(R.id.tv12);
		tv13 = (TextView) findViewById(R.id.tv13);
		btn_no = (Button) findViewById(R.id.btn_no);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll3 = (LinearLayout) findViewById(R.id.ll3);
		btn_yes = (Button) findViewById(R.id.btn_yes);
	}

	@Override
	void initData() {
		// TODO Auto-generated method stub
		Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
		request.setTag(this);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				String json = response.get();
				System.out.println(json);
				Gson gson = new Gson();
				try {
					zdbgBean = gson.fromJson(json, ZDBGBean.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (zdbgBean.getData().getAPPENDNUM() == 1) {
					ll1.setVisibility(View.VISIBLE);
				}
				if (zdbgBean.getData().getAPPENDNUM() == 2) {
					ll1.setVisibility(View.VISIBLE);
					ll2.setVisibility(View.VISIBLE);
				}
				if (zdbgBean.getData().getAPPENDNUM() == 3) {
					ll1.setVisibility(View.VISIBLE);
					ll2.setVisibility(View.VISIBLE);
					ll3.setVisibility(View.VISIBLE);
				}
				tv1.setText(zdbgBean.getData().getDRI_TITLE());
				tv2.setText(zdbgBean.getData().getFACTROYNAME());
				tv3.setText(zdbgBean.getData().getDRI_HAPPENTIME());
				tv4.setText(zdbgBean.getData().getDEI_NAME());

				tv5.setText(getNewString(zdbgBean.getData().getDRI_BEHAVI()));
				tv6.setText(getNewString(zdbgBean.getData().getDRI_CURVE()));
				tv7.setText(getNewString(zdbgBean.getData().getDRI_ANALYSIS()));
				tv8.setText(getNewString(zdbgBean.getData().getDRI_ADVICE()));
				tv9.setText(zdbgBean.getData().getDRIL_USERNAME1());
				tv10.setText(zdbgBean.getData().getDRIL_USERNAME2());
				tv11.setText(zdbgBean.getData().getDRIL_USERNAME3());
				tv12.setText(zdbgBean.getData().getDRIL_USERNAME4());
				tv13.setText(zdbgBean.getData().getDEI_NAME());

			}

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub

			}
		};
		NoHttp.newRequestQueue().add(123, request, responseListener);
	}

	private String getNewString(String old) {
		String string = old.replace("<p>", "").replace("</p>", "").replace("<br/>", "");
		return string;
	}

}
