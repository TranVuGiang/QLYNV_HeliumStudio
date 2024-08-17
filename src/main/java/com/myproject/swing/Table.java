package com.myproject.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Table extends JTable {

    public Table() {
        initializeTable();
        setTableHeaderRenderer();
        setTableCellRenderer();
    }

    private void initializeTable() {
        setShowHorizontalLines(false);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        setAutoResizeMode(Table.AUTO_RESIZE_ALL_COLUMNS);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    private void setTableHeaderRenderer() {
        TableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value.toString());
                return header;
            }
        };
        getTableHeader().setDefaultRenderer(headerRenderer);
    }

    private void setTableCellRenderer() {
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                JLabel label = (JLabel) component;
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 18));
                label.setBorder(noFocusBorder);

                if (isSelected) {
                    component.setForeground(Color.WHITE);
                    component.setBackground(new Color(165, 116, 116));
                } else {
                    component.setForeground(Color.BLACK);
                    component.setBackground(Color.WHITE);
                }

                return component;
            }
        });

        // Custom renderer for Boolean values (JCheckBox)
        setDefaultRenderer(Boolean.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((value != null && (Boolean) value));
                checkBox.setHorizontalAlignment(JLabel.CENTER);
                checkBox.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 18));
                checkBox.setBackground(isSelected ? new Color(204, 204, 204) : Color.WHITE);
                checkBox.setForeground(isSelected ? Color.WHITE : Color.BLACK);
                return checkBox;
            }
        });
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
}
