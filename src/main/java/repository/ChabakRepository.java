package repository;

import database.DatabaseConnection;
import domain.Article;
import domain.Chabak;
import domain.Review;
import domain.facility.Fishing;
import domain.facility.Toilet;
import domain.facility.Utility;
import filter.Filter;
import filter.FishingFilter;
import filter.ToiletFilter;
import repository.facility.FishingRepository;
import repository.facility.ToiletRepository;

//import javax.rmi.CORBA.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChabakRepository {
    private Connection con;
    private final List<Chabak> chabaks;
    private final ToiletRepository toiletRepository;
    private final FishingRepository fishingRepository;
    private final Map<Chabak, List<Utility>> chabakWithUtility;
    private final int TOILET_BOUND = 500;


    public ChabakRepository() {
        chabaks = new ArrayList<>();
        chabakWithUtility = new HashMap<>();
        toiletRepository = new ToiletRepository();
        fishingRepository = new FishingRepository();
        List<Toilet> toilets = toiletRepository.getToiletList();
        List<Fishing> fishings = fishingRepository.getFishingSpots();
        try {
            con = DatabaseConnection.get();

            String query = "select * from cb_chabak_location l natural join cb_chabak_location_filter f " +
                    "where f.placeId = l.placeId and l.user_suggest = 0;";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String add = rs.getString(3);
                String pNum = rs.getString(4);
                String intro = rs.getString(5);
                String fileP = rs.getString(6);
                int jjim = rs.getInt(7);
                double lat = rs.getDouble(8);
                double lng = rs.getDouble(9);
                if (lat < 30.0 || lng < 30.0) {
                    continue;
                }
                Chabak chabak = new Chabak(id, name, add, pNum, intro, fileP, jjim, lat, lng);
                boolean tBool = rs.getBoolean(10);
                boolean fBool = rs.getBoolean(10);
                chabak.setToiletFilter(tBool);
                chabak.setFishingFilter(fBool);
                chabaks.add(chabak);
            }
            chabaks.parallelStream().forEach(cha -> {
                chabakWithUtility.putIfAbsent(cha, new ArrayList<>());
                toilets.parallelStream().filter(to -> distance(cha.getLatitude(), cha.getLongitude(), to.getLat(),
                        to.getLng(), "meter") <= 500).filter(to -> {
                    List<Utility> list = chabakWithUtility.get(cha);
                    for (Utility utility : list) {
                        if (utility instanceof Toilet) {
                            Toilet t = (Toilet) utility;
                            if (distance(t.getLat(), t.getLng(), to.getLat(), to.getLng(), "meter") <= 10) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    return true;
                }).forEach(data -> cha.getUtils().add(data)); // cha 마다 조건에맞는 화장실 넣기

                fishings.parallelStream()
                        .filter(fi -> distance(cha.getLatitude(), cha.getLongitude(), fi.getLat(), fi.getLng(), "meter") <= 500)
                        .filter(fi -> {
                            List<Utility> list = chabakWithUtility.get(cha);
                            for (Utility utility : list) {
                                if (utility instanceof Fishing) {
                                    Fishing f = (Fishing) utility;
                                    if (distance(f.getLat(), f.getLng(), fi.getLat(), fi.getLng(), "meter") <= 10) {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            }
                            return true;
                        }).forEach(data -> cha.getUtils().add(data)); // 낚시터
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Chabak> searchByAddress(String[] query) {
        return chabaks.stream().filter(data -> data.hasAddress(query)).collect(Collectors.toList());
    }

    public List<Chabak> searchByKeyword(String keyword) {
        return chabaks.stream().filter(loc -> (loc.getAddress() + loc.getPlace_name()).contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Chabak> getChabaks(int num) {
        List<Chabak> returnList = new ArrayList<>();
        for (int i = num * 10; i < num * 10 + 10 && i < chabaks.size(); i++) {
            returnList.add(chabaks.get(i));
        }
        return returnList;
    }

    public List<Chabak> getChabaks() {
        return this.chabaks;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit.equals("kilometer")) {
            dist = dist * 1.609344;
        } else if (unit.equals("meter")) {
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public Map<Chabak, List<Utility>> getChabakWithUtility() {
        return chabakWithUtility;
    }

    /**
     * 차박지 평가 및 리뷰 작성
     */
    public int userEval(String memberId, int placeId, String placeName, double eval, String review) {
        String query = "INSERT INTO user_evaluation (memberId,placeId,placeName,evaluation_point,review_content)" +
                " VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE evaluation_point = ?, review_content = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, memberId);
            pstmt.setInt(2, placeId);
            pstmt.setString(3, placeName);
            pstmt.setDouble(4, eval);
            pstmt.setString(5, review);
            pstmt.setDouble(6, eval);
            pstmt.setString(7, review);

            return pstmt.executeUpdate(); // 성공
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패
    }

    /**
     * 차박지 등록하기
     */
    public String suggest(String placeName, String address, String introduce, String phone, String urlPath, double latitude, double longitude){

        String query = "insert into cb_chabak_location " +
                "(placeName, address, phone_number, introduce, filepath, latitude, longitude, user_suggest)" +
                " values (?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, placeName);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setString(4, introduce);
            pstmt.setString(5, urlPath);
            pstmt.setDouble(6, latitude);
            pstmt.setDouble(7, longitude);
            pstmt.setInt(8, 1);

            pstmt.execute();

            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fail to Suggest Chabakji");
            return "false";
        }
    }

    public List<Chabak> getFilteredList(String[] address, String[] flags) {
        FishingFilter fishingFilter = new FishingFilter();
        ToiletFilter toiletFilter = new ToiletFilter();
        return chabaks.stream()
                .filter(data -> {
                    boolean isFiltered = false;
                    List<Utility> utilities = data.getUtils();
                    System.out.println(data + " : " + utilities);
                    if (!data.hasAddress(address)) {
                        return false;
                    }

                    if (flags[0].equals("T")) {
                        if (!toiletFilter.filter(utilities)) {
                            return false;
                        }
                    }

                    if (flags[1].equals("T")) {
                        if (!fishingFilter.filter(utilities)) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

    }

    /**
     * 차박지별 등록된 리뷰 읽기
     */
    public List<Review> getReviews(int placeId){
        List<Review> reviewList = new ArrayList<>();
        try {
            String query = "SELECT placeId, nickName, review_content, evaluation_point, eval_time FROM review_view WHERE placeId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, placeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeID = rs.getInt(1);
                String nickName = rs.getString(2);
                String review_content = rs.getString(3);
                double evaluation_point = rs.getDouble(4);
                String eval_time = rs.getString(5);

                reviewList.add(new Review(placeID, nickName, review_content, evaluation_point, eval_time));
            }
            return reviewList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
