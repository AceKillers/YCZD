package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/15 0015.
 */
public class DailyBean {
    /**
     * code : success
     * data : [{"dw_code":"A","id":"1","zonghechangyongdianlv":null,"de_info":"平顶山分公司","meihao":"102g/KWh","fuhelv":"#1机组30.4%，#2机组0","fadianchangyongdianlv":"4.16%","fadianliang":"100万w/kWh","url":"192.168.66.31:8080/zdpt/sts/rb/20161009.xls"},{"dw_code":"B","id":"2","zonghechangyongdianlv":null,"de_info":"开封分公司","meihao":"109g/KWh","fuhelv":"#1机组0，#2机组26.49%","fadianchangyongdianlv":"2.45%","fadianliang":"102万w/kWh","url":"192.168.66.31:8080/zdpt/sts/rb/20161009.xls"}]
     * message : 获取成功!
     */

    private String code;
    private String message;
    /**
     * dw_code : A
     * id : 1
     * zonghechangyongdianlv : null
     * de_info : 平顶山分公司
     * meihao : 102g/KWh
     * fuhelv : #1机组30.4%，#2机组0
     * fadianchangyongdianlv : 4.16%
     * fadianliang : 100万w/kWh
     * url : 192.168.66.31:8080/zdpt/sts/rb/20161009.xls
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
        private String dw_code;
        private String id;
        private Object zonghechangyongdianlv;
        private String de_info;
        private String meihao;
        private String fuhelv;
        private String fadianchangyongdianlv;
        private String fadianliang;
        private String url;

        public String getDw_code() {
            return dw_code;
        }

        public void setDw_code(String dw_code) {
            this.dw_code = dw_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getZonghechangyongdianlv() {
            return zonghechangyongdianlv;
        }

        public void setZonghechangyongdianlv(Object zonghechangyongdianlv) {
            this.zonghechangyongdianlv = zonghechangyongdianlv;
        }

        public String getDe_info() {
            return de_info;
        }

        public void setDe_info(String de_info) {
            this.de_info = de_info;
        }

        public String getMeihao() {
            return meihao;
        }

        public void setMeihao(String meihao) {
            this.meihao = meihao;
        }

        public String getFuhelv() {
            return fuhelv;
        }

        public void setFuhelv(String fuhelv) {
            this.fuhelv = fuhelv;
        }

        public String getFadianchangyongdianlv() {
            return fadianchangyongdianlv;
        }

        public void setFadianchangyongdianlv(String fadianchangyongdianlv) {
            this.fadianchangyongdianlv = fadianchangyongdianlv;
        }

        public String getFadianliang() {
            return fadianliang;
        }

        public void setFadianliang(String fadianliang) {
            this.fadianliang = fadianliang;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
