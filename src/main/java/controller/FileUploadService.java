package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(
        name = "FileUploadServlet",
        urlPatterns = {"/file/upload.do"},
        loadOnStartup = 1
)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)
public class FileUploadService extends HttpServlet {
    private final String filePath = "C:\\Users\\skxz1_000\\Desktop\\java\\chabak\\web\\resources\\member\\";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String memberId = request.getParameter("id");
        System.out.println("fileUpLoadService is called ==> " + memberId);

        PrintWriter pw = resp.getWriter();
        try {
            File memberDir = new File(filePath + memberId);
            if (!memberDir.exists()) {
                memberDir.mkdir();
            }
            Part part = request.getPart("image");
            String fileName = getFilename(part);
            System.out.println("upload = " + fileName);
            String fileSavePath = filePath + memberId;
            if (!fileName.isEmpty()) {
                // 파일을 저장할 서버상의 경로
                part.write(fileSavePath + "\\" + fileName);

                pw.write("Success");
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.write("fail");
            pw.flush();
        }
    }

    private String getFilename(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] split = contentDisp.split(";");
        for (int i = 0; i < split.length; i++) {
            String temp = split[i];
            if (temp.trim().startsWith("filename")) {
                return temp.substring(temp.indexOf("=") + 2, temp.length() - 1);
            }
        }

        return "";
    }
}
