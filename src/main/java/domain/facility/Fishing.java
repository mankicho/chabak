package domain.facility;

public class Fishing extends Utility {
    private int util;
    private String name;
    private String type;
    private String address;
    private double lat;
    private double lng;

    public Fishing(int util, String name, String type, String address, double lat, double lng) {
        this.util = util;
        this.name = name;
        this.type = type;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public int getUtil() {
        return util;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
        return "Fishing : " + this.name + " " + this.type + " " + this.address;
    }

}
