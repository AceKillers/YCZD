package zz.zept.yczd.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import zz.zept.yczd.R;
import zz.zept.yczd.pager.BasePager;
import zz.zept.yczd.pager.OnePager;
import zz.zept.yczd.pager.ThreePager;
import zz.zept.yczd.pager.TwoPager;
import zz.zept.yczd.view.MyLazyViewpager;

public class MainActivity extends Activity {
	MyLazyViewpager vp_main;
	private long exitTime = 0;
	RadioGroup rg_main;
	Activity context;
	private List<BasePager> pagers = new ArrayList<BasePager>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		initView();
		initListener();

		initData();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// 点击RadioButton ,切换viewpager
				int item = 0;
				switch (checkedId) {
				case R.id.rb_1:// 首页 -- 0
					item = 0;
					break;
				case R.id.rb_2:// 二页-- 1
					item = 1;
					break;
				case R.id.rb_3:// 三页 -- 2
					item = 2;
					break;

				default:
					break;
				}
				vp_main.setCurrentItem(item);
			}
		});
	}

	private void initData() {

		pagers.add(new OnePager(context));
		pagers.add(new TwoPager(context));
		pagers.add(new ThreePager(context));
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
		vp_main.setAdapter(myPagerAdapter);
		vp_main.setCurrentItem(0);
	}

	private void initView() {
		vp_main = (MyLazyViewpager) findViewById(R.id.vp_main);
		rg_main = (RadioGroup) findViewById(R.id.rg_main);

	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pagers.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Log.i("HomeFragment", "instantiateItem:" + position);
			// 根据当前position获取对应的view
			BasePager currentPager = pagers.get(position);
			View currentView = currentPager.initView();
			currentPager.initData();// 初始化数据(一定不要忘记)
			currentPager.initListener();
			container.addView(currentView);
			return currentView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Log.i("HomeFragment", "destroyItem:" + position);
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);

		}
	}
}
