package zz.zept.yczd.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.Company;
import zz.zept.yczd.db.CompanyDBAction;
import zz.zept.yczd.pager.ThreePager;
import zz.zept.yczd.trend_activity.TrendActivity;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;

/**
 * Created by HanChenxi on 2017/4/24.
 */

public class MainTabActivity extends TabActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rb_main)
    RadioButton rbMain;
    @BindView(R.id.rb_time)
    RadioButton rbTime;
    @BindView(R.id.rb_doc)
    RadioButton rbDoc;
    @BindView(R.id.rb_analy)
    RadioButton rbAnaly;
    @BindView(R.id.rg)
    RadioGroup rg;
    private TabHost mTabHost;
    public static final String TAB_NEWS = "tab_news";
    public static final String TAB_BBS = "tab_bbs";
    public static final String TAB_DISCOVER = "tab_discover";
    public static final String TAB_MINE = "tab_mine";
    private List<Company> listRecods2;
    private CompanyDBAction dbAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_actvity);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        dbAction = CompanyDBAction.getInstance(this);
        initTab();
        loadCompany();
    }

    private void initTab() {
        rg.setOnCheckedChangeListener(this);
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS).setContent(new Intent(this, MainInfoActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_BBS).setIndicator(TAB_BBS).setContent(new Intent(this, ThreePager.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_DISCOVER).setIndicator(TAB_DISCOVER).setContent(new Intent(this, DiagnoseActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(new Intent(this, TrendActivity.class)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Configuration mConfiguration = getResources().getConfiguration();
        switch (i) {
            case R.id.rb_main:
                if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                mTabHost.setCurrentTabByTag(TAB_NEWS);
                break;
            case R.id.rb_time:
                if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                mTabHost.setCurrentTabByTag(TAB_BBS);
                break;
            case R.id.rb_doc:
                if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                mTabHost.setCurrentTabByTag(TAB_DISCOVER);
                break;
            case R.id.rb_analy:
                if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                mTabHost.setCurrentTabByTag(TAB_MINE);
                break;
        }
    }

    private void loadCompany(){
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest("http://192.168.66.31:81/api/dept/getFactorys?companyid=101", RequestMethod.POST);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if ("success".equals(jsonObject.get("code"))) {
                        listRecods2 = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<Company>>() {
                        }.getType());
                        if (listRecods2!=null&&listRecods2.size()>0){
                            dbAction.saveCompany(listRecods2);
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToast(MainTabActivity.this, "服务器繁忙,稍后再试");
            }
        };
        callServer.add(12312, request, callback);
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
