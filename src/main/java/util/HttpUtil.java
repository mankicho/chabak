package util;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class HttpUtil {

    public static void writeToResponse(PrintWriter printWriter, String line) {
        printWriter.println(line);
    }
}
