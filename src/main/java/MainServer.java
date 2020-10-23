import database.DatabaseConnection;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

public class MainServer {
    public static void main(String[] args) throws Exception {
        Connection con = DatabaseConnection.get();


        File file = new File("C:\\Users\\82102\\Documents\\카카오톡 받은 파일\\전국공중화장실표준데이터.csv");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String line;

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        String query = "insert into cb_toilet values(?,?,?,?,?,?)";
        int num = 1;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            if (split.length < 6) {
                continue;
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, num);
            pst.setString(2, split[1]);
            pst.setString(3, split[2]);
            pst.setString(4, split[3]);
            pst.setString(5, split[4]);
            pst.setString(6, split[5]);
            pst.executeUpdate();
            num++;
            if(num % 100 == 0){
                System.out.println(num);
            }
        }

        bw.flush();

    }
}
