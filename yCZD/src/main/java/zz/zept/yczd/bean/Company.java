package zz.zept.yczd.bean;

import java.io.Serializable;

/**
 * Created by HanChenxi on 2017/5/5.
 */

public class Company implements Serializable {
    private String FACTORYID;
    private String FACTORYNAME;
    private String CODE;

    public String getFACTORYID() {
        return FACTORYID;
    }

    public void setFACTORYID(String FACTORYID) {
        this.FACTORYID = FACTORYID;
    }

    public String getFACTORYNAME() {
        return FACTORYNAME;
    }

    public void setFACTORYNAME(String FACTORYNAME) {
        this.FACTORYNAME = FACTORYNAME;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }
}
