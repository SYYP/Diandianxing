package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ss {


    /**
     * code : 93000
     * message : 违规停放
     * data : {"id":"1411","identifier":"171200770","address":"河北省廊坊市三河市燕顺路北务镇北杨庄村燕顺石化加油站西南","usage_num":"8","status":"6","longitude":"116.7862600","latitude":"40.0194300","mac":"B4A828058BB6","sim":"1064770760633","imei":"0868120190795358","signal":"4.00","electricity":"3.83","identification":"7f0000010ce600000033","add_time":"2018-01-12 07:00:37","down_time":null}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1411
         * identifier : 171200770
         * address : 河北省廊坊市三河市燕顺路北务镇北杨庄村燕顺石化加油站西南
         * usage_num : 8
         * status : 6
         * longitude : 116.7862600
         * latitude : 40.0194300
         * mac : B4A828058BB6
         * sim : 1064770760633
         * imei : 0868120190795358
         * signal : 4.00
         * electricity : 3.83
         * identification : 7f0000010ce600000033
         * add_time : 2018-01-12 07:00:37
         * down_time : null
         */

        private String id;
        private String identifier;
        private String address;
        private String usage_num;
        private String status;
        private String longitude;
        private String latitude;
        private String mac;
        private String sim;
        private String imei;
        private String signal;
        private String electricity;
        private String identification;
        private String add_time;
        private Object down_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUsage_num() {
            return usage_num;
        }

        public void setUsage_num(String usage_num) {
            this.usage_num = usage_num;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getSim() {
            return sim;
        }

        public void setSim(String sim) {
            this.sim = sim;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }

        public String getElectricity() {
            return electricity;
        }

        public void setElectricity(String electricity) {
            this.electricity = electricity;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public Object getDown_time() {
            return down_time;
        }

        public void setDown_time(Object down_time) {
            this.down_time = down_time;
        }
    }
}
