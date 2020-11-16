package repository.facility;

import domain.facility.Fishing;
import util.ConsoleUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FishingRepository {
    private final File fishing = new File("C:\\Users\\skxz1_000\\Desktop\\java\\chabak_file\\전국낚시터정보표준데이터.csv");

    private List<Fishing> fishingSpots;

    public FishingRepository() {
        fishingSpots = new ArrayList<>();
        try {
            BufferedReader fishingBr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fishing), StandardCharsets.UTF_8));
            String line;
            fishingBr.readLine();
            while ((line = fishingBr.readLine()) != null) {
                String[] split = line.split(",");
                fishingSpots.add(new Fishing(0, split[0], split[1], split[2],
                        Double.parseDouble(split[3]), Double.parseDouble(split[4])));
            }

        } catch (Exception e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public List<Fishing> getFishingSpots() {
        return fishingSpots;
    }
}
