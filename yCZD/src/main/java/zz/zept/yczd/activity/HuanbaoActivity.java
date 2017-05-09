package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
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
import zz.zept.yczd.bean.HuanbaoInfo;
import zz.zept.yczd.bean.ShengchanList;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.CalendarWindow;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class HuanbaoActivity extends Activity {
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
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.list)
    ListView list;
    private String date = "";
    private ArrayList<HuanbaoInfo> listRecods;
    private List<ShengchanList> shengchanLists = new ArrayList<>();
    private ShengchanAdapter shengchanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huanbao);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(ChartUtil.getYesterday(new Date()));
        company.setText(date);
        initListener();
        getData();
    }

    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                CalendarWindow calendarWindow = new CalendarWindow(HuanbaoActivity.this,company);
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
                switch (i) {
                    case R.id.rb1:
                        showData1();
                        break;
                    case R.id.rb2:
                        showData2();
                        break;
                    case R.id.rb3:
                        showData3();
                        break;
                }
            }
        });
    }

    private void getData() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForHBValue.action", RequestMethod.POST);
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
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<HuanbaoInfo>>() {
                        }.getType());
                        if (listRecods != null && listRecods.size() > 0) {
                            if (rb1.isChecked()){
                                showData1();
                            }
                            if (rb2.isChecked()){
                                showData2();
                            }
                            if (rb3.isChecked()){
                                showData3();
                            }
                        }else {
                            ToastUtils.showToast(HuanbaoActivity.this, "查询不到数据");
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(HuanbaoActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(HuanbaoActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showData1(){
        shengchanLists.clear();
        if (listRecods != null && listRecods.size() > 0) {
            for (int i = 0; i < listRecods.size(); i++) {
                ShengchanList item = new ShengchanList();
                item.setCompany(listRecods.get(i).getDw());
                item.setDw(listRecods.get(i).getUnit());
                item.setNum("时间"+listRecods.get(i).getYc());
                item.setTime(company.getText().toString());
                shengchanLists.add(item);
            }
            shengchanAdapter = new ShengchanAdapter(HuanbaoActivity.this, shengchanLists);
            list.setAdapter(shengchanAdapter);
        }
    }

    private void showData2(){
        shengchanLists.clear();
        if (listRecods != null && listRecods.size() > 0) {
            for (int i = 0; i < listRecods.size(); i++) {
                ShengchanList item = new ShengchanList();
                item.setCompany(listRecods.get(i).getDw());
                item.setDw(listRecods.get(i).getUnit());
                item.setNum("时间"+listRecods.get(i).getSo2());
                item.setTime(company.getText().toString());
                shengchanLists.add(item);
            }
            shengchanAdapter = new ShengchanAdapter(HuanbaoActivity.this, shengchanLists);
            list.setAdapter(shengchanAdapter);
        }
    }

    private void showData3(){
        shengchanLists.clear();
        if (listRecods != null && listRecods.size() > 0) {
            for (int i = 0; i < listRecods.size(); i++) {
                ShengchanList item = new ShengchanList();
                item.setCompany(listRecods.get(i).getDw());
                item.setDw(listRecods.get(i).getUnit());
                item.setNum("时间"+listRecods.get(i).getNo());
                item.setTime(company.getText().toString());
                shengchanLists.add(item);
            }
            shengchanAdapter = new ShengchanAdapter(HuanbaoActivity.this, shengchanLists);
            list.setAdapter(shengchanAdapter);
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
            getData();
        }
    };
}
