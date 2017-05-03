package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import zz.zept.yczd.bean.ShengchanInfo1;
import zz.zept.yczd.bean.ShengchanList;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.LineChartView;
import zz.zept.yczd.view.PopWindow;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class ShengchanActivity extends Activity {
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
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.rb7)
    RadioButton rb7;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.layout)
    LinearLayout layout;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;
    private ListView listView;
    private String url = MyRes.BASE_URL + "zdpt/sts/adFindForSCValue.action", date = "";
    private ArrayList<ShengchanInfo1> listRecods;
    private List<ShengchanList> shengchanLists = new ArrayList<>();
    private PopWindow popWindow;
    private List<String> timeList = new ArrayList<>();
    private List<String> companyList = new ArrayList<>();
    private ShengchanAdapter shengchanAdapter;
    private int dataType;
    private String json = " [\n" +
            "        {\n" +
            "            \"gr_m\": \"0.00\",\n" +
            "            \"mh_y\": \"286.18\",\n" +
            "            \"fdl_d\": \"1919.95\",\n" +
            "            \"ydl_y\": \"3.60\",\n" +
            "            \"dw\": \"A\",\n" +
            "            \"mh_d\": \"285.70\",\n" +
            "            \"ydl_m\": \"4.20\",\n" +
            "            \"name\": \" 平顶山分公司\",\n" +
            "            \"gr_d\": \"0.00\",\n" +
            "            \"fdl_y\": \"205820.90\",\n" +
            "            \"fdl_m\": \"33859.76\",\n" +
            "            \"gr_y\": \"0.00\",\n" +
            "            \"mh_m\": \"289.82\",\n" +
            "            \"ydl_d\": \"4.24\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"gr_m\": \"69479.16\",\n" +
            "            \"mh_y\": \"299.19\",\n" +
            "            \"fdl_d\": \"1009.00\",\n" +
            "            \"ydl_y\": \"3.96\",\n" +
            "            \"dw\": \"B\",\n" +
            "            \"mh_d\": \"301.14\",\n" +
            "            \"ydl_m\": \"4.35\",\n" +
            "            \"name\": \" 开封分公 司\",\n" +
            "            \"gr_d\": \"3076.22\",\n" +
            "            \"fdl_y\": \"107189.71\",\n" +
            "            \"fdl_m\": \"18522.27\",\n" +
            "            \"gr_y\": \"374652.70\",\n" +
            "            \"mh_m\": \"302.44\",\n" +
            "            \"ydl_d\": \"4.10\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"gr_m\": \"43773.00\",\n" +
            "            \"mh_y\": \"271.76\",\n" +
            "            \"fdl_d\": \"441.20\",\n" +
            "            \"ydl_y\": \"4.41\",\n" +
            "            \"dw\": \"C\",\n" +
            "            \"mh_d\": \"338.54\",\n" +
            "            \"ydl_m\": \"5.67\",\n" +
            "            \"name\": \" 豫新发 电\",\n" +
            "            \"gr_d\": \"4071.00\",\n" +
            "            \"fdl_y\": \"76485.01\",\n" +
            "            \"fdl_m\": \"8569.27\",\n" +
            "            \"gr_y\": \"2050082.00\",\n" +
            "            \"mh_m\": \"330.01\",\n" +
            "            \"ydl_d\": \"5.24\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"gr_m\": \"86307.00\",\n" +
            "            \"mh_y\": \"270.35\",\n" +
            "            \"fdl_d\": \"656.65\",\n" +
            "            \"ydl_y\": \"4.17\",\n" +
            "            \"dw\": \"D\",\n" +
            "            \"mh_d\": \"316.91\",\n" +
            "            \"ydl_m\": \"5.37\",\n" +
            "            \"name\": \" 平东热 电\",\n" +
            "            \"gr_d\": \"2904.00\",\n" +
            "            \"fdl_y\": \"71580.07\",\n" +
            "            \"fdl_m\": \"10013.18\",\n" +
            "            \"gr_y\": \"1883958.00\",\n" +
            "            \"mh_m\": \"319.69\",\n" +
            "            \"ydl_d\": \"5.32\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"gr_m\": \"20769.00\",\n" +
            "            \"mh_y\": \"306.27\",\n" +
            "            \"fdl_d\": \"444.28\",\n" +
            "            \"ydl_y\": \"5.35\",\n" +
            "            \"dw\": \"E\",\n" +
            "            \"mh_d\": \"320.01\",\n" +
            "            \"ydl_m\": \"5.81\",\n" +
            "            \"name\": \" 南阳热 电\",\n" +
            "            \"gr_d\": \"857.00\",\n" +
            "            \"fdl_y\": \"68502.97\",\n" +
            "            \"fdl_m\": \"6503.38\",\n" +
            "            \"gr_y\": \"1090095.00\",\n" +
            "            \"mh_m\": \"317.91\",\n" +
            "            \"ydl_d\": \"6.10\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"gr_m\": \"0.00\",\n" +
            "            \"mh_y\": \"225.70\",\n" +
            "            \"fdl_d\": \"654.48\",\n" +
            "            \"ydl_y\": \"1.81\",\n" +
            "            \"dw\": \"F\",\n" +
            "            \"mh_d\": \"225.70\",\n" +
            "            \"ydl_m\": \"1.80\",\n" +
            "            \"name\": \" 郑州燃 机\",\n" +
            "            \"gr_d\": \"0.00\",\n" +
            "            \"fdl_y\": \"41464.00\",\n" +
            "            \"fdl_m\": \"8580.62\",\n" +
            "            \"gr_y\": \"0.00\",\n" +
            "            \"mh_m\": \"225.70\",\n" +
            "            \"ydl_d\": \"1.81\"\n" +
            "        }\n" +
            "    ]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengchan);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        initData();
        initListener();
