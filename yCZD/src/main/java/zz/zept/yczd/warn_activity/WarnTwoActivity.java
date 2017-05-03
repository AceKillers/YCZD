package zz.zept.yczd.warn_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.TabPageIndicator;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import zz.zept.yczd.R;
import zz.zept.yczd.bean.WarnMessageBean;
import zz.zept.yczd.bean.WarnMessageBean.DataBean;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;

public class WarnTwoActivity extends BaseActicity {
	// 单次联网的数据集合
	private List<DataBean> datasOne, datasTwo;
	// 整体的数据集合
	private List<DataBean> AlldatasOne, AlldatasTwo;
	Boolean tag = true;
	// 是不是第一次刷新
	Boolean tag_two = true, tag_one = true;
	TabPageIndicator indicator;
	ViewPager pager;
	ArrayList<String> indicatorDatas;
	ArrayList<View> pagerDatas;
	Button btn_back, btn_all, btn_read;
	PullToRefreshListView lv1, lv2;
	private MyOneAdapter myOneAdapter;
	private MyTwoAdapter myTwoAdapter;
	int x = 2;
	int y = 2;
	private RequestQueue queue;
	LinearLayout ll_chang_read;
	String MsgId;
	Intent intent;
	private SharedPreferences sp;
	private TextView tv;
	private  boolean isLoadOne=false;
	private boolean isLoadTwo=false;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	void init() {
		setContentView(R.layout.activity_warn_one);
		queue = NoHttp.newRequestQueue();
		datasTwo = new ArrayList<WarnMessageBean.DataBean>();
		sp = getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
	}

	@Override
	void initListener() {
		btn_back.setOnClickListener(this);

		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (position == 0) {
					if(!isLoadOne){
					AlldatasOne.clear();
			        //ToastUtils.showToast(WarnTwoActivity.this, AlldatasOne.size() + "xxxx");
					y = 2;
					tag_one = true;
					getSerViceOneDatas(1);}
				} else if (position == 1) {
                   if(!isLoadTwo){
                		AlldatasTwo.clear();
    					getSerViceTwoDatas(1);
    					x = 2;
    					tag_two = true;
                   }
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		pager = (ViewPager) findViewById(R.id.pager);
		btn_back = (Button) findViewById(R.id.btn_back);
		tv = (TextView) findViewById(R.id.tv);
	}

	@Override
	void initData() {
		// TODO Auto-generated method stub
		tv.setText("预警");
		indicatorDatas = new ArrayList<String>();
		pagerDatas = new ArrayList<View>();
		indicatorDatas.add("未处理");
		indicatorDatas.add("已处理");
		View view_no_read = View.inflate(WarnTwoActivity.this, R.layout.pager_warn_one, null);
		View view_yes_read = View.inflate(WarnTwoActivity.this, R.layout.pager_warn_one2, null);
		pagerDatas.add(view_yes_read);
		pagerDatas.add(view_no_read);
		MypagerAdapter mypagerAdapter = new MypagerAdapter();
		pager.setAdapter(mypagerAdapter);
		indicator.setViewPager(pager);
		getSerViceOneDatas(1);

		AlldatasOne = new ArrayList<WarnMessageBean.DataBean>();
		AlldatasTwo = new ArrayList<WarnMessageBean.DataBean>();

	}

	private void getSerViceTwoDatas(int pager) {
		if(!isLoadTwo){
		Utils.showWaiting(WarnTwoActivity.this);}
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@SuppressWarnings("deprecation")
			@Override
			public void onSucceed(int what, Response<String> response) {
				Gson gson = new Gson();
				datasTwo = gson.fromJson(response.get(), WarnMessageBean.class).getData();
				AlldatasTwo.addAll(datasTwo);
				if (tag_two) {
					myTwoAdapter = new MyTwoAdapter();
					lv2.setAdapter(myTwoAdapter);
					tag_two = false;
				} else {
					myTwoAdapter.notifyDataSetChanged();
				
				}
                Utils.closeWaiting();
               isLoadTwo=true;
				RefreshListener2();

			}

			@Override
			public void onStart(int what) {

			}

			@Override
			public void onFinish(int what) {
				  Utils.closeWaiting();
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				ToastUtils.showToast(WarnTwoActivity.this, "联网失败,请检查网络");
				  Utils.closeWaiting();
			}
		};
		// TODO Auto-generated method stub
		queue.add(21321,
				NoHttp.createStringRequest("http://192.168.66.13:8090/SPIC/todomessage.action?username_="
						+ sp.getString(MyRes.LOGIN_NAME, "") + "&msgState_=1&pageNo_=" + pager + "&msgOrigin_=2"),
				responseListener);

	}

