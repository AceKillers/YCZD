package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
import zz.zept.yczd.bean.AnquanInfo;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class AnquanActivity extends Activity {
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
    private List<AnquanInfo> listRecods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anquan);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(new Date());
        initListener();
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
                switch (i) {
                    case R.id.rb1:
                        break;
                    case R.id.rb2:
                        break;
                    case R.id.rb3:
                        break;
                }
            }
        });
    }

    private void getData() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "zdpt/sts/adFindForHBValue.action", RequestMethod.POST);
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
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<AnquanInfo>>() {
                        }.getType());
                        showData1();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(AnquanActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(AnquanActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showData1() {

    }
}
