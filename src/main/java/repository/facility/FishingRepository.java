package repository.facility;

import database.DatabaseConnection;
import domain.facility.FishingSpot;
import domain.facility.FishingType;
import domain.facility.Toilet;
import domain.facility.ToiletType;
import util.ConsoleUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FishingRepository {
    private final String FILE_PATH = "C:\\Users\\skxz1_000\\Desktop\\java\\" +
            "chabak_file\\전국낚시터정보표준데이터.csv";

    private List<FishingSpot> fishingSpots;
    private Map<String, List<FishingSpot>> groupingFishingSpots = new HashMap<>();

    public FishingRepository() {
        fishingSpots = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length < 6 || split[0].equals("") || split[1].equals("") ||
                        split[2].equals("") || split[3].equals("") ||
                        split[4].equals("") || split[5].equals("")) {
                    continue;
                }
                String name = split[0];
                String type = split[1];
                String address = split[2];
                String latitude = split[3];
                String longitude = split[4];
                String phoneNumber = split[5];
                double lat;
                double lng;
                try {
                    lat = Double.parseDouble(latitude);
                    lng = Double.parseDouble(longitude);
                } catch (Exception e) {
                    continue;
                }
                String[] tmpSplit = address.split(" ");
                if (tmpSplit.length == 1) {
                    continue;
                }
                address = tmpSplit[0] + " " + tmpSplit[1];
                fishingSpots.add(new FishingSpot(name, FishingType.getFishingType(type), address, lat, lng, phoneNumber));
            }

            groupingFishingSpots = fishingSpots.stream().collect(Collectors.groupingBy(FishingSpot::getAddress));
        } catch (Exception e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public Map<String, List<FishingSpot>> getGroupingFishingSpots() {
        return groupingFishingSpots;
    }
}
