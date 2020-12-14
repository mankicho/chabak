package repository;

import database.DatabaseConnection;
import domain.Article;
import domain.Comment;
import util.ConsoleUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private Connection con;

    public ArticleRepository() {
        try {
            con = DatabaseConnection.get();
        } catch (SQLException e) {
            ConsoleUtil.exceptionPrint(e);
        }
    }

    /**
     * 게시글 작성
     */
    public int writeArticle(String memberId, String title, String content, String path) {
        try {
            String query = "insert into cb_article (memberId,title,content,imagePath) values (?,?,?,?) ";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, path);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 게시글 수정
     */
    public int updateArticle(int articleId, String title, String content, String path) {
        try {
            String query = "UPDATE cb_article SET title = ?, content = ?, imagePath = ? WHERE articleId = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, path);
            pstmt.setInt(4, articleId);

            return pstmt.executeUpdate(); // 성공시 변경된 행 수 리턴
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 게시글 삭제
     */
    public int deleteArticle(int articleId) {
        try {
            String query = "UPDATE cb_article SET isDeleted = 1 WHERE articleId = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, articleId);

            return pstmt.executeUpdate(); // 성공시 변경된 행 수 리턴
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 게시글 리스트 읽어오기
     */
    public List<Article> get() {
        List<Article> result = new ArrayList<>();

        String query = "SELECT * FROM article_view " +
                " ORDER BY articleId";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int articleId = rs.getInt(1);
                String memberId = rs.getString(2);
                String nickName = rs.getString(3);
                String title = rs.getString(4);
                String content = rs.getString(5);
                String imagePath = rs.getString(6);
                String createTime = rs.getString(7);

                result.add(new Article(articleId, memberId, nickName, title, content, imagePath, createTime));
            }

            System.out.println("result => " + result);
            return result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 페이징처리 위해 가장 마지막 articleId 가져오기
     */
    public int getNext() {
        String SQL = "SELECT articleId FROM cb_article ORDER BY articleId DESC";
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("getNext => " + rs.getInt(1) + 1);

                return rs.getInt(1) + 1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 게시글 하나 읽기
     */
    public List<Article> getArticle(int articleId) {
        List<Article> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM article_view WHERE articleId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, articleId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int artId = rs.getInt(1);
                String id = rs.getString(2);
                String nickName = rs.getString(3);
                String title = rs.getString(4);
                String content = rs.getString(5);
                String path = rs.getString(6);
                String cTime = rs.getString(7);

                list.add(new Article(artId, id, nickName, title, content, path, cTime));
            }
        } catch (SQLException e) {
            System.out.println("잘못된 요청입니다.");
            e.printStackTrace();
            return new ArrayList<>();
        }

        return list;
    }

    /**
     * 댓글 리스트 읽기
     */
    public List<Comment> getComments(int articleId) {
        List<Comment> commentList = new ArrayList<>();
        try {
            String query = "SELECT * FROM comment_view WHERE articleId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, articleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int commentId = rs.getInt(1);
                int articleID = rs.getInt(2);
                String memberId = rs.getString(3);
                String nickName = rs.getString(4);
                String content = rs.getString(5);
                String createTime = rs.getString(6);

                commentList.add(new Comment(commentId, articleID, memberId, nickName, content, createTime));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return commentList;
    }

    /**
     * 댓글 쓰기
     */
    public int writeComment(int articleId, String memberId, String content) {
        try {
            String query = "INSERT INTO article_comment (articleId, memberId, content) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, articleId);
            pstmt.setString(2, memberId);
            pstmt.setString(3, content);

            return pstmt.executeUpdate(); // 1
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // 에러
        }
    }

    /**
     * 사용자별 작성한 게시글 읽기
     */
    public List<Article> getArticles(String memberId) {
        List<Article> articleList = new ArrayList<>();
        try {
            String query = "SELECT * FROM article_view WHERE memberId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int articleId = rs.getInt(1);
                String memberID = rs.getString(2);
                String nickName = rs.getString(3);
                String title = rs.getString(4);
                String content = rs.getString(5);
                String imagePath = rs.getString(6);
                String createTime = rs.getString(7);

                articleList.add(new Article(articleId, memberID, nickName, title, content, imagePath, createTime));
            }
            return articleList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

}
