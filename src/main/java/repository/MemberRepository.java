package repository;

import crypto.CryptoUtil;
import database.DatabaseConnection;
import domain.Member;
import util.ConsoleUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {

    Connection con;

    public MemberRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.dbConnectError(e);
        }
    }

    public void insert(Member member) {
        try {
            String pw = CryptoUtil.encryptAES256(member.getPw(), member.getPw().hashCode() + "");
            String id = member.getId();
            String nickName = member.getNickName();
            PreparedStatement pstmt = con.prepareStatement("insert into cb_member values (?,?,?)");
            pstmt.setString(1, id);
            pstmt.setString(2, nickName);
            pstmt.setString(3, pw);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Member select(String id, String pw) throws Exception {
        String query = "select * from cb_member where userId = ? AND pw = ?";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, CryptoUtil.encryptAES256(pw, pw.hashCode() + ""));
        ResultSet rs = pstmt.executeQuery();
        System.out.println(CryptoUtil.encryptAES256(pw, pw.hashCode() + ""));

        if (rs.next()) {
            Member member = new Member(rs.getString(1), rs.getString(2), rs.getString(3));
            System.out.println(member);
            return member;
        }
        System.out.println("??");
        return new Member("", "", "");
    }
}
