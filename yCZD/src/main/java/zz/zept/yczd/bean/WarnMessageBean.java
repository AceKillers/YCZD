package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by sld on 2016/12/13.
 */

public class WarnMessageBean {
	/**
	 * code : success data :
	 * [{"factoryName":"平顶山分公司","attachementname":null,"macName":"#1炉A一次风机",
	 * "subject":"振动精密诊断","sendTime":"2016-12-13 10:09:28.0"
	 * ,"attachementurl":"http://192.168.66.13:8090/SPIC/MsgCenter/null",
	 * "serialversionuid":1,"sendtype":"短信,邮件,微信,","id":
	 * "447401ba-6dde-4368-b482-55540757e5c0","content":
	 * "平顶山分公司--#1炉A一次风机--测点报警：风机轴承水平--4.66309mm/s","sender":"系统","msglevel":1,
	 * "msgtype":1,"mesOrigin":"1"},{"factoryName":"平顶山分公司","attachementname":
	 * null,"macName":"#1机B汽动给水泵","subject":"振动精密诊断","sendTime":
	 * "2016-12-13 10:09:28.0"
	 * ,"attachementurl":"http://192.168.66.13:8090/SPIC/MsgCenter/null",
	 * "serialversionuid":1,"sendtype":"短信,邮件,微信,","id":
	 * "dfa9fc7d-2f5a-4f0e-9f2c-515686ba6e9b","content":
	 * "平顶山分公司--#1机B汽动给水泵--测点报警：汽泵前水平--82.5526um","sender":"系统","msglevel":1,
	 * "msgtype":1,"mesOrigin":"1"}] message : 读取数据成功
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
		 * factoryName : 平顶山分公司 attachementname : null macName : #1炉A一次风机
		 * subject : 振动精密诊断 sendTime : 2016-12-13 10:09:28.0 attachementurl :
		 * http://192.168.66.13:8090/SPIC/MsgCenter/null serialversionuid : 1
		 * sendtype : 短信,邮件,微信, id : 447401ba-6dde-4368-b482-55540757e5c0
		 * content : 平顶山分公司--#1炉A一次风机--测点报警：风机轴承水平--4.66309mm/s sender : 系统
		 * msglevel : 1 msgtype : 1 mesOrigin : 1
		 */

		private String factoryName;
		private Object attachementname;
		private String macName;
		private String subject;
		private String sendTime;
		private String attachementurl;
		private int serialversionuid;
		private String sendtype;
		private String id;
		private String content;
		private String sender;
		private int msglevel;
		private int msgtype;
		private String mesOrigin;
		public boolean check = false;

		public String getFactoryName() {
			return factoryName;
		}

		public void setFactoryName(String factoryName) {
			this.factoryName = factoryName;
		}

		public Object getAttachementname() {
			return attachementname;
		}

		public void setAttachementname(Object attachementname) {
			this.attachementname = attachementname;
		}

		public String getMacName() {
			return macName;
		}

		public void setMacName(String macName) {
			this.macName = macName;
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

		public String getAttachementurl() {
			return attachementurl;
		}

		public void setAttachementurl(String attachementurl) {
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

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
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
