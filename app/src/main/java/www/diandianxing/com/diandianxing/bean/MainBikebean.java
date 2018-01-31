package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MainBikebean {


    /**
     * code : 200
     * msg : success
     * datas : {"enclosure":[{"id":"1","longitude":"39.9845211043","latitude":"116.2884418246","surplusbike":"10"},{"id":"2","longitude":"39.9845211050","latitude":"116.2884418250","surplusbike":"3"},{"id":"3","longitude":"39.9845211041","latitude":"116.2884418247","surplusbike":"10"}],"bike":[{"id":"1","longitude":"39.9845211041","latitude":"116.2884418242","identifier":"1574871"}]}
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
        private List<EnclosureBean> enclosure;
        private List<BikeBean> bike;

        public List<EnclosureBean> getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(List<EnclosureBean> enclosure) {
            this.enclosure = enclosure;
        }

        public List<BikeBean> getBike() {
            return bike;
        }

        public void setBike(List<BikeBean> bike) {
            this.bike = bike;
        }

        public static class EnclosureBean {
            /**
             * id : 1
             * longitude : 39.9845211043
             * latitude : 116.2884418246
             * surplusbike : 10
             */

            private String id;
            private String longitude;
            private String latitude;
            private String surplusbike;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getSurplusbike() {
                return surplusbike;
            }

            public void setSurplusbike(String surplusbike) {
                this.surplusbike = surplusbike;
            }
        }

        public static class BikeBean {
            /**
             * id : 1
             * longitude : 39.9845211041
             * latitude : 116.2884418242
             * identifier : 1574871
             */

            private String id;
            private String longitude;
            private String latitude;
            private String identifier;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }
        }
    }
}
