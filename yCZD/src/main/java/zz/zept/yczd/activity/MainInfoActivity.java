package zz.zept.yczd.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;
import zz.zept.yczd.message.acrtivity.MessageActivity;
import zz.zept.yczd.pager.ThreePager;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.TipsToast;

/**
 * Created by HanChenxi on 2017/4/24.
 */

public class MainInfoActivity extends Activity {
    @BindView(R.id.daiban)
    LinearLayout daiban;
    @BindView(R.id.menhu_text)
    TextView menhuText;
    @BindView(R.id.menhu)
    LinearLayout menhu;
    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.msg_num)
    TextView msgNum;
    private int[] icon = {R.drawable.icon_shishi,
            R.drawable.icon_zonghe,
            R.drawable.icon_shengchan,
            R.drawable.icon_ranliao,
            R.drawable.icon_huanbao,
            R.drawable.icon_anquan,
            R.drawable.icon_xinnengyuan,
            R.drawable.icon_baojing,
            R.drawable.icon_zhuxiao};
    private String[] name = {"实时信息",
            "综合信息",
            "生产信息",
            "燃料信息",
            "环保信息",
            "安全信息",
            "新能源信息",
            "实时监测",
            "注销"};
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);
        ButterKnife.bind(this);

        initView();
        loadData();
    }

    private void initView() {
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"image", "text"};
        int[] to = {R.id.img, R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_main_grid, from, to);
        grid.setAdapter(sim_adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        intent.setClass(MainInfoActivity.this, ShishiActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MainInfoActivity.this, ZongheActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MainInfoActivity.this, ShengchanActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(MainInfoActivity.this, RanliaoActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(MainInfoActivity.this, HuanbaoActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(MainInfoActivity.this, AnquanActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(MainInfoActivity.this, XinnengyuanActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.setClass(MainInfoActivity.this, ThreePager.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.setClass(MainInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

            }
        });
    }

    private List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", name[i]);
            data_list.add(map);
        }

        return data_list;
    }


    @OnClick({R.id.daiban, R.id.menhu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.daiban:
                Intent intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.menhu:
                break;
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "todomessage.action?username_=" + sharedPreferences.getString(MyRes.LOGIN_NAME, "") + "&msgState_=01&msgOrigin_=0", RequestMethod.GET);
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();
                Utils.closeWaiting();
                Log.e("json", json);
                msgNum.setText(json.replaceAll("\"",""));
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // TODO Auto-generated method stub
                Utils.closeWaiting();
                TipsToast.show(MainInfoActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(MainInfoActivity.this);
        callServer.add(12312, request, callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
