package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.warn_activity.WarnOneActivity;
import zz.zept.yczd.warn_activity.WarnTwoActivity;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class BaojingActivity extends Activity {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.baojing)
    LinearLayout baojing;
    @BindView(R.id.yujing)
    LinearLayout yujing;
    @BindView(R.id.num_baojing)
    TextView numBaojing;
    @BindView(R.id.num_yujing)
    TextView numYujing;
    private String url1, url2;
    private int loadNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baojing);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
        url1 = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
                + "&msgState_=01&msgOrigin_=1";
        url2 = MyRes.BASE_URL + "todomessage.action?username_=" + sp.getString(MyRes.LOGIN_NAME, "")
                + "&msgState_=01&msgOrigin_=2";
        getServiceDatas();
    }

    @OnClick({R.id.btn_back, R.id.baojing, R.id.yujing})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.baojing:
                intent = intent.setClass(BaojingActivity.this, WarnOneActivity.class);
                startActivity(intent);
                break;
            case R.id.yujing:
                intent = intent.setClass(BaojingActivity.this, WarnTwoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getServiceDatas() {
        Utils.showWaiting(BaojingActivity.this);
        OnResponseListener<String> responseListener = new OnResponseListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                System.out.println(response.get());
                numBaojing.setText("振动报警（"+response.get().replace("\"", "")+"）");
                loadNum++;
                isLoadFinish();
            }

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onFinish(int what) {
//				loadNum++;
//				isLoadFinish();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToast(BaojingActivity.this, "联网失败,请检查网络");
                loadNum++;
                isLoadFinish();
            }
        };
        NoHttp.newRequestQueue().add(123231, NoHttp.createStringRequest(url1), responseListener);

        OnResponseListener<String> responseListener2 = new OnResponseListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                System.out.println(response.get());
                numYujing.setText("预警（"+response.get().replace("\"", "")+"）");
                loadNum++;
                isLoadFinish();
            }

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onFinish(int what) {
//				loadNum++;
//				isLoadFinish();

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                loadNum++;
                isLoadFinish();
            }
        };

        NoHttp.newRequestQueue().add(123231, NoHttp.createStringRequest(url2), responseListener2);

    }

    private void isLoadFinish() {
        if (loadNum == 2) {
            Utils.closeWaiting();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
