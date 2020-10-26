package repository;

import database.DatabaseConnection;
import domain.Chabak;
import domain.facility.FishingSpot;
import domain.facility.Toilet;
import repository.facility.FishingRepository;
import repository.facility.ToiletRepository;
import util.ConsoleUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class ChabakRepository {
    Connection connection;
    private final List<Chabak> chabaks;
    private final Map<Chabak, List<Toilet>> chabaksWithToilets;

    private final ToiletRepository toiletRepository;
    private final FishingRepository fishingRepository;
    private final Map<String, List<Toilet>> toiletsMap;
    private final Map<String, List<FishingSpot>> fishingsMap;
    private final int TOILET_BOUND = 500;


    public ChabakRepository() {
        chabaks = new ArrayList<>();
        chabaksWithToilets = new HashMap<>();
        toiletRepository = new ToiletRepository();
        fishingRepository = new FishingRepository();
        toiletsMap = toiletRepository.getGroupingToilets();
        fishingsMap = fishingRepository.getGroupingFishingSpots();
        try {
            connection = DatabaseConnection.get();
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * from cb_chabak_location"
            );
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                chabaks.add(new Chabak(rs.getString(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getDouble(8), rs.getDouble(9)));
            }
            chabaks.parallelStream().forEach(data -> {
                String add = data.getAddress();
                String[] tmp = add.split(" ");
                add = tmp[0] + " " + tmp[1];
                chabaksWithToilets.putIfAbsent(data, new ArrayList<>());
                List<Toilet> toilets = toiletsMap.get(add);

                if (toilets != null) {
                    toilets.forEach(t -> {
                        if (distance(t.getLatitude(), t.getLongitude(), data.getLatitude(), data.getLongitude()
                                , "meter") <= 500) {
                            chabaksWithToilets.get(data).add(t);
                        }
                    });
                }
            });
            System.out.println(chabaksWithToilets);

        } catch (SQLException e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public List<Chabak> searchByAddress(String query) {
        return chabaks.stream().filter(loc -> loc.getAddress().contains(query)).collect(Collectors.toList());
    }

    public List<Chabak> searchByKeyword(String keyword) {
        System.out.println(keyword);
        return chabaks.stream().filter(loc -> (loc.getAddress() + loc.getPlace_name() + loc.getUtility() + loc.getNotify()).contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Chabak> getChabaks(int num) {
        List<Chabak> returnList = new ArrayList<>();
        for (int i = num * 10; i < num * 10 + 10 && i < chabaks.size(); i++) {
            returnList.add(chabaks.get(i));
        }
        return returnList;
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


}
