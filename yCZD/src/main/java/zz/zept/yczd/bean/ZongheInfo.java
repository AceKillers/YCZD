package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/9.
 */

public class ZongheInfo implements Serializable {
    private String text;
    private String row;
    private String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
