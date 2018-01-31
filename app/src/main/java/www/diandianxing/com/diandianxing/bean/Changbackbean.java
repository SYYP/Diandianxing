package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Changbackbean {

    /**
     * code : 200
     * msg : success
     * datas : {"addTime":"2018-1-5 10:19:30","identnum":7551002192}
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
         * addTime : 2018-1-5 10:19:30
         * identnum : 7551002192
         */

        private String addTime;
        private long identnum;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public long getIdentnum() {
            return identnum;
        }

        public void setIdentnum(long identnum) {
            this.identnum = identnum;
        }
    }
}
