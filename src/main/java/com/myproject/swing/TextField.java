package com.myproject.swing;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

public class TextField extends JTextField {
    private Shape shape;
    private final int arcWidth = 30;
    private final int arcHeight = 30;
    private final int padding = 10;

    public TextField() {
        customize();
    }

    private void customize() {
        setOpaque(true);
        setFont(new Font("Segoe UI Semilight", Font.PLAIN, 18));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setSelectionColor(new Color(184, 207, 229));
        setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2.dispose();
        super.paintComponent(g);
    }

}
