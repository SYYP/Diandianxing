package www.diandianxing.com.diandianxing.bean;

/**
 * Created by ASUS on 2018/3/12.
 */

public class Wetpaybean {
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
         * ordersn : 201803121032350483
         * sign : {"appid":"wx6d0a0a32729dfd2c","partnerid":"1499415322","prepayid":"wx20180312172744e6068e485c0471007698","package":"Sign=WXPay","noncestr":"izng0x66cqdw9v61l8d6bbhu94ixvvot","timestamp":1520846864,"sign":"3E0CDBC9E6B03CB8F614769B2217EC7A"}
         */

        private String ordersn;
        private SignBean sign;

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public SignBean getSign() {
            return sign;
        }

        public void setSign(SignBean sign) {
            this.sign = sign;
        }

        public static class SignBean {
            /**
             * appid : wx6d0a0a32729dfd2c
             * partnerid : 1499415322
             * prepayid : wx20180312172744e6068e485c0471007698
             * package : Sign=WXPay
             * noncestr : izng0x66cqdw9v61l8d6bbhu94ixvvot
             * timestamp : 1520846864
             * sign : 3E0CDBC9E6B03CB8F614769B2217EC7A
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
