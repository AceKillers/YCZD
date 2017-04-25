package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by aa on 2016/9/21.
 */

public class OtherReciverBean {
    /**
     * code : success
     * data : {"sysdept":[{"id":"29325188-aad8-4e93-b52d-fc0a9b121892","parentId":"9433c3cc-03a9-43b1-89f9-b6ddda1db827","name":"汽机"},{"id":"dddd4f0f-04b2-4196-b0fb-7a7e2f5cfbf8","parentId":"9433c3cc-03a9-43b1-89f9-b6ddda1db827","name":"锅炉"}],"sysuser":[{"username":"test1"},{"username":"ZYQ"},{"username":"SHH"}]}
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
         * id : 29325188-aad8-4e93-b52d-fc0a9b121892
         * parentId : 9433c3cc-03a9-43b1-89f9-b6ddda1db827
         * name : 汽机
         */

        private List<SysdeptBean> sysdept;
        /**
         * username : test1
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
            private String parentId;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
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
