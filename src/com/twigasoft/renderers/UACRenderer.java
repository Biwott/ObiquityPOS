/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class UACRenderer extends DefaultTableCellRenderer {

    JPanel panel;
    JCheckBox checker;

    public UACRenderer() {
        panel = new JPanel();
        checker = new JCheckBox();
        checker.setOpaque(false);
        checker.setFont(new java.awt.Font("Tahoma", 1, 11));
        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(203, 203, 203)));
        panel.setLayout(new BorderLayout());
        panel.add(checker, BorderLayout.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            if (value.equals(1)) {
                checker.setSelected(true);
            } else {
                checker.setSelected(false);
            }
            checker.setText(value.equals(1) ? "Allowed" : "Denied");
        }
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
            checker.setForeground(table.getSelectionForeground());
        } else {
            panel.setBackground(table.getBackground());
            checker.setForeground(Color.BLACK);
        }

        return panel;
    }

}