//        getData1();
        listRecods = new Gson().fromJson(json, new TypeToken<ArrayList<ShengchanInfo1>>() {
        }.getType());
        if (listRecods != null && listRecods.size() > 0) {
            for (int i = 0; i < listRecods.size(); i++) {
                ShengchanList item = new ShengchanList();
                item.setCompany(listRecods.get(i).getName());
                item.setDw("万kWh");
                item.setNum(listRecods.get(i).getFdl_d());
                item.setTime(date);
                shengchanLists.add(item);
            }
            shengchanAdapter = new ShengchanAdapter(ShengchanActivity.this, shengchanLists);
            listView.setAdapter(shengchanAdapter);
            listView.setDivider(null);
            layout.addView(listView, layoutParams);
        }
    }

    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                    popWindow = new PopWindow(this, timeList, company);
                    popWindow.setOnItemClick(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            popWindow.dissmiss();
                            company.setText(timeList.get(i));
                            dataType = i;
                            switch (i) {
                                case 0:
                                    dayData();
                                    break;
                                case 1:
                                    monthData();
                                    break;
                                case 2:
                                    yearData();
                                    break;
                            }
                        }
                    });
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(new Date());
        listView = new ListView(this);
        lineChartView = new LineChartView(this);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        timeList.add("当日");
        timeList.add("月累");
        timeList.add("年累");
    }

    private void initListener() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Configuration mConfiguration = getResources().getConfiguration();
                layout.removeAllViews();
                switch (i) {
                    case R.id.rb1:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        switch (dataType){
                            case 0:
                                dayData();
                                break;
                            case 1:
                                monthData();
                                break;
                            case 2:
                                yearData();
                                break;
                        }
                        layout.addView(listView,layoutParams);
                        break;
                    case R.id.rb2:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        switch (dataType){
                            case 0:
                                dayData();
                                break;
                            case 1:
                                monthData();
                                break;
                            case 2:
                                yearData();
                                break;
                        }
                        layout.addView(listView,layoutParams);
                        break;
                    case R.id.rb3:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        switch (dataType){
                            case 0:
                                dayData();
                                break;
                            case 1:
                                monthData();
                                break;
                            case 2:
                                yearData();
                                break;
                        }
                        layout.addView(listView,layoutParams);
                        break;
                    case R.id.rb4:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        switch (dataType){
                            case 0:
                                dayData();
                                break;
                            case 1:
                                monthData();
                                break;
                            case 2:
                                yearData();
                                break;
                        }
                        layout.addView(listView,layoutParams);
                        break;
                    case R.id.rb5:
                        company.setVisibility(View.GONE);
                        break;
                    case R.id.rb6:
                        company.setVisibility(View.GONE);
                        break;
                    case R.id.rb7:
                        company.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void getData1() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.add("date", date);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if ("success".equals(jsonObject.get("code"))) {
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ShengchanInfo1>>() {
                        }.getType());
                        if (listRecods != null && listRecods.size() > 0) {
                            for (int i = 0; i < listRecods.size(); i++) {
                                ShengchanList item = new ShengchanList();
                                item.setCompany(listRecods.get(i).getName());
                                item.setDw("万kWh");
                                item.setNum(listRecods.get(i).getFdl_d());
                                item.setTime(date);
                                shengchanLists.add(item);
                            }
                            shengchanAdapter = new ShengchanAdapter(ShengchanActivity.this, shengchanLists);
                            listView.setAdapter(shengchanAdapter);
                            layout.addView(listView, layoutParams);
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(ShengchanActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(ShengchanActivity.this);
        callServer.add(12312, request, callback);
    }

    private void dayData() {
        if (listRecods != null && listRecods.size() > 0) {
            shengchanLists.clear();
            if (rb1.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("万kWh");
                    item.setNum(listRecods.get(i).getFdl_d());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb2.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("%");
                    item.setNum(listRecods.get(i).getYdl_d());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb3.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("g/kWh");
                    item.setNum(listRecods.get(i).getMh_d());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb4.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("GJ");
                    item.setNum(listRecods.get(i).getGr_d());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            shengchanAdapter.notifyDataSetChanged();
        }

    }

    private void monthData() {
        if (listRecods != null && listRecods.size() > 0) {
            shengchanLists.clear();
            if (rb1.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("万kWh");
                    item.setNum(listRecods.get(i).getFdl_m());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb2.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("%");
                    item.setNum(listRecods.get(i).getYdl_m());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb3.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("g/kWh");
                    item.setNum(listRecods.get(i).getMh_m());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb4.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("GJ");
                    item.setNum(listRecods.get(i).getGr_m());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            shengchanAdapter.notifyDataSetChanged();
        }

    }

    private void yearData() {
        if (listRecods != null && listRecods.size() > 0) {
            shengchanLists.clear();
            if (rb1.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("万kWh");
                    item.setNum(listRecods.get(i).getFdl_y());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb2.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("%");
                    item.setNum(listRecods.get(i).getYdl_y());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb3.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("g/kWh");
                    item.setNum(listRecods.get(i).getMh_y());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            if (rb4.isChecked()) {
                for (int i = 0; i < listRecods.size(); i++) {
                    ShengchanList item = new ShengchanList();
                    item.setCompany(listRecods.get(i).getName());
                    item.setDw("GJ");
                    item.setNum(listRecods.get(i).getGr_y());
                    item.setTime(date);
                    shengchanLists.add(item);
                }
            }
            shengchanAdapter.notifyDataSetChanged();
        }

    }
}
