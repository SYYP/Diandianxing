package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Shouyebean {


    /**
     * code : 200
     * msg : success
     * datas : {"uid":"1","token":"89ae099e9fa0cdc1b8aa296f510009db","nickName":"啊咯","contact":"18310482720","credit":"95","id_ident":"1","balance":"2.00","securityDeposit":"1.00","ridingState":"0","deposit_status":"4","fanbei":"0","credit_normal":"50","wechat":"","tencent_qq":"DF70E82323425B308C58D2ABEBB77DC7","cartStatus":"0","realType":"0","headImageUrl":"/server/uploads/head/20180306/5a9deef364f9a.jpg","bikeNumber":"","bikeDistance":"","bikeTime":"","isTemporary":"","tripId":""}
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
         * token : 89ae099e9fa0cdc1b8aa296f510009db
         * nickName : 啊咯
         * contact : 18310482720
         * credit : 95
         * id_ident : 1
         * balance : 2.00
         * securityDeposit : 1.00
         * ridingState : 0
         * deposit_status : 4
         * fanbei : 0
         * credit_normal : 50
         * wechat :
         * tencent_qq : DF70E82323425B308C58D2ABEBB77DC7
         * cartStatus : 0
         * realType : 0
         * headImageUrl : /server/uploads/head/20180306/5a9deef364f9a.jpg
         * bikeNumber :
         * bikeDistance :
         * bikeTime :
         * isTemporary :
         * tripId :
         */

        private String uid;
        private String token;
        private String nickName;
        private String contact;
        private String credit;
        private String id_ident;
        private String balance;
        private String securityDeposit;
        private String ridingState;
        private String deposit_status;
        private String fanbei;
        private String credit_normal;
        private String wechat;
        private String tencent_qq;
        private String cartStatus;
        private String realType;
        private String headImageUrl;
        private String bikeNumber;
        private String bikeDistance;
        private String bikeTime;
        private String isTemporary;
        private String tripId;

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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public String getDeposit_status() {
            return deposit_status;
        }

        public void setDeposit_status(String deposit_status) {
            this.deposit_status = deposit_status;
        }

        public String getFanbei() {
            return fanbei;
        }

        public void setFanbei(String fanbei) {
            this.fanbei = fanbei;
        }

        public String getCredit_normal() {
            return credit_normal;
        }

        public void setCredit_normal(String credit_normal) {
            this.credit_normal = credit_normal;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getTencent_qq() {
            return tencent_qq;
        }

        public void setTencent_qq(String tencent_qq) {
            this.tencent_qq = tencent_qq;
        }

        public String getCartStatus() {
            return cartStatus;
        }

        public void setCartStatus(String cartStatus) {
            this.cartStatus = cartStatus;
        }

        public String getRealType() {
            return realType;
        }

        public void setRealType(String realType) {
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

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }
    }
}
