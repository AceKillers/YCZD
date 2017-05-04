package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/4.
 */

public class HuanbaoInfo implements Serializable {
    private String id;
    private String dw;
    private String so2;
    private String no;
    private String yc;
    private String unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getYc() {
        return yc;
    }

    public void setYc(String yc) {
        this.yc = yc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
