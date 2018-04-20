package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Xinyongdetailbean {

    /**
     * code : 200
     * msg : success
     * datas : [{"id":"198","uid":"1","num":"5","reason":"故障申报信息真实","add_time":"2018-03-21 17:47:51"},{"id":"149","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-21 12:34:03"},{"id":"112","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-20 16:34:19"},{"id":"95","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-16 10:03:37"},{"id":"94","uid":"1","num":"1","reason":"骑行171200140","add_time":"2018-03-15 20:23:33"},{"id":"92","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-15 20:03:59"},{"id":"90","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-15 19:48:25"},{"id":"88","uid":"1","num":"-10","reason":"违规骑行171200140","add_time":"2018-03-15 19:33:28"},{"id":"74","uid":"1","num":"5","reason":"分享","add_time":"2018-03-14 09:40:44"},{"id":"60","uid":"1","num":"5","reason":"分享","add_time":"2018-03-13 11:59:51"}]
     */

    private int code;
    private String msg;
    private List<DatasBean> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 198
         * uid : 1
         * num : 5
         * reason : 故障申报信息真实
         * add_time : 2018-03-21 17:47:51
         */

        private String id;
        private String uid;
        private String num;
        private String reason;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
