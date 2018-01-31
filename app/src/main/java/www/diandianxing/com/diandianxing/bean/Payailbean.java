package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Payailbean {


    /**
     * code : 200
     * msg : success
     * datas : {"ordersn":"20180112727127522","sign":"app_id=2018010401567031&biz_content=%7B%22body%22%3A%22%E5%A4%A7%E5%8D%83%E7%95%8C%E9%80%9A%22%2C%22subject%22%3A%22%E8%B4%A6%E6%88%B7%E4%BA%A4%E6%98%93%22%2C%22out_trade_no%22%3A%2220180112727127522%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22123%22%2C%22timeout_express%22%3A%2210m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.93.45.38%2Fserver%2Fapi.php%2FNotify%2FaliNotify&sign_type=RSA2&timestamp=2018-01-12+17%3A05%3A16&version=1.0&sign=ShvNwj3K%2B8GPymdXXicZUqUoX3d%2FhfftePg2%2FGnX7Cs5W6MkfLQEGvP3BcE4NQ4IpEyvhRaTLMk%2BFCyFEnDCg80hqsuHw1OsilI%2BA%2Fz%2BxDqbvuXB%2FSAWcXD4HcoGx0Q8q2rjtAUCdP2m62TcE2z6xu3Layfx8hoecPuzTemy0ryWJlHVXhoVVEjlA6wRS%2BXOsseOY51LIU%2BXuhRQRW89kYk702QzzpKauuDX%2Flf5o2KJlKlzRkfVSMvU5DWcy5%2BTF%2FmOMosTdMtlroXgsF%2Bn5UioDaA2pwcn%2BLtv%2BiwGfZFbuGQsEyeSITuaRO1o33q1LGTeUbr24SW4oMV513to4g%3D%3D"}
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
         * ordersn : 20180112727127522
         * sign : app_id=2018010401567031&biz_content=%7B%22body%22%3A%22%E5%A4%A7%E5%8D%83%E7%95%8C%E9%80%9A%22%2C%22subject%22%3A%22%E8%B4%A6%E6%88%B7%E4%BA%A4%E6%98%93%22%2C%22out_trade_no%22%3A%2220180112727127522%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22123%22%2C%22timeout_express%22%3A%2210m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.93.45.38%2Fserver%2Fapi.php%2FNotify%2FaliNotify&sign_type=RSA2&timestamp=2018-01-12+17%3A05%3A16&version=1.0&sign=ShvNwj3K%2B8GPymdXXicZUqUoX3d%2FhfftePg2%2FGnX7Cs5W6MkfLQEGvP3BcE4NQ4IpEyvhRaTLMk%2BFCyFEnDCg80hqsuHw1OsilI%2BA%2Fz%2BxDqbvuXB%2FSAWcXD4HcoGx0Q8q2rjtAUCdP2m62TcE2z6xu3Layfx8hoecPuzTemy0ryWJlHVXhoVVEjlA6wRS%2BXOsseOY51LIU%2BXuhRQRW89kYk702QzzpKauuDX%2Flf5o2KJlKlzRkfVSMvU5DWcy5%2BTF%2FmOMosTdMtlroXgsF%2Bn5UioDaA2pwcn%2BLtv%2BiwGfZFbuGQsEyeSITuaRO1o33q1LGTeUbr24SW4oMV513to4g%3D%3D
         */

        private String ordersn;
        private String sign;

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
