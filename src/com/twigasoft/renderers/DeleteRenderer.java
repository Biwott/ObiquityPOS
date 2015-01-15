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
public class DeleteRenderer extends DefaultTableCellRenderer {
    JPanel panel;
    JButton button1;
    JLabel label;
    public DeleteRenderer() {
        panel = new JPanel();
        button1 = new JButton(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/delete.png")));
        panel.setLayout(new BorderLayout());
        panel.add(button1);
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
