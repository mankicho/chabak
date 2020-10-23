package repository;

import crypto.CryptoUtil;
import database.DatabaseConnection;
import domain.Chabak;
import domain.Member;
import util.ConsoleUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    Connection con;

    public MemberRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.dbConnectError(e);
        }
    }

    public String insert(Member member) {
        try {
            String pw = CryptoUtil.encryptAES256(member.getPw(), member.getPw().hashCode() + "");
            String id = member.getId();
            String nickName = member.getNickName();
            PreparedStatement pstmt = con.prepareStatement("insert into cb_member values (?,?,?)");
            pstmt.setString(1, id);
            pstmt.setString(2, nickName);
            pstmt.setString(3, pw);
            pstmt.execute();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    public String select(String id, String pw) {
        String query = "select * from cb_member where memberId = ? AND password = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, CryptoUtil.encryptAES256(pw, pw.hashCode() + ""));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    public String update(String id, String nickName, String password) {
        try {
            String pw = CryptoUtil.encryptAES256(password, password.hashCode() + "");

            String query = "UPDATE cb_member SET nickname = ?, pw = ? WHERE userId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nickName);
            pstmt.setString(2, pw);
            pstmt.setString(3, id);

            pstmt.executeUpdate();

            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "false";
        }
    }

    public String jjimDo(String id, String placeName) {
        String query = "INSERT INTO cb_jjim_list values (?,?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, placeName);

            pstmt.execute();
            return "true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

    public String jjimUndo(String id, String placeName){
        String query = "DELETE FROM cb_jjim_list where id = ? AND chabak_name = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, placeName);

            pstmt.execute();
            return "true";
        } catch (SQLException e) {
            return "false";
        }
    }

    public List<Chabak> getJJimList(String id) {
        List<Chabak> result = new ArrayList<>();
        try {
            String query = "SELECT cb_chabak_location.*\" +\n" +
                    "                    \"from cb_jjim_list,cb_chabak_location\" +\n" +
                    "                    \"where id = ? AND cb_jjim_list.chabak_name = cb_chabak_location.placeName";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String placeName = rs.getString(1);
                String address = rs.getString(2);
                String utility = rs.getString(3);
                String notify = rs.getString(4);
                String introduce = rs.getString(5);
                String filePath = rs.getString(6);
                int jjim = rs.getInt(7);
                double latitude = rs.getDouble(8);
                double longitude = rs.getDouble(9);

                result.add(new Chabak(placeName, address, utility, notify, introduce, filePath, jjim, latitude, longitude));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
