package zz.zept.yczd.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.max;

/**
 * Created by HanChenxi on 2017/5/4.
 */

public class ChartUtil {

    public static double getMax(ArrayList<Double> list){
        double max = list.get(0);
        for (int i = 0;i<list.size();i++){
            if (list.get(i)>max){
                max = list.get(i);
            }
        }
        return max;
    }

    public static double getMin(ArrayList<Double> list){
        double min = list.get(0);
        for (int i = 0;i<list.size();i++){
            if (list.get(i)<min){
                min = list.get(i);
            }
        }
        return min;
    }

    public static Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static long getTodayZero() {
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime(long time) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        return format.format(d1);
    }
}
