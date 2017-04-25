package zz.zept.yczd.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.Response;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import config.AppConfig;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.DailyBean;
import zz.zept.yczd.bean.DailyBean.DataBean;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.FileUtil;
import zz.zept.yczd.utils.HttpResponseListener.HttpListener;
import zz.zept.yczd.utils.TimeUtil;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.view.MyCalendarView;
import zz.zept.yczd.view.MyCalendarView.OnCalendarClickListener;
import zz.zept.yczd.view.MyCalendarView.OnCalendarDateChangedListener;

public class DailyActiviyt extends BaseActicity {
	private Button btn_back, bt_query, btn_more, btn_no;
	ListView lv_daily;
	LinearLayout ll;
	EditText et__time;
	String date = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式
	String url;

	private List<DataBean> data;

	@Override
	void initListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		bt_query.setOnClickListener(this);
		et__time.setOnClickListener(this);
		btn_no.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		lv_daily.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ll.setVisibility(View.VISIBLE);
				return false;
			}
		});
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btn_no = (Button) findViewById(R.id.btn_no);
		btn_back = (Button) findViewById(R.id.btn_back);
		bt_query = (Button) findViewById(R.id.bt_query);
		et__time = (EditText) findViewById(R.id.et__time);
		lv_daily = (ListView) findViewById(R.id.lv_daily);
		ll = (LinearLayout) findViewById(R.id.ll);
		btn_more = (Button) findViewById(R.id.btn_more);
	}

	// Calendar cal = Calendar.getInstance();// 使用日历类
	// Calendar calfirst = Calendar.getInstance();// 使用日历类
	//
	// calfirst.set(Calendar.DAY_OF_MONTH, 2);// 设置为1号,当前日期既为本月第一天
	// Date time = calfirst.getTime();
	// TimeUtil timeUtil = new TimeUtil();
	//
	// String date2StringYMD = TimeUtil.date2StringYMD(time);
	@Override
	void initData() {

		// TODO Auto-generated method stub
		Date dt = new Date();
		Long time = dt.getTime();// 这就是距离1970年1月1日0点0分0秒的毫秒数
		long x = time - (long) 86400000;
		et__time.setText(TimeUtil.date2StringYMD(new Date(x)));
		getServiceData();
	}

	@Override
	void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_daily);
		Date date = new Date();
		long time = date.getTime();
		sp.edit().putLong("time", time).commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			Intent intent = new Intent(DailyActiviyt.this, MainActivity.class);

			startActivity(intent);
			finish();
			break;
		case R.id.btn_more:
			ll.setVisibility(View.GONE);
			System.out.println(data.get(0).getUrl());

			// ToastUtils.showToast(this, "btn_more");
			DownloadRequest request = NoHttp.createDownloadRequest(data.get(0).getUrl(),
					AppConfig.getInstance().APP_PATH_ROOT, "日报.xls", true, true);
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
					ToastUtils.showToast(DailyActiviyt.this, "已下载:" + arg1 + "%");
				}

				@Override
				public void onFinish(int arg0, String arg1) {
					// TODO Auto-generated method stub

					FileUtil.openFile(DailyActiviyt.this, new File(AppConfig.getInstance().APP_PATH_ROOT + "/日报.xls"));
				}

				@Override
				public void onDownloadError(int arg0, Exception arg1) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(DailyActiviyt.this, "下载失败,请稍后再试");
				}

				@Override
				public void onCancel(int arg0) {
					// TODO Auto-generated method stub

				}
			};
			NoHttp.newDownloadQueue().add(12312312, request, downloadListener);

			break;
		case R.id.bt_query:
			// ToastUtils.showToast(DailyActiviyt.this, "bt_query");
			getServiceData();

			break;
		case R.id.et__time:
			// ToastUtils.showToast(DailyActiviyt.this, "et_trend_start_time");
			new PopupWindows(DailyActiviyt.this, v);

			break;
		case R.id.btn_no:
			ll.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void getServiceData() {
		url = "http://192.168.66.31:8080/zdpt/sts/adFindForDayValue.action?date="
				+ et__time.getText().toString().trim();
		CallServer callServer = CallServer.getRequestInstance();
		HttpListener<String> callback = new HttpListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(DailyActiviyt.this, "长按展示可下载表格");
				System.out.println(response.get());
				Gson gson = new Gson();
				DailyBean dailyBean = gson.fromJson(response.get(), DailyBean.class);
				data = dailyBean.getData();
				BaseAdapter adapter = new BaseAdapter() {

					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						// TODO Auto-generated method stub
						View view;
						TextView tv1, tv2, tv3, tv4, tv5;
						if (convertView == null) {
							view = View.inflate(DailyActiviyt.this, R.layout.item_daily, null);
						} else {
							view = convertView;
						}
						tv1 = (TextView) view.findViewById(R.id.tv1);
						tv2 = (TextView) view.findViewById(R.id.tv2);
						tv3 = (TextView) view.findViewById(R.id.tv3);
						tv4 = (TextView) view.findViewById(R.id.tv4);
						tv5 = (TextView) view.findViewById(R.id.tv5);
						tv1.setText(data.get(position).getDe_info() + "\n");
						tv2.setText(data.get(position).getMeihao() + "\n" + "g/kWh");
						String fuhelv = data.get(position).getFuhelv();
						String[] strings = fuhelv.split("\\，");
						tv3.setText(strings[0] + "\n" + strings[1]);
						tv4.setText(data.get(position).getFadianchangyongdianlv() + "%");
						tv5.setText(data.get(position).getFadianliang() + "\n" + " 万kWh");

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
						return data.get(position);
					}

					@Override
					public int getCount() {
						// TODO Auto-generated method stub
						return data.size();
					}
				};
				lv_daily.setAdapter(adapter);

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub

			}
		};
		callServer.add(111, NoHttp.createStringRequest(url), callback);
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
					et__time.setText(date);

				}
			});
		}
	}
}
