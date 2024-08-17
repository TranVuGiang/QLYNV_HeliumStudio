package com.myproject.swing;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DateChooser extends JDateChooser {
    private final int arcWidth = 15;
    private final int arcHeight = 15;
    private final int padding = 10;

    public DateChooser() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(null);
        setOpaque(false);
        
        // Đặt con trỏ chuột thành hình bàn tay khi di chuột qua
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thêm sự kiện focus để thay đổi màu nền khi focus
        getDateEditor().getUiComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(new Color(230, 230, 230));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }

    



}
