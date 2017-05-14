package zz.zept.yczd.message.acrtivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.TabPageIndicator;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zz.zept.yczd.R;
import zz.zept.yczd.activity.MainTabActivity;
import zz.zept.yczd.bean.No_Read_Message_Bean;
import zz.zept.yczd.bean.No_Read_Message_Bean.DataBean;
import zz.zept.yczd.bean.Yes_Read_Message_Bean;
import zz.zept.yczd.bean.Yes_Send_Message_Bean;
import zz.zept.yczd.bean.Yes_Send_Message_Bean.DataBean.MsgcenteraoBean;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;

public class MessageActivity extends BaseActicity {
	private MyYesSendAdapter myYesSendAdapter;
	private PullToRefreshListView lv_no_read;
	private MyNoReadAdapter myNoReadAdapter;
	private PullToRefreshListView lv_yes_read;
	private PullToRefreshListView lv_yes_send;
	private ImageView btn_home;

	LinearLayout ll_chang_read;
	// 未读信息的bean对象
	private No_Read_Message_Bean no_Read_Message_Bean;
	// 未读信息的存储集合
	List<DataBean> no_reda_datas;
	// 未读信息的 总集合
	List<DataBean> no_reda_alldatas;
	// 是不是未读的第一次联网
	boolean no_read_first_tag = true;
	// 是不是未读的第一次显示
	boolean no_read_first_show = true;
	// 未读真正的listview
	private ListView trueListView;
	// 是否让未读消息的选择框显示出来
	boolean no_read_check_show = false;
	// 未读消息的选择框显示的是不是被勾选
	ProgressBar pb1, pb2, pb3;
	// 要更改信息状态的消息的msgID以.拼接起来
	// 是不是只全选了一页
	boolean onlyone = true;
	String MsgId = "";
	boolean no_read_check = false;
	// 已读消息bean对象
	Yes_Read_Message_Bean yes_Read_Message_Bean;
	// 已读消息的存储集合
	List<Yes_Read_Message_Bean.DataBean> yes_reda_datas;
	// 已读信息的 总集合
	List<Yes_Read_Message_Bean.DataBean> yes_reda_alldatas;
	// 是不是已读的第一次联网
	boolean yes_read_first_tag = true;
	// 是不是已读的第一次显示
	boolean yes_read_first_show = true;
	// 已发消息对象
	Yes_Send_Message_Bean yes_Send_Message_Bean;
	// 已发消息的存储集合
	List<MsgcenteraoBean> yes_send_datas;
	// 已发信息的 总集合
	List<MsgcenteraoBean> yes_send_alldatas;
	// 是不是已发的第一次联网
	boolean yes_send_first_tag = true;
	// 是不是已发的第一次显示
	boolean yes_send_first_show = true;
	boolean all_check = true;
	// on_read_listview第一次被点击的毫秒值
	long firstTime;
	// on_read_listview是不是第一次被点

	// on_read_listview第二次被点击的毫秒值
	// 两次点击的时间间隔的毫秒值
	long time;

	long secondTime;
	View view_no_read;
	View view_yes_read;
	View view_yes_send;
	TextView bt_send;
	private List<String> indicatorDatas;
	private List<View> pagerDatas;
	private MyPagerAdapter adapter;
	private MyYesReadAdapter myYesReadAdapter;
	boolean isChange;
	private TabPageIndicator indicator;
	ViewPager viewPager;
	// 标记已读的按钮
	private Button btn_read;
	// 标记全选的按钮
	private Button btn_all;
	// 联网请求队列
	RequestQueue queue;
	// 请求对象
	Request<String> request;
	// 未读信息的base_url
	String no_read_baseurl;
	// 已读信息的base_url
	String yes_read_baseurl;
	// 已发信息的base_url
	String yes_send_baseurl;
	// 未读消息的页码
	int no_Read_Pager = 1;
	// 已读信息的页面
	int yes_Read_Pager = 1;
	// 已发信息的页面
	int yes_Send_Pager = 1;
	private Date date;

