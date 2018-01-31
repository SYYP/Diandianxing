package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Xingchengbean {


    /**
     * code : 200
     * msg : success
     * datas : {"list":[{"year":"2018","month":"01","day":"15","hours":"11:50","lengthOfTime":"23","money":"1.00","log_id":"652"},{"year":"2018","month":"01","day":"15","hours":"10:15","lengthOfTime":"455","money":"1.00","log_id":"630"},{"year":"2018","month":"01","day":"15","hours":"10:12","lengthOfTime":"122","money":"1.00","log_id":"629"},{"year":"2018","month":"01","day":"15","hours":"10:09","lengthOfTime":"124","money":"1.00","log_id":"626"},{"year":"2018","month":"01","day":"15","hours":"09:59","lengthOfTime":"126","money":"1.00","log_id":"624"},{"year":"2018","month":"01","day":"14","hours":"19:47","lengthOfTime":"627","money":"1.00","log_id":"613"},{"year":"2018","month":"01","day":"14","hours":"18:51","lengthOfTime":"29","money":"1.00","log_id":"574"},{"year":"2018","month":"01","day":"14","hours":"18:50","lengthOfTime":"30","money":"1.00","log_id":"573"},{"year":"2018","month":"01","day":"14","hours":"18:48","lengthOfTime":"21","money":"1.00","log_id":"572"},{"year":"2018","month":"01","day":"14","hours":"18:47","lengthOfTime":"28","money":"1.00","log_id":"571"},{"year":"2018","month":"01","day":"14","hours":"18:45","lengthOfTime":"23","money":"1.00","log_id":"570"},{"year":"2018","month":"01","day":"14","hours":"18:43","lengthOfTime":"28","money":"1.00","log_id":"569"},{"year":"2018","month":"01","day":"14","hours":"18:41","lengthOfTime":"24","money":"1.00","log_id":"568"},{"year":"2018","month":"01","day":"14","hours":"18:40","lengthOfTime":"29","money":"1.00","log_id":"567"},{"year":"2018","month":"01","day":"14","hours":"18:38","lengthOfTime":"39","money":"1.00","log_id":"564"},{"year":"2018","month":"01","day":"14","hours":"18:36","lengthOfTime":"28","money":"1.00","log_id":"563"},{"year":"2018","month":"01","day":"14","hours":"18:32","lengthOfTime":"44","money":"1.00","log_id":"560"},{"year":"2018","month":"01","day":"14","hours":"18:18","lengthOfTime":"36","money":"1.00","log_id":"558"},{"year":"2018","month":"01","day":"14","hours":"18:16","lengthOfTime":"19","money":"1.00","log_id":"557"},{"year":"2018","month":"01","day":"14","hours":"18:13","lengthOfTime":"28","money":"1.00","log_id":"556"}],"uinfo":{"mileage":"0.00","add_time":"2017-12-29 13:52:26","dayNum":"18"}}
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
         * list : [{"year":"2018","month":"01","day":"15","hours":"11:50","lengthOfTime":"23","money":"1.00","log_id":"652"},{"year":"2018","month":"01","day":"15","hours":"10:15","lengthOfTime":"455","money":"1.00","log_id":"630"},{"year":"2018","month":"01","day":"15","hours":"10:12","lengthOfTime":"122","money":"1.00","log_id":"629"},{"year":"2018","month":"01","day":"15","hours":"10:09","lengthOfTime":"124","money":"1.00","log_id":"626"},{"year":"2018","month":"01","day":"15","hours":"09:59","lengthOfTime":"126","money":"1.00","log_id":"624"},{"year":"2018","month":"01","day":"14","hours":"19:47","lengthOfTime":"627","money":"1.00","log_id":"613"},{"year":"2018","month":"01","day":"14","hours":"18:51","lengthOfTime":"29","money":"1.00","log_id":"574"},{"year":"2018","month":"01","day":"14","hours":"18:50","lengthOfTime":"30","money":"1.00","log_id":"573"},{"year":"2018","month":"01","day":"14","hours":"18:48","lengthOfTime":"21","money":"1.00","log_id":"572"},{"year":"2018","month":"01","day":"14","hours":"18:47","lengthOfTime":"28","money":"1.00","log_id":"571"},{"year":"2018","month":"01","day":"14","hours":"18:45","lengthOfTime":"23","money":"1.00","log_id":"570"},{"year":"2018","month":"01","day":"14","hours":"18:43","lengthOfTime":"28","money":"1.00","log_id":"569"},{"year":"2018","month":"01","day":"14","hours":"18:41","lengthOfTime":"24","money":"1.00","log_id":"568"},{"year":"2018","month":"01","day":"14","hours":"18:40","lengthOfTime":"29","money":"1.00","log_id":"567"},{"year":"2018","month":"01","day":"14","hours":"18:38","lengthOfTime":"39","money":"1.00","log_id":"564"},{"year":"2018","month":"01","day":"14","hours":"18:36","lengthOfTime":"28","money":"1.00","log_id":"563"},{"year":"2018","month":"01","day":"14","hours":"18:32","lengthOfTime":"44","money":"1.00","log_id":"560"},{"year":"2018","month":"01","day":"14","hours":"18:18","lengthOfTime":"36","money":"1.00","log_id":"558"},{"year":"2018","month":"01","day":"14","hours":"18:16","lengthOfTime":"19","money":"1.00","log_id":"557"},{"year":"2018","month":"01","day":"14","hours":"18:13","lengthOfTime":"28","money":"1.00","log_id":"556"}]
         * uinfo : {"mileage":"0.00","add_time":"2017-12-29 13:52:26","dayNum":"18"}
         */

        private UinfoBean uinfo;
        private List<ListBean> list;

        public UinfoBean getUinfo() {
            return uinfo;
        }

        public void setUinfo(UinfoBean uinfo) {
            this.uinfo = uinfo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class UinfoBean {
            /**
             * mileage : 0.00
             * add_time : 2017-12-29 13:52:26
             * dayNum : 18
             */

            private String mileage;
            private String add_time;
            private String dayNum;

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getDayNum() {
                return dayNum;
            }

            public void setDayNum(String dayNum) {
                this.dayNum = dayNum;
            }
        }

        public static class ListBean {
            /**
             * year : 2018
             * month : 01
             * day : 15
             * hours : 11:50
             * lengthOfTime : 23
             * money : 1.00
             * log_id : 652
             */

            private String year;
            private String month;
            private String day;
            private String hours;
            private String lengthOfTime;
            private String money;
            private String log_id;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getLengthOfTime() {
                return lengthOfTime;
            }

            public void setLengthOfTime(String lengthOfTime) {
                this.lengthOfTime = lengthOfTime;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }
        }
    }
}
