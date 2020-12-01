package domain.facility;

public class Toilet extends Utility {
    private int util;
    private int id;
    private String address;
    private double lat;
    private double lng;

    public Toilet() {

    }

    public Toilet(int util, int id, String address, double lat, double lng) {
        this.util = util;
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public int getUtil() {
        return util;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "toilet : " + this.id + " " + this.address;
    }
}
