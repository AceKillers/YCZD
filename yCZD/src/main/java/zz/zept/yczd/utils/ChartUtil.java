package zz.zept.yczd.utils;

import java.util.ArrayList;

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
}
