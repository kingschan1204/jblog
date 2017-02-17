package com.kingschan.blog.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImgWaterMark {
    
      /**   
     * 打印文字水印图片   
     *    
     * @param pressText 
     *            --文字   
     * @param targetImg --   
     *            目标图片   
     */   

     public static void pressText(String pressText, String targetImg) {    
            try {    
                File _file = new File(targetImg);    
                Image src = ImageIO.read(_file);    
                int wideth = src.getWidth(null);    
                int height = src.getHeight(null);    
                BufferedImage image = new BufferedImage(wideth, height,    
                        BufferedImage.TYPE_INT_RGB);    
                Graphics g = image.createGraphics();    
                g.drawImage(src, 0, 0, wideth, height, null);    
                   
                g.setColor(Color.white);
                g.fillRect(wideth-110, height-30, 100, 25);
                
                g.setColor(Color.red);  
                Font  f = new Font("微软雅黑", 20, 20);
                g.setFont(f );    
                g.drawString(pressText, wideth-110,height-12);
                
                g.dispose();    
                FileOutputStream out = new FileOutputStream(targetImg);    
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
                encoder.encode(image);    
                out.close();  
            } catch (Exception e) {    
                System.out.println(e);    
            }    
        } 
     
     
     
      /**   
         * 把图片印刷到图片上   
         *    
         * @param pressImg --   
         *            水印文件   
         * @param targetImg --   
         *            目标文件   
         * @param x   
         * @param y   
         */   
        public final static void pressImage(String pressImg, String targetImg,    
                int x, int y) {    
            try {    
                File _file = new File(targetImg);    
                Image src = ImageIO.read(_file);    
                int wideth = src.getWidth(null);    
                int height = src.getHeight(null);    
                BufferedImage image = new BufferedImage(wideth, height,    
                        BufferedImage.TYPE_INT_RGB);    
                Graphics g = image.createGraphics();    
                g.drawImage(src, 0, 0, wideth, height, null);    
       
                // 水印文件    
                File _filebiao = new File(pressImg);    
                Image src_biao = ImageIO.read(_filebiao);    
                int wideth_biao = src_biao.getWidth(null);    
                int height_biao = src_biao.getHeight(null);    
                g.drawImage(src_biao, wideth - wideth_biao - x, height    
                        - height_biao - y, wideth_biao, height_biao, null);    
                // /    
                g.dispose();    
                FileOutputStream out = new FileOutputStream(targetImg);    
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
                encoder.encode(image);    
                out.close();    
            } catch (Exception e) {    
                e.printStackTrace();    
            }    
        }    
}
