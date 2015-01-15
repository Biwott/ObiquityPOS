/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class CustomRenderer extends DefaultTableCellRenderer {
    JPanel panel;
    JButton button;
    JLabel label;
    public CustomRenderer(String buttonText) {
        panel = new JPanel();
        button = new JButton(buttonText);
        panel.setLayout(new BorderLayout());
        panel.add(button);
        button.setFont(new java.awt.Font("Tahoma", 1, 12));
        button.setMargin(new java.awt.Insets(2, 2, 2, 2));
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return panel;
    }
}
