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
import zz.zept.yczd.adapter.RanliaoAdapter;
import zz.zept.yczd.adapter.ShengchanAdapter;
import zz.zept.yczd.bean.Company;
import zz.zept.yczd.bean.RanliaoInfo1;
import zz.zept.yczd.bean.RanliaoInfo2;
import zz.zept.yczd.bean.ShengchanList;
import zz.zept.yczd.db.CompanyDBAction;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.BarChart06View;
import zz.zept.yczd.view.BarChart08View;
import zz.zept.yczd.view.CalendarWindow;
import zz.zept.yczd.view.CompanyPopWindow;
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
    @BindView(R.id.time)
    TextView time;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;
    private String date = "";
    private ListView listView;
    private List<RanliaoInfo1> listRecods;
    private List<RanliaoInfo2> listRecods2;
    private BarChart06View barChart06View;
    private ShengchanAdapter shengchanAdapter;
    private List<Company> companyList = new ArrayList<>();
    private CompanyDBAction dbAction;
    private CompanyPopWindow companyPopWindow;
    private String companyId;
    private RanliaoAdapter ranliaoAdapter;
    private BarChart08View barChart08View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranliao);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(ChartUtil.getYesterday(new Date()));
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dbAction = CompanyDBAction.getInstance(this);
        companyList = dbAction.searchCompany();
        company.setText(companyList.get(0).getFACTORYNAME());
        companyId = companyList.get(0).getCODE();
        initListener();
        getData1();
    }

    @OnClick({R.id.company, R.id.back, R.id.time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                companyPopWindow = new CompanyPopWindow(this, companyList, company);
                companyPopWindow.setOnItemClick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        companyId = companyList.get(i).getCODE();
                        company.setText(companyList.get(i).getFACTORYNAME());
                        companyPopWindow.dissmiss();

                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.time:
                CalendarWindow calendarWindow = new CalendarWindow(RanliaoActivity.this,time);
                break;
        }
    }

    private void initListener() {
        time.addTextChangedListener(textWatcher);
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
                        if (listRecods2 == null) {
                            getData2();
                        } else {
                            showData2();
                        }
                        break;
                    case R.id.rb3:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        if (listRecods2 == null) {
                            getData2();
                        } else {
                            showData3();
                        }
                        break;
                    case R.id.rb4:
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        if (listRecods2 == null) {
                            getData2();
                        } else {
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
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<RanliaoInfo1>>() {
                        }.getType());
                        showData1();
                    }else {
                        ToastUtils.showToast(RanliaoActivity.this, "查询不到数据");
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
            max1 = ChartUtil.getMax(data1) + 1000;
            max2 = ChartUtil.getMax(data2) + 1000;
            if (max1 > max2) {
                barChart06View = new BarChart06View(RanliaoActivity.this, Math.ceil(max1), label, data1, data2, "t/万立方米");
            } else {
                barChart06View = new BarChart06View(RanliaoActivity.this, Math.ceil(max2), label, data1, data2, "t/万立方米");
            }
            layout.addView(barChart06View, layoutParams);
        }
    }

    private void getData2() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForRLMValue.action", RequestMethod.POST);
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
                        listRecods2 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<RanliaoInfo2>>() {
                        }.getType());
                        if (listRecods2 != null && listRecods2.size() > 0) {
                            if (rb2.isChecked()) {
                                showData2();
                            }
                            if (rb3.isChecked()) {
                                showData3();
                            }
                            if (rb4.isChecked()) {
                                showData4();
                            }
                        }else {
                            ToastUtils.showToast(RanliaoActivity.this, "查询不到数据");
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

    private void showData2() {
        ArrayList<Double> data = new ArrayList<>();
        List<String> label = new ArrayList<>();
        for (int i = 0; i < listRecods2.size(); i++) {
            data.add(Double.parseDouble(listRecods2.get(i).getRc()));
            label.add(listRecods2.get(i).getDw());
        }
        barChart08View = new BarChart08View(RanliaoActivity.this, Math.ceil(ChartUtil.getMax(data)),Math.ceil(ChartUtil.getMin(data)), label, data, "大卡");
        layout.addView(barChart08View, layoutParams);
    }

    private void showData3() {
        listView = new ListView(this);
        ranliaoAdapter = new RanliaoAdapter(RanliaoActivity.this,listRecods2);
        listView.setAdapter(ranliaoAdapter);
        layout.addView(listView, layoutParams);
    }

    private void showData4() {
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
            if (rb1.isChecked()){
                getData1();
            }else {
                getData2();
            }
        }
    };
}
