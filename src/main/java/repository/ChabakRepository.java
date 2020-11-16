package repository;

import database.DatabaseConnection;
import domain.Chabak;
import domain.facility.Fishing;
import domain.facility.Toilet;
import domain.facility.Utility;
import repository.facility.FishingRepository;
import repository.facility.ToiletRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            String query = "select * from cb_chabak_location";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                String add = rs.getString(2);
                String intro = rs.getString(3);
                String fileP = rs.getString(4);
                int jjim = rs.getInt(5);
                double lat = rs.getDouble(6);
                double lng = rs.getDouble(7);
                String pNum = rs.getString(8);
                chabaks.add(new Chabak(name, add, intro, fileP, jjim, lat, lng, pNum));
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

            chabakWithUtility.entrySet().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Chabak> searchByAddress(String query) {
        return chabaks.stream().filter(loc -> loc.getAddress().contains(query)).collect(Collectors.toList());
    }

    public List<Chabak> searchByKeyword(String keyword) {
        System.out.println(keyword);
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
}
