/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.Utils;

import java.awt.BorderLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.DimensionUIResource;

public class ImageHelper {
    private static JFrame frame = new JFrame("hello");

    public static void main(String[] args) {
        frame.setSize(500, 500);
        JLabel l = new JLabel();

        ImageIcon c = new ImageIcon(getPathFromImage("/images/khanh.png"));
        l.setIcon(resizeImageIcon(c, 1000, 1000));
        frame.add(l);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static String getPathFromImage(String imgname) {
        String imgFile = "images/" + imgname;
        String imgPath = "";
        if (imgname.startsWith("images/")) {
            imgFile = imgname;
        }
        if (imgname.startsWith("/images/")) {
            imgFile = imgname.substring(1);
        }

        try {
            // Lấy URL của tệp ảnh từ resources
            URL imageUrl = ImageHelper.class.getClassLoader().getResource(imgFile.trim());
            if (imageUrl == null) {
                return null;
            }
            String path = imageUrl.getPath();
            if (path.startsWith("/")) {
                imgPath = path.substring(1);
            } else {
                imgPath = path;
            }
            return imgPath;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

    }

    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        try {
            Image img = icon.getImage();
            Image reImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon reIcon = new ImageIcon(reImg);
            return reIcon;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
