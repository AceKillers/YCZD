package zz.zept.yczd.trend_activity;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import zz.zept.tree.FileBean;
import zz.zept.tree.Node;
import zz.zept.tree.TreeListViewAdapter;
import zz.zept.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import zz.zept.yczd.R;
import zz.zept.yczd.activity.MainActivity;
import zz.zept.yczd.adapter.SimpleTreeAdapter;
import zz.zept.yczd.adapter.TrendChannelLVApapter;
import zz.zept.yczd.bean.ChannelBean;
import zz.zept.yczd.bean.TrendBean;
import zz.zept.yczd.bean.TrendBean.DataBean;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.TimeUtil;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.view.MyCalendarView;
import zz.zept.yczd.view.MyCalendarView.OnCalendarClickListener;
import zz.zept.yczd.view.MyCalendarView.OnCalendarDateChangedListener;

public class TrendActivity extends BaseActicity {
	private Button btn_query, btn_clean;
	private ImageView btn;
	private TextView et_machine_name, et_channel1, et_channel2, et_start, et_end;
	private List<FileBean> mDatas = new ArrayList<FileBean>();
	String url, channel_Url;
	String channelId1, channelId2;
	String date = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式
	// 数据写到哪个EditText的标记
	String x = "inti";
	private TreeListViewAdapter<FileBean> mAdapter;
	private Intent intent;
	private PopupWindow popupwindow;
	private ListView lv;
	private int width;
	private int height;
	int unitid;
	private View view;
	private List<zz.zept.yczd.bean.ChannelBean.DataBean> data;
	private Request<String> request;
	private OnResponseListener<String> responseListener;

	@Override
	void initListener() {
		// TODO Auto-generated method stub
		btn.setOnClickListener(this);
		btn_query.setOnClickListener(this);
		et_machine_name.setOnClickListener(this);
		et_channel1.setOnClickListener(this);
		btn_clean.setOnClickListener(this);
		et_channel2.setOnClickListener(this);
		et_start.setOnClickListener(this);
		et_end.setOnClickListener(this);
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btn = (ImageView) findViewById(R.id.btn);
		btn_clean = (Button) findViewById(R.id.btn_clean);
		btn_query = (Button) findViewById(R.id.btn_query);
		et_start = (TextView) findViewById(R.id.et_start);
		et_machine_name = (TextView) findViewById(R.id.et_machine_name);
		et_end = (TextView) findViewById(R.id.et_end);
		et_channel1 = (TextView) findViewById(R.id.et_channel1);
		et_channel2 = (TextView) findViewById(R.id.et_channel2);
	}

	@Override
	void initData() {

		Date date = new Date();
		String time = TimeUtil.date2StringYMD(date);
		et_end.setText(time);
		Calendar cal = Calendar.getInstance();// 使用日历类
		Calendar calfirst = Calendar.getInstance();// 使用日历类

		calfirst.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		et_start.setText(TimeUtil.date2StringYMD(calfirst.getTime()));

	}

