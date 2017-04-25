package zz.zept.yczd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 屏蔽左右滑动的懒viewpager
 * 不中断
      不消费 
 * @author wangdh
 *
 */
public class MyLazyViewpager extends LazyViewPager {

    public MyLazyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLazyViewpager(Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
