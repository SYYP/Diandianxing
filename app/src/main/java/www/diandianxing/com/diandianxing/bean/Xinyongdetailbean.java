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
     * datas : [{"id":"2","uid":"1","num":"-10","reason":"恶意申报","add_time":"2017-11-29 06:11:25"},{"id":"1","uid":"1","num":"20","reason":"增加","add_time":"2017-11-22 14:17:20"}]
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
         * id : 2
         * uid : 1
         * num : -10
         * reason : 恶意申报
         * add_time : 2017-11-29 06:11:25
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
