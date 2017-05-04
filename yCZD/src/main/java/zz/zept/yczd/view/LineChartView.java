package zz.zept.yczd.view;

/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName LineChart01View
 * @Description 折线图的例子 <br/>
 *              * ~_~
 * @author XiongChuanLiang<br/>
 *         (xcl_168@aliyun.com)
 */
public class LineChartView extends DemoView implements Runnable {

	private String TAG = "LineChart02View";
	private LineChart chart = new LineChart();

	// 标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<LineData> chartData = new LinkedList<LineData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);

	private double max;
	private List<String> label;
	private List<Double> data;
	private String name;

	public LineChartView(Context context, double max, List<String> label, ArrayList<Double> data, String name) {
		super(context);
		// TODO Auto-generated constructor stub
		this.max = max;
		this.label = label;
		this.data = data;
		this.name = name;
		initView();
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		chartLabels();
		chartDataSet();
		chartDesireLines();
		chartRender();
		new Thread(this).start();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	private void chartRender() {
		try {

			// 设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
			int[] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1],
					ltrb[2], ltrb[3]);

			// 设定数据源
			chart.setCategories(labels);
			// chart.setDataSource(chartData);
//			chart.setDesireLines(mCustomLineDataset);

			//数据轴最大值
			chart.getDataAxis().setAxisMax(max);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(max/10);
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(1);

			// 背景网格
			chart.getPlotGrid().getHorizontalLinePaint().setStrokeWidth(0.1f);
			chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.parseColor("#edeff5"));
			chart.getPlotGrid().getVerticalLinePaint().setColor(Color.parseColor("#edeff5"));
			chart.getPlotGrid().showHorizontalLines();

			// 标题
			// chart.setTitle("个人历年租房情况一览");
			// chart.addSubtitle("(XCL-Charts Demo)");

			// 隐藏顶轴和右边轴
			// chart.hideTopAxis();
			// chart.hideRightAxis();

			// 设置轴风格

			// chart.getDataAxis().setTickMarksVisible(false);
			chart.getDataAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getDataAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));

			chart.getDataAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getDataAxis().getAxisPaint().setStrokeWidth(2);
			chart.getDataAxis().getTickMarksPaint().setStrokeWidth(2);
			chart.getDataAxis().showAxisLabels();


			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(2);
			chart.getCategoryAxis().hideTickMarks();

			// 激活点击监听
			chart.ActiveListenItemClick();
			// 为了让触发更灵敏，可以扩大5px的点击监听范围
			chart.extPointClickRange(5);
			chart.showClikedFocus();

			// 定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub
					Double tmp = Double.parseDouble(value);
					DecimalFormat df = new DecimalFormat("#0");
					String label = df.format(tmp).toString();
					return (label);
				}

			});

			// 定义线上交叉点标签显示格式
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df = new DecimalFormat("#0");
					String label = df.format(value).toString();
					return label;
				}
			});

			// chart.setItemLabelFormatter(callBack)

			// 允许线与轴交叉时，线会断开
			chart.setLineAxisIntersectVisible(false);

			// chart.setDataSource(chartData);
			// 动态线
			chart.showDyLine();
			int axisColor = Color.rgb(184,184,184); // Color.rgb(222, 136, 166);
			chart.getDataAxis().getAxisPaint().setColor(axisColor);
			chart.getCategoryAxis().getAxisPaint().setColor(axisColor);			
			chart.getDataAxis().getTickMarksPaint().setColor(axisColor);
			chart.getCategoryAxis().getTickMarksPaint().setColor(axisColor);
			// 不封闭
			chart.setAxesClosed(false);
			chart.disableScale();

			// 扩展绘图区右边分割的范围，让定制线的说明文字能显示出来
			chart.getClipExt().setExtRight(150.f);
			chart.disablePanMode();
			// chart.getDataAxis().hide();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	// 计算下平均线
	private double calcAvg() {
		double total = 400d + 480d + 500d + 560d + 800d + 950d + 1200d + 630d
				+ 710d;
		double yearNumber = 9d;

		return (total / yearNumber);
	}

	private void chartDataSet() {
		//Line 1
		LinkedList<Double> dataSeries1= new LinkedList<Double>();
		for (int i = 0;i<data.size();i++){
			dataSeries1.add(data.get(i));
		}
//		dataSeries1.add(200d);
//		dataSeries1.add(480d);
//		dataSeries1.add(500d);
//		dataSeries1.add(560d);
//		dataSeries1.add(760d);
//		dataSeries1.add(380d);
//		dataSeries1.add(660d);
//		dataSeries1.add(570d);
//		dataSeries1.add(920d);
		LineData lineData1 = new LineData(name,dataSeries1, Color.parseColor("#00baff"));
		lineData1.setDotStyle(XEnum.DotStyle.DOT);

		//Line 2
		LinkedList<Double> dataSeries2= new LinkedList<Double>();
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add((double)800);
		dataSeries2.add((double)950);
		dataSeries2.add((double)1200);

		LineData lineData2 = new LineData("一房一厅(3层无光线)",dataSeries2, Color.rgb(75, 166, 51));
		lineData2.setDotStyle(XEnum.DotStyle.PRISMATIC);
		lineData2.getPlotLine().getDotPaint().setColor(Color.rgb(234, 142, 43));
		lineData2.getDotLabelPaint().setColor(Color.rgb(234, 142, 43));
		lineData2.setLabelVisible(true);
		lineData2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(76, 76, 76));
		//lineData2.getPlotLabel().hideBox();

		//Line 3
		LinkedList<Double> dataSeries3= new LinkedList<Double>();
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(630d);
		dataSeries3.add(710d);

		LineData lineData3 = new LineData("单间(9层无电梯)",dataSeries3, Color.rgb(123, 89, 168));
		lineData3.setDotStyle(XEnum.DotStyle.DOT);

		//轴上分界线的交叉点
		LinkedList<Double> dataSeries4= new LinkedList<Double>();
		dataSeries4.add(1500d);
		LinkedList<Double> dataSeries5= new LinkedList<Double>();
		dataSeries5.add(3000d);
		LinkedList<Double> dataSeries6= new LinkedList<Double>();
		dataSeries6.add(calcAvg());

		LineData lineData4 = new LineData("",dataSeries4, Color.rgb(35, 172, 57));
		lineData4.setDotStyle(XEnum.DotStyle.RECT);
		LineData lineData5 = new LineData("",dataSeries5, Color.rgb(69, 181, 248));
		lineData5.setDotStyle(XEnum.DotStyle.RECT);
		LineData lineData6 = new LineData("",dataSeries6, Color.rgb(251, 79, 128));
		lineData6.setDotStyle(XEnum.DotStyle.TRIANGLE);

		chartData.add(lineData1);
