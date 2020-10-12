import crypto.CryptoUtil;
import util.DateUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Date;

public class MainServer {
    public static void main(String[] args) throws Exception {
        Image image = ImageIO.read(new File("C:\\Users\\82102\\Desktop\\부엉이\\정보처리기사\\정처기 신청 영수증.JPG"));
        String imagePath = "C:\\Users\\82102\\Desktop\\java\\chabak_server\\src\\main\\resources\\manki\\2020-10-12 15.46.jpg";
        ImageIO.write((RenderedImage) image, "jpg", new File(imagePath));

    }
}
