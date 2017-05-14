package zz.zept.yczd.message.acrtivity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.util.List;

import zz.zept.yczd.R;
import zz.zept.yczd.bean.TopReciverBean;
import zz.zept.yczd.res.MyRes;

public class MessageReciverActivity1 extends BaseActicity implements OnItemClickListener {

	// 当前界面的层级的数据和工作人员的数据的集合
	private List<zz.zept.yczd.bean.TopReciverBean.DataBean.SysdeptBean> content;
	private List<zz.zept.yczd.bean.TopReciverBean.DataBean.SysuserBean> people;
	ListView lv_message_content, lv_message_people;
	ImageView btn_back_send_message_activity;
	TextView tv_content, tv_people;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_send_message_activity:
			Intent intent1 = new Intent(this, SendMessageActivity.class);
			startActivity(intent1);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_reciver_level1);
	}

	@Override
	void initListener() {
		// TODO Auto-generated method stub
		btn_back_send_message_activity.setOnClickListener(this);
		lv_message_content.setOnItemClickListener(this);
		lv_message_people.setOnItemClickListener(this);

	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btn_back_send_message_activity = (ImageView) findViewById(R.id.btn_back_send_message_activity);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_people = (TextView) findViewById(R.id.tv_people);
		lv_message_content = (ListView) findViewById(R.id.lv_message_content);
		lv_message_people = (ListView) findViewById(R.id.lv_message_people);
	}

	@Override
	void initData() {
		RequestQueue queue = NoHttp.newRequestQueue();
		Request<String> request = NoHttp.createStringRequest(MyRes.MY_URL + "username_=spic&msgState_=24&pid_=null",
				RequestMethod.GET);

		queue.add(111, request, new SimpleResponseListener<String>() {

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub
				super.onStart(what);
			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub
				super.onFinish(what);
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				super.onFailed(what, response);
				System.out.println("收件人一级界面联网失败");

				lv_message_content.setVisibility(View.GONE);
				lv_message_people.setVisibility(View.GONE);
			}

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				super.onSucceed(what, response);
				String json = response.get();
				System.out.println(json);
				Gson gson = new Gson();

				TopReciverBean topReciverBean = gson.fromJson(json, TopReciverBean.class);
				content = topReciverBean.getData().getSysdept();
				people = topReciverBean.getData().getSysuser();
				lv_message_content.setAdapter(new MyContentAdapter());
				lv_message_people.setAdapter(new MyPeopleAdapter());
			}

		});

	}

	class MyPeopleAdapter extends BaseAdapter {
		public int getCount() {
			// TODO Auto-generated method stub
			return people.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub

			return people.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			TextView tv_People = null;
			if (convertView == null) {
				view = View.inflate(MessageReciverActivity1.this, R.layout.item_textview, null);
			} else {
				view = convertView;
			}
			tv_People = (TextView) view.findViewById(R.id.tv);
			tv_People.setText(people.get(position).getUsername());
			return view;
		}
	}

	class MyContentAdapter extends BaseAdapter {
		public int getCount() {
			// TODO Auto-generated method stub
			return content.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return content.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			TextView tv_Content = null;
			if (convertView == null) {
				view = View.inflate(MessageReciverActivity1.this, R.layout.item_textview, null);
			} else {
				view = convertView;
			}
			tv_Content = (TextView) view.findViewById(R.id.tv);
			tv_Content.setText(content.get(position).getName());
			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.lv_message_content:
			// ToastUtils.showToast(this, "lv_message_content");
//			String parentId = (String) content.get(position).getId();
//			sp.edit().putString(MyRes.MESSAGE_TAG1, parentId).commit();
//			// System.out.println(sp.getString(MyRes.MESSAGE_TAG1, "xxxxx"));
//			Intent intent1 = new Intent(this, MessageReciverActivity2.class);
//			intent1.putExtra(MyRes.PARENTID, parentId);
//			startActivity(intent1);
//			finish();
			break;
		case R.id.lv_message_people:

			String username = people.get(position).getUsername();
			Intent intent2 = new Intent(this, SendMessageActivity.class);
			intent2.putExtra(MyRes.USERNAME, username);
			startActivity(intent2);
			finish();
			break;
		default:
			break;
		}
	}
}
