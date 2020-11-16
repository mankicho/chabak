package domain;

import domain.facility.Utility;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Chabak {
    private String place_name; // 장소 명
    private String address; // 주소
    private String introduce; // 한 줄 소개
    private String filePath; // 사진 파일 경로
    private int jjim;  // 찜
    private double latitude; // 위도
    private double longitude; // 경도
    private String phoneNumber;

    private List<Utility> utils = new ArrayList<>();

    public Chabak(String place_name, String address, String introduce,
                  String filePath, int jjim, double latitude, double longitude, String phoneNumber) {
        this.place_name = place_name;
        this.address = address;
        this.introduce = introduce;
        this.filePath = filePath;
        this.jjim = jjim;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
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
}
