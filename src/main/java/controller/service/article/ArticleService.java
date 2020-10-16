package controller.service.article;

import domain.Article;
import org.apache.axis.utils.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import repository.ArticleRepository;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

@Controller
@RequestMapping(value = "/article")
public class ArticleService {

    ArticleRepository articleRepository = new ArticleRepository();

    @RequestMapping(value = "/selectOne.do")
    public void select(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String time = request.getParameter("response");

        Article article = articleRepository.selectOneArticle(id, time);

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("id=" + article.getMemberId() + "\n");
            printWriter.println("title=" + article.getTitle() + "\n");
            printWriter.println("content=" + article.getContent() + "\n");
            printWriter.println("urlPath=" + article.getUrlPath() + "\n");
            printWriter.println("createTime=" + article.getCreateTime() + "\n");

            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "insert.do")
    public void insertArticle(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String urlPath = request.getParameter("urlPath");
        String createTime = request.getParameter("createTime");
    }
}
