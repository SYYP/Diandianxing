package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class backbean {


    /**
     * code : 96300
     * message : 结算成功
     * data : {"time":1203,"money":1,"log_id":1}
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
         * time : 1203
         * money : 1
         * log_id : 1
         */

        private int time;
        private int money;
        private int log_id;
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
        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getLog_id() {
            return log_id;
        }

        public void setLog_id(int log_id) {
            this.log_id = log_id;
        }
    }
}
