package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class xingbean {


    /**
     * code : 200
     * msg : success
     * datas : {"bikenumber":"171200885","distance":"0.50","time":"74","headimageurl":null,"money":"1.00","routeArray":[{"log":"114.01622715777","lat":"22.681603239472"},{"log":114.01612913493,"lat":22.681582943011}]}
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
         * bikenumber : 171200885
         * distance : 0.50
         * time : 74
         * headimageurl : null
         * money : 1.00
         * routeArray : [{"log":"114.01622715777","lat":"22.681603239472"},{"log":114.01612913493,"lat":22.681582943011}]
         */

        private String bikenumber;
        private String distance;
        private String time;
        private Object headimageurl;
        private String money;
        private List<RouteArrayBean> routeArray;

        public String getBikenumber() {
            return bikenumber;
        }

        public void setBikenumber(String bikenumber) {
            this.bikenumber = bikenumber;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getHeadimageurl() {
            return headimageurl;
        }

        public void setHeadimageurl(Object headimageurl) {
            this.headimageurl = headimageurl;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<RouteArrayBean> getRouteArray() {
            return routeArray;
        }

        public void setRouteArray(List<RouteArrayBean> routeArray) {
            this.routeArray = routeArray;
        }

        public static class RouteArrayBean {
            /**
             * log : 114.01622715777
             * lat : 22.681603239472
             */

            private String log;
            private String lat;

            public String getLog() {
                return log;
            }

            public void setLog(String log) {
                this.log = log;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