	@Override
	void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_trend);
		WindowManager wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn:
			intent = new Intent(TrendActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.btn_clean:
			et_channel1.setText("");
			et_channel2.setText("");
			et_end.setText("");
			et_start.setText("");
			et_machine_name.setText("");
			break;
		case R.id.et_start:
			// ToastUtils.showToast(TrendActivity.this, "et_trend_start_time");
			new PopupWindows(TrendActivity.this, v);
			x = "1";
			break;

		case R.id.et_end:
			// ToastUtils.showToast(TrendActivity.this, "et_trend_end_time");
			new PopupWindows(TrendActivity.this, v);
			x = "2";
			break;
		case R.id.btn_query:

			if (TextUtils.isEmpty(et_machine_name.getText()) || TextUtils.isEmpty(et_channel1.getText())
					|| TextUtils.isEmpty(et_channel2.getText()) || TextUtils.isEmpty(et_start.getText())
					|| TextUtils.isEmpty(et_end.getText())) {
				ToastUtils.showToast(TrendActivity.this, "选项不能为空");
			} else {
				url = MyRes.BASE_URL + "historyAnalysis.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
						+ "&startDate_=" + et_start.getText().toString().trim() + "&endDate_="
						+ et_end.getText().toString().trim() + "&channelId1_=" + channelId1 + "&channelId2_="
						+ channelId2;
				intent = new Intent(this, BoXingTuActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
				sp.edit().putString(MyRes.channel_name1, et_channel1.getText().toString()).commit();
				sp.edit().putString(MyRes.channel_name2, et_channel2.getText().toString()).commit();

			}

			break;

		case R.id.et_channel1:
			if (TextUtils.isEmpty(et_machine_name.getText().toString())) {
				ToastUtils.showToast(this, "请先选取设备");
			} else {
				channel_Url = MyRes.BASE_URL + "channelChoose.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
						+ "&locationId_=" + unitid;
				System.out.println(channel_Url);
				view = View.inflate(this, R.layout.popupwindow2, null);
				popupwindow = new PopupWindow(view, width, height / 100 * 99);
				popupwindow.setFocusable(true);
				popupwindow.setOutsideTouchable(true);

				// 必须设置背景,不然点击外面让消失不能生效
				popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_day_bg));
				;
				popupwindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				lv = (ListView) view.findViewById(R.id.lv);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						channelId1 = data.get(position).getID();
						et_channel1.setText(data.get(position).getName());
						popupwindow.dismiss();
					}
				});
				request = NoHttp.createStringRequest(channel_Url);
				responseListener = new OnResponseListener<String>() {

					@Override
					public void onSucceed(int what, Response<String> response) {
						// TODO Auto-generated method stub
						String json = response.get();
						Gson gson = new Gson();
						ChannelBean channelBean = gson.fromJson(json, ChannelBean.class);
						data = channelBean.getData();
						lv.setAdapter(new TrendChannelLVApapter(TrendActivity.this, data));
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
				NoHttp.newRequestQueue().add(3123, request, responseListener);
			}

			break;
		case R.id.et_channel2:
			if (TextUtils.isEmpty(et_machine_name.getText().toString())) {
				ToastUtils.showToast(this, "请先选取设备");
			} else {
				channel_Url = MyRes.BASE_URL + "channelChoose.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
						+ "&locationId_=" + unitid;
				System.out.println(channel_Url);
				view = View.inflate(this, R.layout.popupwindow2, null);
				popupwindow = new PopupWindow(view, width, height / 100 * 99);
				popupwindow.setFocusable(true);
				popupwindow.setOutsideTouchable(true);

				// 必须设置背景,不然点击外面让消失不能生效
				popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_day_bg));
				;
				popupwindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				lv = (ListView) view.findViewById(R.id.lv);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						channelId2 = data.get(position).getID();
						et_channel2.setText(data.get(position).getName());
						popupwindow.dismiss();
					}
				});
				request = NoHttp.createStringRequest(channel_Url);
				responseListener = new OnResponseListener<String>() {

					@Override
					public void onSucceed(int what, Response<String> response) {
						// TODO Auto-generated method stub
						String json = response.get();
						Gson gson = new Gson();
						ChannelBean channelBean = gson.fromJson(json, ChannelBean.class);
						data = channelBean.getData();
						lv.setAdapter(new TrendChannelLVApapter(TrendActivity.this, data));
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
				NoHttp.newRequestQueue().add(3123, request, responseListener);
			}

			break;
		case R.id.et_machine_name:
			view = View.inflate(this, R.layout.popupwindow, null);
			popupwindow = new PopupWindow(view, width, height / 100 * 99);
			popupwindow.setFocusable(true);
			popupwindow.setOutsideTouchable(true);

			// 必须设置背景,不然点击外面让消失不能生效
			popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_day_bg));
			;
			popupwindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			lv = (ListView) view.findViewById(R.id.lv);

			getServiceMachineDatas();
			// ToastUtils.showToast(TrendActivity.this, "et_machine_name");
			// intent = new Intent(this, MachineNameActivity11.class);
			// startActivity(intent);
			// finish();
			break;

		default:
			break;
		}
	}

	private void getServiceMachineDatas() {
		Request<String> request = NoHttp
				.createStringRequest("http://192.168.66.13:8090/SPIC/message.action?msgState_=100");
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			private DataBean dataBean;

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				TrendBean yCZDMachineBean = gson.fromJson(response.get(), TrendBean.class);
				mDatas.clear();
				if (yCZDMachineBean.getData().size() > 0) {
					for (int i = 0; i < yCZDMachineBean.getData().size(); i++) {
						dataBean = yCZDMachineBean.getData().get(i);
						mDatas.add(new FileBean(dataBean.getDID(), dataBean.getPID(), dataBean.getName()));
					}

					try {
						mAdapter = new SimpleTreeAdapter<FileBean>(lv, TrendActivity.this, mDatas, 2);
						mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
							@Override
							public void onClick(Node node, int position) {
								if (node.isLeaf()) {
									// Toast.makeText(getApplicationContext(),
									// node.getName(),
									// Toast.LENGTH_SHORT).show();
									popupwindow.dismiss();
									mDatas.clear();
									et_machine_name.setText(node.getName());
									unitid = node.getId();
									// ToastUtils.showToast(TrendActivity.this,
									// unitid + "");
								}
							}

						});

					} catch (Exception e) {
						e.printStackTrace();
					}
					lv.setAdapter(mAdapter);
				} else {
					ToastUtils.showToast(TrendActivity.this, "服务器无数据");
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
				ToastUtils.showToast(TrendActivity.this, "联网失败");
			}
		};
		NoHttp.newRequestQueue().add(123, request, responseListener);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
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
					if (x.equals("1")) {
						System.out.println("是1点击的");
						et_start.setText(date);

						x = "init";

					} else if (x.equals("2")) {
						et_end.setText(date);
						x = "init";
					}

				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
