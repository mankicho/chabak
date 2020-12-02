package repository;

import crypto.CryptoUtil;
import database.DatabaseConnection;
import domain.Chabak;
import domain.Review;
import domain.member.Member;
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
            ConsoleUtil.exceptionPrint(e);
        }
    }

    public String insert(Member member) {
        try {
            String pw = CryptoUtil.encryptAES256(member.getPw(), member.getPw().hashCode() + "");
            String id = member.getId();
            String nickName = member.getNickName();
            PreparedStatement pstmt = con.prepareStatement("insert into cb_member(memberId,nickName,password) values (?,?,?)");
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
        String query = "SELECT * FROM cb_member WHERE memberId = ? AND password = ? AND isDeleted = 0";

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

    /**
     * 닉네임 중복확인
     */
    public int nickDoubleCheck(String nickName) {
        String query = "SELECT nickName FROM cb_member WHERE nickName = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nickName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return 0; // 중복된 닉네임 존재
            }
            return 1; // 중복된 닉네임 없음
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 에러
        }
    }

    /**
     * 이메일(아이디) 중복확인
     */
    public int idDoubleCheck(String memberId) {
        String query = "SELECT memberId FROM cb_member WHERE memberId = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return 0; // 중복된 아이디 존재
            }
            return 1; // 중복된 아이디 없음
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 에러
        }
    }

    /**
     * 비밀번호 변경
     */
    public int changePassword(String memberId, String password) {
        try {
            String encrypted = CryptoUtil.encryptAES256(password, password.hashCode() + "");

            String query = "UPDATE cb_member SET password = ? WHERE memberId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, encrypted);
            pstmt.setString(2, memberId);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * 닉네임 변경
     */
    public int changeNickname(String memberId, String nickName) {
        try {
            String query = "UPDATE cb_member SET nickname = ? WHERE memberId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nickName);
            pstmt.setString(2, memberId);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int withdraw(String memberId){
        try {
            String query = "UPDATE cb_member SET isDeleted = 1 WHERE memberId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * 차박지 찜
     */
    public String jjimDo(String memberId, int placeId, String placeName) {
        String query = "INSERT INTO cb_jjim_list values (?,?,?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setInt(2, placeId);
            pstmt.setString(3, placeName);

            pstmt.execute();
            System.out.println("["+memberId+"]가 ["+placeId+"] 찜");
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 차박지 찜 취소
     */
    public String jjimUndo(String memberId, int placeId) {
        String query = "DELETE FROM cb_jjim_list where memberId = ? AND placeId = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setInt(2, placeId);

            pstmt.execute();
            System.out.println("["+memberId+"]가 ["+placeId+"] 찜 취소");
            return "success";
        } catch (SQLException e) {
            return "false";
        }
    }

    /**
     * 사용자의 차박지 찜 리스트 가져오기
     */
    public List<Chabak> getJJimList(String id) {
        List<Chabak> result = new ArrayList<>();
        try {
            String query = "SELECT c.* from cb_jjim_list l, cb_chabak_location c where memberId = ? AND l.placeId = c.placeId";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int chabakId = rs.getInt(1);
                String placeName = rs.getString(2);
                String address = rs.getString(3);
                String phoneNumber = rs.getString(4);
                String introduce = rs.getString(5);
                String filePath = rs.getString(6);
                int jjim = rs.getInt(7);
                double latitude = rs.getDouble(8);
                double longitude = rs.getDouble(9);

                result.add(new Chabak(chabakId, placeName, address, phoneNumber, introduce, filePath, jjim, latitude, longitude));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 사용자가 특정 차박지 찜 여부 확인
     */
    public String isJJim(String memberId, int placeId) {
        String query = "SELECT COUNT(*) as isJJim FROM cb_jjim_list WHERE memberId = ? AND placeId = ? GROUP BY memberId";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setInt(2, placeId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return String.valueOf(rs.getInt(1));
            }else{
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    /**
     * 사용자가 특정 차박지 평가 여부 확인
     */
    public String isEvaluated(String memberId, int placeId) {
        String query = "SELECT evaluation_point FROM user_evaluation WHERE memberId = ? AND placeId = ?;";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setInt(2, placeId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return String.valueOf(rs.getInt(1));
            }else{
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    /**
     * 사용자가 작성한 리뷰 가져오기
     */
    public List<Review> getUsersReview(String memberId) {
        List<Review> reviewList = new ArrayList<>();
        try {
            String query = " SELECT placeId, nickName, review_content, evaluation_point, eval_time FROM user_evaluation WHERE memberId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int placeID = rs.getInt(1);
                String nickName = rs.getString(2);
                String review_content = rs.getString(3);
                double evaluation_point = rs.getDouble(4);
                String eval_time = rs.getString(5);

                reviewList.add(new Review(placeID, nickName, review_content, evaluation_point, eval_time));
            }
            return reviewList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
