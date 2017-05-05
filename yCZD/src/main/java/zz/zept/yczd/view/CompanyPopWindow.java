package zz.zept.yczd.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zz.zept.yczd.R;
import zz.zept.yczd.bean.Company;

/**
 * Created by HanChenxi on 2017/5/3.
 */

public class CompanyPopWindow {
    private Activity activity;
    private List<Company> list;
    private PopupWindow popupWindow;
    private ListView listView;
    private View view;

    public CompanyPopWindow(Activity activity, List<Company> list, View view) {
        this.activity = activity;
        this.list = list;
        this.view = view;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.pop_list, null);
        listView = (ListView) layout.findViewById(R.id.list);
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<list.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", list.get(i).getFACTORYNAME());
            data_list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(activity,data_list,R.layout.pop_list_item,new String[]{"text"},new int[]{R.id.text});
        listView.setAdapter(simpleAdapter);
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        popupWindow.showAsDropDown(view);
    }

    public void setOnItemClick(AdapterView.OnItemClickListener listener){
        listView.setOnItemClickListener(listener);
    }

    public void dissmiss(){
        popupWindow.dismiss();
    }
}
