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
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class DescriptionEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel;
    private final JPanel panel1;
    private final JButton edit;
    private final JTextField field;
    private String myValue;
    private Color foreColor;
    private Color backColor;

    public DescriptionEditor() {
        panel1 = new JPanel();
        panel = new JPanel();
        field = new JTextField();
        edit = new JButton("Edit");
        edit.setFont(new java.awt.Font("Tahoma", 1, 11));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setBackground(new Color(255, 255, 255));
                field.setForeground(new java.awt.Color(0, 102, 0));
                field.setEditable(true);
                field.setRequestFocusEnabled(true);
                field.setFocusable(true);
                field.requestFocus();
            }
        });
        field.setFont(new java.awt.Font("Tahoma", 1, 13));
        field.setForeground(new java.awt.Color(0, 102, 0));
        field.setEditable(false);
        field.setFocusable(false);
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!field.getText().equals("")) {
                    field.setBackground(backColor);
                    field.setForeground(foreColor);
                    myValue = field.getText();
                    field.setFocusable(false);
                    field.setRequestFocusEnabled(false);
                    field.setEditable(false);
                    stopCellEditing();
                }
            }
        });
        panel.setLayout(new BorderLayout());
        panel1.setLayout(new BorderLayout());
        panel.add(field, BorderLayout.CENTER);
        panel1.add(edit, BorderLayout.EAST);
        panel.add(panel1, BorderLayout.EAST);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {
        foreColor = table.getSelectionForeground();
        backColor = table.getBackground();
        if (value != null) {
            field.setText(value.toString());
            myValue = value.toString();
        }
        if (isSelected) {
            field.setBackground(table.getSelectionBackground());
            field.setForeground(table.getBackground());
            panel.setBackground(table.getSelectionBackground());
            return panel;
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;//super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    }

    @Override
    public Object getCellEditorValue() {
        return myValue;
    }

}
