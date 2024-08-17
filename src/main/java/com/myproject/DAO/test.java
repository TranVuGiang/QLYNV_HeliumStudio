package com.myproject.DAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame {

    private JPanel panel;
    private JButton button;
    private Dimension originalSize; // Lưu trữ kích thước ban đầu của JPanel

    public test() {
        // Khởi tạo JPanel và nút
        panel = new JPanel();
        button = new JButton("Resize");

        // Đặt kích thước ban đầu cho JPanel
        panel.setPreferredSize(new Dimension(100, 100));
        originalSize = new Dimension(100, 100); // Lưu trữ kích thước ban đầu

        // Thêm nút vào JPanel
        panel.add(button);

        // Đặt JPanel vào JFrame
        getContentPane().add(panel);

        // Thêm sự kiện cho nút
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra xem JPanel đã được thay đổi kích thước chưa
                if (panel.getPreferredSize().equals(originalSize)) {
                    // Nếu chưa thay đổi kích thước, thay đổi kích thước
                    panel.setPreferredSize(new Dimension(200, 200));
                } else {
                    // Nếu đã thay đổi kích thước, đặt lại kích thước ban đầu
                    panel.setPreferredSize(originalSize);
                }
                pack();
                revalidate();
            }
        });
    }

    public static void main(String[] args) {
        test frame = new test();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}