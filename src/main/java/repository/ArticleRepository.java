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
        if (article.getImage() != null) {
            Image image = article.getImage();

            imagePath = memberDir + "\\" + today;
            System.out.println(imagePath);
            try {
                ImageIO.write((RenderedImage) image, "jpg", new File(imagePath));
            } catch (IOException e) {
                System.out.println("무슨에러냐? : " + e.getMessage());
            }

        }
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

    public void update() {

    }

    public void delete(Article article) {
        String query = "delete from cb_article where memberId = ? AND createTime = ?";

    }

    public List<Article> select(Member member) {
        List<Article> articles = new ArrayList<>();
        return articles;
    }
}
