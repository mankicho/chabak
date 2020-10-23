<%--
  Created by IntelliJ IDEA.
  User: skxz1_000
  Date: 2020-10-15
  Time: ¿ÀÈÄ 10:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.io.*" contentType="text/html;charset=euc-kr" %>
<%!
    void sendImage(HttpServletResponse response, byte[] imgContentsArray) {
        ServletOutputStream svrOut = null;
        BufferedOutputStream outStream = null;
        try {
            svrOut = response.getOutputStream();
            outStream = new BufferedOutputStream(svrOut);
            outStream.write(imgContentsArray, 0, imgContentsArray.length);
            outStream.flush();
        } catch (Exception writeException) {
            writeException.printStackTrace();
        } finally {
            try {
                if (outStream != null) outStream.close();
            } catch (Exception closeException) {
                closeException.printStackTrace();
            }
        }
    }

    byte[] readImage() throws Exception {
        String filePath = "C:\\Users\\skxz1_000\\Desktop\\java\\chabak\\src\\main\\resources\\manki\\test.jpg";
        int BUF_SIZE;
        byte[] buf = null;
        DataInputStream in = null;
        try {

            File imgFile = new File(filePath);

            BUF_SIZE = (int) imgFile.length();

            buf = new byte[BUF_SIZE];

            in = new DataInputStream(new FileInputStream(imgFile));

            in.readFully(buf);

        } finally {
            in.close();
        }
        return buf;
    }

%><%
    try {
        sendImage(response, readImage());
    } catch (Exception e) {
        e.printStackTrace();
    }

%>

