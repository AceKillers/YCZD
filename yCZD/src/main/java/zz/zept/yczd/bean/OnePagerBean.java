package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by sld on 2016/12/19.
 */

public class OnePagerBean {
    /**
     * code : success
     * data : [{"Capacity":"1030MW","FactoryID":"201","FactoryName":"平顶山分公司","LoadTag":"PDSFD_1000_01_FDJ_10CGB01-MW","LoadValue":"951.8246","UnitID":"301","UnitName":"#1机","greenQuota":{"NOxTag":"PDSFD_1000_01_TL_TL_10FGDAO013","NOxValue":"54.02194","SO2Tag":"PDSFD_1000_01_TL_TL_10HTA30CQ001","SO2Value":"19.73267","SootTag":"PDSFD_1000_01_TL_10FGDAO015","SootValue":"0.7155761"}}]
     * message : 读取数据成功
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
         * Capacity : 1030MW
         * FactoryID : 201
         * FactoryName : 平顶山分公司
         * LoadTag : PDSFD_1000_01_FDJ_10CGB01-MW
         * LoadValue : 951.8246
         * UnitID : 301
         * UnitName : #1机
         * greenQuota : {"NOxTag":"PDSFD_1000_01_TL_TL_10FGDAO013","NOxValue":"54.02194","SO2Tag":"PDSFD_1000_01_TL_TL_10HTA30CQ001","SO2Value":"19.73267","SootTag":"PDSFD_1000_01_TL_10FGDAO015","SootValue":"0.7155761"}
         */

        private String Capacity;
        private String FactoryID;
        private String FactoryName;
        private String LoadTag;
        private double LoadValue;
        private String UnitID;
        private String UnitName;
        private GreenQuotaBean greenQuota;

        public String getCapacity() {
            return Capacity;
        }

        public void setCapacity(String Capacity) {
            this.Capacity = Capacity;
        }

        public String getFactoryID() {
            return FactoryID;
        }

        public void setFactoryID(String FactoryID) {
            this.FactoryID = FactoryID;
        }

        public String getFactoryName() {
            return FactoryName;
        }

        public void setFactoryName(String FactoryName) {
            this.FactoryName = FactoryName;
        }

        public String getLoadTag() {
            return LoadTag;
        }

        public void setLoadTag(String LoadTag) {
            this.LoadTag = LoadTag;
        }

        public double getLoadValue() {
            return LoadValue;
        }

        public void setLoadValue(Float LoadValue) {
            this.LoadValue = LoadValue;
        }

        public String getUnitID() {
            return UnitID;
        }

        public void setUnitID(String UnitID) {
            this.UnitID = UnitID;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String UnitName) {
            this.UnitName = UnitName;
        }

        public GreenQuotaBean getGreenQuota() {
            return greenQuota;
        }

        public void setGreenQuota(GreenQuotaBean greenQuota) {
            this.greenQuota = greenQuota;
        }

        public static class GreenQuotaBean {
            /**
             * NOxTag : PDSFD_1000_01_TL_TL_10FGDAO013
             * NOxValue : 54.02194
             * SO2Tag : PDSFD_1000_01_TL_TL_10HTA30CQ001
             * SO2Value : 19.73267
             * SootTag : PDSFD_1000_01_TL_10FGDAO015
             * SootValue : 0.7155761
             */

            private String NOxTag;
            private String NOxValue;
            private String SO2Tag;
            private String SO2Value;
            private String SootTag;
            private String SootValue;

            public String getNOxTag() {
                return NOxTag;
            }

            public void setNOxTag(String NOxTag) {
                this.NOxTag = NOxTag;
            }

            public String getNOxValue() {
                return NOxValue;
            }

            public void setNOxValue(String NOxValue) {
                this.NOxValue = NOxValue;
            }

            public String getSO2Tag() {
                return SO2Tag;
            }

            public void setSO2Tag(String SO2Tag) {
                this.SO2Tag = SO2Tag;
            }

            public String getSO2Value() {
                return SO2Value;
            }

            public void setSO2Value(String SO2Value) {
                this.SO2Value = SO2Value;
            }

            public String getSootTag() {
                return SootTag;
            }

            public void setSootTag(String SootTag) {
                this.SootTag = SootTag;
            }

            public String getSootValue() {
                return SootValue;
            }

            public void setSootValue(String SootValue) {
                this.SootValue = SootValue;
            }
        }
    }
}
