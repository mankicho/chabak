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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

public class MainServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            while(true){
                System.out.println(serverSocket.accept().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
