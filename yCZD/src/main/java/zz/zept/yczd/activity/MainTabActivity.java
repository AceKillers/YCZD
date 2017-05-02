package zz.zept.yczd.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import butterknife.BindView;
import butterknife.ButterKnife;
import zz.zept.yczd.R;
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
        mTabHost.addTab(mTabHost.newTabSpec(TAB_BBS).setIndicator(TAB_BBS).setContent(new Intent(this, MainInfoActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_DISCOVER).setIndicator(TAB_DISCOVER).setContent(new Intent(this, DiagnoseActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(new Intent(this, MainInfoActivity.class)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_main:
                mTabHost.setCurrentTabByTag(TAB_NEWS);
                break;
            case R.id.rb_time:
                mTabHost.setCurrentTabByTag(TAB_BBS);
                break;
            case R.id.rb_doc:
                mTabHost.setCurrentTabByTag(TAB_DISCOVER);
                break;
            case R.id.rb_analy:
                mTabHost.setCurrentTabByTag(TAB_MINE);
                break;
        }
    }
}
