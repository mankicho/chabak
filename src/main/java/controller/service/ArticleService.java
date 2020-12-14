package controller.service;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/article")
public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepository();
    private final String filePath = "/resources/article/";

    /**
     * 게시글 리스트 읽기
     */
    @RequestMapping(value = "/get.do")
    public List<Article> get(HttpServletRequest request) {
        System.out.println(getClass().getName() + " is called article");
        return articleRepository.get();
    }

    /**
     * 게시글 하나에 대한 정보 읽기
     */
    @RequestMapping(value = "/getArticle.do")
    public List<Article> getArticle(HttpServletRequest request) {
        return articleRepository.getArticle(Integer.parseInt(request.getParameter("articleId")));
    }

    /**
     * 게시글 작성
     */
    @RequestMapping(value = "/writeArticle.do")
    public int writeArticle(HttpServletRequest request) {
        String memberId = request.getParameter("memberId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String fileName = request.getParameter("fileName");
        System.out.println(fileName + " ==> is called");
        System.out.println(memberId + "," + title + "," + content);
        String urlPath = "";
        if (!fileName.equals("")) {
            urlPath = filePath + fileName;
        }

        return articleRepository.writeArticle(memberId, title, content, urlPath);
    }

    /**
     * 게시글 수정
     */
    @RequestMapping(value = "/updateArticle.do")
    public int updateArticle(HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String fileName = request.getParameter("fileName");
        String urlPath = "";
        if (!fileName.equals("")) {
            urlPath = filePath + fileName;
        }

        return articleRepository.updateArticle(articleId, title, content, urlPath);
    }

    /**
     * 게시글 삭제
     */
    @RequestMapping(value = "/deleteArticle.do")
    public int deleteArticle(HttpServletRequest request) {
        return articleRepository.deleteArticle(Integer.parseInt(request.getParameter("articleId")));
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
    public List<Article> getArticles(HttpServletRequest request) {
        return articleRepository.getArticles(request.getParameter("memberId"));
    }

    @RequestMapping(value = "/getArticleByKeyword.do")
    public List<Article> getArticlesByKeyword(HttpServletRequest request) {
        String key = request.getParameter("key");
        return articleRepository.get().stream().filter(article -> {
            String text = article.getContent() + article.getTitle();
            return text.contains(key);
        }).collect(Collectors.toList());
    }
}
