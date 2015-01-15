/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class DescriptionRenderer extends DefaultTableCellRenderer {

    private final JPanel panel;
    private final JPanel panel1;
    private final JButton edit;
    private final JTextField field;

    public DescriptionRenderer() {
        panel1 = new JPanel();
        panel = new JPanel();
        field = new JTextField();
        field.setFont(new java.awt.Font("Tahoma", 1, 13));
        field.setEditable(false);
        edit = new JButton("Edit");
        edit.setFont(new java.awt.Font("Tahoma", 1, 11));
        panel.setLayout(new BorderLayout());
        panel.add(panel1, BorderLayout.EAST);
        panel.add(field, BorderLayout.CENTER);
        panel1.add(edit, BorderLayout.EAST);
        panel1.setLayout(new BorderLayout());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int height = table.getRowHeight();
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            field.setBackground(table.getSelectionBackground());
            field.setForeground(table.getBackground());
            field.setText(value.toString());
            table.setRowHeight(row, 30);
            return panel;
        } else {
            table.setRowHeight(row, height);
            setBackground(table.getBackground());
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

}
