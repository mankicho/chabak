package domain.facility;

public class Toilet {
    private ToiletType type;
    private String address;
    private String openTime;
    private double latitude;
    private double longitude;

    public Toilet(ToiletType type, String address, String openTime, double latitude, double longitude) {
        this.type = type;
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ToiletType getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toString() {
        return address + "," + type;
    }
}
