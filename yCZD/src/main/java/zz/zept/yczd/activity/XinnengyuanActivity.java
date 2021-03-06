package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
import zz.zept.yczd.adapter.XinnengyuanAdapter;
import zz.zept.yczd.bean.XinnengyuanInfo;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.UItools;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.BarChart04View;
import zz.zept.yczd.view.CalendarWindow;
import zz.zept.yczd.view.DountChart01View;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class XinnengyuanActivity extends Activity {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.layout)
    LinearLayout layout;
    private BarChart04View barChart04View;
    private LinearLayout.LayoutParams layoutParams;
    private List<XinnengyuanInfo> listRecods;
    private String date = "";
    private double total = 0.00;
    private DountChart01View dountChart01View;
    private XinnengyuanAdapter adapter;
    private int[] colors = {Color.parseColor("#00baff"), Color.parseColor("#438483"), Color.parseColor("#31e5e3"), Color.parseColor("#3ca3a1"), Color.parseColor("#3d6463"), Color.parseColor("#39abad"), Color.parseColor("#ff8386")};
    private View view;
    private LinearLayout linearLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinnengyuan);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        view = getLayoutInflater().inflate(R.layout.view_xinnengyuan, null);
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        listView = (ListView) view.findViewById(R.id.listview);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(ChartUtil.getYesterday(new Date()));
        company.setText(date);
        layoutParams = new LinearLayout.LayoutParams(
                UItools.dip2px(this,240), UItools.dip2px(this,240));
        initListener();
        getData();
    }

    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                CalendarWindow calendarWindow = new CalendarWindow(XinnengyuanActivity.this, company);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void initListener() {
        company.addTextChangedListener(textWatcher);
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
                        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        showData2();
                        break;

                }
            }
        });
    }

    private void getData() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.NEW_URL + "zdpt/sts/adFindForXnyValue.action", RequestMethod.POST);
        request.add("date", company.getText().toString());
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<XinnengyuanInfo>>() {
                        }.getType());
                        if (listRecods != null && listRecods.size() > 0) {
                            if (rb1.isChecked()) {
                                showData1();
                            }
                            if (rb2.isChecked()) {
                                showData2();
                            }
                        } else {
                            ToastUtils.showToast(XinnengyuanActivity.this, "查询不到数据");
                        }

                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(XinnengyuanActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(XinnengyuanActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showData2() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        layoutParams = new LinearLayout.LayoutParams(
                width, UItools.dip2px(this,240));
        linearLayout.removeAllViews();
        if (dountChart01View != null) {
            linearLayout.addView(dountChart01View, layoutParams);
            listView.setAdapter(adapter);
            layout.addView(view);
        } else {
            for (int i = 0; i < listRecods.size(); i++) {
                total += Double.parseDouble(listRecods.get(i).getYx());
            }
            for (int i = 0; i < listRecods.size(); i++) {
                double percent = Double.parseDouble(listRecods.get(i).getYx()) / total;
                listRecods.get(i).setPercent(percent * 100);
            }
            dountChart01View = new DountChart01View(XinnengyuanActivity.this, listRecods);
            linearLayout.addView(dountChart01View, layoutParams);
            adapter = new XinnengyuanAdapter(XinnengyuanActivity.this, listRecods, colors);
            listView.setAdapter(adapter);
            layout.addView(view);
        }

    }

    private void showData1() {
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (barChart04View != null) {
            layout.addView(barChart04View, layoutParams);
        } else {
            ArrayList<Double> data = new ArrayList<>();
            List<String> label = new ArrayList<>();
            for (int i = 0; i < listRecods.size(); i++) {
                data.add(Double.parseDouble(listRecods.get(i).getFdl()));
                label.add(listRecods.get(i).getInfo());
            }
            barChart04View = new BarChart04View(XinnengyuanActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, "万/kWh");
            layout.addView(barChart04View, layoutParams);
        }

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
            getData();
        }
    };

}
