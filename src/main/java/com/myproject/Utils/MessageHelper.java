/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.Utils;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author anhba
 */
public class MessageHelper {
    public static void MessInfo(JFrame frame,String s){
        JOptionPane.showMessageDialog(frame, s, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void MessErr(JFrame frame,String s){
        JOptionPane.showMessageDialog(frame, s, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
        public static boolean MessConfirm(JFrame frame,String s){
        int op = JOptionPane.showConfirmDialog(frame, s);
        if(op == 0){
            return false;
        }else{
            return true;
        }
    }
        
        
        
    public static void showSuccessDialog(String filePath) {
// Tạo JButton "Mở File"
        JButton openButton = new JButton("Mở File");

        // Tạo JDialog chứa JOptionPane
        JOptionPane optionPane = new JOptionPane(
                "File Excel đã được xuất thành công!",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{openButton}, // Đặt nút vào JOptionPane
                openButton);

        JDialog dialog = optionPane.createDialog("Export Success");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(filePath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Không thể mở file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Đóng dialog sau khi mở file
                    dialog.dispose();
                }
            }
        });

        dialog.setVisible(true);
    }
}
