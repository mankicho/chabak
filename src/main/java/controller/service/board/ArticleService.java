package controller.service.board;

import domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.ArticleRepository;
import util.DateUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Date;

@Controller
@RequestMapping(value = "/article")
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService() {
        articleRepository = new ArticleRepository();
    }

    @RequestMapping(value = "/write.do")
    public void write(Article article) {
        articleRepository.insert(article);
    }

    @RequestMapping(value = "/test.do")
    public void w() {
        try {
            Image image = ImageIO.read(new File("C:\\Users\\82102\\Desktop\\부엉이\\정보처리기사\\정처기 신청 영수증.JPG"));

            Article article = new Article("manki", "test title", "test content", image, DateUtil.simpleFormat(new Date()));
            articleRepository.insert(article);
        } catch (Exception e) {
        }
    }
}
