package zz.zept.yczd.message.acrtivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.res.MyRes;

public class MessageContentActivity extends Activity {

	TextView tv1, tv4, tv5, tv6, tv7, tv8;
	String msgType, msgSys, tag;
	Button btn_backnews, bnt_check;
	LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_content);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);

		tag = getIntent().getStringExtra("floworder");
		if (tag == null) {
			tag = "10";
		}
		ll = (LinearLayout) findViewById(R.id.ll);
		// ToastUtils.showToast(this, tag);
		btn_backnews = (Button) findViewById(R.id.btn_backnews);

		bnt_check = (Button) findViewById(R.id.bnt_check);
		if (tag.equals("0")) {
			ll.setVisibility(View.GONE);
		} else if (tag == "10") {
			ll.setVisibility(View.GONE);
		} else {
			ll.setVisibility(View.VISIBLE);
		}
		bnt_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ToastUtils.showToast(MessageContentActivity.this,
				// "bnt_check");
				Intent intent = new Intent(MessageContentActivity.this, ZDBGActivity.class);
				intent.putExtra("url", "http://192.168.66.31:81/api/Dept/getDiagInstantInfo?driID="
						+ getIntent().getStringExtra("driID"));
				intent.putExtra("driID", getIntent().getStringExtra("driID"));
				startActivity(intent);
				finish();
			}
		});
		btn_backnews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MessageContentActivity.this, MessageActivity.class);

				startActivity(intent);
				finish();

			}
		});

		String extra1 = getIntent().getStringExtra(zz.zept.yczd.res.MyRes.MESORIGIN);
		if ((0 + "").equals(extra1)) {
			msgSys = "门户系统";
		} else if ((1 + "").equals(extra1)) {
			msgSys = "辅助诊断系统";
		} else if ((2 + "").equals(extra1)) {
			msgSys = "核心诊断系统";
		} else if ((3 + "").equals(extra1)) {
			msgSys = "通流诊断系统";
		}

		String extra2 = getIntent().getStringExtra(MyRes.MSGTYPE);

		if ((0 + "").equals(extra2)) {
			msgType = "事务类";
		} else if ((1 + "").equals(extra2)) {
			msgType = "通知类";
		}
		tv1.setText(msgSys);
		tv4.setText(getIntent().getStringExtra(MyRes.SUBJECT));
		tv5.setText(msgType);
		tv6.setText(getIntent().getStringExtra(MyRes.MSGLEVEL));
		tv7.setText(getIntent().getStringExtra(MyRes.SENDER));
		tv8.setText(getIntent().getStringExtra(MyRes.SENDTIME));

	}

}
