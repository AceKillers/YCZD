package zz.zept.yczd.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_actvity);
        ButterKnife.bind(this);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#3471ee"));
        initTab();
    }

    private void initTab() {
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS).setContent(new Intent(this, MainInfoActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_BBS).setIndicator(TAB_BBS).setContent(new Intent(this, MainInfoActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_DISCOVER).setIndicator(TAB_DISCOVER).setContent(new Intent(this, MainInfoActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(new Intent(this, MainInfoActivity.class)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
