package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import zz.zept.yczd.adapter.ZongheAdapter;
import zz.zept.yczd.bean.ZongheInfo;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.CalendarWindow;

/**
 * Created by HanChenxi on 2017/5/2.
 */

public class ZongheActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.list)
    ListView list;
    private List<ZongheInfo> listRecods;
    private String date = "";
    private ZongheAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonghe);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(ChartUtil.getYesterday(new Date()));
        company.setText(date);
        company.addTextChangedListener(textWatcher);
        getData();
    }


    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                CalendarWindow calendarWindow = new CalendarWindow(ZongheActivity.this,company);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getData() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.NEW_URL + "zdpt/sts/adFindDlByDate.action", RequestMethod.POST);
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
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ZongheInfo>>() {
                        }.getType());
                        if (listRecods!=null&&listRecods.size()>0){
                            for (int i = 0;i<listRecods.size();i++){
                                listRecods.get(i).setTime(company.getText().toString());
                            }
                            adapter = new ZongheAdapter(ZongheActivity.this,listRecods);
                            list.setAdapter(adapter);
                        }else {
                            ToastUtils.showToast(ZongheActivity.this, "查询不到数据");
                        }

                    }else {
                        ToastUtils.showToast(ZongheActivity.this, "查询不到数据");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(ZongheActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(ZongheActivity.this);
        callServer.add(12312, request, callback);
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
