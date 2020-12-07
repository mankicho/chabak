package domain;

import java.util.Map;

public class Chabak {
    private int placeId;
    private String placeName;
    private String address;
    private String phone_number;
    private String introduce;
    private String filePath;
    private int jjim;
    private double latitude;
    private double longitude;
    private double avg_point;
    private Map<String, Integer> utilityCount;

    public Chabak(int placeId, String placeName, String address, String phone_number,
                  String introduce, String filePath, int jjim, double latitude, double longitude,
                  double avg_point, Map<String, Integer> utilityCount) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.address = address;
        this.phone_number = phone_number;
        this.introduce = introduce;
        this.filePath = filePath;
        this.jjim = jjim;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avg_point = avg_point;
        this.utilityCount = utilityCount;
    }

    public int getPlaceId() { return placeId; }
    public String getPlaceName() { return placeName; }
    public String getAddress() { return address; }
    public String getPhone_number() { return phone_number; }
    public String getIntroduce() { return introduce; }
    public String getFilePath() { return filePath; }
    public int getJjim() { return jjim; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAvg_point() { return avg_point; }
    public Map<String, Integer> getUtilityCount() { return utilityCount; }
    /*
    private int placeId;
    private String placeName; // 장소 명
    private String address; // 주소
    private String phoneNumber;
    private String introduce; // 한 줄 소개
    private String filePath; // 사진 파일 경로
    private int jjim;  // 찜
    private double latitude; // 위도
    private double longitude; // 경도
    private boolean toiletFilter;
    private boolean fishingFilter;
*/

/*    private List<Utility> utils = new ArrayList<>();

    public void setUtils(List<Utility> utils) {
        this.utils = utils;
    }

    public int getId() {
        return id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getAddress() {
        return address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getJjim() {
        return jjim;
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

    public List<Utility> getUtils() {


        return utils;
    }

    public boolean isToiletFilter() {
        return toiletFilter;
    }

    public void setToiletFilter(boolean toiletFilter) {
        this.toiletFilter = toiletFilter;
    }

    public boolean isFishingFilter() {
        return fishingFilter;
    }

    public void setFishingFilter(boolean fishingFilter) {
        this.fishingFilter = fishingFilter;
    }

    public boolean hasAddress(String[] strs) {
        for (String str : strs) {
            if (address.contains(str)) {
                return true;
            }
        }
        return false;
    }*/
}
