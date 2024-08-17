package com.myproject.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomComboBox<E> extends JComboBox<E> {
    public CustomComboBox() {
        super();
        initUI();
    }

    public CustomComboBox(E[] items) {
        super(items);
        initUI();
    }

    private void initUI() {
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setUI(new CustomComboBoxUI());
        setBackground(Color.WHITE);
    }

    private class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(new Font("Segoe UI Semilight", Font.BOLD, 18));
            label.setForeground(Color.DARK_GRAY);
            label.setBackground(Color.WHITE);
            if (isSelected) {
                label.setBackground(Color.WHITE);
            }
            return label;
        }
    }

    private class CustomComboBoxEditor extends BasicComboBoxEditor {
        @Override
        protected JTextField createEditorComponent() {
            JTextField editor = new JTextField();
            editor.setFont(new Font("Segoe UI Semilight", Font.BOLD, 18));
            editor.setForeground(Color.DARK_GRAY);
            editor.setBackground(Color.WHITE); // Set background color to white
            editor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            return editor;
        }
    }

    private class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton();
            button.setIcon(new ImageIcon(getClass().getResource("/com/myproject/Image/down.png")));
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            return button;
        }
    }
}
