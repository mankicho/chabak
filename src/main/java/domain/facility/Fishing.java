package domain.facility;

public class Fishing {
    private int fishingspotId;
    private String name;
    private String address;
    private String type;
    private String phone;
    private double latitude;
    private double longitude;

    public Fishing(int fishingspotId, String name, String address,
                   String type, String phone, double latitude, double longitude) {
        this.fishingspotId = fishingspotId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getFishingspotId() { return fishingspotId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getType() { return type; }
    public String getPhone() { return phone; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
