package zz.zept.yczd.bean;

import java.util.List;

public class ZDBGBean {
	/**
	 * code : success data :
	 * {"APPENDNUM":"1","Appends":[{"DRIA_APPENDID":44,"DRIA_APPENDNAME":
	 * "6号机缺少测点.xlsx","DRIA_APPENDPATH":
	 * "/upload/DiagAppend/201612/PDSFD120160025/5e91a6ad-0de9-44a6-bcc9-0a4d3abdb81d.xlsx"
	 * ,"DRIA_DRI_ID":"PDSFD_#1_20160025"}],"DEI_NAME":"本体各轴承振动",
	 * "DRIL_USERNAME1":"系统管理员","DRIL_USERNAME2":"","DRIL_USERNAME3":"",
	 * "DRIL_USERNAME4":"","DRIT_NAME":"设备预警","DRI_ADVICE":"<p>ss<br/><\/p>"
	 * ,"DRI_ANALYSIS":"<p>sds<br/><\/p>","DRI_BEHAVI":"<p>few<br/><\/p>"
	 * ,"DRI_CURVE":"<p>dsd<br/><\/p>","DRI_ENTERTIME":"2016-12-23 14:38:28"
	 * ,"DRI_EQUIP_CODE":"0201","DRI_FACTORY_ID":"301","DRI_HAPPENTIME":
	 * "2016-12-21","DRI_ID":"PDSFD_#1_20160025","DRI_STATE":"1","DRI_TITLE":
	 * "测试接口","DRI_TYPEID":"1","FACTROYNAME":"平顶山分公司#1机","HASPREM":"1"} message
	 * : 读取数据成功
	 */

