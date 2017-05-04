package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/4.
 */

public class AnquanInfo implements Serializable {
    private String type;
    private String jz;
    private String rl;
    private String cause;
    private String time_start;
    private String time_end;
    private String property;
    private String mark;
    private String spare;
    private String impact;
    private String plan_time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }

    public String getRl() {
        return rl;
    }

    public void setRl(String rl) {
        this.rl = rl;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }
}
