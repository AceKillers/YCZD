package zz.zept.yczd.warn_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import zz.zept.yczd.R;

public class WarnContentActivity extends Activity {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
	Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warn_message_content);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(WarnContentActivity.this, WarnOneActivity.class);
//				startActivity(intent);
				finish();
			}
		});
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		// tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);

		tv1.setText(getIntent().getStringExtra("1"));
		tv2.setText(getIntent().getStringExtra("2"));
		tv3.setText(getIntent().getStringExtra("3"));
		// tv4.setText(getIntent().getStringExtra("4"));
		tv5.setText(getIntent().getStringExtra("5"));
		tv6.setText(getIntent().getStringExtra("6"));
		tv7.setText(getIntent().getStringExtra("7"));
		tv8.setText(getIntent().getStringExtra("8"));
	}
}
