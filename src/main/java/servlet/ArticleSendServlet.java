//package servlet;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(
//        name = "gy.do",
//        urlPatterns = {"/gy.do"},
//        loadOnStartup = 1
//)
//
//public class ArticleSendServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    resp.getWriter().println("HELLO");
//        System.out.println(req.getParameter("id"));
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.getWriter().println("HELLO");
//        System.out.println(req.getParameter("id"));
//    }
//}
