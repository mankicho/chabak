package repository.facility;

import domain.facility.Toilet;
import domain.facility.ToiletType;
import util.ConsoleUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ToiletRepository {
    private final String FILE_PATH = "C:\\Users\\skxz1_000\\Desktop\\java\\chabak_file\\전국공중화장실표준데이터.csv";
    private final List<Toilet> toilets = new ArrayList<>();
    private Map<String, List<Toilet>> groupingToilets = new HashMap<>();

    public ToiletRepository() {
        try {
            File file = new File(FILE_PATH);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length < 6 || split[4].equals("") || split[5].equals("")) {
                    continue;
                }
                String type = split[1];
                String address = split[2];
                String openTime = split[3];
                String latitude = split[4];
                String longitude = split[5];

                String[] tmpSplit = address.split(" ");
                if (tmpSplit.length == 1) {
                    continue;
                }
                address = tmpSplit[0] + " " + tmpSplit[1];
                double lat;
                double lng;
                try {
                    lat = Double.parseDouble(latitude);
                    lng = Double.parseDouble(longitude);
                } catch (Exception e) {
                    continue;
                }
                toilets.add(new Toilet(ToiletType.getInstance(type),
                        address, openTime, lat, lng));
            }
            groupingToilets = toilets.stream().collect(Collectors.groupingBy(Toilet::getAddress));


        } catch (Exception e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public Map<String, List<Toilet>> getGroupingToilets() {
        return groupingToilets;
    }
}
