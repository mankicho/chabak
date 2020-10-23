package repository;

import database.DatabaseConnection;
import domain.Article;
import domain.Member;
import util.ConsoleUtil;
import util.DateUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleRepository {

    private String filePath = "C:\\Users\\82102\\Desktop\\java\\chabak_server\\src\\main\\resources\\";
    private Connection con;

    public ArticleRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.dbConnectError(e);
        }
    }


    public void insert(Article article) {
        File memberDir = new File(filePath + article.getMemberId());
        if (!memberDir.exists()) {
            memberDir.mkdir();
        }
        System.out.println(memberDir.toString());
        String imagePath = "";
        String today = DateUtil.simpleFormat(new Date());
//        if (article.getImage() != null) {
//            Image image = article.getImage();
//
//            imagePath = memberDir + "\\" + today;
//            System.out.println(imagePath);
//            try {
//                ImageIO.write((RenderedImage) image, "jpg", new File(imagePath));
//            } catch (IOException e) {
//                System.out.println("무슨에러냐? : " + e.getMessage());
//            }
//
//        }
        String query = "insert into cb_article values (?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, article.getMemberId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContent());
            pstmt.setString(4, imagePath);
            pstmt.setString(5, today);

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println("하 에러처리 ㅅㅂ");
        }


    }

    public Article selectOneArticle(String memberId, String createTime) {
        Article article = null;
        try {
            String query = "SELECT * FROM cb_article WHERE memberId = ? AND createTime = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, memberId);
            pstmt.setString(2, createTime);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String path = rs.getString(4);
                String cTime = rs.getString(5);

                article = new Article(id, title, content, path, cTime);
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
