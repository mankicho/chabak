package repository;

import database.DatabaseConnection;
import domain.Article;
import util.ConsoleUtil;
import util.DateUtil;

import javax.servlet.http.Part;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class ArticleRepository {

    private Connection con;

    public ArticleRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.dbConnectError(e);
        }
    }


    public String insert(String memberId, String title, String content, String path, String createTime) {

        String query = "insert into cb_article (memberId,title,content,imagePath,createTime) values (?,?,?,?,?) ";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, path);
            pstmt.setString(5, createTime);

            pstmt.execute();

            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("하 에러처리...");
            return "false";
        }
    }

    public List<Article> get(int num) {
        List<Article> result = new ArrayList<>();

        String query = "SELECT * FROM cb_article " +
                "WHERE articleId < ? AND isDeleted = 1 ORDER BY articleId DESC LIMIT 10";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, getNext() - ((num - 1) * 10));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int articleId = rs.getInt(1);
                String memberId = rs.getString(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                String imagePath = rs.getString(5);
                String createTime = rs.getString(6);

                result.add(new Article(articleId, memberId, title, content, imagePath, createTime));
            }

            return result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public int getNext() {
        String SQL = "SELECT articleId FROM cb_article ORDER BY articleId DESC";
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Article selectOneArticle(int articleId) {
        Article article = null;
        try {
            String query = "SELECT * FROM cb_article WHERE articleId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, articleId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int artId = rs.getInt(1);
                String id = rs.getString(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                String path = rs.getString(5);
                String cTime = rs.getString(6);

                article = new Article(artId, id, title, content, path, cTime);
            }
        } catch (SQLException e) {
            System.out.println("잘못된 요청입니다.");
        }

        return article;
    }

    public void update() {

    }

    public void delete(Article article) {
        String query = "delete from cb_article where memberId = ? AND createTime = ?";

    }

}
