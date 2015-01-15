/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.NumUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class DiscountRenderer extends DefaultTableCellRenderer {

    JPanel panel;
    JButton add;
    JButton reduce;
    JTextField field;

    public DiscountRenderer() {
        panel = new JPanel();
        add = new JButton(" +  ");
        field = new JTextField();
        reduce = new JButton("  -  ");
        add.setMargin(new java.awt.Insets(2, 2, 2, 2));
        reduce.setMargin(new java.awt.Insets(2, 2, 2, 2));
        field.setFont(new java.awt.Font("Tahoma", 1, 13));
        field.setForeground(new java.awt.Color(0, 102, 0));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setLayout(new BorderLayout());
        panel.add(add, BorderLayout.EAST);
        panel.add(reduce, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int height = table.getRowHeight();
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            field.setText(NumUtils.removeSignStr(value.toString(), '%'));
            table.setRowHeight(row, 30);
            return panel;
        } else {
            table.setRowHeight(row, height);
            setBackground(table.getBackground());
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

}