	@Override
	void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_news);

		StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
		queue = NoHttp.newRequestQueue();
		no_reda_alldatas = new ArrayList<No_Read_Message_Bean.DataBean>();
		yes_reda_alldatas = new ArrayList<Yes_Read_Message_Bean.DataBean>();
		yes_send_alldatas = new ArrayList<Yes_Send_Message_Bean.DataBean.MsgcenteraoBean>();
		date = new Date();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sp.edit().putString(MyRes.MESSAGE_TITLE, "").commit();
		sp.edit().putString(MyRes.MESSAGE_CONTENT, "").commit();
	}

	@Override
	void initListener() {
		// TODO Auto-generated method stub
		btn_home.setOnClickListener(this);
		bt_send.setOnClickListener(this);

	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btn_home = (ImageView) findViewById(R.id.btn_home);
		bt_send = (TextView) findViewById(R.id.bt_send);
		view_no_read = View.inflate(MessageActivity.this, R.layout.pager_no_read, null);
		view_yes_read = View.inflate(MessageActivity.this, R.layout.pager_yes_read, null);
		view_yes_send = View.inflate(MessageActivity.this, R.layout.pager_yes_send, null);
	}

	@Override
	void initData() {
		Date date = new Date();
		long time = date.getTime();
		sp.edit().putLong("time", time).commit();
		// no_read_baseurl = MyRes.MY_URL + "username_=" +
		// sp.getString(MyRes.LOGIN_NAME, "13123t")
		// + "&msgState_=00&pageNo_=";
		// yes_read_baseurl = MyRes.MY_URL + "username_=" +
		// sp.getString(MyRes.LOGIN_NAME, "13123t")
		// + "&msgState_=1&pageNo_=";
		// yes_send_baseurl = MyRes.MY_URL + "username_=" +
		// sp.getString(MyRes.LOGIN_NAME, "13123t")
		// + "&msgState_=3&pageNo_=";
		no_read_baseurl = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=00&msgOrigin_=0&pageNo_=";
		yes_read_baseurl = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=1&msgOrigin_=0&pageNo_=";
		yes_send_baseurl = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=3&msgOrigin_=0&pageNo_=";
		pagerDatas = new ArrayList<View>();
		indicatorDatas = new ArrayList<String>();

		indicatorDatas.add("待办事项");
		indicatorDatas.add("已发消息");
		indicatorDatas.add("已读消息");
		// pagerDatas viewpager的数据
		pagerDatas.add(view_no_read);
		pagerDatas.add(view_yes_send);
		pagerDatas.add(view_yes_read);
		// Viewpager设置适配器
		viewPager = (ViewPager) findViewById(R.id.pager);

		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		// viewPager.setOnPageChangeListener(new OnPageChangeListener() {});
		// TabPagerIndicator绑定Viewpager
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			// finish();
			Intent intent = new Intent(MessageActivity.this, MainTabActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.bt_send:
			Intent intent2 = new Intent(MessageActivity.this, SendMessageActivity.class);
			startActivity(intent2);
			finish();
			break;

		default:
			break;
		}
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pagerDatas.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			sp.edit().putString(MyRes.MESSAGE_TITLE, "").commit();
			String string = sp.getString(MyRes.MESSAGE_TITLE, "");

			View view = pagerDatas.get(position);
			if (position == 0) {
				btn_read = (Button) view.findViewById(R.id.btn_read);
				btn_all = (Button) view.findViewById(R.id.btn_all);
				pb1 = (ProgressBar) view.findViewById(R.id.pb1);
				ll_chang_read = (LinearLayout) view.findViewById(R.id.ll_chang_read);
				lv_no_read = (PullToRefreshListView) view.findViewById(R.id.lv_no_read);
				trueListView = lv_no_read.getRefreshableView();

				trueListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						// 当前点击的bean对象
						ll_chang_read.setVisibility(View.VISIBLE);
						no_read_check_show = true;
						myNoReadAdapter.notifyDataSetChanged();
						DataBean bean = no_reda_alldatas.get(position - 1);
						secondTime = System.currentTimeMillis();
						// 如果两次按键时间间隔大于秒，则没有响应
						if (secondTime - firstTime > 200) {
							firstTime = secondTime;// 更新firstTime
						} else {
							Intent intent = new Intent(MessageActivity.this, MessageContentActivity.class);
							intent.putExtra(zz.zept.yczd.res.MyRes.MESORIGIN, bean.getMesOrigin());
							intent.putExtra(MyRes.SUBJECT, bean.getSubject());
							intent.putExtra(MyRes.MSGTYPE, bean.getMsgtype() + "");
							intent.putExtra(MyRes.MSGLEVEL, bean.getMsglevel() + "");
							intent.putExtra(MyRes.SENDER, bean.getSender());
							intent.putExtra(MyRes.SENDTIME, bean.getSendTime());
							intent.putExtra(MyRes.CONTENT, bean.getContent());
							intent.putExtra("floworder", bean.getFloworder());
							intent.putExtra("driID", bean.getAttachementurl());
							read(bean.getId());
							startActivity(intent);
							// finish();
						}
						bean.ischeck = !bean.ischeck;
						myNoReadAdapter.notifyDataSetChanged();

					}
				});
				btn_all.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// ToastUtils.showToast(MessageActivity.this, "全选");
						no_read_check_show = false;

						if (no_reda_alldatas.size() < 30) {
							for (int i = 0; i < no_reda_alldatas.size(); i++) {
								MsgId = MsgId + "," + no_reda_alldatas.get(i).getId();
							}
						} else {
							for (int i = 0; i < 30; i++) {
								MsgId = MsgId + "," + no_reda_alldatas.get(i).getId();
							}
						}

						// ToastUtils.showToast(NewsActivity.this, MsgId);
						ll_chang_read.setVisibility(View.GONE);

						read(MsgId);

					}
				});
				btn_read.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						no_read_check_show = false;
						// no_reda_datas.clear();
						for (DataBean bean : no_reda_alldatas) {
							if (bean.ischeck) {

								MsgId = MsgId + "," + bean.getId();

							}

						}

						read(MsgId);
						ll_chang_read.setVisibility(View.GONE);

					}

				});

				lv_no_read.setMode(Mode.PULL_UP_TO_REFRESH);
				lv_no_read.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						onlyone = false;
						no_Read_Pager++;

						getNoReadDatas(no_Read_Pager);

						new UpRefresh1().execute();
					}
				});

				if (no_read_first_tag) {
					getNoReadDatas(no_Read_Pager);
					no_read_first_tag = false;
				}

			} else if (position == 1) {
				pb3 = (ProgressBar) view.findViewById(R.id.pb3);
				lv_yes_send = (PullToRefreshListView) view.findViewById(R.id.lv_yes_send);
				lv_yes_send.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						// 当前点击的bean对象

						MsgcenteraoBean bean = yes_send_alldatas.get(position - 1);
						secondTime = System.currentTimeMillis();
						// 如果两次按键时间间隔大于秒，则没有响应
						if (secondTime - firstTime > 500) {
							firstTime = secondTime;// 更新firstTime
						} else {
							Intent intent = new Intent(MessageActivity.this, MessageContentActivity.class);

							intent.putExtra(MyRes.MESORIGIN, bean.getMesOrigin());
							intent.putExtra(MyRes.FACTORYNAME, (String) bean.getFactoryName());
							intent.putExtra(MyRes.MACNAME, (String) bean.getMacName());
							intent.putExtra(MyRes.SUBJECT, bean.getSubject());
							intent.putExtra(MyRes.MSGTYPE, bean.getMsgtype() + "");
							intent.putExtra(MyRes.MSGLEVEL, bean.getMsglevel() + "");
							intent.putExtra(MyRes.SENDER, bean.getSender());
							intent.putExtra(MyRes.SENDTIME, bean.getSendTime());
							intent.putExtra(MyRes.CONTENT, bean.getContent());

							startActivity(intent);
							// finish();
						}

					}
				});
				lv_yes_send.setMode(Mode.PULL_UP_TO_REFRESH);
				lv_yes_send.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						yes_Send_Pager++;

						getYesSendDatas(yes_Send_Pager);

						new UpRefresh3().execute();
					}
				});
				if (yes_send_first_tag) {
					getYesSendDatas(no_Read_Pager);
					yes_send_first_tag = false;
				}
			} else if (position == 2) {
				lv_yes_read = (PullToRefreshListView) view.findViewById(R.id.lv_yes_read);
				pb2 = (ProgressBar) view.findViewById(R.id.pb2);
				lv_yes_read.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						// 当前点击的bean对象

						Yes_Read_Message_Bean.DataBean bean = yes_reda_alldatas.get(position - 1);
						secondTime = System.currentTimeMillis();
						// 如果两次按键时间间隔大于秒，则没有响应
						if (secondTime - firstTime > 500) {
							firstTime = secondTime;// 更新firstTime
						} else {
							Intent intent = new Intent(MessageActivity.this, MessageContentActivity.class);
							intent.putExtra(MyRes.MESORIGIN, bean.getMesOrigin());
							intent.putExtra(MyRes.FACTORYNAME, bean.getFactoryName());
							intent.putExtra(MyRes.MACNAME, bean.getMacName());
							intent.putExtra(MyRes.SUBJECT, bean.getSubject());
							intent.putExtra(MyRes.MSGTYPE, bean.getMsgtype() + "");
							intent.putExtra(MyRes.MSGLEVEL, bean.getMsglevel() + "");
							intent.putExtra(MyRes.SENDER, bean.getSender());
							intent.putExtra(MyRes.SENDTIME, bean.getSendTime());
							intent.putExtra(MyRes.CONTENT, bean.getContent());

							startActivity(intent);
							// finish();
						}

					}
				});

				lv_yes_read.setMode(Mode.PULL_UP_TO_REFRESH);
				lv_yes_read.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

						yes_Read_Pager++;

						getYesReadDatas(yes_Read_Pager);

						new UpRefresh2().execute();
					}
				});
				if (yes_read_first_tag) {
					getYesReadDatas(no_Read_Pager);
					yes_read_first_tag = false;
				}

			}

			container.addView(view);
			return view;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		/**
		 * 标签的数据源
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return indicatorDatas.get(position);
		}

	}

	public class MyNoReadAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return no_reda_alldatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub

			return no_reda_alldatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			View view;
			final NoReadMessageViewHolder holder;
			if (convertView == null) {
				view = View.inflate(MessageActivity.this, R.layout.item_no_read_list, null);
				holder = new NoReadMessageViewHolder();
				holder.type = (TextView) view.findViewById(R.id.type);
				holder.time = (TextView) view.findViewById(R.id.time);
				holder.content = (TextView) view.findViewById(R.id.content);
				holder.system = (TextView) view.findViewById(R.id.system);
				holder.sender = (TextView) view.findViewById(R.id.sender);
				holder.level = (TextView) view.findViewById(R.id.level);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (NoReadMessageViewHolder) view.getTag();
			}
			String system_Name = null;
			if ("0".equals(no_reda_alldatas.get(position).getMesOrigin())) {
				system_Name = "门户系统";
			}
			if ("1".equals(no_reda_alldatas.get(position).getMesOrigin())) {
				system_Name = "辅助诊断系统";
			}
			if ("2".equals(no_reda_alldatas.get(position).getMesOrigin())) {
				system_Name = "核心诊断系统";
			}
			if ("3".equals(no_reda_alldatas.get(position).getMesOrigin())) {
				system_Name = "通流诊断系统";
			}

//			if (no_read_check_show) {
//				holder.ima_check.setVisibility(View.VISIBLE);
//			} else {
//				holder.ima_check.setVisibility(View.INVISIBLE);
//			}
//			if (no_reda_alldatas.get(position).ischeck) {
//				holder.ima_check.setBackgroundResource(R.drawable.check2);
//			} else {
//				holder.ima_check.setBackgroundResource(R.drawable.check1);
//			}

			// holder.ima_check.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// if (no_read_check) {
			// holder.ima_check.setBackgroundResource(R.drawable.check1);
			// no_reda_alldatas.get(position).ischeck = false;
			// no_read_check = false;
			//
			// } else {
			// holder.ima_check.setBackgroundResource(R.drawable.check2);
			// no_read_check = true;
			// no_reda_alldatas.get(position).ischeck = true;
			//
			// }
			// }
			// });
			Drawable shiwu= getResources().getDrawable(R.drawable.ic_shiwu);
			Drawable tongzhi= getResources().getDrawable(R.drawable.ic_tongzhi);
			shiwu.setBounds(0, 0, shiwu.getMinimumWidth(), shiwu.getMinimumHeight());
			tongzhi.setBounds(0, 0, tongzhi.getMinimumWidth(), tongzhi.getMinimumHeight());
			if (0==no_reda_alldatas.get(position).getMsgtype()){
				holder.type.setText("事务");
				holder.type.setTextColor(Color.parseColor("#48cfae"));
				holder.type.setCompoundDrawables(null,shiwu,null,null);
			}else {
				holder.type.setText("通知");
				holder.type.setTextColor(Color.parseColor("#2187e7"));
				holder.type.setCompoundDrawables(null,tongzhi,null,null);
			}
			holder.system.setText(system_Name);
			// holder.tv2.setText((CharSequence)
			// no_reda_alldatas.get(position).getFactoryName());
			holder.content.setText(no_reda_alldatas.get(position).getSubject());
			holder.level.setText(no_reda_alldatas.get(position).getMsglevel() + "");
			holder.time.setText(no_reda_alldatas.get(position).getSendTime());
			holder.sender.setText(no_reda_alldatas.get(position).getSender());
			return view;
		}
	}

	private class UpRefresh1 extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		protected void onPostExecute(Void result) {
			lv_no_read.onRefreshComplete();

		}
	}

	private class UpRefresh2 extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		protected void onPostExecute(Void result) {
			lv_yes_read.onRefreshComplete();
		}
	}

	private class UpRefresh3 extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		protected void onPostExecute(Void result) {
			lv_yes_send.onRefreshComplete();
		}
	}

	public class MyYesSendAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return yes_send_alldatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return yes_send_alldatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			NoReadMessageViewHolder holder;
			if (convertView == null) {
//				view = View.inflate(MessageActivity.this, R.layout.item_yes_send_list, null);
//				holder = new NoReadMessageViewHolder();
//
//				holder.system = (TextView) view.findViewById(R.id.tv1);
//				holder.content = (TextView) view.findViewById(R.id.tv3);
//				holder.level = (TextView) view.findViewById(R.id.tv4);
//				holder.tv5 = (TextView) view.findViewById(R.id.tv5);
				view = View.inflate(MessageActivity.this, R.layout.item_no_read_list, null);
				holder = new NoReadMessageViewHolder();
				holder.type = (TextView) view.findViewById(R.id.type);
				holder.time = (TextView) view.findViewById(R.id.time);
				holder.content = (TextView) view.findViewById(R.id.content);
				holder.system = (TextView) view.findViewById(R.id.system);
				holder.sender = (TextView) view.findViewById(R.id.sender);
				holder.level = (TextView) view.findViewById(R.id.level);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (NoReadMessageViewHolder) view.getTag();
			}
			String system_Name = null;

			String yes_No_Messager = null;
			String mesOrigin = yes_send_alldatas.get(position).getMesOrigin();
			if ("0".equals(mesOrigin)) {
				system_Name = "门户系统";
			}
			if ("1".equals(mesOrigin)) {
				system_Name = "辅助诊断系统";
			}
			if ("2".equals(mesOrigin)) {
				system_Name = "核心诊断系统";
			}
			if ("3".equals(mesOrigin)) {
				system_Name = "通流诊断系统";
			}

			if ("0".equals(yes_send_alldatas.get(position).getIsread())) {
				yes_No_Messager = "未读";
			} else {

				yes_No_Messager = "已读";
			}

//			holder.tv1.setText(system_Name);
//			holder.tv3.setText(yes_send_alldatas.get(position).getSubject());
//			holder.tv4.setText(yes_send_alldatas.get(position).getMsglevel() + "");
//			holder.tv5.setText(yes_No_Messager);
			Drawable shiwu= getResources().getDrawable(R.drawable.ic_shiwu);
			Drawable tongzhi= getResources().getDrawable(R.drawable.ic_tongzhi);
			shiwu.setBounds(0, 0, shiwu.getMinimumWidth(), shiwu.getMinimumHeight());
			tongzhi.setBounds(0, 0, tongzhi.getMinimumWidth(), tongzhi.getMinimumHeight());
			if (0==yes_send_alldatas.get(position).getMsgtype()){
				holder.type.setText("事务");
				holder.type.setTextColor(Color.parseColor("#48cfae"));
				holder.type.setCompoundDrawables(null,shiwu,null,null);
			}else {
				holder.type.setText("通知");
				holder.type.setTextColor(Color.parseColor("#2187e7"));
				holder.type.setCompoundDrawables(null,tongzhi,null,null);
			}
			holder.system.setText(system_Name);
			// holder.tv2.setText((CharSequence)
			// no_reda_alldatas.get(position).getFactoryName());
			holder.content.setText(yes_send_alldatas.get(position).getSubject());
			holder.level.setText(yes_send_alldatas.get(position).getMsglevel() + "");
			holder.time.setText(yes_send_alldatas.get(position).getSendTime());
			holder.sender.setText(yes_send_alldatas.get(position).getSender());
			return view;
		}
	}

	public class MyYesReadAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return yes_reda_alldatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return yes_reda_alldatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String system_Name = null;
			View view;
			NoReadMessageViewHolder holder;
			if (convertView == null) {
//				view = View.inflate(MessageActivity.this, R.layout.item_yes_read_list, null);
//				holder = new NoReadMessageViewHolder();
//				holder.tv1 = (TextView) view.findViewById(R.id.tv1);
//				holder.tv3 = (TextView) view.findViewById(R.id.tv3);
//				holder.tv4 = (TextView) view.findViewById(R.id.tv4);
//				holder.tv5 = (TextView) view.findViewById(R.id.tv5);
//				holder.tv6 = (TextView) view.findViewById(R.id.tv6);
				view = View.inflate(MessageActivity.this, R.layout.item_no_read_list, null);
				holder = new NoReadMessageViewHolder();
				holder.type = (TextView) view.findViewById(R.id.type);
				holder.time = (TextView) view.findViewById(R.id.time);
				holder.content = (TextView) view.findViewById(R.id.content);
				holder.system = (TextView) view.findViewById(R.id.system);
				holder.sender = (TextView) view.findViewById(R.id.sender);
				holder.level = (TextView) view.findViewById(R.id.level);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (NoReadMessageViewHolder) view.getTag();
			}
			if (yes_reda_alldatas.get(position).getMesOrigin().equals("0")) {
				system_Name = "门户系统";
			}
			if (yes_reda_alldatas.get(position).getMesOrigin().equals("1")) {
				system_Name = "辅助诊断系统";
			}
			if (yes_reda_alldatas.get(position).getMesOrigin().equals("2")) {
				system_Name = "核心诊断系统";
			}
			if (yes_reda_alldatas.get(position).getMesOrigin().equals("3")) {
				system_Name = "通流诊断系统";
			}
			Drawable shiwu= getResources().getDrawable(R.drawable.ic_shiwu);
			Drawable tongzhi= getResources().getDrawable(R.drawable.ic_tongzhi);
			shiwu.setBounds(0, 0, shiwu.getMinimumWidth(), shiwu.getMinimumHeight());
			tongzhi.setBounds(0, 0, tongzhi.getMinimumWidth(), tongzhi.getMinimumHeight());
			if (0==yes_reda_alldatas.get(position).getMsgtype()){
				holder.type.setText("事务");
				holder.type.setTextColor(Color.parseColor("#48cfae"));
				holder.type.setCompoundDrawables(null,shiwu,null,null);
			}else {
				holder.type.setText("通知");
				holder.type.setTextColor(Color.parseColor("#2187e7"));
				holder.type.setCompoundDrawables(null,tongzhi,null,null);
			}
			holder.system.setText(system_Name);
			// holder.tv2.setText((CharSequence)
			// no_reda_alldatas.get(position).getFactoryName());
			holder.content.setText(yes_reda_alldatas.get(position).getSubject());
			holder.level.setText(yes_reda_alldatas.get(position).getMsglevel() + "");
			holder.time.setText(yes_reda_alldatas.get(position).getSendTime());
			holder.sender.setText(yes_reda_alldatas.get(position).getSender());
			return view;
		}
	}

	private void getNoReadDatas(int pagerSize) {
		System.out.println(no_read_baseurl + pagerSize);
		request = NoHttp.createStringRequest(no_read_baseurl + pagerSize, RequestMethod.GET);
		queue.add(1, request, new SimpleResponseListener<String>() {

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub
				super.onStart(what);
				pb1.setVisibility(View.VISIBLE);
				lv_no_read.setVisibility(View.GONE);
			}

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(MessageActivity.this, "双击消息查看详情");
				String json = response.get();
				System.out.println(json);
				Gson gson = new Gson();
				no_Read_Message_Bean = gson.fromJson(json, No_Read_Message_Bean.class);
				no_reda_datas = no_Read_Message_Bean.getData();
				no_reda_alldatas.addAll(no_reda_datas);

				if (no_read_first_show) {
					myNoReadAdapter = new MyNoReadAdapter();
					lv_no_read.setAdapter(myNoReadAdapter);
					no_read_first_show = false;
				} else {
					myNoReadAdapter.notifyDataSetChanged();

				}
			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub
				super.onFinish(what);
				// lv_no_read.setVisibility(View.VISIBLE);
				pb1.setVisibility(View.GONE);
				lv_no_read.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				String json = "";
				ToastUtils.showToast(MessageActivity.this, "服务器繁忙,请稍后再试试");

			}
		});
	}

	private void getYesReadDatas(int pagerSize) {
		// TODO no_read_baseurl参数没有加

		request = NoHttp.createStringRequest(yes_read_baseurl + pagerSize, RequestMethod.GET);
		queue.add(1, request, new SimpleResponseListener<String>() {
			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub
				super.onStart(what);
				lv_yes_read.setVisibility(View.GONE);
				pb2.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub
				super.onFinish(what);
				lv_yes_read.setVisibility(View.VISIBLE);
				pb2.setVisibility(View.GONE);
			}

			@Override

			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				String json = response.get();
				Gson gson = new Gson();
				yes_Read_Message_Bean = gson.fromJson(json, Yes_Read_Message_Bean.class);
				yes_reda_datas = yes_Read_Message_Bean.getData();
				yes_reda_alldatas.addAll(yes_reda_datas);

				if (yes_read_first_show) {
					myYesReadAdapter = new MyYesReadAdapter();
					lv_yes_read.setAdapter(myYesReadAdapter);
					yes_read_first_show = false;
				} else {
					if (yes_reda_alldatas.size() > yes_reda_datas.size()) {
						myYesReadAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				System.out.println("3");
				String json = "";
				ToastUtils.showToast(MessageActivity.this, "服务器繁忙,请稍后再试试");
			}
		});
	}

	private void getYesSendDatas(int pagerSize) {
		// TODO no_read_baseurl参数没有加
		request = NoHttp.createStringRequest(yes_send_baseurl + pagerSize, RequestMethod.GET);
		queue.add(1, request, new SimpleResponseListener<String>() {

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub
				super.onStart(what);
				System.out.println("已发开始联网");
				lv_yes_send.setVisibility(View.GONE);
				pb3.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub
				super.onFinish(what);
				System.out.println("已发联网结束");
				lv_yes_send.setVisibility(View.VISIBLE);
				pb3.setVisibility(View.GONE);
			}

			@Override
			public void onSucceed(int what, Response<String> response) {

				// TODO Auto-generated method stub
				String json = response.get();
				System.out.println(json);
				Gson gson = new Gson();
				yes_Send_Message_Bean = gson.fromJson(json, Yes_Send_Message_Bean.class);
				yes_send_datas = yes_Send_Message_Bean.getData().getMsgcenterao();
				yes_send_alldatas.addAll(yes_send_datas);

				if (yes_send_first_show) {
					myYesSendAdapter = new MyYesSendAdapter();
					System.out.println("xxxxxxxxxxx");
					lv_yes_send.setAdapter(myYesSendAdapter);
					yes_send_first_show = false;
				} else {
					myYesSendAdapter.notifyDataSetChanged();
					System.out.println("lllllllllllllllllllllllllllllll'");

				}

			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				String json = "";
				ToastUtils.showToast(MessageActivity.this, "服务器繁忙,请稍后再试试");
			}
		});
	}

	class NoReadMessageViewHolder {
		public TextView type;
		public TextView time;
		public TextView content;
		public TextView system;
		public TextView sender;
		public TextView level;
	}

	void read(String MsgId) {

		RequestQueue requestQueue = NoHttp.newRequestQueue();

		String url = MyRes.BASE_URL + "message.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
				+ "&msgState_=02&msgId_=" + MsgId;
		Request<String> createStringRequest = NoHttp.createStringRequest(url);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub

				ToastUtils.showToast(MessageActivity.this, "标记成功");
				no_reda_alldatas.clear();
				getNoReadDatas(1);
				no_Read_Pager = 1;
				myNoReadAdapter.notifyDataSetChanged();
				System.out.println(response.get() + "");
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
				ToastUtils.showToast(MessageActivity.this, "网络太差,请检查网络");
			}
		};
		requestQueue.add(123123, createStringRequest, responseListener);

	};

}
