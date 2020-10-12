package domain;

public class Chabak {
    private String place_name; // 장소 명
    private String address; // 주소
    private String utility; // 편의시설
    private String notify; // 주의사항
    private int totalVisitNum; // 총 방문 횟수
    private int todayVisitNum; // 오늘 방문 횟수
    private double latitude; // 위도
    private double longitude; // 경도

    public Chabak(String place_name, String address, String utility, String notify, int totalVisitNum, int todayVisitNum, double latitude, double longitude) {
        this.place_name = place_name;
        this.address = address;
        this.utility = utility;
        this.notify = notify;
        this.totalVisitNum = totalVisitNum;
        this.todayVisitNum = todayVisitNum;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public int getTotalVisitNum() {
        return totalVisitNum;
    }

    public void setTotalVisitNum(int totalVisitNum) {
        this.totalVisitNum = totalVisitNum;
    }

    public int getTodayVisitNum() {
        return todayVisitNum;
    }

    public void setTodayVisitNum(int todayVisitNum) {
        this.todayVisitNum = todayVisitNum;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.place_name;
    }
}
