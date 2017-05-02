package zz.zept.yczd.message.acrtivity;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import zz.zept.yczd.R;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;

public class SendMessageActivity extends BaseActicity {
	// 联网请求队列
	RequestQueue queue;
	// 请求对象
	Request<String> request;
	// 发送信息的base_url
	String send_baseurl = MyRes.MY_SEND_URL;
	Button bt_newsactivity, bt_send_message, bt_cancel_message;
	EditText et_title, et_people, et_content;
	String message_title, message_people, message_content;
	private String username;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_newsactivity:
			Intent intent1 = new Intent(this, MessageActivity.class);
			startActivity(intent1);
			break;
		case R.id.bt_send_message:
			ToastUtils.showToast(this, "发送中，请等待");
			message_title = et_title.getText().toString().trim();
			message_people = et_people.getText().toString().trim();
			message_content = et_content.getText().toString().trim();

			if (TextUtils.isEmpty(message_content) || TextUtils.isEmpty(message_people)
					|| TextUtils.isEmpty(message_title)) {
				ToastUtils.showToast(SendMessageActivity.this, "请仔细检查,标题,收件人,内容选项不能为空");
			} else {
				// 联网 把那五个参数给服务器

				queue = NoHttp.newRequestQueue();
				request = NoHttp.createStringRequest(send_baseurl, RequestMethod.POST);
				request.add("title_", message_title);
				request.add("msgState_", 20 + "");
				// 发送人
				request.add("username_", sp.getString(MyRes.LOGIN_NAME, ""));
				request.add("receiver_", message_people);
				request.add("context_", message_content);
				request.setTag(this);
				queue.add(88, request, new SimpleResponseListener<String>() {
					@Override
					public void onFailed(int what, Response<String> response) {
						super.onFailed(what, response);
						ToastUtils.showToast(SendMessageActivity.this, "联网失败，请检查网络");
					}

					@Override
					public void onSucceed(int what, Response<String> response) {
						super.onSucceed(what, response);
						ToastUtils.showToast(SendMessageActivity.this, "发送成功");
						Intent intent = new Intent(SendMessageActivity.this, MessageActivity.class);
						startActivity(intent);
						finish();
					}

				});

			}

			break;
		case R.id.bt_cancel_message:
			Intent intent3 = new Intent(this, MessageActivity.class);
			startActivity(intent3);
			finish();
			break;
		case R.id.et_people:
			Intent intent4 = new Intent(this, MessageReciverActivity1.class);
			startActivity(intent4);
			message_title = et_title.getText().toString().trim();
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		message_content = et_content.getText().toString().trim();
		sp.edit().putString(MyRes.MESSAGE_TITLE, message_title).commit();
		sp.edit().putString(MyRes.MESSAGE_CONTENT, message_content).commit();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	void init() {
		setContentView(R.layout.activity_send_message);
		username = getIntent().getStringExtra(MyRes.USERNAME);
		StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
	}

	@Override
	void initListener() {
		bt_newsactivity.setOnClickListener(this);
		bt_send_message.setOnClickListener(this);

		bt_cancel_message.setOnClickListener(this);
		et_people.setOnClickListener(this);
	}

	@Override
	void initView() {
		bt_newsactivity = (Button) findViewById(R.id.bt_newsactivity);
		bt_send_message = (Button) findViewById(R.id.bt_send_message);
		bt_cancel_message = (Button) findViewById(R.id.bt_cancel_message);
		et_title = (EditText) findViewById(R.id.et_title);
		et_people = (EditText) findViewById(R.id.et_people);
		et_content = (EditText) findViewById(R.id.et_content);
	}

	@Override
	void initData() {
		et_people.setText(username);
		et_title.setText(sp.getString(MyRes.MESSAGE_TITLE, ""));
		et_content.setText(sp.getString(MyRes.MESSAGE_CONTENT, ""));
	}
}
