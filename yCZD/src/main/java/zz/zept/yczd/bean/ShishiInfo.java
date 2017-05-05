package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/5.
 */

public class ShishiInfo implements Serializable {
    private String pValue;
    private String LocalDate;
    private String Defined;
    private String status;

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getLocalDate() {
        return LocalDate;
    }

    public void setLocalDate(String localDate) {
        LocalDate = localDate;
    }

    public String getDefined() {
        return Defined;
    }

    public void setDefined(String defined) {
        Defined = defined;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