//		chartData.add(lineData2);
//		chartData.add(lineData3);


		// lineData2.getPlotLine().getDotPaint().setColor((int)Color.rgb(234,
		// 142, 43));
		// lineData2.getDotLabelPaint().setColor((int)Color.rgb(234, 142, 43));
		// lineData2.setLabelVisible(true);
		// lineData2.getPlotLabel().hideBox();


		
	}

	private void chartLabels() {
		for (int i = 0;i<label.size();i++){
			labels.add(label.get(i));
		}
//		labels.add("2006");
//		labels.add("2007");
//		labels.add("2008");
//		labels.add("2009");
//		labels.add("2010");
//		labels.add("2011");
//		labels.add("2012");
//		labels.add("2013");
//		labels.add("2014");
	}

	/**
	 * 期望线/分界线
	 */
	private void chartDesireLines() {
		 mCustomLineDataset.add(new
				 CustomLineData("0轴线",0d,(int) Color.rgb(35, 172, 57),5));
		// mCustomLineDataset.add(new
		// CustomLineData("舒适",3000d,(int)Color.rgb(69, 181, 248),5));
		// mCustomLineDataset.add(new
		// CustomLineData("[个人均线]",calcAvg(),(int)Color.rgb(251, 79, 128),6));
	}

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			chartAnimation();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void chartAnimation() {
		try {
			int count = chartData.size();
			for (int i = 0; i < count; i++) {
				Thread.sleep(150);
				LinkedList<LineData> animationData = new LinkedList<LineData>();
				for (int j = 0; j <= i; j++) {
					animationData.add(chartData.get(j));
				}

				// Log.e(TAG,"size = "+animationData.size());
				chart.setDataSource(animationData);
				if (i == count - 1) {
					chart.getDataAxis().show();
					chart.getDataAxis().showAxisLabels();

				}
				postInvalidate();
			}
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (event.getAction() == MotionEvent.ACTION_UP) {
			triggerClick(event.getX(), event.getY());
		}
		super.onTouchEvent(event);
		return true;
	}

	// 触发监听
	private void triggerClick(float x, float y) {

		// 交叉线
		if (chart.getDyLineVisible())
			chart.getDyLine().setCurrentXY(x, y);
		if (!chart.getListenItemClickStatus()) {
			// 交叉线
			if (chart.getDyLineVisible())
				this.invalidate();
		} else {
			PointPosition record = chart.getPositionRecord(x, y);
			if (null == record) {
				if (chart.getDyLineVisible())
					this.invalidate();
				return;
			}

			LineData lData = chartData.get(record.getDataID());
			Double lValue = lData.getLinePoint().get(record.getDataChildID());

			float r = record.getRadius();
			chart.showFocusPointF(record.getPosition(), r + r * 0.5f);
			chart.getFocusPaint().setStyle(Style.STROKE);
			chart.getFocusPaint().setStrokeWidth(3);
			if (record.getDataID() >= 3) {
				chart.getFocusPaint().setColor(Color.BLUE);
			} else {
				chart.getFocusPaint().setColor(Color.RED);
			}

			// 在点击处显示tooltip
			mPaintTooltips.setColor(Color.RED);
			mPaintTooltips.setTextSize(22);
			chart.getToolTip().setCurrentXY(x, y);
			chart.getToolTip().addToolTip(lData.getLineKey(), mPaintTooltips);
			// chart.getToolTip().addToolTip(" Label:"+lData.getLabel(),mPaintTooltips);
			chart.getToolTip().addToolTip(lValue+"",mPaintTooltips);

			this.invalidate();
		}

	}

}
