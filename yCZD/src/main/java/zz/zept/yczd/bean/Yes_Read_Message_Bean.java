package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Yes_Read_Message_Bean {
    /**
     * code : success
     * data : [{"factoryName":"平顶山发电分公司","macName":"1#汽轮机","attachementname":null,"subject":"辅助辅助诊断报警","sendTime":"2016-09-20","attachementurl":null,"serialversionuid":1,"sendtype":"0","content":"请密切关注汽轮机轴承2#轴承振动！","id":"1","sender":"sys","msglevel":1,"msgtype":0,"mesOrigin":"0"},{"factoryName":"平顶山发电分公司","macName":"1#汽轮机","attachementname":null,"subject":"XXX合同，请查收！","sendTime":"2016-09-09","attachementurl":null,"serialversionuid":1,"sendtype":"0","content":"XXX合同，确定范围；","id":"10","sender":"spic","msglevel":1,"msgtype":0,"mesOrigin":"3"}]
     * message : 读取数据成功
     */

    private String code;
    private String message;
    /**
     * factoryName : 平顶山发电分公司
     * macName : 1#汽轮机
     * attachementname : null
     * subject : 辅助辅助诊断报警
     * sendTime : 2016-09-20
     * attachementurl : null
     * serialversionuid : 1
     * sendtype : 0
     * content : 请密切关注汽轮机轴承2#轴承振动！
     * id : 1
     * sender : sys
     * msglevel : 1
     * msgtype : 0
     * mesOrigin : 0
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
        private String factoryName;
        private String macName;
        private Object attachementname;
        private String subject;
        private String sendTime;
        private Object attachementurl;
        private int serialversionuid;
        private String sendtype;
        private String content;
        private String id;
        private String sender;
        private int msglevel;
        private int msgtype;
        private String mesOrigin;

        public String getFactoryName() {
            return factoryName;
        }

        public void setFactoryName(String factoryName) {
            this.factoryName = factoryName;
        }

        public String getMacName() {
            return macName;
        }

        public void setMacName(String macName) {
            this.macName = macName;
        }

        public Object getAttachementname() {
            return attachementname;
        }

        public void setAttachementname(Object attachementname) {
            this.attachementname = attachementname;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public Object getAttachementurl() {
            return attachementurl;
        }

        public void setAttachementurl(Object attachementurl) {
            this.attachementurl = attachementurl;
        }

        public int getSerialversionuid() {
            return serialversionuid;
        }

        public void setSerialversionuid(int serialversionuid) {
            this.serialversionuid = serialversionuid;
        }

        public String getSendtype() {
            return sendtype;
        }

        public void setSendtype(String sendtype) {
            this.sendtype = sendtype;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public int getMsglevel() {
            return msglevel;
        }

        public void setMsglevel(int msglevel) {
            this.msglevel = msglevel;
        }

        public int getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(int msgtype) {
            this.msgtype = msgtype;
        }

        public String getMesOrigin() {
            return mesOrigin;
        }

        public void setMesOrigin(String mesOrigin) {
            this.mesOrigin = mesOrigin;
        }
    }
}
