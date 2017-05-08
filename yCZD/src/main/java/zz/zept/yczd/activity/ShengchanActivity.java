package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import zz.zept.yczd.bean.Company;
import zz.zept.yczd.bean.ShengchanInfo1;
import zz.zept.yczd.bean.ShengchanInfo2;
import zz.zept.yczd.bean.ShengchanList;
import zz.zept.yczd.bean.ShishiInfo1;
import zz.zept.yczd.db.CompanyDBAction;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.CalendarWindow;
import zz.zept.yczd.view.CompanyPopWindow;
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
    @BindView(R.id.rb8)
    RadioButton rb8;
    @BindView(R.id.time)
    EditText time;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;
    private ListView listView;
    private String date = "";
    private ArrayList<ShengchanInfo1> listRecods;
    private ArrayList<ShengchanInfo2> listRecods2;
    private ArrayList<ShengchanInfo2> listRecods3;
    private ArrayList<ShishiInfo1> listRecods4;
    private List<ShengchanList> shengchanLists = new ArrayList<>();
    private PopWindow popWindow;
    private List<String> timeList = new ArrayList<>();
    private ShengchanAdapter shengchanAdapter;
    private int dataType;
    private List<Company> companyList = new ArrayList<>();
    private CompanyDBAction dbAction;
    private CompanyPopWindow companyPopWindow;
    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengchan);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        initData();
        initListener();
        getData1();
    }

    @OnClick({R.id.company, R.id.back, R.id.time})
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
                }else {
                    companyPopWindow = new CompanyPopWindow(this, companyList, company);
                    companyPopWindow.setOnItemClick(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            layout.removeAllViews();
                            companyId = companyList.get(i).getCODE();
                            company.setText(companyList.get(i).getFACTORYNAME());
                            companyPopWindow.dissmiss();
                            if (rb5.isChecked()){
                                getData2();
                            }
                            if (rb6.isChecked()){
                                if (listRecods3 == null) {
                                    getData3();
                                } else {
                                    showData6();
                                }
                            }
                            if (rb7.isChecked()){
                                if (listRecods3 == null) {
                                    getData3();
                                } else {
                                    showData7();
                                }
                            }

                        }
                    });
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.time:
                CalendarWindow calendarWindow = new CalendarWindow(ShengchanActivity.this,time);

                break;
        }
    }

    private void initData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(new Date());
        time.setText(date);
        listView = new ListView(this);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        timeList.add("当日");
        timeList.add("月累");
        timeList.add("年累");
        dbAction = CompanyDBAction.getInstance(this);
        companyList = dbAction.searchCompany();
        company.setText("当日");
        companyId = companyList.get(0).getCODE();
    }

    private void initListener() {
        time.addTextChangedListener(textWatcher);
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
                        company.setText("当日");
                        dataType = 0;
                        switch (dataType) {
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
                        layout.addView(listView, layoutParams);
                        break;
                    case R.id.rb2:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        company.setText("当日");
                        dataType = 0;
                        switch (dataType) {
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
                        layout.addView(listView, layoutParams);
                        break;
                    case R.id.rb3:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        company.setText("当日");
                        dataType = 0;
                        switch (dataType) {
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
                        layout.addView(listView, layoutParams);
                        break;
                    case R.id.rb4:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        company.setText("当日");
                        dataType = 0;
                        switch (dataType) {
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
                        layout.addView(listView, layoutParams);
                        break;
                    case R.id.rb5:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        company.setText(companyList.get(0).getFACTORYNAME());
                        getData2();
                        break;
                    case R.id.rb6:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        company.setText(companyList.get(0).getFACTORYNAME());
                        if (listRecods3 == null) {
                            getData3();
                        } else {
                            showData6();
                        }
                        break;
                    case R.id.rb7:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        company.setText(companyList.get(0).getFACTORYNAME());
                        if (listRecods3 == null) {
                            getData3();
                        } else {
                            showData7();
                        }
                        break;
                    case R.id.rb8:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        if (listRecods4 == null) {
                            getData8();
                        } else {
                            showData8();
                        }
                        break;
                }
            }
        });
    }

    private void getData1() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForSCValue.action", RequestMethod.POST);
        request.add("date", time.getText().toString());
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
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

    private void getData2() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        String start = sDateFormat.format(new Date()) + "-01";
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForEveryDayValue.action", RequestMethod.POST);
        request.add("timeStart", start);
        request.add("timeEnd", time.getText().toString());
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if ("success".equals(jsonObject.get("code"))) {
                        listRecods2 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ShengchanInfo2>>() {
                        }.getType());
                        if (listRecods2 != null && listRecods2.size() > 0) {
                            ArrayList<Double> data = new ArrayList<>();
                            List<String> label = new ArrayList<>();
                            String unit = "";
                            for (int i = 0; i < listRecods2.size(); i++) {
                                if (listRecods2.get(i).getId().contains(companyId)) {
                                    data.add(Double.parseDouble(listRecods2.get(i).getValue()));
                                    label.add(listRecods2.get(i).getDate());
                                    unit = listRecods2.get(i).getUnit();
                                }
                            }
                            lineChartView = new LineChartView(ShengchanActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, unit);
                            layout.addView(lineChartView, layoutParams);
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

    private void getData3() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        String end = sDateFormat.format(new Date()) + "-30";
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForFHValue.action", RequestMethod.POST);
        request.add("timeStart", time.getText().toString());
        request.add("timeEnd", end);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if ("success".equals(jsonObject.get("code"))) {
                        listRecods3 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ShengchanInfo2>>() {
                        }.getType());
                        if (rb6.isChecked()) {
                            showData6();
                        }
                        if (rb7.isChecked()) {
                            showData7();
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

    private void showData6() {
        if (listRecods3 != null && listRecods3.size() > 0) {
            ArrayList<Double> data = new ArrayList<>();
            List<String> label = new ArrayList<>();
            String unit = "";
            for (int i = 0; i < listRecods3.size(); i++) {
                if (listRecods3.get(i).getId().equals("A") && listRecods3.get(i).getName().contains("发电量")) {
                    if (!TextUtils.isEmpty(listRecods3.get(i).getValue())){
                        data.add(Double.parseDouble(listRecods3.get(i).getValue()));
                        label.add(listRecods3.get(i).getDate());
                        unit = listRecods3.get(i).getUnit();
                    }

                }
            }
            lineChartView = new LineChartView(ShengchanActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, unit);
            layout.addView(lineChartView, layoutParams);
        }

    }

    private void showData7() {
        if (listRecods3 != null && listRecods3.size() > 0) {
            ArrayList<Double> data = new ArrayList<>();
            List<String> label = new ArrayList<>();
            String unit = "";
            for (int i = 0; i < listRecods3.size(); i++) {
                if (listRecods3.get(i).getId().equals("A") && listRecods3.get(i).getName().contains("负荷率")) {
                    data.add(Double.parseDouble(listRecods3.get(i).getValue()));
                    label.add(listRecods3.get(i).getDate());
                    unit = listRecods3.get(i).getUnit();
                }
            }
            lineChartView = new LineChartView(ShengchanActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, unit);
            layout.addView(lineChartView, layoutParams);
        }

    }

    private void getData8() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindFhInfoByDate.action", RequestMethod.POST);
        request.add("date", time.getText().toString());
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
                        listRecods4 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ShishiInfo1>>() {
                        }.getType());
                        if (listRecods4 != null && listRecods4.size() > 0) {
                            showData8();
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

    private void showData8() {
        shengchanLists.clear();
        for (int i = 0; i < listRecods4.size(); i++) {
            ShengchanList item = new ShengchanList();
            item.setCompany(listRecods4.get(i).getDw());
            item.setDw("%");
            item.setNum(listRecods4.get(i).getFuhe());
            item.setTime(date);
            shengchanLists.add(item);

        }
        shengchanAdapter = new ShengchanAdapter(ShengchanActivity.this, shengchanLists);
        listView.setAdapter(shengchanAdapter);
        layout.addView(listView, layoutParams);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            layout.removeAllViews();
            if (rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked()){
                getData1();
            }
            if (rb5.isChecked()){
                getData2();
            }
            if (rb6.isChecked()||rb7.isChecked()){
                getData3();
            }
            if (rb8.isChecked()){
                getData8();
            }
        }
    };
}
