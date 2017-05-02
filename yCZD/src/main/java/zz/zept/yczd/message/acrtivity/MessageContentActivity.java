package zz.zept.yczd.message.acrtivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zz.zept.yczd.R;
import zz.zept.yczd.res.MyRes;

public class MessageContentActivity extends Activity {

	TextView type, tittle, time, content, system, sender,level;
	String msgType, msgSys, tag;
	Button bnt_check;
	ImageView btn_backnews;
	LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_content);
		type = (TextView) findViewById(R.id.type);
		tittle = (TextView) findViewById(R.id.tittle);
		time = (TextView) findViewById(R.id.time);
		content = (TextView) findViewById(R.id.content);
		system = (TextView) findViewById(R.id.system);
		sender = (TextView) findViewById(R.id.sender);
		level = (TextView) findViewById(R.id.level);

		tag = getIntent().getStringExtra("floworder");
		if (tag == null) {
			tag = "10";
		}
//		ll = (LinearLayout) findViewById(R.id.ll);
//		btn_backnews = (Button) findViewById(R.id.btn_backnews);
//
//		bnt_check = (Button) findViewById(R.id.bnt_check);
//		if (tag.equals("0")) {
//			ll.setVisibility(View.GONE);
//		} else if (tag == "10") {
//			ll.setVisibility(View.GONE);
//		} else {
//			ll.setVisibility(View.VISIBLE);
//		}
//		bnt_check.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				// ToastUtils.showToast(MessageContentActivity.this,
//				// "bnt_check");
//				Intent intent = new Intent(MessageContentActivity.this, ZDBGActivity.class);
//				intent.putExtra("url", "http://192.168.66.31:81/api/Dept/getDiagInstantInfo?driID="
//						+ getIntent().getStringExtra("driID"));
//				intent.putExtra("driID", getIntent().getStringExtra("driID"));
//				startActivity(intent);
//				finish();
//			}
//		});
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
		system.setText(msgSys);
		tittle.setText(getIntent().getStringExtra(MyRes.SUBJECT));
		Drawable shiwu= getResources().getDrawable(R.drawable.ic_shiwu);
		Drawable tongzhi= getResources().getDrawable(R.drawable.ic_tongzhi);
		shiwu.setBounds(0, 0, shiwu.getMinimumWidth(), shiwu.getMinimumHeight());
		tongzhi.setBounds(0, 0, tongzhi.getMinimumWidth(), tongzhi.getMinimumHeight());
		if ((0 + "").equals(extra2)){
			type.setText("事务");
			type.setTextColor(Color.parseColor("#48cfae"));
			type.setCompoundDrawables(null,shiwu,null,null);
		}else {
			type.setText("通知");
			type.setTextColor(Color.parseColor("#2187e7"));
			type.setCompoundDrawables(null,tongzhi,null,null);
		}
		level.setText(getIntent().getStringExtra(MyRes.MSGLEVEL));
		sender.setText(getIntent().getStringExtra(MyRes.SENDER));
		time.setText(getIntent().getStringExtra(MyRes.SENDTIME));
		content.setText(getIntent().getStringExtra(MyRes.CONTENT));
	}

}