	private void getSerViceOneDatas(int pager) {
		if(!isLoadOne){
			Utils.showWaiting(WarnTwoActivity.this);
		}
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				datasOne = gson.fromJson(response.get(), WarnMessageBean.class).getData();
				AlldatasOne.addAll(datasOne);
			//	ToastUtils.showToast(WarnTwoActivity.this, AlldatasOne.size() + "");
				if (myOneAdapter==null) {
					myOneAdapter = new MyOneAdapter();
					lv1.setAdapter(myOneAdapter);
				}else {
					myOneAdapter.notifyDataSetChanged();
				}
				Utils.closeWaiting();
			    isLoadOne=true;
				RefreshListener1();

			}

			@Override
			public void onStart(int what) {

			}

			@Override
			public void onFinish(int what) {
				Utils.closeWaiting();
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				ToastUtils.showToast(WarnTwoActivity.this, "联网失败,请检查网络");
				Utils.closeWaiting();
			}
		};

		queue.add(21321,
				NoHttp.createStringRequest("http://192.168.66.13:8090/SPIC/todomessage.action?username_="
						+ sp.getString(MyRes.LOGIN_NAME, "") + "&msgState_=00&pageNo_=" + pager + "&msgOrigin_=2"),
				responseListener);

	}

	class MypagerAdapter extends PagerAdapter {
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View view = pagerDatas.get(position);
			System.out.println(position + "sld");
			if (position == 0) {
				lv1 = (PullToRefreshListView) view.findViewById(R.id.lv);
				ll_chang_read = (LinearLayout) view.findViewById(R.id.ll_chang_read);
				btn_all = (Button) view.findViewById(R.id.btn_all);
				btn_read = (Button) view.findViewById(R.id.btn_read);
				btn_all.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ll_chang_read.setVisibility(View.GONE);
					//	ToastUtils.showToast(WarnTwoActivity.this, "btn_all");
						if (AlldatasOne.size() > 30) {
							for (int i = 0; i < 29; i++) {
								MsgId = MsgId + "," + AlldatasOne.get(i).getId();
							}
						} else {
							for (int i = 0; i < AlldatasOne.size(); i++) {
								MsgId = MsgId + "," + AlldatasOne.get(i).getId();
							}
						}
				//		ToastUtils.showToast(WarnTwoActivity.this, MsgId);
						read(MsgId);
						MsgId = "";
					}
				});
				btn_read.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ll_chang_read.setVisibility(View.GONE);
				//		ToastUtils.showToast(WarnTwoActivity.this, "btn_read");
						for (int i = 0; i < AlldatasOne.size(); i++) {
						//	ToastUtils.showToast(WarnTwoActivity.this, AlldatasOne.get(i).getId() + "qwe");
							if (AlldatasOne.get(i).check) {
								MsgId = MsgId + "," + AlldatasOne.get(i).getId();
							}
						}
						read(MsgId);
					//	ToastUtils.showToast(WarnTwoActivity.this, MsgId + "zxcv");
						MsgId = "";
					}
				});
				lv1.setOnItemClickListener(new OnItemClickListener() {
					private long firstTime;
					private long secondTime;

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						ll_chang_read.setVisibility(View.VISIBLE);
						// 当前点击的bean对象
						DataBean dataBean = AlldatasOne.get(position - 1);
						secondTime = System.currentTimeMillis();

						// 如果两次按键时间间隔大于秒，则没有响应
						if (secondTime - firstTime > 500) {
							firstTime = secondTime;// 更新firstTime
						//	ToastUtils.showToast(WarnTwoActivity.this, "1sdd");
						} else {
						//	ToastUtils.showToast(WarnTwoActivity.this, "1sdd");
							// ToastUtils.showToast(WarnOneActivity.this,
							// "sld");
							DataBean dataBeanz = AlldatasOne.get(position - 1);
							intent = new Intent(WarnTwoActivity.this, WarnContentActivity2.class);
							intent.putExtra("1", dataBeanz.getSubject());
							intent.putExtra("2", dataBeanz.getFactoryName());
							intent.putExtra("3", dataBeanz.getMacName());
							// intent.putExtra("4", dataBean.getMsgtype() + "");
							intent.putExtra("5", dataBeanz.getMsglevel() + "");
							intent.putExtra("6", dataBeanz.getSender());
							intent.putExtra("7", dataBeanz.getSendTime());
							intent.putExtra("8", dataBeanz.getContent());
							startActivity(intent);
						}
						AlldatasOne.get(position - 1).check = !AlldatasOne.get(position - 1).check;
						myOneAdapter.notifyDataSetChanged();

					}
				});
			} else if (position == 1) {
				lv2 = (PullToRefreshListView) view.findViewById(R.id.lv);
				ImageView iv_ll1 = (ImageView) view.findViewById(R.id.iv_ll1);
				ImageView iv_ll2 = (ImageView) view.findViewById(R.id.iv_ll2);
				iv_ll1.setVisibility(View.GONE);
				iv_ll2.setVisibility(View.GONE);
				lv2.setOnItemClickListener(new OnItemClickListener() {

					private long firstTime;
					private long secondTime;

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						secondTime = System.currentTimeMillis();

						// 如果两次按键时间间隔大于秒，则没有响应
						if (secondTime - firstTime > 500) {
							firstTime = secondTime;// 更新firstTime

						} else {
							// ToastUtils.showToast(WarnOneActivity.this,
							// "sld");
							DataBean dataBean = AlldatasTwo.get(position - 1);
							intent = new Intent(WarnTwoActivity.this, WarnContentActivity2.class);
							intent.putExtra("1", dataBean.getSubject());
							intent.putExtra("2", dataBean.getFactoryName());
							intent.putExtra("3", dataBean.getMacName());
							// intent.putExtra("4", dataBean.getMsgtype() + "");
							intent.putExtra("5", dataBean.getMsglevel() + "");
							intent.putExtra("6", dataBean.getSender());
							intent.putExtra("7", dataBean.getSendTime());
							intent.putExtra("8", dataBean.getContent());
							startActivity(intent);
						}
					}
				});
			}

			container.addView(view);
			return pagerDatas.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagerDatas.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return indicatorDatas.get(position);
		}
	}

	class MyOneAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return AlldatasOne.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return AlldatasOne.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = View.inflate(WarnTwoActivity.this, R.layout.item_list_warm, null);
				vh = new ViewHolder();
				vh.iv = (ImageView) convertView.findViewById(R.id.iv_check);
				vh.tv1 = (TextView) convertView.findViewById(R.id.tv1);
				vh.tv2 = (TextView) convertView.findViewById(R.id.tv2);
				vh.tv3 = (TextView) convertView.findViewById(R.id.tv3);
				vh.tv4 = (TextView) convertView.findViewById(R.id.tv4);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			if (AlldatasOne.get(position).check) {
				vh.iv.setBackgroundResource(R.drawable.check2);
			} else {
				vh.iv.setBackgroundResource(R.drawable.check1);
			}
			vh.tv1.setText(AlldatasOne.get(position).getFactoryName());
			vh.tv2.setText(AlldatasOne.get(position).getMacName());
			vh.tv3.setText(AlldatasOne.get(position).getMsglevel() + "");
			vh.tv4.setText(AlldatasOne.get(position).getSendTime());

			return convertView;
		}
	}

	class MyTwoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return AlldatasTwo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return AlldatasTwo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = View.inflate(WarnTwoActivity.this, R.layout.item_list_warn2, null);
				vh = new ViewHolder();

				vh.tv1 = (TextView) convertView.findViewById(R.id.tv1);
				vh.tv2 = (TextView) convertView.findViewById(R.id.tv2);
				vh.tv3 = (TextView) convertView.findViewById(R.id.tv3);
				vh.tv4 = (TextView) convertView.findViewById(R.id.tv4);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			vh.tv1.setText(AlldatasTwo.get(position).getFactoryName());
			vh.tv2.setText(AlldatasTwo.get(position).getMacName());
			vh.tv3.setText(AlldatasTwo.get(position).getMsglevel() + "");
			vh.tv4.setText(AlldatasTwo.get(position).getSendTime());

			return convertView;
		}
	}

	private class UpRefresh1 extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		protected void onPostExecute(Void result) {
			lv1.onRefreshComplete();

		}
	}

	private class UpRefresh2 extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		protected void onPostExecute(Void result) {
			lv2.onRefreshComplete();

		}
	}

	class ViewHolder {
		ImageView iv;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
	}

	private void RefreshListener1() {
		lv1.setMode(Mode.PULL_UP_TO_REFRESH);
		// lv的上拉和下拉的监听
		lv1.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
	//			// TODO Auto-generated method stub
		//		ToastUtils.showToast(WarnTwoActivity.this, "下");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
			//	ToastUtils.showToast(WarnTwoActivity.this, "上");

				getSerViceOneDatas(y++);
				new UpRefresh1().execute();
			}
		});
	}

	private void RefreshListener2() {
		lv2.setMode(Mode.PULL_UP_TO_REFRESH);
		lv2.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(@SuppressWarnings("rawtypes") PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
		//		ToastUtils.showToast(WarnTwoActivity.this, "下");
			}

			@Override
			public void onPullUpToRefresh(@SuppressWarnings("rawtypes") PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

				getSerViceTwoDatas(x++);
				new UpRefresh2().execute();
			}
		});
	}

	void read(String MsgId) {

		queue = NoHttp.newRequestQueue();

		String url = "http://192.168.66.13:8090/SPIC/alarmquerymessage.action?username_="
				+ sp.getString(MyRes.LOGIN_NAME, "") + "&msgState_=02&msgId_=" + MsgId;
		Request<String> request = NoHttp.createStringRequest(url);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(WarnTwoActivity.this, "标记成功");
				AlldatasOne.clear();
				getSerViceOneDatas(1);
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
				ToastUtils.showToast(WarnTwoActivity.this, "网络太差,请检查网络");
			}
		};
		queue.add(2213, request, responseListener);

	};

}
