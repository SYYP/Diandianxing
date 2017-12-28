package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Xinyongbean {

    /**
     * code : 200
     * msg : success
     * datas : {"credit":"80","mount":"10","month":"2017-11","evaluation_date":"2017-11-29"}
     */

    private int code;
    private String msg;
    private DatasBean datas;

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

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * credit : 80
         * mount : 10
         * month : 2017-11
         * evaluation_date : 2017-11-29
         */

        private String credit;
        private String mount;
        private String month;
        private String evaluation_date;

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getMount() {
            return mount;
        }

        public void setMount(String mount) {
            this.mount = mount;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getEvaluation_date() {
            return evaluation_date;
        }

        public void setEvaluation_date(String evaluation_date) {
            this.evaluation_date = evaluation_date;
        }
    }
}
