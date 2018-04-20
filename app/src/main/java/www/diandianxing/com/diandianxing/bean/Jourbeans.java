package www.diandianxing.com.diandianxing.bean;

import java.util.List;

/**
 * Created by ASUS on 2018/3/26.
 */

public class Jourbeans {
    private List<databean> list;

    public List<databean> getList() {
        return list;
    }

    public void setList(List<databean> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return list.toString()
                ;
    }
}
