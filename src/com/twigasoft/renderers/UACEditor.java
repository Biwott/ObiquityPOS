/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class UACEditor extends AbstractCellEditor implements TableCellEditor {

    private Object value;
    private JPanel panel;
    private JCheckBox checker;

    public UACEditor() {
        panel = new JPanel();
        checker = new JCheckBox();
        checker.setOpaque(false);
        checker.setFont(new java.awt.Font("Tahoma", 1, 11));
        checker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checker.isSelected()) {
                    value = 1;
                    stopCellEditing();
                } else {
                    value = 0;
                    stopCellEditing();
                }
                checker.setText(value.equals(1) ? "Allowed" : "Denied");
            }
        });
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                checker.doClick();
            }

        });
        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 0, 0)));
        panel.setLayout(new BorderLayout());
        panel.add(checker, BorderLayout.CENTER);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            if (value.equals(1)) {
                checker.setSelected(true);
            } else if (value.equals(0)) {
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
        this.value = value;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }
}
