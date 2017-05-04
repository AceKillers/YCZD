package zz.zept.yczd.activity;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import config.AppConfig;
import zz.zept.tree.FileBean;
import zz.zept.tree.Node;
import zz.zept.tree.TreeListViewAdapter;
import zz.zept.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import zz.zept.yczd.R;
import zz.zept.yczd.adapter.SimpleTreeAdapter;
import zz.zept.yczd.bean.DiaMachineContenBean.DataBean;
import zz.zept.yczd.bean.YCZDMachineBean;
import zz.zept.yczd.utils.FileUtil;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.TimeUtil;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.view.ListViewForScrollView;
import zz.zept.yczd.view.MyCalendarView;
import zz.zept.yczd.view.MyCalendarView.OnCalendarClickListener;
import zz.zept.yczd.view.MyCalendarView.OnCalendarDateChangedListener;

public class DiagnoseActivity extends BaseActicity {
	private Button btn_no;
	private Button btn_query;
	private Button btn_more;
	int unitid;
	private Button btn_clean;
	private TextView et_start_time, et_end_time, et_machine;
	String date = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式
	int tag;
	private List<FileBean> mDatas = new ArrayList<FileBean>();
	String machineID;
	LinearLayout ll;
	String url;
	ListViewForScrollView lv_diagonse;
	private List<DataBean> list;
	private Intent intent;
	private int height;
	private int width;
	private PopupWindow popupwindow;
	private ListView lv;
	private TreeListViewAdapter<FileBean> mAdapter;

