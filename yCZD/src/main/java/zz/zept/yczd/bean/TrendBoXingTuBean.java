package zz.zept.yczd.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TrendBoXingTuBean {
    /**
     * limitHigh : 175
     * yLabel : μm
     * color : #0000FF
     * xLabel : 时间
     * legend : [[{"text":"轴振2X","color":"#0000FF","fontStyle":"12px 宋体"},{"text":"轴振4Y","color":"#faac4a","fontStyle":"12px 宋体"}]]
     * name : {"id":"bac13f29-008e-4678-86b7-a2b969c2ee7b","name":"轴振2X"}
     * xvalues : ["2016-10-17 10:14:03","2016-10-17 10:24:29","2016-10-17 10:35:32","2016-10-17 10:45:35","2016-10-17 10:56:35","2016-10-17 11:06:34","2016-10-18 19:13:40","2016-10-18 19:23:39","2016-10-18 19:34:08","2016-10-18 19:44:39","2016-10-18 19:55:11","2016-10-18 20:05:42","2016-10-18 20:15:42","2016-10-18 20:26:13","2016-10-18 20:36:42","2016-10-18 20:47:13","2016-10-18 20:57:13","2016-10-18 21:07:13","2016-10-18 21:17:44","2016-10-18 21:27:44","2016-10-18 21:37:41","2016-10-18 21:48:15","2016-10-18 21:58:44","2016-10-18 22:09:44","2016-10-18 22:19:47","2016-10-18 22:29:47","2016-10-18 22:40:47","2016-10-18 22:51:18","2016-10-18 23:01:18","2016-10-18 23:11:46","2016-10-18 23:22:15","2016-10-18 23:32:49","2016-10-18 23:43:18","2016-10-18 23:53:17","2016-10-19 00:03:49","2016-10-19 00:13:49","2016-10-19 23:59:37","2016-10-20 00:10:37","2016-10-20 00:21:08","2016-10-20 00:31:08","2016-10-20 00:41:08","2016-10-20 00:52:11","2016-10-20 01:02:39","2016-10-20 01:13:11","2016-10-20 01:23:08","2016-10-20 01:34:11","2016-10-20 01:44:39","2016-10-20 01:54:42","2016-10-20 02:05:13","2016-10-20 02:15:42","2016-10-20 23:58:05","2016-10-21 00:08:34","2016-10-21 00:18:33","2016-10-21 00:28:36","2016-10-21 00:39:36","2016-10-21 00:49:36","2016-10-21 01:00:05","2016-10-21 01:10:04","2016-10-21 01:21:07","2016-10-21 01:31:07","2016-10-21 01:41:07","2016-10-21 01:51:38","2016-10-21 02:01:38","2016-10-21 02:11:35","2016-10-21 02:21:38","2016-10-21 02:32:09","2016-10-21 02:42:38","2016-10-21 02:53:09","2016-10-21 03:03:09","2016-10-21 03:13:40","2016-10-21 03:24:09","2016-10-21 03:34:40","2016-10-21 03:45:12","2016-10-21 03:55:40","2016-10-21 04:05:37","2016-10-21 04:16:11","2016-10-21 04:26:11","2016-10-21 04:36:40"]
     * decimal : 2
     * yvalues : [63.5,64,65,63,63,66,65.5,66.5,64,68.5,65.5,63.5,68.5,68,67,65.5,68.5,65,68.5,65,66,70,65.5,64,66,66.5,64,65.5,68.5,67,62.5,67.5,66.5,65.5,69,64,66.5,65,63.5,65.5,64.5,66.5,66.5,68,69,67.5,68.5,70.5,69,68.5,66.5,67,63,66,66.5,65,66.5,63,62.5,65.5,63.5,69.5,64.5,65,65,66.5,63,64,64,66,62.5,67,66,65.5,61.5,66,64,64.5]
     * limitLow : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int limitHigh;
        private String yLabel;
        private String color;
        private String xLabel;
        /**
         * id : bac13f29-008e-4678-86b7-a2b969c2ee7b
         * name : 轴振2X
         */

        private NameBean name;
        private int decimal;
        private int limitLow;
        /**
         * text : 轴振2X
         * color : #0000FF
         * fontStyle : 12px 宋体
         */

        private List<List<LegendBean>> legend;
        private List<String> xvalues;
        private List<Float> yvalues;

        public int getLimitHigh() {
            return limitHigh;
        }

        public void setLimitHigh(int limitHigh) {
            this.limitHigh = limitHigh;
        }

        public String getYLabel() {
            return yLabel;
        }

        public void setYLabel(String yLabel) {
            this.yLabel = yLabel;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getXLabel() {
            return xLabel;
        }

        public void setXLabel(String xLabel) {
            this.xLabel = xLabel;
        }

        public NameBean getName() {
            return name;
        }

        public void setName(NameBean name) {
            this.name = name;
        }

        public int getDecimal() {
            return decimal;
        }

        public void setDecimal(int decimal) {
            this.decimal = decimal;
        }

        public int getLimitLow() {
            return limitLow;
        }

        public void setLimitLow(int limitLow) {
            this.limitLow = limitLow;
        }

        public List<List<LegendBean>> getLegend() {
            return legend;
        }

        public void setLegend(List<List<LegendBean>> legend) {
            this.legend = legend;
        }

        public List<String> getXvalues() {
            return xvalues;
        }

        public void setXvalues(List<String> xvalues) {
            this.xvalues = xvalues;
        }

        public List<Float> getYvalues() {
            return yvalues;
        }

        public void setYvalues(List<Float> yvalues) {
            this.yvalues = yvalues;
        }

        public static class NameBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class LegendBean {
            private String text;
            private String color;
            private String fontStyle;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getFontStyle() {
                return fontStyle;
            }

            public void setFontStyle(String fontStyle) {
                this.fontStyle = fontStyle;
            }
        }
    }
}
