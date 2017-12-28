package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Mingxibean {

    /**
     * code : 200
     * msg : success
     * datas : [{"id":"3","type":"充值","uid":"3","gold":"1.00","pay_type":"微信","add_time":"2017-12-21 18:39:34"},{"id":"2","type":"消费","uid":"3","gold":"-1.00","pay_type":"微信","add_time":"2017-12-21 18:38:21"}]
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
         * id : 3
         * type : 充值
         * uid : 3
         * gold : 1.00
         * pay_type : 微信
         * add_time : 2017-12-21 18:39:34
         */

        private String id;
        private String type;
        private String uid;
        private String gold;
        private String pay_type;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
