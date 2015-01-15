/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.NumUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class SubTotalEditor extends AbstractCellEditor implements TableCellEditor {
    
    private String myValue = null;
    private JPanel panel = null;
    
    private JTextField txtBox;
    
    public SubTotalEditor() {
        panel = new JPanel();
        
        txtBox = new JTextField();
        txtBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myValue = txtBox.getText();
                stopCellEditing();
            }
        });
        
        panel.setLayout(new BorderLayout());
        panel.add(txtBox, BorderLayout.CENTER);
    }
    
    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {
        myValue = NumUtils.removeSignStr(value.toString(), ',');
        if (value != null) {
            txtBox.setText(myValue);
        }
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return NumUtils.formatAmount(myValue);
    }
}
