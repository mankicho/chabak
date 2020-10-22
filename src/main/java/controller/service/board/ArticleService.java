package controller.service.board;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import domain.Article;
import org.apache.axis.attachments.MultiPartDimeInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ArticleRepository;
import util.DateUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@RestController
@RequestMapping(value = "/article")
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService() {
        articleRepository = new ArticleRepository();
    }

    @RequestMapping(value = "/write.do")
    public String write(HttpServletRequest request) {
        String savePath = "C:\\Users\\82102\\Desktop\\" + DateUtil.simpleFormat(new Date());
        int sizeLimit = 10 * 1024 * 1024;
        try {
            MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
            String helloText = multi.getParameter("helloText");
            String helloFile = multi.getFilesystemName("helloFile");
            String originFileName = multi.getOriginalFileName("helloFile");
            String mimeType = multi.getContentType("helloFile");


            Enumeration<String> e = multi.getParameterNames();
            while(e.hasMoreElements()){
                String name = e.nextElement();
                System.out.println(name+":"+multi.getParameter(name));
            }
            return "success";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "fail";
        }
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
