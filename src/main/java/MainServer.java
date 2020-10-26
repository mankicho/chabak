import database.DatabaseConnection;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

public class MainServer {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://studyplanet.kr/member/sms-auth");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("content-type", "application/json;charset=utf-8");
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//        connection.setRequestMethod("POST");
//        bw.write("phone=01028470440&");
//        bw.write("accessCode=C2A4D50CB9BF00320030003200300021");

        JSONObject object = new JSONObject();
        object.put("phone","01028470440");
        object.put("accessCode","C2A4D50CB9BF00320030003200300021");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(object.toString());


//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        System.out.println(connection.getResponseMessage());
        System.out.println(connection.getRequestMethod());
        InputStream is = connection.getErrorStream();
//
        int t;
//
        while ((t = is.read()) != -1) {
            System.out.print((char) t);
        }
//        accessCode"="C2A4D50CB9BF00320030003200300021"
        System.out.println(connection.getResponseCode());

//        Connection con = DatabaseConnection.get();
//
//
//        File file = new File("C:\\Users\\skxz1_000\\Desktop\\java\\" +
//                "chabak_file\\전국낚시터정보표준데이터.csv");
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            String[] split = line.split(",");
//            if (split.length < 6 || split[0].equals("") || split[1].equals("") ||
//                    split[2].equals("") || split[3].equals("") ||
//                    split[4].equals("") || split[5].equals("")) {
//                continue;
//            }
//
//            System.out.println(Arrays.toString(split));
//        }


    }
}
