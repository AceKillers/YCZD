package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by sld on 2016/12/8.
 */

public class YCZDMachineBean {
    /**
     * code : success
     * message : 读取数据成功！
     * data : [{"id":434,"text":"郑州燃机","parentid":101},{"id":432,"text":"南阳热电","parentid":101},{"id":429,"text":"豫新发电","parentid":101},{"id":426,"text":"平东热电","parentid":101},{"id":202,"text":"开封分公司","parentid":101},{"id":201,"text":"平顶山分公司","parentid":101},{"id":203,"text":"燕山湖电厂","parentid":102},{"id":302,"text":"#2机","parentid":201},{"id":301,"text":"#1机","parentid":201},{"id":306,"text":"#2机","parentid":202},{"id":303,"text":"#1机","parentid":202},{"id":305,"text":"#2机","parentid":203},{"id":304,"text":"#1机","parentid":203},{"id":428,"text":"#7机组","parentid":426},{"id":427,"text":"#6机组","parentid":426},{"id":431,"text":"#7机组","parentid":429},{"id":430,"text":"#6机组","parentid":429},{"id":433,"text":"#1机组","parentid":432},{"id":437,"text":"#2机组","parentid":432},{"id":436,"text":"#2机组","parentid":434},{"id":435,"text":"#1机组","parentid":434},{"id":102,"text":"国家电投东北公司","parentid":0},{"id":101,"text":"国家电投河南公司","parentid":0}]
     */

    private String code;
    private String message;
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
        /**
         * id : 434
         * text : 郑州燃机
         * parentid : 101
         */

        private int id;
        private String text;
        private int parentid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }
    }
}
