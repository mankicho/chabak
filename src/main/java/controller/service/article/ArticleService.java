package controller.service.article;

import domain.Article;
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

    @RequestMapping(value = "/get.do")
    public List<Article> get(HttpServletRequest request) {
        System.out.println(getClass().getName()+" is called article");

        String num = request.getParameter("num");
        int n = Integer.parseInt(num);

        return articleRepository.get(n);
    }

    @RequestMapping(value = "/selectOne.do")
    public List<Article> select(HttpServletRequest request, HttpServletResponse response) {
        String articleId = request.getParameter("articleId");
        int id = Integer.parseInt(articleId);

        return articleRepository.selectOneArticle(id);
    }

    @RequestMapping(value = "/insert.do")
    public String insertArticle(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String isAttached = request.getParameter("isAttached"); // 사진보냈다 flag
        String createTime = request.getParameter("createTime");
        String fileName = request.getParameter("fileName");
        System.out.println(memberId + "," + title + "," + content + "," + createTime);
        String urlPath = "";
        if (!isAttached.equals("")) {
            urlPath = filePath + memberId + "/" + fileName;
        }

        return articleRepository.insert(memberId, title, content, urlPath, createTime);
    }


}
