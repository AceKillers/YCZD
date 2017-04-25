package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by aa on 2016/9/21.
 */

public class TopReciverBean {
    /**
     * code : success
     * data : {"sysdept":[{"id":"cbaa66bb-d95e-4bb8-a094-25bda8662981","parentId":null,"name":"国家电投"}],"sysuser":[{"username":"spic"}]}
     * message : 读取数据成功
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
         * id : cbaa66bb-d95e-4bb8-a094-25bda8662981
         * parentId : null
         * name : 国家电投
         */

        private List<SysdeptBean> sysdept;
        /**
         * username : spic
         */

        private List<SysuserBean> sysuser;

        public List<SysdeptBean> getSysdept() {
            return sysdept;
        }

        public void setSysdept(List<SysdeptBean> sysdept) {
            this.sysdept = sysdept;
        }

        public List<SysuserBean> getSysuser() {
            return sysuser;
        }

        public void setSysuser(List<SysuserBean> sysuser) {
            this.sysuser = sysuser;
        }

        public static class SysdeptBean {
            private String id;
            private Object parentId;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class SysuserBean {
            private String username;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