	@Override
	void initListener() {
		et_end_time.setOnClickListener(this);
		btn_no.setOnClickListener(this);
		btn_query.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		btn_clean.setOnClickListener(this);
		et_start_time.setOnClickListener(this);
		et_machine.setOnClickListener(this);
		lv_diagonse.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ll.setVisibility(View.VISIBLE);
				return true;
			}
		});
	}

	@Override
	void initView() {
		et_end_time = (TextView) findViewById(R.id.et_end_time);
		btn_no = (Button) findViewById(R.id.btn_no);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_more = (Button) findViewById(R.id.btn_more);
		btn_clean = (Button) findViewById(R.id.btn_clean);
		et_start_time = (TextView) findViewById(R.id.et_start_time);
		et_machine = (TextView) findViewById(R.id.et_machine);
		lv_diagonse = (ListViewForScrollView) findViewById(R.id.lv_diagonse);

	}

	@Override
	void initData() {
		Date date = new Date();
		String time = TimeUtil.date2StringYMD(date);
		et_end_time.setText(time);
		Calendar cal = Calendar.getInstance();// 使用日历类
		Calendar calfirst = Calendar.getInstance();// 使用日历类

		calfirst.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		et_start_time.setText(TimeUtil.date2StringYMD(calfirst.getTime()));
	}

	@Override
	void init() {
		setContentView(R.layout.activity_diagonse);
		WindowManager wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
		StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_query:
			queryData();
			break;
		case R.id.btn_more:
			ll.setVisibility(View.GONE);
			DownloadRequest request = NoHttp.createDownloadRequest(
					"http://192.168.66.31:81/Append/DiagInstant/" + list.get(0).getDRI_ID() + ".doc",
					AppConfig.getInstance().APP_PATH_ROOT, "20160001.doc", true, true);
			// System.out.println(AppConfig.getInstance().APP_PATH_ROOT);
			Logger.setDebug(true);

			DownloadListener downloadListener = new DownloadListener() {

				@Override
				public void onDownloadError(int what, Exception exception) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders,
						long allCount) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(DiagnoseActivity.this, "开始下载");
				}

				@Override
				public void onProgress(int what, int progress, long fileCount) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(DiagnoseActivity.this, "已经下载" + progress + "%");
				}

				@Override
				public void onFinish(int what, String filePath) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					// System.out.println(AppConfig.getInstance().APP_PATH_ROOT
					// + "/20160001.doc");
					FileUtil.openFile(DiagnoseActivity.this, new File(filePath));
				}

				@Override
				public void onCancel(int what) {
					// TODO Auto-generated method stub

				}
			};
			NoHttp.newDownloadQueue().add(12312312, request, downloadListener);

			break;
		case R.id.btn_clean:
			et_start_time.setText(null);
			et_end_time.setText(null);
			et_machine.setText("");
			break;
		case R.id.et_start_time:
			// ToastUtils.showToast(DiagnoseActivity1.this, "et_start_time置");
			tag = 1;
			new PopupWindows(DiagnoseActivity.this, v);
			break;
		case R.id.et_end_time:
			// ToastUtils.showToast(DiagnoseActivity1.this, "et_start_time置");
			tag = 2;
			new PopupWindows(DiagnoseActivity.this, v);
			break;
		case R.id.et_machine:
			View view = View.inflate(this, R.layout.popupwindow, null);
			popupwindow = new PopupWindow(view, width, height / 100 * 99);
			popupwindow.setFocusable(true);
			popupwindow.setOutsideTouchable(true);

			// 必须设置背景,不然点击外面让消失不能生效
			popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_day_bg));
			;
			popupwindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			lv = (ListView) view.findViewById(R.id.lv);

			getServiceDatas();

			break;

		case R.id.btn_no:
			ll.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void queryData() {
		if (TextUtils.isEmpty(et_start_time.getText().toString()) || TextUtils.isEmpty(et_machine.getText().toString())
				|| TextUtils.isEmpty(et_end_time.getText().toString())) {
			ToastUtils.showToast(this, "选项不能为空");
		} else {
			ToastUtils.showToast(DiagnoseActivity.this, "正在查询,请稍等");
			url = "http://192.168.66.31:81/api/dept/getDiagReportInstantList" + "?unitid=" + unitid + "&starttime="
					+ et_start_time.getText().toString() + "&endtime=" + et_end_time.getText().toString();
			System.out.println(url);

			OnResponseListener<String> responseListener = new OnResponseListener<String>() {

				@Override
				public void onSucceed(int arg0, Response<String> arg1) {
					// TODO Auto-generated method stub
					String json = arg1.get();
					Gson gson = new Gson();
					list = gson.fromJson(json, zz.zept.yczd.bean.DiaMachineContenBean.class).getData();
					if (list.size() == 0) {
						ToastUtils.showToast(DiagnoseActivity.this, "服务器数据为空");
					} else {
						ToastUtils.showToast(DiagnoseActivity.this, "长按可下载并打开文档");
						lv_diagonse.setAdapter(new BaseAdapter() {

							@Override
							public View getView(int position, View convertView, ViewGroup parent) {
								View view = null;
								TextView tv1, tv2, tv3;
								// TODO Auto-generated method stub
								if (convertView == null) {
									view = View.inflate(DiagnoseActivity.this, R.layout.item_dia_machine_content, null);
								} else {
									view = convertView;
								}
								tv1 = (TextView) view.findViewById(R.id.type);
								tv2 = (TextView) view.findViewById(R.id.time);
								tv3 = (TextView) view.findViewById(R.id.tittle);

								tv1.setText(list.get(position).getDRIT_NAME());
								tv2.setText(list.get(position).getDRI_ENTERTIME());
								tv3.setText(list.get(position).getDRI_TITLE());
								if (position == 0) {
									view.setBackgroundResource(R.drawable.item2);
								} else {
									if (position % 2 == 0) {
										view.setBackgroundResource(R.drawable.item2);
									} else {
										view.setBackgroundResource(R.drawable.item);
									}
								}
								return view;
							}

							@Override
							public long getItemId(int position) {
								// TODO Auto-generated method stub
								return position;
							}

							@Override
							public Object getItem(int position) {
								// TODO Auto-generated method stub
								return list.get(position);
							}

							@Override
							public int getCount() {
								// TODO Auto-generated method stub
								return list.size();
							}
						});
					}

				}

				@Override
				public void onStart(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFinish(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailed(int arg0, Response<String> arg1) {
					// TODO Auto-generated method stub

				}
			};
			NoHttp.newRequestQueue().add(111, NoHttp.createStringRequest(url), responseListener);
		}
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.popupwindow_calendar, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_1));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);//
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			final TextView popupwindow_calendar_month = (TextView) view.findViewById(R.id.popupwindow_calendar_month);
			final MyCalendarView calendar = (MyCalendarView) view.findViewById(R.id.popupwindow_calendar);
			Button popupwindow_calendar_bt_enter = (Button) view.findViewById(R.id.popupwindow_calendar_bt_enter);

			popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年" + calendar.getCalendarMonth() + "月");

			if (null != date) {

				int years = Integer.parseInt(date.substring(0, date.indexOf("-")));
				int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
				popupwindow_calendar_month.setText(years + "年" + month + "月");

				calendar.showCalendar(years, month);
				calendar.setCalendarDayBgColor(date, R.drawable.calendar_date_focused);
			}

			List<String> list = new ArrayList<String>(); // 设置标记列表
			list.add("2014-04-01");
			list.add("2014-04-02");
			calendar.addMarks(list, 0);

			// 监听所选中的日期
			calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

				public void onCalendarClick(int row, int col, String dateFormat) {
					int month = Integer
							.parseInt(dateFormat.substring(dateFormat.indexOf("-") + 1, dateFormat.lastIndexOf("-")));

					if (calendar.getCalendarMonth() - month == 1// 跨年跳转
							|| calendar.getCalendarMonth() - month == -11) {
						calendar.lastMonth();

					} else if (month - calendar.getCalendarMonth() == 1 // 跨年跳转
							|| month - calendar.getCalendarMonth() == -11) {
						calendar.nextMonth();

					} else {
						calendar.removeAllBgColor();
						calendar.setCalendarDayBgColor(dateFormat, R.drawable.calendar_date_focused);
						date = dateFormat;// 最后返回给全局 date
					}
				}
			});

			// 监听当前月份
			calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
				public void onCalendarDateChanged(int year, int month) {
					popupwindow_calendar_month.setText(year + "年" + month + "月");
				}
			});

			// 上月监听按钮
			RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view
					.findViewById(R.id.popupwindow_calendar_last_month);
			popupwindow_calendar_last_month.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					calendar.lastMonth();
				}

			});

			// 下月监听按钮
			RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view
					.findViewById(R.id.popupwindow_calendar_next_month);
			popupwindow_calendar_next_month.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					calendar.nextMonth();
				}
			});

			// 关闭窗口
			popupwindow_calendar_bt_enter.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					dismiss();
					if (tag == 1) {
						et_start_time.setText(date);
					} else {
						et_end_time.setText(date);
					}

				}
			});
		}
	}

	void getServiceDatas() {
		Request<String> request = NoHttp
				.createStringRequest("http://192.168.66.31:81/api/dept/getDeptList/?DeptDeep=4");
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			private zz.zept.yczd.bean.YCZDMachineBean.DataBean dataBean;

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				YCZDMachineBean yCZDMachineBean = gson.fromJson(response.get(), YCZDMachineBean.class);
				mDatas.clear();
				if (yCZDMachineBean.getData().size() > 0) {
					for (int i = 0; i < yCZDMachineBean.getData().size(); i++) {
						dataBean = yCZDMachineBean.getData().get(i);
						mDatas.add(new FileBean(dataBean.getId(), dataBean.getParentid(), dataBean.getText()));
					}

					try {
						mAdapter = new SimpleTreeAdapter<FileBean>(lv, DiagnoseActivity.this, mDatas, 1);
						mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
							@Override
							public void onClick(Node node, int position) {
								if (node.isLeaf()) {
									// Toast.makeText(getApplicationContext(),
									// node.getName(),
									// Toast.LENGTH_SHORT).show();
									popupwindow.dismiss();
									mDatas.clear();
									et_machine.setText(node.getName());
									unitid = node.getId();
								}
							}

						});

					} catch (Exception e) {
						e.printStackTrace();
					}
					lv.setAdapter(mAdapter);
				} else {
					ToastUtils.showToast(DiagnoseActivity.this, "服务器无数据");
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
				ToastUtils.showToast(DiagnoseActivity.this, "联网失败");
			}
		};
		NoHttp.newRequestQueue().add(123, request, responseListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
