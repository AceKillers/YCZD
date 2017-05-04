package zz.zept.yczd.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;
import zz.zept.yczd.pager.ThreePager;
import zz.zept.yczd.trend_activity.TrendActivity;
import zz.zept.yczd.utils.StatusBarCompat;

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
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_actvity);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        initTab();
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
