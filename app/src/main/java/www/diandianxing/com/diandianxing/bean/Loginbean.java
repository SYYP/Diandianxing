package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Loginbean {

    /**
     * code : 200
     * msg : success
     * datas : {"id":"1","contact":"18713419469","type":"1","deposit":"0","token":"ecde77cdf95cb6ab22aab17d45086d0e"}
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
         * id : 1
         * contact : 18713419469
         * type : 1
         * deposit : 0
         * token : ecde77cdf95cb6ab22aab17d45086d0e
         */

        private String id;
        private String contact;
        private String type;
        private String deposit;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
