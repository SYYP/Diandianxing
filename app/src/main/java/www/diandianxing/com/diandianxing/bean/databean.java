package www.diandianxing.com.diandianxing.bean;

/**
 * Created by ASUS on 2018/3/24.
 */

public class databean {

    private String hours;
     private String money;
    private String log_id;
    private String lengthOfTime;


    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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

    public String getLengthOfTime() {
        return lengthOfTime;
    }

    public void setLengthOfTime(String lengthOfTime) {
        this.lengthOfTime = lengthOfTime;
    }

    @Override
    public String toString() {
        return
                 hours
              + money
             + log_id +
               lengthOfTime
               ;
    }
}
