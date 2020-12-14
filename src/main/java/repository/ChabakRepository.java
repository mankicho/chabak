package repository;

import database.DatabaseConnection;
import domain.BestAndCount;
import domain.Chabak;
import domain.Review;
import domain.facility.Fishing;
import domain.facility.Toilet;
import util.ConsoleUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChabakRepository {
    private Connection con;

    public ChabakRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.exceptionPrint(e);
        }
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

    /**
     * 사용자가 설정한 조건에 따른 차박지 필터링
     */
    public List<Chabak> getFilteredList(String[] addresses, String[] flags) {
        List<Chabak> chabakList = new ArrayList<>();
        try {
            String queryPrefix = "SELECT * FROM chabak_info_view WHERE (";
            String querySuffix = parsingAddressQuery(addresses) + parsingFilterQuery(flags);

            System.out.println(queryPrefix + querySuffix);

            PreparedStatement pstmt = con.prepareStatement(queryPrefix + querySuffix);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeId = rs.getInt(1);
                int toiletCount = rs.getInt(2);
                int fishingCount = rs.getInt(3);
                String placeName = rs.getString(4);
                String address = rs.getString(5);
                String phone_number = rs.getString(6);
                String introduce = rs.getString(7);
                String filePath = rs.getString(8);
                int jjim = rs.getInt(9);
                double avg_point = rs.getDouble(10);
                double latitude = rs.getDouble(11);
                double longitude = rs.getDouble(12);

                Map<String, Integer> map = new HashMap<>();
                map.put("toilet", toiletCount);
                map.put("fishing", fishingCount);
                chabakList.add(new Chabak(placeId, placeName, address, phone_number, introduce,
                        filePath, jjim, latitude, longitude, avg_point, map));
            }
            return chabakList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 주소 파싱 및 SQL문 작성
     */
    public String parsingAddressQuery (String[] addresses){
        StringBuilder addressQuery = new StringBuilder();
        for(int i=0; i<addresses.length; i++){
            if(i==addresses.length-1){
                addressQuery.append("address LIKE '%").append(addresses[i]).append("%')");
            }else{
                addressQuery.append("address LIKE '%").append(addresses[i]).append("%' OR ");
            }
        }
        return addressQuery.toString();
    }

    /**
     * 필터 파싱 및 SQL문 작성
     */
    public String parsingFilterQuery (String[] flags){
        StringBuilder filterQuery = new StringBuilder();
        if(flags[0].equals("T")){ // 화장실
            filterQuery.append("AND toiletCount != 0 ");
        }
        if(flags[1].equals("T")){ // 낚시터
            filterQuery.append("AND fishingCount != 0");
        }
        return filterQuery.toString();
    }

    /**
     * 차박지별 등록된 리뷰 읽기
     */
    public List<Review> getReviews(int placeId){
        List<Review> reviewList = new ArrayList<>();
        try {
            String query = "SELECT * FROM review_view WHERE placeId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, placeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeID = rs.getInt(1);
                String memberID = rs.getString(2);
                String nickName = rs.getString(3);
                String review_content = rs.getString(4);
                double evaluation_point = rs.getDouble(5);
                String eval_time = rs.getString(6);

                reviewList.add(new Review(placeID, memberID, nickName, review_content, evaluation_point, eval_time));
            }
            return reviewList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 특별시, 광역시, 도 단위의 총 차박지 개수, 인기 있는 차박지 정보
     */
    public Map<String, BestAndCount> getBestAndCount(){
        Map<String, BestAndCount> map = new HashMap<>();
        try {
            String query = "SELECT * FROM cityProvince_bestAndCount";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cityProvince = rs.getString(1);
                String bestPlaceName = rs.getString(2);
                String bestPlaceImage = rs.getString(3);
                int count = rs.getInt(4);
                int numOfJJIM = rs.getInt(5);

                map.put(cityProvince, new BestAndCount(cityProvince,bestPlaceName,bestPlaceImage,count,numOfJJIM));
            }
            return map;
        } catch (SQLException e) {
            return new HashMap<>();
        }
    }

    /**
     * 모든 차박지 리스트
     */
    public List<Chabak> getAllChabakList(){
        List<Chabak> chabakList = new ArrayList<>();
        try {
            String query = "SELECT * FROM chabak_info_view;";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeId = rs.getInt(1);
                int toiletCount = rs.getInt(2);
                int fishingCount = rs.getInt(3);
                String placeName = rs.getString(4);
                String address = rs.getString(5);
                String phone_number = rs.getString(6);
                String introduce = rs.getString(7);
                String filePath = rs.getString(8);
                int jjim = rs.getInt(9);
                double avg_point = rs.getDouble(10);
                double latitude = rs.getDouble(11);
                double longitude = rs.getDouble(12);

                Map<String, Integer> map = new HashMap<>();
                map.put("toilet", toiletCount);
                map.put("fishing", fishingCount);
                chabakList.add(new Chabak(placeId, placeName, address, phone_number, introduce,
                        filePath, jjim, latitude, longitude, avg_point, map));
            }
            return chabakList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 하나의 차박지 정보
     */
    public List<Chabak> getOne(int placeId){
        List<Chabak> chabakList = new ArrayList<>();
        try {
            String query = "SELECT * FROM chabak_info_view WHERE placeId = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, placeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int placeID = rs.getInt(1);
                int toiletCount = rs.getInt(2);
                int fishingCount = rs.getInt(3);
                String placeName = rs.getString(4);
                String address = rs.getString(5);
                String phone_number = rs.getString(6);
                String introduce = rs.getString(7);
                String filePath = rs.getString(8);
                int jjim = rs.getInt(9);
                double avg_point = rs.getDouble(10);
                double latitude = rs.getDouble(11);
                double longitude = rs.getDouble(12);

                Map<String, Integer> map = new HashMap<>();
                map.put("toilet", toiletCount);
                map.put("fishing", fishingCount);
                chabakList.add(new Chabak(placeID, placeName, address, phone_number, introduce,
                        filePath, jjim, latitude, longitude, avg_point, map));
            }
            return chabakList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 현재 인기있는 차박지 리스트 (별점 기준 상위 10개)
     */
    public List<Chabak> getPopularList(){
        List<Chabak> chabakList = new ArrayList<>();
        try {
            String query = "SELECT * FROM chabak_info_view ORDER BY avg_point DESC LIMIT 10;";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeId = rs.getInt(1);
                int toiletCount = rs.getInt(2);
                int fishingCount = rs.getInt(3);
                String placeName = rs.getString(4);
                String address = rs.getString(5);
                String phone_number = rs.getString(6);
                String introduce = rs.getString(7);
                String filePath = rs.getString(8);
                int jjim = rs.getInt(9);
                double avg_point = rs.getDouble(10);
                double latitude = rs.getDouble(11);
                double longitude = rs.getDouble(12);

                Map<String, Integer> map = new HashMap<>();
                map.put("toilet", toiletCount);
                map.put("fishing", fishingCount);
                chabakList.add(new Chabak(placeId, placeName, address, phone_number, introduce,
                        filePath, jjim, latitude, longitude, avg_point, map));
            }
            return chabakList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 특별시, 광역시, 도 단위 차박지 리스트
     */
    public List<Chabak> getProvinceChabakList(String province){
        List<Chabak> chabakList = new ArrayList<>();
        try {
            String query = "SELECT v.* FROM chabak_info_view v, cb_chabak_location l " +
                    "WHERE v.placeId = l.placeId AND l.city_province = ? ORDER BY avg_point DESC";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, province);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeId = rs.getInt(1);
                int toiletCount = rs.getInt(2);
                int fishingCount = rs.getInt(3);
                String placeName = rs.getString(4);
                String address = rs.getString(5);
                String phone_number = rs.getString(6);
                String introduce = rs.getString(7);
                String filePath = rs.getString(8);
                int jjim = rs.getInt(9);
                double avg_point = rs.getDouble(10);
                double latitude = rs.getDouble(11);
                double longitude = rs.getDouble(12);

                Map<String, Integer> map = new HashMap<>();
                map.put("toilet", toiletCount);
                map.put("fishing", fishingCount);
                chabakList.add(new Chabak(placeId, placeName, address, phone_number, introduce,
                        filePath, jjim, latitude, longitude, avg_point, map));
            }
            return chabakList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 차박지별 화장실 정보
     */
    public List<Toilet> getToilets(int placeId){
        List<Toilet> toiletList = new ArrayList<>();
        try {
            String query = "SELECT t.toiletId, address, open_time, ST_X(geom), ST_Y(geom) " +
                    "FROM relation_chabak_and_toilet r, cb_toilet t " +
                    "WHERE r.toiletId = t.toiletId AND placeId = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, placeId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeID = rs.getInt(1);
                String address = rs.getString(2);
                String open_time = rs.getString(3);
                double latitude = rs.getDouble(4);
                double longitude = rs.getDouble(5);

                toiletList.add(new Toilet(placeID, address, open_time, latitude, longitude));
            }
            return toiletList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 차박지별 낚시터 정보
     */
    public List<Fishing> getFishings(int placeId){
        List<Fishing> fishingList = new ArrayList<>();
        try {
            String query = "SELECT f.fishingspotId, name, address, type, phone, ST_X(geom), ST_Y(geom) " +
                    "FROM relation_chabak_and_toilet r, cb_fishing f " +
                    "WHERE r.fishingspotId = f.fishingspotId AND placeId = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, placeId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int fishingspotId = rs.getInt(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                String type = rs.getString(4);
                String phone = rs.getString(5);
                double latitude = rs.getDouble(6);
                double longitude = rs.getDouble(7);

                fishingList.add(new Fishing(fishingspotId, name, address, type, phone, latitude, longitude));
            }
            return fishingList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
