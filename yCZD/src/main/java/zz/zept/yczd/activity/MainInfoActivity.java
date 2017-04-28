package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;

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
            "报警查询",
            "注销"};
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        String [] from ={"image","text"};
        int [] to = {R.id.img,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_main_grid, from, to);
        grid.setAdapter(sim_adapter);

    }

    private List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
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
                break;
            case R.id.menhu:
                break;
        }
    }
}
