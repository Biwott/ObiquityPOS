/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.DataUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class TranferEditor extends AbstractCellEditor implements TableCellEditor {

    private Object myValue;
    private final JPanel panel;
    JComboBox combo;

    public TranferEditor() {
        panel = new JPanel();
        combo = new JComboBox();
        combo.setFont(new java.awt.Font("Tahoma", 1, 13));
        combo.setForeground(new java.awt.Color(0, 102, 0));
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myValue = combo.getSelectedItem();
            }
        });
       DataUtils.fillCombo(combo, "tbl_store_locations");
        panel.setLayout(new BorderLayout());
        panel.add(combo, BorderLayout.CENTER);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            combo.setSelectedItem(value);
        }
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());

        } else {

            panel.setBackground(table.getBackground());
        }
        myValue = value;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return myValue;
    }
}
