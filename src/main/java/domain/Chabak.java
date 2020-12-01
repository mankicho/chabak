package domain;

import domain.facility.Utility;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chabak {
    private int id;
    private String place_name; // 장소 명
    private String address; // 주소
    private String phoneNumber;
    private String introduce; // 한 줄 소개
    private String filePath; // 사진 파일 경로
    private int jjim;  // 찜
    private double latitude; // 위도
    private double longitude; // 경도
    private boolean toiletFilter;
    private boolean fishingFilter;

    private List<Utility> utils = new ArrayList<>();

    public Chabak(int id, String place_name, String address, String phoneNumber,
                  String introduce, String filePath, int jjim,
                  double latitude, double longitude) {
        this.id = id;
        this.place_name = place_name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.introduce = introduce;
        this.filePath = filePath;
        this.jjim = jjim;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
    }
}
