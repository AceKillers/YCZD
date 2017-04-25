package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by sld on 2016/12/12.
 */

public class ChannelBean {
    /**
     * code : success
     * data : [{"name":"运行状态","ID":"ebad60a3-fe99-4f25-aea9-e4e2d8f10e83"},{"name":"下端轴承X","ID":"f4e939aa-aa35-4518-a029-9081cc87a138"},{"name":"下端轴承Y","ID":"bd641c15-c03c-4628-9eb7-4699769e2c7c"},{"name":"电机负荷侧水平","ID":"454f7c2f-adcf-434e-885b-31001463e602"},{"name":"电机负荷侧垂直","ID":"6406b2e4-4c05-4310-8b6c-620519abf937"},{"name":"电机自由侧轴向","ID":"bd97598a-9e1d-4a57-ab80-da0df6a68617"},{"name":"进出口差压","ID":"117f6313-8e6a-4b52-9d70-fc99c2f7727a"},{"name":"入口一次风压","ID":"f8e73411-09be-4bf3-a85a-6185138903e5"},{"name":"入口一次风量(补偿后)","ID":"956e143b-844d-4c2a-b221-ffe14f10fe47"},{"name":"磨煤机电流","ID":"259d7fb9-20e2-4062-b9c4-ff9ce3f89d50"}]
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
         * name : 运行状态
         * ID : ebad60a3-fe99-4f25-aea9-e4e2d8f10e83
         */

        private String name;
        private String ID;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }}
