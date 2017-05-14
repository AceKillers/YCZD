package zz.zept.yczd.message.acrtivity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import zz.zept.yczd.bean.OtherReciverBean;
import zz.zept.yczd.bean.OtherReciverBean.DataBean.SysdeptBean;
import zz.zept.yczd.bean.OtherReciverBean.DataBean.SysuserBean;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.ToastUtils;

public class MessageReciverActivity3 extends BaseActicity implements OnItemClickListener {
	Button btn_back_send_message_activity;
	private String deptid;
	// 两个listview的数据源
	private List<SysdeptBean> content;
	private List<SysuserBean> people;
	ListView lv_message_content, lv_message_people;
	String baseurl;
	TextView tv_content, tv_people;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_send_message_activity:
			Intent intent1 = new Intent(this, MessageReciverActivity2.class);
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
		setContentView(R.layout.activity_reciver_level3);
		deptid = getIntent().getStringExtra(MyRes.PARENTID);
		// sp.edit().putString(MyRes.MESSAGE_TAG, deptid);
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
		tv_people = (TextView) findViewById(R.id.tv_people);
		tv_content = (TextView) findViewById(R.id.tv_content);
		// TODO Auto-generated method stub
		btn_back_send_message_activity = (Button) findViewById(R.id.btn_back_send_message_activity);
		lv_message_content = (ListView) findViewById(R.id.lv_message_content);
		lv_message_people = (ListView) findViewById(R.id.lv_message_people);
	}

	@Override
	void initData() {
		baseurl = MyRes.MY_URL + "username_=" + sp.getString(MyRes.LOGIN_NAME, "") + "&msgState_=25&deptid_=";
		String url;
		String old_deptid = null;
		if (deptid == null) {
			old_deptid = sp.getString(MyRes.MESSAGE_TAG2, null);
			url = baseurl + old_deptid;
		} else {
			url = baseurl + deptid;
		}
		RequestQueue queue = NoHttp.newRequestQueue();
		Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
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
			}

			@Override
			public void onSucceed(int what, Response<String> response) {
				// TODO Auto-generated method stub
				super.onSucceed(what, response);
				String json = response.get();
				Gson gson = new Gson();

				OtherReciverBean otherReciverBean = gson.fromJson(json, OtherReciverBean.class);

				content = otherReciverBean.getData().getSysdept();
				people = otherReciverBean.getData().getSysuser();

				if (content.size() == 0) {
					tv_content.setVisibility(View.GONE);
					lv_message_content.setVisibility(View.GONE);
				} else {
					tv_content.setVisibility(View.VISIBLE);
					lv_message_content.setVisibility(View.VISIBLE);
				}
				if (people.size() == 0) {
					tv_people.setVisibility(View.GONE);
					lv_message_people.setVisibility(View.GONE);
				} else {
					tv_people.setVisibility(View.VISIBLE);
					lv_message_people.setVisibility(View.VISIBLE);
				}

				if (people.size() == 0 && content.size() == 0) {
					ToastUtils.showToast(MessageReciverActivity3.this, "此页暂无数据,请返回上一页");
				}
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
				view = View.inflate(MessageReciverActivity3.this, R.layout.item_textview, null);
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
				view = View.inflate(MessageReciverActivity3.this, R.layout.item_textview, null);
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
			String parentId = (String) content.get(position).getId();
			sp.edit().putString(MyRes.MESSAGE_TAG3, parentId).commit();
			Intent intent1 = new Intent(this, MessageReciverActivity4.class);
			intent1.putExtra(MyRes.PARENTID, parentId);
			startActivity(intent1);
			finish();
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
