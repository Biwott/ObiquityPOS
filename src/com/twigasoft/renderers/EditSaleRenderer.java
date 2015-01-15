/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class EditSaleRenderer extends DefaultTableCellRenderer {

    JPanel panel;
    JLabel label;

    public EditSaleRenderer() {
        panel = new JPanel();
        label = new JLabel();
        label.setFont(new java.awt.Font("Tahoma", 1, 12));
        label.setOpaque(true);
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setBackground(new java.awt.Color(0, 153, 51));
        panel.setLayout(new BorderLayout());
        panel.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            label.setText(value.toString());
            if (value.equals(" Return")) {
                label.setBackground(new java.awt.Color(0, 153, 51));
            } else if (value.equals(" Cancel")) {
                label.setBackground(new java.awt.Color(204, 0, 0));
            }
        }
//        if (isSelected) {
//            setBackground(table.getSelectionBackground());
//        } else {
//            setBackground(table.getBackground());
//        }
        return panel;
    }
}
