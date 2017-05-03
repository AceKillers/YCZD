package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.view.LineChartView;

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
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.list)
    ListView list;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anquan);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
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
}
