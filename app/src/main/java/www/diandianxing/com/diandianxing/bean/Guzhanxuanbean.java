package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Guzhanxuanbean {

    /**
     * code : 200
     * msg : success
     * datas : [{"id":"1","name":"二维码遗失"},{"id":"2","name":"踏板坏了"},{"id":"3","name":"座椅被偷"}]
     */

    private int code;
    private String msg;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 1
         * name : 二维码遗失
         */

        private String id;
        private String name;
        private  boolean aBoolean;

        public boolean isaBoolean() {
            return aBoolean;
        }

        public void setaBoolean(boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
