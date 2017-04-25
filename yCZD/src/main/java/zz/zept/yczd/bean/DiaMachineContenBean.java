package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class DiaMachineContenBean {
    /**
     * code : success
     * data : [{"DRIT_NAME":"设备预警","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160001","DRI_TITLE":"手动填写即时诊断报告1（测试）"},{"DRIT_NAME":"节能诊断","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160002","DRI_TITLE":"手动填写即时诊断报告2（测试）"},{"DRIT_NAME":"设备状态诊断","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160003","DRI_TITLE":"手动填写即时诊断报告3（测试）"},{"DRIT_NAME":"安全诊断","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160004","DRI_TITLE":"手动填写即时诊断报告4（测试）"},{"DRIT_NAME":"设备预警","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160005","DRI_TITLE":"手动填写即时诊断报告5（测试）"},{"DRIT_NAME":"节能诊断","DRI_ENTERTIME":"2016-10-14","DRI_ID":"PDSFD#120160006","DRI_TITLE":"手动填写即时诊断报告6（测试）"}]
     * message : 读取数据成功
     */

    private String code;
    private String message;
    /**
     * DRIT_NAME : 设备预警
     * DRI_ENTERTIME : 2016-10-14
     * DRI_ID : PDSFD#120160001
     * DRI_TITLE : 手动填写即时诊断报告1（测试）
     */

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String DRIT_NAME;
        private String DRI_ENTERTIME;
        private String DRI_ID;
        private String DRI_TITLE;

        public String getDRIT_NAME() {
            return DRIT_NAME;
        }

        public void setDRIT_NAME(String DRIT_NAME) {
            this.DRIT_NAME = DRIT_NAME;
        }

        public String getDRI_ENTERTIME() {
            return DRI_ENTERTIME;
        }

        public void setDRI_ENTERTIME(String DRI_ENTERTIME) {
            this.DRI_ENTERTIME = DRI_ENTERTIME;
        }

        public String getDRI_ID() {
            return DRI_ID;
        }

        public void setDRI_ID(String DRI_ID) {
            this.DRI_ID = DRI_ID;
        }

        public String getDRI_TITLE() {
            return DRI_TITLE;
        }

        public void setDRI_TITLE(String DRI_TITLE) {
            this.DRI_TITLE = DRI_TITLE;
        }
    }
}
