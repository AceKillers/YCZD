package zz.zept.yczd.pager;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义pager的基类
 * @author wangdh
 *
 */
public abstract class BasePager {
    
    public Context context;

    public BasePager(Context context){
        this.context = context;
    }
    /**
     * 初始化view  TODO 还没有被调用
     * 1.确定布局
     * 2.findViewById
     * @return
     */
    public abstract View initView();
    /**
     * 初始化数据
     * 给控件赋值
     */
    public abstract void initData();
    /**
     * 注册监听
     * 
     */
    public abstract void   initListener();
    
    
}
