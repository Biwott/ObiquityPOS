/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.DataUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor
 */
public class TransferRenderer extends DefaultTableCellRenderer {

    JPanel panel;
    JComboBox combo;

    public TransferRenderer() {
        panel = new JPanel();
        combo = new JComboBox();
        panel.setLayout(new BorderLayout());
        panel.add(combo, BorderLayout.CENTER);
        DataUtils.fillCombo(combo, "tbl_store_locations");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            combo.setSelectedItem(value);
            return panel;
        } else {
            setBackground(table.getBackground());
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

}
