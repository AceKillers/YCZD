package zz.zept.yczd.utils;

import android.util.Log;
/**
 * 日志工具类
 * @author wangdh
 *
 */
public class LogUtils {
    //日志开关
    private static final boolean ENABLE = true;
    
    public static void i(String tag, String msg) {
        if (ENABLE) {
            Log.i(tag, msg);
        }
    }
    
    public static void i(Class<?> cls, String msg) {
        if (ENABLE) {
            Log.i("itcast_" + cls.getSimpleName(), msg);
        }
    }
}
