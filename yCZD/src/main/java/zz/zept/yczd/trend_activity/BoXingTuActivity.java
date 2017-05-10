package zz.zept.yczd.trend_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import zz.zept.yczd.R;
import zz.zept.yczd.bean.TrendBoXingTuBean.DataBean;
import zz.zept.yczd.utils.ToastUtils;

public class BoXingTuActivity extends BaseActicity {
	String url;
	private List<String> xvalues1;
	private List<String> xvalues2;
	private List<Float> yvalues1;
	private List<Float> yvalues2;
	ArrayList<LineDataSet> lineDataSets;
	ProgressBar pb;
	private LineDataSet lineDataSet1;
	private LineData lineData1;
	private LineDataSet lineDataSet2;
	private LineData lineData2;
	TextView tv_xy;
	LineChart chartView;
	private ArrayList<Entry> y1;
	ImageView btn_back;
	private ArrayList<Entry> y2;
	private Intent intent;
	private List<DataBean> data;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_boxingtu2);

	}

	@Override
	void initListener() {
		btn_back.setOnClickListener(this);
		// TODO Auto-generated method stub
		chartView.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				// TODO Auto-generated method stub

				try {
					tv_xy.setText("当前坐标:" + "x轴坐标" + "" + "(" + xvalues1.get(e.getXIndex()) + ":" + "y轴坐标" + e.getVal()
							+ ")");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					tv_xy.setText("当前坐标:" + "x轴坐标" + "" + "(" + xvalues2.get(e.getXIndex()) + ":" + "y轴坐标" + e.getVal()
							+ ")");
				}
			}

			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		chartView = (LineChart) findViewById(R.id.chartView);
		pb = (ProgressBar) findViewById(R.id.pb);
		tv_xy = (TextView) findViewById(R.id.tv_xy);
		btn_back = (ImageView) findViewById(R.id.btn_back);
	}

	@Override
	void initData() {
		lineDataSets = new ArrayList<LineDataSet>();
		chartView.setDescription("");
		y1 = new ArrayList<Entry>();
		y2 = new ArrayList<Entry>();
		// TODO Auto-generated method stub
		url = getIntent().getStringExtra("url");
		System.out.println(url);
		OnResponseListener<String> responseListener = new OnResponseListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				try {
					String json = response.get();
					Gson gson = new Gson();
					data = gson.fromJson(json, zz.zept.yczd.bean.TrendBoXingTuBean.class).getData();
					if (data.size() > 0) {

						xvalues1 = data.get(0).getXvalues();
						xvalues2 = data.get(1).getXvalues();
						yvalues1 = data.get(0).getYvalues();
						yvalues2 = data.get(1).getYvalues();

						new MyAsyncTask().execute();
					} else {
						ToastUtils.showToast(BoXingTuActivity.this, "服务器数据为空");
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub
				chartView.setVisibility(View.INVISIBLE);
				pb.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub
				chartView.setVisibility(View.VISIBLE);
				pb.setVisibility(View.GONE);
			}

			@Override
			public void onFailed(int what, Response<String> response) {
				// TODO Auto-generated method stub

			}
		};
		NoHttp.newRequestQueue().add(123123, NoHttp.createStringRequest(url), responseListener);

	}

	private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {

			
				for (int i = 0; i < xvalues1.size(); i++) {

					y1.add(new Entry(yvalues1.get(i), i));

				}
				for (int i = 0; i < xvalues2.size(); i++) {

					y2.add(new Entry(yvalues2.get(i), i));

				}

			

			return null;
		}

		protected void onPostExecute(Void result) {

			lineDataSet1 = new LineDataSet(y1, data.get(0).getName().getName());
			lineDataSet1.setColor(Color.BLUE);
			lineDataSet1.setDrawCircles(false);

			lineDataSet2 = new LineDataSet(y2, data.get(1).getName().getName());
			lineDataSet2.setColor(Color.GREEN);
			lineDataSet2.setDrawCircles(false);
			lineDataSets.add(lineDataSet1);
			lineDataSets.add(lineDataSet2);
			if (xvalues2.size() > xvalues1.size()) {
				lineData1 = new LineData(xvalues2, lineDataSets);
			} else {
				lineData1 = new LineData(xvalues1, lineDataSets);
			}

			chartView.setData(lineData1);
			chartView.invalidate();

		}
	}

}
