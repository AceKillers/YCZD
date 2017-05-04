package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/4.
 */

public class XinnengyuanInfo implements Serializable {
    private String info;
    private String yx;
    private String fdl;
    private String yx_unit;
    private String fdl_unit;
    private Double percent;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getYx() {
        return yx;
    }

    public void setYx(String yx) {
        this.yx = yx;
    }

    public String getFdl() {
        return fdl;
    }

    public void setFdl(String fdl) {
        this.fdl = fdl;
    }

    public String getYx_unit() {
        return yx_unit;
    }

    public void setYx_unit(String yx_unit) {
        this.yx_unit = yx_unit;
    }

    public String getFdl_unit() {
        return fdl_unit;
    }

    public void setFdl_unit(String fdl_unit) {
        this.fdl_unit = fdl_unit;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
