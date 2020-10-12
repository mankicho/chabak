package util;

public class ConsoleUtil {
    public static void dbConnectError(Exception e) {
        System.out.println("DB 와의 연결에 실패했습니다.");
        throw new RuntimeException(e);
    }
}
