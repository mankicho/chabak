package controller.service.article;

import domain.Article;
import domain.Comment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ArticleRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/article")
public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepository();
    private final String filePath = "/resources/member/";

    /**
     * 게시글 리스트 읽기
     */
    @RequestMapping(value = "/get.do")
    public List<Article> get(HttpServletRequest request) {
        System.out.println(getClass().getName()+" is called article");

        String num = request.getParameter("num");
        int n = Integer.parseInt(num);

        return articleRepository.get(n);
    }

    /**
     * 게시글 하나에 대한 정보 읽기
     */
    @RequestMapping(value = "/selectOne.do")
    public List<Article> select(HttpServletRequest request, HttpServletResponse response) {
        String articleId = request.getParameter("articleId");
        int id = Integer.parseInt(articleId);

        return articleRepository.selectOneArticle(id);
    }

    /**
     * 게시글 작성
     */
    @RequestMapping(value = "/insert.do")
    public String insertArticle(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String isAttached = request.getParameter("isAttached"); // 사진보냈다 flag
        String fileName = request.getParameter("fileName");
        System.out.println(fileName + " ==> is called");
        System.out.println(memberId + "," + title + "," + content);
        String urlPath = "";
        if (!isAttached.equals("")) {
            urlPath = filePath + memberId + "/" + fileName;
        }

        return articleRepository.insert(memberId, title, content, urlPath);
    }

    /**
     * 댓글 리스트 읽기
     */
    @RequestMapping(value = "/getComments.do")
    public List<Comment> getComments(HttpServletRequest request) {
        return articleRepository.getComments(Integer.parseInt(request.getParameter("articleId")));
    }

    /**
     * 댓글 쓰기
     */
    @RequestMapping(value = "/writeComment.do")
    public int writeComment(HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String memberId = request.getParameter("memberId");
        String content = request.getParameter("content");
        return articleRepository.writeComment(articleId, memberId, content);
    }

    /**
     * 사용자별 작성한 게시글 읽기
     */
    @RequestMapping(value = "/getArticles.do")
    public List<Article> getArticles(HttpServletRequest request){
        return articleRepository.getArticles(request.getParameter("memberId"));
    }

}
