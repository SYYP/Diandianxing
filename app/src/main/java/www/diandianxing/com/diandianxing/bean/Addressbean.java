package www.diandianxing.com.diandianxing.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Addressbean implements Comparable<Addressbean> {

       private String name;//名字
    private String longitude;//经度
    private String latitude;//纬度
    private String City;
     private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object arg0) {
        // TODO Auto-generated method stub
        Addressbean p = (Addressbean) arg0;
        return name.equals(p.name) ;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        String str = name ;
        return str.hashCode();
    }

    @Override
    public int compareTo(Addressbean addressbean) {
        if(addressbean.num>this.num){
            return 1;
        }else if(addressbean.num>this.num){
            return 0;
        }else{
            return -1;
        }
    }
}
