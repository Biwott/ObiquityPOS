/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.FormUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class QuantityEditor extends AbstractCellEditor implements TableCellEditor {

    private int myValue;
    private JPanel panel = null;
    private JButton add = null;
    private JTextField field = null;
    private JButton reduce = null;
    JTable table;

    public QuantityEditor() {
        panel = new JPanel();
        add = new JButton(" +  ");
        field = new JTextField();
        reduce = new JButton("  -  ");
        add.setMargin(new java.awt.Insets(2, 2, 2, 2));
        reduce.setMargin(new java.awt.Insets(2, 2, 2, 2));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myValue >= 0) {
                    myValue = myValue + 1;
                    field.setText(Integer.toString(myValue));
                    stopCellEditing();
                }
            }
        });

        reduce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myValue > 1) {
                    myValue = myValue - 1;
                    field.setText(Integer.toString(myValue));
                    stopCellEditing();
                }
            }
        });
        field.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (field.getText().equals("")) {
                    myValue = 1;
                } else {
                    int val = Integer.parseInt(field.getText());
                    if (val > 0) {
                        myValue = val;           
                    }
                }
                stopCellEditing();
            }
        });
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormUtils.validateInteger(evt);
            }
        });
        field.setFont(new java.awt.Font("Tahoma", 1, 13));
        field.setForeground(new java.awt.Color(0, 102, 0));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setLayout(new BorderLayout());
        panel.add(add, BorderLayout.EAST);
        panel.add(reduce, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            field.setText(value.toString());
            this.table = table;
        }
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());

        } else {

            panel.setBackground(table.getBackground());
        }
        this.myValue = Integer.parseInt(value.toString());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return myValue;
    }
}
