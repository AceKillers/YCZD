package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/4.
 */

public class RanliaoInfo1 implements Serializable {
    private String id;
    private String dw;
    private String date;
    private String jml;
    private String jml_unit;
    private String mh;
    private String mh_unit;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJml() {
        return jml;
    }

    public void setJml(String jml) {
        this.jml = jml;
    }

    public String getJml_unit() {
        return jml_unit;
    }

    public void setJml_unit(String jml_unit) {
        this.jml_unit = jml_unit;
    }

    public String getMh() {
        return mh;
    }

    public void setMh(String mh) {
        this.mh = mh;
    }

    public String getMh_unit() {
        return mh_unit;
    }

    public void setMh_unit(String mh_unit) {
        this.mh_unit = mh_unit;
    }
}
