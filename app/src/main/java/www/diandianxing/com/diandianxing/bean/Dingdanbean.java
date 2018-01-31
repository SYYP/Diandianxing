package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Dingdanbean {

    /**
     * code : 200
     * msg : success
     * datas : {"uid":"1","token":"ecde77cdf95cb6ab22aab17d45086d0e","nickName":"焦焦","credit":"80","id_ident":"1","balance":"0.00","securityDeposit":"0.00","ridingState":"0","realType":0,"headImageUrl":"","bikeNumber":"","bikeDistance":"","bikeTime":"","isTemporary":""}
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
         * uid : 1
         * token : ecde77cdf95cb6ab22aab17d45086d0e
         * nickName : 焦焦
         * credit : 80
         * id_ident : 1
         * balance : 0.00
         * securityDeposit : 0.00
         * ridingState : 0
         * realType : 0
         * headImageUrl :
         * bikeNumber :
         * bikeDistance :
         * bikeTime :
         * isTemporary :
         */

        private String uid;
        private String token;
        private String nickName;
        private String credit;
        private String id_ident;
        private String balance;
        private String securityDeposit;
        private String ridingState;
        private int realType;
        private String headImageUrl;
        private String bikeNumber;
        private String bikeDistance;
        private String bikeTime;
        private String isTemporary;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getId_ident() {
            return id_ident;
        }

        public void setId_ident(String id_ident) {
            this.id_ident = id_ident;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getSecurityDeposit() {
            return securityDeposit;
        }

        public void setSecurityDeposit(String securityDeposit) {
            this.securityDeposit = securityDeposit;
        }

        public String getRidingState() {
            return ridingState;
        }

        public void setRidingState(String ridingState) {
            this.ridingState = ridingState;
        }

        public int getRealType() {
            return realType;
        }

        public void setRealType(int realType) {
            this.realType = realType;
        }

        public String getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(String headImageUrl) {
            this.headImageUrl = headImageUrl;
        }

        public String getBikeNumber() {
            return bikeNumber;
        }

        public void setBikeNumber(String bikeNumber) {
            this.bikeNumber = bikeNumber;
        }

        public String getBikeDistance() {
            return bikeDistance;
        }

        public void setBikeDistance(String bikeDistance) {
            this.bikeDistance = bikeDistance;
        }

        public String getBikeTime() {
            return bikeTime;
        }

        public void setBikeTime(String bikeTime) {
            this.bikeTime = bikeTime;
        }

        public String getIsTemporary() {
            return isTemporary;
        }

        public void setIsTemporary(String isTemporary) {
            this.isTemporary = isTemporary;
        }
    }
}
