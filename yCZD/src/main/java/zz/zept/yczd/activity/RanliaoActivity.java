package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;
import zz.zept.yczd.adapter.ShengchanAdapter;
import zz.zept.yczd.bean.RanliaoInfo1;
import zz.zept.yczd.bean.RanliaoInfo2;
import zz.zept.yczd.bean.ShengchanList;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.BarChart06View;
import zz.zept.yczd.view.LineChartView;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class RanliaoActivity extends Activity {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.layout)
    LinearLayout layout;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;
    private String date = "";
    private ListView listView;
    private List<RanliaoInfo1> listRecods;
    private List<RanliaoInfo2> listRecods2;
    private BarChart06View barChart06View;
    private ShengchanAdapter shengchanAdapter;
    private String json = "[\n" +
            "        {\n" +
            "            \"id\": \"A\",\n" +
            "            \"dw\": \"平顶山分公司\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"8944.00\",\n" +
            "            \"jml\": \"13272.50\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"B\",\n" +
            "            \"dw\": \"开封分公司\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"4542.00\",\n" +
            "            \"jml\": \"4393.50\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"C\",\n" +
            "            \"dw\": \"豫新发电\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"2259.98\",\n" +
            "            \"jml\": \"0\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"D\",\n" +
            "            \"dw\": \"平东热电\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"3972.03\",\n" +
            "            \"jml\": \"3684.11\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"E\",\n" +
            "            \"dw\": \"南阳热电\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"2010.00\",\n" +
            "            \"jml\": \"491.08\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"F\",\n" +
            "            \"dw\": \"郑州燃机\",\n" +
            "            \"jml_unit\": \"t/万立方米\",\n" +
            "            \"mh\": \"127.59\",\n" +
            "            \"jml\": \"130.00\",\n" +
            "            \"mh_unit\": \"t/万立方米\"\n" +
            "        }\n" +
            "    ]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranliao);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(new Date());
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initListener();
//        getData1();
        listRecods = new Gson().fromJson(json, new TypeToken<ArrayList<RanliaoInfo1>>() {
        }.getType());
        showData1();
    }

    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void initListener() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                layout.removeAllViews();
                Configuration mConfiguration = getResources().getConfiguration();
                switch (i) {
                    case R.id.rb1:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        showData1();
                        break;
                    case R.id.rb2:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        break;
                    case R.id.rb3:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        if (listRecods2==null){
                            getData2();
                        }else {
                            showData3();
                        }
                        break;
                    case R.id.rb4:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        if (listRecods2==null){
                            getData2();
                        }else {
                            showData4();
                        }
                        break;
                }
            }
        });
    }

    private void getData1() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForMHValue.action", RequestMethod.POST);
        request.add("date", date);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<RanliaoInfo1>>() {
                        }.getType());

                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(RanliaoActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(RanliaoActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showData1() {
        if (listRecods != null && listRecods.size() > 0) {
            ArrayList<Double> data1 = new ArrayList<>();
            ArrayList<Double> data2 = new ArrayList<>();
            List<String> label = new ArrayList<>();
            for (int i = 0; i < listRecods.size(); i++) {
                data1.add(Double.parseDouble(listRecods.get(i).getJml()));
                data2.add(Double.parseDouble(listRecods.get(i).getMh()));
                label.add(listRecods.get(i).getDw());
            }
            double max1, max2;
            max1 = ChartUtil.getMax(data1)+1000;
            max2 = ChartUtil.getMax(data2)+1000;
            if (max1 > max2) {
                barChart06View = new BarChart06View(RanliaoActivity.this, Math.ceil(max1), label, data1, data2, "t/万立方米");
            } else {
                barChart06View = new BarChart06View(RanliaoActivity.this, Math.ceil(max2), label, data1, data2, "t/万立方米");
            }
            layout.addView(barChart06View,layoutParams);
        }
    }

    private void getData2() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForRLMValue.action", RequestMethod.POST);
        request.add("date", date);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
                        listRecods2 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<RanliaoInfo2>>() {
                        }.getType());
                        if (listRecods2!=null&&listRecods2.size()>0){
                            if (rb3.isChecked()){
                                showData3();
                            }
                            if (rb4.isChecked()){
                                showData4();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(RanliaoActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(RanliaoActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showData2(){

    }

    private void showData3(){

    }

    private void showData4(){
        List<ShengchanList> shengchanLists = new ArrayList<>();
        for (int i = 0; i < listRecods2.size(); i++) {
            ShengchanList item = new ShengchanList();
            item.setCompany(listRecods2.get(i).getDw());
            item.setDw("t");
            item.setNum(listRecods2.get(i).getZr());
            item.setTime(date);
            shengchanLists.add(item);
        }
        shengchanAdapter = new ShengchanAdapter(RanliaoActivity.this, shengchanLists);
        listView.setAdapter(shengchanAdapter);
        layout.addView(listView, layoutParams);
    }
}
