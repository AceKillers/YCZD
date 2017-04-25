package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class No_Read_Message_Bean {

	/**
	 * code : success data :
	 * [{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":null,
	 * "subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 14:38:32"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "2cb36b4b-d398-40e2-9396-a877ff12fddf","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 14:24:07"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "ba6474ec-b604-4cd9-877d-c7398be6ce15","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 14:19:37"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "a083b809-fcc2-4ca8-a974-d4913b839948","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 11:57:19"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "2f8a6e01-969a-4a03-a97c-35fffba31e7a","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 11:43:47"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "64d1b913-593b-4d28-bb6d-4aae7d59b0a7","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 11:42:55"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "2059b31e-cd64-4c4f-9cf8-24a745289e6c","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 11:41:42"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "9406e53e-ab8f-4323-b5f0-6263a8dd927e","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"},{"factoryName":null,"attachementname":"PDSFD_1_20160025","macName":
	 * null,"subject":"系统管理员填写的即时诊断报告已录入，等待初审！","floworder":"1","sendTime":
	 * "2016-12-23 08:46:16"
	 * ,"attachementurl":"http://192.168.23.30:8080/SPIC/MsgCenter/",
	 * "serialversionuid":1,"sendtype":"站内信","id":
	 * "5856d1d8-6784-4e55-bd78-69caeb28ba35","content":
	 * "诊断报告：<a href=\"http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025\" target=\"_blank\">测试接口 <\/a> 已录入完成，等待初审！"
	 * ,"sender":"admin","msglevel":5,"msgtype":1,"flowaction":"1","mesOrigin":
	 * "0"}] message : 读取数据成功
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
		 * factoryName : null attachementname : PDSFD_1_20160025 macName : null
		 * subject : 系统管理员填写的即时诊断报告已录入，等待初审！ floworder : 1 sendTime : 2016-12-23
		 * 14:38:32 attachementurl : http://192.168.23.30:8080/SPIC/MsgCenter/
		 * serialversionuid : 1 sendtype : 站内信 id :
		 * 2cb36b4b-d398-40e2-9396-a877ff12fddf content : 诊断报告：<a href=
		 * "http://192.168.66.31:81/Frame/Main?func=103002&sysid=1&diagID=PDSFD_1_20160025"
		 * target="_blank">测试接口 </a> 已录入完成，等待初审！ sender : admin msglevel : 5
		 * msgtype : 1 flowaction : 1 mesOrigin : 0
		 */

		private String factoryName;
		private String attachementname;
		private String macName;
		private String subject;
		private String floworder;
		private String sendTime;
		private String attachementurl;
		private int serialversionuid;
		private String sendtype;
		private String id;
		private String content;
		private String sender;
		private int msglevel;
		private int msgtype;
		private String flowaction;
		private String mesOrigin;
		public boolean ischeck;

		public Object getFactoryName() {
			return factoryName;
		}

		public void setFactoryName(String factoryName) {
			this.factoryName = factoryName;
		}

		public String getAttachementname() {
			return attachementname;
		}

		public void setAttachementname(String attachementname) {
			this.attachementname = attachementname;
		}

		public Object getMacName() {
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

		public String getFloworder() {
			return floworder;
		}

		public void setFloworder(String floworder) {
			this.floworder = floworder;
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

		public String getFlowaction() {
			return flowaction;
		}

		public void setFlowaction(String flowaction) {
			this.flowaction = flowaction;
		}

		public String getMesOrigin() {
			return mesOrigin;
		}

		public void setMesOrigin(String mesOrigin) {
			this.mesOrigin = mesOrigin;
		}
	}
}
