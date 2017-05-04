package zz.zept.yczd.pager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import zz.zept.tree.FileBean;
import zz.zept.tree.Node;
import zz.zept.tree.TreeListViewAdapter;
import zz.zept.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import zz.zept.yczd.R;
import zz.zept.yczd.adapter.SimpleTreeAdapter;
import zz.zept.yczd.bean.PicBean;
import zz.zept.yczd.bean.YCZDMachineBean;
import zz.zept.yczd.utils.LogUtils;
import zz.zept.yczd.utils.ToastUtils;

/**
 * 自定义pager:维护了一个view view的初始化 view的数据填充 设置对应的界面
 * 
 * @author sld
 *
 */
public class ThreePager extends Activity {
	TextView tv_nox, tv_dypqwd1, tv_dypqwd2, tv_zk, tv_zryl, tv_zrqw, tv_zqyl, tv_zqwd, tv_so2,
			tv_pywd, tv_gsll, tv_gjckwd, tv_djckwd, tv_gjrkwd, tv_njswd, tv_jzfh,tv_zqll;
	private View view;
	TextView et;
	private int width;
	private int height;
	private int unitid;


	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				getServicePicDatas(unitid + "" );
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic3);
		initView();
		initData();
		initListener();
	}

	public void initView() {
//		et = (TextView) view.findViewById(R.id.et_mach);
//		tv_nox = (TextView) view.findViewById(R.id.tv_nox);
//		tv_pywd = (TextView) view.findViewById(R.id.tv_pywd);
//		tv_gsll = (TextView) view.findViewById(R.id.tv_gsll);
//		tv_gjckwd = (TextView) view.findViewById(R.id.tv_gjckwd);
//		tv_gjrkwd = (TextView) view.findViewById(R.id.tv_gjrkwd);
//		tv_djckwd = (TextView) view.findViewById(R.id.tv_djckwd);
//		tv_njswd = (TextView) view.findViewById(R.id.tv_njswd);
//		tv_zryl = (TextView) view.findViewById(R.id.tv_zryl);
//		tv_zrqw = (TextView) view.findViewById(R.id.tv_zrqw);
//		tv_zqyl = (TextView) view.findViewById(R.id.tv_zqyl);
//		tv_zqwd = (TextView) view.findViewById(R.id.tv_zqwd);
//		tv_dypqwd1 = (TextView) view.findViewById(R.id.tv_dypqwd1);
//		tv_dypqwd2 = (TextView) view.findViewById(R.id.tv_dypqwd2);
//		tv_zk = (TextView) view.findViewById(R.id.tv_zk);
//		tv_so2 = (TextView) view.findViewById(R.id.tv_so2);
//		tv_jzfh = (TextView) view.findViewById(R.id.tv_jzfh);
		
		
		et = (TextView)findViewById(R.id.et_mach);
		tv_nox = (TextView)findViewById(R.id.tv_nox);
		tv_pywd = (TextView)findViewById(R.id.tv_pywd);
		tv_gsll = (TextView) findViewById(R.id.tv_gsll);
		tv_gjckwd = (TextView)findViewById(R.id.tv_gjckwd);
		tv_gjrkwd = (TextView)findViewById(R.id.tv_gjrkwd);
		tv_djckwd = (TextView)findViewById(R.id.tv_djckwd);
		tv_njswd = (TextView)findViewById(R.id.tv_njswd);
		tv_zryl = (TextView)findViewById(R.id.tv_zryl);
		tv_zrqw = (TextView)findViewById(R.id.tv_zrqw);
		tv_zqll = (TextView)findViewById(R.id.tv_zqll);
		tv_zqwd = (TextView)findViewById(R.id.tv_zqwd);
		tv_dypqwd1 = (TextView) findViewById(R.id.tv_dypqwd1);
		tv_dypqwd2 = (TextView) findViewById(R.id.tv_dypqwd2);
		tv_zk = (TextView)findViewById(R.id.tv_zk);
		tv_so2 = (TextView)findViewById(R.id.tv_so2);
		tv_jzfh = (TextView)findViewById(R.id.tv_jzfh);
		tv_zqyl = (TextView)findViewById(R.id.tv_zqyl);
	}

	public void initData() {
		WindowManager wm = getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	public void initListener() {
		et.setOnClickListener(new OnClickListener() {

			private PopupWindow popupwindow;

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				view = View.inflate(ThreePager.this, R.layout.popupwindow3, null);
				popupwindow = new PopupWindow(view, width, height - et.getHeight() * 2);
				popupwindow.setFocusable(true);
				popupwindow.setOutsideTouchable(true);

				// 必须设置背景,不然点击外面让消失不能生效
				popupwindow.setBackgroundDrawable(ThreePager.this.getResources().getDrawable(R.drawable.bai));

				popupwindow.showAsDropDown(v);
				lv = (ListView) view.findViewById(R.id.lv);
				getServiceDatas();
			}

			private List<FileBean> mDatas = new ArrayList<FileBean>();
			private TreeListViewAdapter<FileBean> mAdapter;
			private ListView lv;

			private void getServiceDatas() {
				Request<String> request = NoHttp
						.createStringRequest("http://192.168.66.31:81/api/dept/getDeptList/?DeptDeep=4");
				OnResponseListener<String> responseListener = new OnResponseListener<String>() {

					private zz.zept.yczd.bean.YCZDMachineBean.DataBean dataBean;

					@Override
					public void onSucceed(int what, Response<String> response) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						LogUtils.i("ss", response.get());
						YCZDMachineBean yCZDMachineBean = gson.fromJson(response.get(), YCZDMachineBean.class);
						mDatas.clear();
						if (yCZDMachineBean.getData().size() > 0) {
							for (int i = 0; i < yCZDMachineBean.getData().size(); i++) {
								dataBean = yCZDMachineBean.getData().get(i);
								mDatas.add(new FileBean(dataBean.getId(), dataBean.getParentid(), dataBean.getText()));
							}

							try {
								mAdapter = new SimpleTreeAdapter<FileBean>(lv, ThreePager.this, mDatas, 1);
								mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {

									@Override
									public void onClick(Node node, int position) {
										if (node.isLeaf()) {
											// Toast.makeText(getApplicationContext(),
											// node.getName(),
											// Toast.LENGTH_SHORT).show();

											boolean showing = popupwindow.isShowing();
											if (showing) {
												popupwindow.dismiss();
												LogUtils.i("123", "11");
											}
											LogUtils.i("123", "12");
											mDatas.clear();
											et.setText(node.getName());
											unitid = node.getId();
											getServicePicDatas(unitid + "");

										}
									}

								});

							} catch (Exception e) {
								e.printStackTrace();
							}
							lv.setAdapter(mAdapter);
						} else {
							ToastUtils.showToast(ThreePager.this, "服务器无数据");
						}
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
						ToastUtils.showToast(ThreePager.this, "联网失败");
					}
				};
				NoHttp.newRequestQueue().add(123, request, responseListener);
			}
		});
	}

	public void getServicePicDatas(String string) {
		// TODO Auto-generated method stub
		Request<String> stringRequest = NoHttp
				.createStringRequest("http://192.168.66.31:81/api/advanced/getSnapByUnit?unitID=" + string);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {
			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method
				// stub
				// ToastUtils.showToast(PicActivity.this,
				// response.get());
				LogUtils.i("ss", response.get());
				Gson gson = new Gson();
				PicBean picBean = gson.fromJson(response.get(), PicBean.class);
				tv_nox.setText("N0x:" + picBean.getNox() + "mg/Nm³");
				tv_pywd.setText(picBean.getPywd() + "℃");
				tv_gsll.setText(picBean.getGsll() + "t/h");
				tv_gjckwd.setText(picBean.getGjckwd() + "℃");
				tv_gjrkwd.setText(picBean.getGjrkwd() + "℃");
				tv_djckwd.setText(picBean.getDjckwd() + "℃");
				tv_njswd.setText(picBean.getNjswd() + "℃");
				tv_zryl.setText(picBean.getZryl() + "Mpa");
				tv_zqyl.setText(picBean.getZqyl() + "Mpa");
				tv_zrqw.setText(picBean.getZrwd() + "℃");
				tv_zqll.setText("主气流量："+picBean.getZqll() + "t/h");
				tv_zqwd.setText(picBean.getZqwd() + "℃");
				tv_dypqwd1.setText(picBean.getDypqwd1() + "℃");
				tv_dypqwd2.setText(picBean.getDypqwd2() + "℃");
				tv_zk.setText("真空："+picBean.getZk() + "kpa");
				tv_so2.setText("SO2:" + picBean.getSo2() + "mg/Nm³");
				tv_jzfh.setText("负荷："+picBean.getJzfh() + "MW");

			}

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method
				// stub

			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method
				// stub

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method
				// stub
				ToastUtils.showToast(ThreePager.this, "服务器繁忙，请稍后再试");
			}
		};
		NoHttp.newRequestQueue().add(1231, stringRequest, responseListener);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
