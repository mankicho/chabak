package util;

public class ConsoleUtil {
    public static void exceptionPrint(Exception e) {
        System.out.println("에러 발생 ==> " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}
