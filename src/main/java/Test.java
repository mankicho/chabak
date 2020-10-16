import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
        name = "FileUploadServlet",
        urlPatterns = {"/FileUploadTest"},
        loadOnStartup = 1
)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)
public class Test extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        try{
            request.setCharacterEncoding("UTF-8");
            Part part = request.getPart("image");
            String fileName = getFilename(part);
            String fileSavePath = "";
            if (!fileName.isEmpty()) {
                // 파일을 저장할 서버상의 경로
                fileSavePath = "C:\\uploadTest\\";
                part.write(fileSavePath+fileName);
            }
            writer.println("WritePost_OK");

            // 파일과 함께 넘어온 데이터 획득
            // DB에 삽입 해야함
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String createTime = request.getParameter("createTime");
            String image = fileSavePath + fileName; // 파일 업로드 안하면  ""

        } catch (Exception e){
            writer.println("Error");
            e.printStackTrace();
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

