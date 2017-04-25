package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class YCZDBean {
    /**
     * code : success
     * message : 读取数据成功
     * data : [{"id":"101","text":"国家电投河南公司","children":[{"id":"201","text":"平顶山分公司","children":[{"id":"301","text":"#1机","children":[{"id":"401","text":"汽机","children":null},{"id":"406","text":"锅炉","children":null},{"id":"407","text":"电气","children":null},{"id":"408","text":"环保","children":null}]},{"id":"302","text":"#2机","children":[{"id":"402","text":"汽机","children":null},{"id":"409","text":"锅炉","children":null},{"id":"410","text":"电气","children":null},{"id":"411","text":"环保","children":null}]}]},{"id":"202","text":"开封分公司","children":[{"id":"303","text":"#1机","children":[{"id":"403","text":"汽机","children":null},{"id":"412","text":"锅炉","children":null},{"id":"413","text":"电气","children":null},{"id":"414","text":"环保","children":null}]},{"id":"306","text":"#2机","children":[{"id":"422","text":"汽机","children":null},{"id":"423","text":"锅炉","children":null},{"id":"424","text":"电气","children":null},{"id":"425","text":"环保","children":null}]}]},{"id":"426","text":"平东热电","children":[{"id":"427","text":"#6机组","children":null},{"id":"428","text":"#7机组","children":null}]},{"id":"429","text":"豫新发电","children":[{"id":"430","text":"#6机组","children":null},{"id":"431","text":"#7机组","children":null}]},{"id":"432","text":"南阳热电","children":[{"id":"433","text":"#1机组","children":null},{"id":"437","text":"#2机组","children":null}]},{"id":"434","text":"郑州燃机","children":[{"id":"435","text":"#1机组","children":null},{"id":"436","text":"#2机组","children":null}]}]},{"id":"102","text":"国家电投东北公司","children":[{"id":"203","text":"燕山湖电厂","children":[{"id":"304","text":"#1机","children":[{"id":"404","text":"汽机","children":null},{"id":"415","text":"锅炉","children":null},{"id":"416","text":"电气","children":null},{"id":"417","text":"环保","children":null}]},{"id":"305","text":"#2机","children":[{"id":"418","text":"汽机","children":null},{"id":"419","text":"锅炉","children":null},{"id":"420","text":"电气","children":null},{"id":"421","text":"环保","children":null}]}]}]}]
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
         * id : 101
         * text : 国家电投河南公司
         * children : [{"id":"201","text":"平顶山分公司","children":[{"id":"301","text":"#1机","children":[{"id":"401","text":"汽机","children":null},{"id":"406","text":"锅炉","children":null},{"id":"407","text":"电气","children":null},{"id":"408","text":"环保","children":null}]},{"id":"302","text":"#2机","children":[{"id":"402","text":"汽机","children":null},{"id":"409","text":"锅炉","children":null},{"id":"410","text":"电气","children":null},{"id":"411","text":"环保","children":null}]}]},{"id":"202","text":"开封分公司","children":[{"id":"303","text":"#1机","children":[{"id":"403","text":"汽机","children":null},{"id":"412","text":"锅炉","children":null},{"id":"413","text":"电气","children":null},{"id":"414","text":"环保","children":null}]},{"id":"306","text":"#2机","children":[{"id":"422","text":"汽机","children":null},{"id":"423","text":"锅炉","children":null},{"id":"424","text":"电气","children":null},{"id":"425","text":"环保","children":null}]}]},{"id":"426","text":"平东热电","children":[{"id":"427","text":"#6机组","children":null},{"id":"428","text":"#7机组","children":null}]},{"id":"429","text":"豫新发电","children":[{"id":"430","text":"#6机组","children":null},{"id":"431","text":"#7机组","children":null}]},{"id":"432","text":"南阳热电","children":[{"id":"433","text":"#1机组","children":null},{"id":"437","text":"#2机组","children":null}]},{"id":"434","text":"郑州燃机","children":[{"id":"435","text":"#1机组","children":null},{"id":"436","text":"#2机组","children":null}]}]
         */

        private String id;
        private String text;
        private List<ChildrenBeanXX> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<ChildrenBeanXX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanXX> children) {
            this.children = children;
        }

        public static class ChildrenBeanXX {
            /**
             * id : 201
             * text : 平顶山分公司
             * children : [{"id":"301","text":"#1机","children":[{"id":"401","text":"汽机","children":null},{"id":"406","text":"锅炉","children":null},{"id":"407","text":"电气","children":null},{"id":"408","text":"环保","children":null}]},{"id":"302","text":"#2机","children":[{"id":"402","text":"汽机","children":null},{"id":"409","text":"锅炉","children":null},{"id":"410","text":"电气","children":null},{"id":"411","text":"环保","children":null}]}]
             */

            private String id;
            private String text;
            private List<ChildrenBeanX> children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<ChildrenBeanX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanX> children) {
                this.children = children;
            }

            public static class ChildrenBeanX {
                /**
                 * id : 301
                 * text : #1机
                 * children : [{"id":"401","text":"汽机","children":null},{"id":"406","text":"锅炉","children":null},{"id":"407","text":"电气","children":null},{"id":"408","text":"环保","children":null}]
                 */

                private String id;
                private String text;
                private List<ChildrenBean> children;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<ChildrenBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBean> children) {
                    this.children = children;
                }

                public static class ChildrenBean {
                    /**
                     * id : 401
                     * text : 汽机
                     * children : null
                     */

                    private String id;
                    private String text;
                    private Object children;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public Object getChildren() {
                        return children;
                    }

                    public void setChildren(Object children) {
                        this.children = children;
                    }
                }
            }
        }
    }
}
