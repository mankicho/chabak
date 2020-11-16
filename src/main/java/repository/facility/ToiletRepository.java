package repository.facility;

import domain.facility.Toilet;
import util.ConsoleUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ToiletRepository {
    private final File toilet = new File("C:\\Users\\skxz1_000\\Desktop\\java\\chabak_file\\toilet.csv");

    private final List<Toilet> toiletList = new ArrayList<>();

    public ToiletRepository() {
        try {
            BufferedReader toiletBr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(toilet), StandardCharsets.UTF_8));
            toiletBr.readLine();
            String line;
            while ((line = toiletBr.readLine()) != null) {
                String[] split = line.split(",");
                toiletList.add(new Toilet(1,Integer.parseInt(split[0]), split[2],
                        Double.parseDouble(split[4]), Double.parseDouble(split[5])));
            }
        } catch (Exception e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public List<Toilet> getToiletList() {
        return toiletList;
    }
}
