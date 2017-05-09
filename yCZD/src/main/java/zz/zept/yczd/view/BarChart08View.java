package zz.zept.yczd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BarChart08View extends DemoView{

    private String TAG = "BarChart08View";
    private BarChart chart = new BarChart();
    //轴数据源
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();
    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    private double max,min;
    private List<String> label;
    private List<Double> data;
    private String name;

    public BarChart08View(Context context, double max, double min, List<String> label, ArrayList<Double> data, String name) {
        super(context);
        // TODO Auto-generated constructor stub
        this.max = max;
        this.max = min;
        this.label = label;
        this.data = data;
        this.name = name;
        initView();
    }

    public BarChart08View(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public BarChart08View(Context context, AttributeSet attrs, int defStyle) {
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
//            chart.showRoundBorder();

            //标题
//            chart.setTitle("正负背向式图");
//            chart.addSubtitle("(XCL-Charts Demo)");
            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);
            chart.setCustomLines(mCustomLineDataset);

            //图例
            //chart.getAxisTitle().setLeftAxisTitle("参考成年男性标准值");
            //chart.getAxisTitle().setLowerAxisTitle("(请不要忽视您的健康)");

            //数据轴
            chart.getDataAxis().setAxisMax(max);
            chart.getDataAxis().setAxisMin(min);
            chart.getDataAxis().setAxisSteps((max-min)/10);
            chart.getDataAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
            chart.getCategoryAxis().getAxisPaint().setColor(Color.parseColor("#a6a5b3"));
            chart.getDataAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));
            chart.getCategoryAxis().getTickMarksPaint().setColor(Color.parseColor("#a6a5b3"));

            chart.getDataAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
            chart.getCategoryAxis().getTickLabelPaint().setColor(Color.parseColor("#a6a5b3"));
            chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.parseColor("#edeff5"));
            chart.getPlotGrid().getVerticalLinePaint().setColor(Color.parseColor("#edeff5"));
            //指隔多少个轴刻度(即细刻度)后为主刻度
            chart.getDataAxis().setDetailModeSteps(1);


            chart.getDataAxis().enabledAxisStd();
            chart.getDataAxis().setAxisStd(0);
            chart.getCategoryAxis().setAxisBuildStd(true);

            //背景网格
            chart.getPlotGrid().showHorizontalLines();

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

            //标签旋转45度
            //	chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);

            //在柱形顶部显示值
            chart.getBar().setItemLabelVisible(true);
            //设定格式
//            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
//                @Override
//                public String doubleFormatter(Double value) {
//                    // TODO Auto-generated method stub
//                    DecimalFormat df=new DecimalFormat("#0");
//                    String label = df.format(value).toString();
//                    return label;
//                }});

            //隐藏Key
            chart.getPlotLegend().hide();

            //让柱子间没空白
            chart.getBar().setBarInnerMargin(0.1f); //可尝试0.1或0.5各有啥效果噢


            //禁用平移模式
            // chart.disablePanMode();

            chart.disableHighPrecision();

            //限制只能左右滑动
            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);

            chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);

            // chart.showRoundBorder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void chartDataSet()
    {
        //标签对应的柱形数据集
        List<Double> dataSeriesA= new LinkedList<Double>();
        //依数据值确定对应的柱形颜色.
        List<Integer> dataColorA= new LinkedList<Integer>();
        for(int i=1;i<data.size();i++){
            dataSeriesA.add(data.get(i));
        }

        //此地的颜色为Key值颜色及柱形的默认颜色
        BarData BarDataA = new BarData(name,dataSeriesA,
                Color.parseColor("#00baff"));

        chartData.add(BarDataA);
    }

    private void chartLabels()
    {
        for(int i=1;i<label.size();i++)
        {
            chartLabels.add(label.get(i));
        }
    }


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

}
