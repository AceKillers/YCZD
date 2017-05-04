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
package zz.zept.yczd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName AnimationBar01View
 * @Description  柱形图柱形渐显动画例子,<br/>
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */


public class BarChart06View extends DemoView implements Runnable {
	
	private String TAG = "AnimationBar01View";
	private BarChart chart = new BarChart();
	
	//标签轴
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	
	//RGB颜色数组
	private int[] colors ={Color.parseColor("#00baff"), Color.parseColor("#438483"), Color.parseColor("#31e5e3")};
   	private final int labelsNumber = 15;

	private double max;
	private List<String> label;
	private List<Double> data1;
	private List<Double> data2;
	private String name;
	
	public BarChart06View(Context context, double max, List<String> label, ArrayList<Double> data1,ArrayList<Double> data2, String name) {
		super(context);
		// TODO Auto-generated constructor stub
		this.max = max;
		this.label = label;
		this.data1 = data1;
		this.data2 = data2;
		this.name = name;
		initView();
	}
	
	public BarChart06View(Context context, AttributeSet attrs){
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart06View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			chartRender();
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
			
			new Thread(this).start();
	 }
	 
	 
	 @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	    
	       //图所占范围大小
	        chart.setChartRange(w,h);
	    }  
	
	private void chartRender()
	{
		try {			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//显示边框
//			chart.showRoundBorder();
			
			chart.setDataSource(chartData);
			chart.getPlotGrid().getHorizontalLinePaint().setStrokeWidth(0.1f);
			chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.parseColor("#edeff5"));
			chart.getPlotGrid().getVerticalLinePaint().setColor(Color.parseColor("#edeff5"));
			chart.getPlotGrid().showHorizontalLines();
			//数据源
			chart.setCategories(chartLabels);	
			
			//数据轴
			chart.getDataAxis().setAxisMax(max);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(max/10);
			chart.getDataAxis().setDetailModeSteps(1);
			chart.getDataAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getDataAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));

			chart.getDataAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
			chart.getDataAxis().getAxisPaint().setStrokeWidth(2);
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(2);
			chart.getDataAxis().getTickMarksPaint().setStrokeWidth(2);
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();
					return (label);
				}				
			});			
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			//设定格式
//			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
//				@Override
//				public String doubleFormatter(Double value) {
//					// TODO Auto-generated method stub
//					DecimalFormat df=new DecimalFormat("#0");
//					String label = df.format(value).toString();
//					return label;
//				}});
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
			 //让柱子间不留空白
			 chart.getBar().setBarInnerMargin(5f);
			
			//隐藏Key
//			 chart.getPlotLegend().hide();
			 chart.disableHighPrecision();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartDataSet()
	{
		LinkedList<Double> dataSeries1= new LinkedList<Double>();
		for(int i=0;i<data1.size();i++){
			dataSeries1.add(data1.get(i));
		}
		BarData barData1 = new BarData("进煤量",dataSeries1,colors[0]);
		chartData.add(barData1);
		LinkedList<Double> dataSeries2= new LinkedList<Double>();
		for(int i=0;i<data2.size();i++){
			dataSeries2.add(data2.get(i));
		}
		BarData barData2 = new BarData("耗煤量",dataSeries2,colors[1]);
		chartData.add(barData2);

		//标签对应的柱形数据集		
		/*int max = 96;
	    int min = 15;
	    
		for(int i=0;i <colors.length;i++)
		{			
			List<Double> dataSeries= new LinkedList<Double>();
			for(int j=0;j<labelsNumber;j++)
			{
				Random random = new Random();
				int num = random.nextInt(max)%(max-min+1) + min;					
				dataSeries.add((double) num);				
			}
			BarData barData = new BarData("柱形渐显动画",dataSeries,
					colors[i]);
			chartData.add(barData);
		}*/
		
	}
	
	private void chartLabels()
	{
		for(int i=0;i < label.size();i++)
			chartLabels.add(label.get(i));
	}	
		
	@Override
    public void render(Canvas canvas) {
        try{        	        	
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub		
       // while如打开，会循环显示动画
	   // while(!Thread.currentThread().isInterrupted()) {				
            try {          
            	chartAnimation();
            }
            catch(Exception e) {
                Thread.currentThread().interrupt();
            }                    
       // } //end while
	}
	
	
	
	private void chartAnimation()
	{
		  try {                            	           		
			chart.getDataAxis().hide();
			
						          
          	int [] ltrb = getBarLnDefaultSpadding();          	
          	for(int i=8; i> 0 ;i--)
          	{
          		Thread.sleep(100);
          		chart.setPadding(ltrb[0],i *  ltrb[1], ltrb[2], ltrb[3]);	    	
          		
          		if(1 == i)
          		{          			
          			drawTitle();
          		}
          		postInvalidate();    
          	} 
          	                 
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
	
	private void drawTitle()
	{
		//标题
//		chart.setTitle("柱形渐显动画演示");
//		chart.addSubtitle("(XCL-Charts Demo)");
				
		chart.getDataAxis().show();		
	}
	
	
	
}