	private String code;
	private DataBean data;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static class DataBean {
		/**
		 * APPENDNUM : 1 Appends :
		 * [{"DRIA_APPENDID":44,"DRIA_APPENDNAME":"6号机缺少测点.xlsx",
		 * "DRIA_APPENDPATH":
		 * "/upload/DiagAppend/201612/PDSFD120160025/5e91a6ad-0de9-44a6-bcc9-0a4d3abdb81d.xlsx"
		 * ,"DRIA_DRI_ID":"PDSFD_#1_20160025"}] DEI_NAME : 本体各轴承振动
		 * DRIL_USERNAME1 : 系统管理员 DRIL_USERNAME2 : DRIL_USERNAME3 :
		 * DRIL_USERNAME4 : DRIT_NAME : 设备预警 DRI_ADVICE :
		 * <p>
		 * ss<br/>
		 * </p>
		 * DRI_ANALYSIS :
		 * <p>
		 * sds<br/>
		 * </p>
		 * DRI_BEHAVI :
		 * <p>
		 * few<br/>
		 * </p>
		 * DRI_CURVE :
		 * <p>
		 * dsd<br/>
		 * </p>
		 * DRI_ENTERTIME : 2016-12-23 14:38:28 DRI_EQUIP_CODE : 0201
		 * DRI_FACTORY_ID : 301 DRI_HAPPENTIME : 2016-12-21 DRI_ID :
		 * PDSFD_#1_20160025 DRI_STATE : 1 DRI_TITLE : 测试接口 DRI_TYPEID : 1
		 * FACTROYNAME : 平顶山分公司#1机 HASPREM : 1
		 */

		private int APPENDNUM;
		private String DEI_NAME;
		private String DRIL_USERNAME1;
		private String DRIL_USERNAME2;
		private String DRIL_USERNAME3;
		private String DRIL_USERNAME4;
		private String DRIT_NAME;
		private String DRI_ADVICE;
		private String DRI_ANALYSIS;
		private String DRI_BEHAVI;
		private String DRI_CURVE;
		private String DRI_ENTERTIME;
		private String DRI_EQUIP_CODE;
		private String DRI_FACTORY_ID;
		private String DRI_HAPPENTIME;
		private String DRI_ID;
		private String DRI_STATE;
		private String DRI_TITLE;
		private String DRI_TYPEID;
		private String FACTROYNAME;
		private String HASPREM;
		private List<AppendsBean> Appends;

		public int getAPPENDNUM() {
			return APPENDNUM;
		}

		public void setAPPENDNUM(int APPENDNUM) {
			this.APPENDNUM = APPENDNUM;
		}

		public String getDEI_NAME() {
			return DEI_NAME;
		}

		public void setDEI_NAME(String DEI_NAME) {
			this.DEI_NAME = DEI_NAME;
		}

		public String getDRIL_USERNAME1() {
			return DRIL_USERNAME1;
		}

		public void setDRIL_USERNAME1(String DRIL_USERNAME1) {
			this.DRIL_USERNAME1 = DRIL_USERNAME1;
		}

		public String getDRIL_USERNAME2() {
			return DRIL_USERNAME2;
		}

		public void setDRIL_USERNAME2(String DRIL_USERNAME2) {
			this.DRIL_USERNAME2 = DRIL_USERNAME2;
		}

		public String getDRIL_USERNAME3() {
			return DRIL_USERNAME3;
		}

		public void setDRIL_USERNAME3(String DRIL_USERNAME3) {
			this.DRIL_USERNAME3 = DRIL_USERNAME3;
		}

		public String getDRIL_USERNAME4() {
			return DRIL_USERNAME4;
		}

		public void setDRIL_USERNAME4(String DRIL_USERNAME4) {
			this.DRIL_USERNAME4 = DRIL_USERNAME4;
		}

		public String getDRIT_NAME() {
			return DRIT_NAME;
		}

		public void setDRIT_NAME(String DRIT_NAME) {
			this.DRIT_NAME = DRIT_NAME;
		}

		public String getDRI_ADVICE() {
			return DRI_ADVICE;
		}

		public void setDRI_ADVICE(String DRI_ADVICE) {
			this.DRI_ADVICE = DRI_ADVICE;
		}

		public String getDRI_ANALYSIS() {
			return DRI_ANALYSIS;
		}

		public void setDRI_ANALYSIS(String DRI_ANALYSIS) {
			this.DRI_ANALYSIS = DRI_ANALYSIS;
		}

		public String getDRI_BEHAVI() {
			return DRI_BEHAVI;
		}

		public void setDRI_BEHAVI(String DRI_BEHAVI) {
			this.DRI_BEHAVI = DRI_BEHAVI;
		}

		public String getDRI_CURVE() {
			return DRI_CURVE;
		}

		public void setDRI_CURVE(String DRI_CURVE) {
			this.DRI_CURVE = DRI_CURVE;
		}

		public String getDRI_ENTERTIME() {
			return DRI_ENTERTIME;
		}

		public void setDRI_ENTERTIME(String DRI_ENTERTIME) {
			this.DRI_ENTERTIME = DRI_ENTERTIME;
		}

		public String getDRI_EQUIP_CODE() {
			return DRI_EQUIP_CODE;
		}

		public void setDRI_EQUIP_CODE(String DRI_EQUIP_CODE) {
			this.DRI_EQUIP_CODE = DRI_EQUIP_CODE;
		}

		public String getDRI_FACTORY_ID() {
			return DRI_FACTORY_ID;
		}

		public void setDRI_FACTORY_ID(String DRI_FACTORY_ID) {
			this.DRI_FACTORY_ID = DRI_FACTORY_ID;
		}

		public String getDRI_HAPPENTIME() {
			return DRI_HAPPENTIME;
		}

		public void setDRI_HAPPENTIME(String DRI_HAPPENTIME) {
			this.DRI_HAPPENTIME = DRI_HAPPENTIME;
		}

		public String getDRI_ID() {
			return DRI_ID;
		}

		public void setDRI_ID(String DRI_ID) {
			this.DRI_ID = DRI_ID;
		}

		public String getDRI_STATE() {
			return DRI_STATE;
		}

		public void setDRI_STATE(String DRI_STATE) {
			this.DRI_STATE = DRI_STATE;
		}

		public String getDRI_TITLE() {
			return DRI_TITLE;
		}

		public void setDRI_TITLE(String DRI_TITLE) {
			this.DRI_TITLE = DRI_TITLE;
		}

		public String getDRI_TYPEID() {
			return DRI_TYPEID;
		}

		public void setDRI_TYPEID(String DRI_TYPEID) {
			this.DRI_TYPEID = DRI_TYPEID;
		}

		public String getFACTROYNAME() {
			return FACTROYNAME;
		}

		public void setFACTROYNAME(String FACTROYNAME) {
			this.FACTROYNAME = FACTROYNAME;
		}

		public String getHASPREM() {
			return HASPREM;
		}

		public void setHASPREM(String HASPREM) {
			this.HASPREM = HASPREM;
		}

		public List<AppendsBean> getAppends() {
			return Appends;
		}

		public void setAppends(List<AppendsBean> Appends) {
			this.Appends = Appends;
		}

		public static class AppendsBean {
			/**
			 * DRIA_APPENDID : 44 DRIA_APPENDNAME : 6号机缺少测点.xlsx DRIA_APPENDPATH
			 * :
			 * /upload/DiagAppend/201612/PDSFD120160025/5e91a6ad-0de9-44a6-bcc9-
			 * 0a4d3abdb81d.xlsx DRIA_DRI_ID : PDSFD_#1_20160025
			 */

			private int DRIA_APPENDID;
			private String DRIA_APPENDNAME;
			private String DRIA_APPENDPATH;
			private String DRIA_DRI_ID;

			public int getDRIA_APPENDID() {
				return DRIA_APPENDID;
			}

			public void setDRIA_APPENDID(int DRIA_APPENDID) {
				this.DRIA_APPENDID = DRIA_APPENDID;
			}

			public String getDRIA_APPENDNAME() {
				return DRIA_APPENDNAME;
			}

			public void setDRIA_APPENDNAME(String DRIA_APPENDNAME) {
				this.DRIA_APPENDNAME = DRIA_APPENDNAME;
			}

			public String getDRIA_APPENDPATH() {
				return DRIA_APPENDPATH;
			}

			public void setDRIA_APPENDPATH(String DRIA_APPENDPATH) {
				this.DRIA_APPENDPATH = DRIA_APPENDPATH;
			}

			public String getDRIA_DRI_ID() {
				return DRIA_DRI_ID;
			}

			public void setDRIA_DRI_ID(String DRIA_DRI_ID) {
				this.DRIA_DRI_ID = DRIA_DRI_ID;
			}
		}
	}
}
