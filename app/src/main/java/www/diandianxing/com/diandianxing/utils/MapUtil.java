package www.diandianxing.com.diandianxing.utils;


import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Joe.
 */

public class MapUtil {
    private static MapUtil instance = new MapUtil();
    ArrayList<LatLng> latLngss = new ArrayList<>();

    private MapUtil() {
    }

    public static MapUtil getInstance() {
        return instance;
    }

    public ArrayList<LatLng> getNearbyll() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(40.046875, 116.287589));
        latLngs.add(new LatLng(40.04687145, 116.32315));
        latLngs.add(new LatLng(40.0468715, 116.75512));
        latLngs.add(new LatLng(23.1206380000, 113.3236220000));
        latLngs.add(new LatLng(23.1206280000, 113.3230540000));
        return latLngs;
    }

    public LatLng getMyll() {
        return new LatLng(40.046877, 116.287588);
    }
}
