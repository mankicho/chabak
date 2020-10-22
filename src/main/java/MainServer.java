import crypto.CryptoUtil;
import database.DatabaseConnection;
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
    public static void main(String[] args)  {

      try{
          DatabaseConnection.get();
      }catch (Exception e){
          e.printStackTrace();
      }
    }
}
