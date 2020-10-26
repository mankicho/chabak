package domain.facility;

public class FishingSpot {
    private String name;
    private FishingType type;
    private String address;
    private double latitude;
    private double longitude;
    private String phoneNumber;

    public FishingSpot(String name, FishingType type, String address,
                       double latitude, double longitude, String phoneNumber) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public FishingType getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
       return name + "," + type + "," + address;
    }
}
