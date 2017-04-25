package zz.zept.yczd.pager;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import zz.zept.yczd.R;
import zz.zept.yczd.activity.DailyActiviyt;
import zz.zept.yczd.activity.DiagnoseActivity;
import zz.zept.yczd.activity.PicActivity;
import zz.zept.yczd.adapter.GVApapter;
import zz.zept.yczd.adapter.LVApapter;
import zz.zept.yczd.bean.OnePagerBean;
import zz.zept.yczd.bean.OnePagerBean.DataBean;
import zz.zept.yczd.message.acrtivity.MessageActivity;
import zz.zept.yczd.res.MyRes;
import zz.zept.yczd.trend_activity.TrendActivity;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.LogUtils;
import zz.zept.yczd.utils.HttpResponseListener.HttpListener;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.warn_activity.WarnActivity;

/**
 * 自定义pager:维护了一个view view的初始化 view的数据填充
 * 
 * 
 * @author sld
 *
 */
public class OnePager extends BasePager {
	private String[] texts;
	private int[] images;
	private List<DataBean> datas;
	TextView tv_name;
	private View view;
	GridView gv_main;
	SharedPreferences sp;
	ListView lv_main;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				handler.sendEmptyMessageDelayed(1, 600000);// 10分钟更新一次数据
				getServiceDatas();
				break;

			default:
				break;
			}
		}

		private void getServiceDatas() {
			Request<String> request = NoHttp.createStringRequest("http://192.168.66.31:81/api/Dept/getUnitState",
					RequestMethod.POST);
			request.setTag(this);
			HttpListener<String> callback = new HttpListener<String>() {

				@Override
				public void onSucceed(int what, Response<String> response) {
					// TODO Auto-generated method stub
					// ToastUtils.showToast(context,
					// response.get()+"asdsadasdadad");
					try {
						LogUtils.i("onepager", response.get());
						String json = response.get();
						Gson gson = new Gson();
						datas = gson.fromJson(json, OnePagerBean.class).getData();
						// ToastUtils.showToast(context, datas.size() + "");
						lv_main.setAdapter(new LVApapter(context, datas));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFailed(int what, Response<String> response) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(context, "联网失败,请检查网络");

				}

			};
			CallServer.getRequestInstance().add(1312, request, callback);
		};
	};

	public OnePager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
			((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		view = View.inflate(context, R.layout.pager_one, null);
		gv_main = (GridView) view.findViewById(R.id.gv_main);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		lv_main = (ListView) view.findViewById(R.id.lv_main);
		return view;
	}

	@Override
	public void initData() {
		texts = new String[] { "待办事项", "报警查询", "即时报告", "生产日报", "实时监测", "振动趋势" };
		images = new int[] { R.drawable.wddb, R.drawable.bjcx, R.drawable.jsbg, R.drawable.scrb, R.drawable.ssjc,
				R.drawable.zdqs, };
		gv_main.setAdapter(new GVApapter(context, texts, images));
		handler.sendEmptyMessageDelayed(1, 100);// 0.1s发送消息
		sp = context.getSharedPreferences(MyRes.MY_SHAREDPREFERENCES_NAME, 0);
		tv_name.setText( sp.getString(MyRes.LOGIN_NAME, ""));
	}

	public void initListener() {
		gv_main.setOnItemClickListener(new OnItemClickListener() {
			private Intent intent;

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					intent = new Intent(context, MessageActivity.class);
					context.startActivity(intent);
					break;
				case 1:
					intent = new Intent(context, WarnActivity.class);
					context.startActivity(intent);
					break;
				case 2:
					intent = new Intent(context, DiagnoseActivity.class);
					context.startActivity(intent);
					break;
				case 3:
					intent = new Intent(context, DailyActiviyt.class);
					context.startActivity(intent);
					break;
				case 4:
					intent = new Intent(context, PicActivity.class);
					context.startActivity(intent);
					break;
				case 5:
					intent = new Intent(context, TrendActivity.class);
					context.startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}

}
