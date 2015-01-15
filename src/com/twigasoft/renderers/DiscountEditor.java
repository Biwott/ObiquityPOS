/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import com.twigasoft.utils.NumUtils;
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
public class DiscountEditor extends AbstractCellEditor implements TableCellEditor {

    private int myValue;
    private JPanel panel;
    private JButton add;
    private JTextField field;
    private JButton reduce;

    public DiscountEditor() {
        panel = new JPanel();
        add = new JButton(" +  ");
        field = new JTextField();
        reduce = new JButton("  -  ");
        field.setFont(new java.awt.Font("Tahoma", 1, 13));
        field.setForeground(new java.awt.Color(0, 102, 0));
        add.setMargin(new java.awt.Insets(2, 2, 2, 2));
        reduce.setMargin(new java.awt.Insets(2, 2, 2, 2));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myValue < 100) {
                    myValue = myValue + 1;
                    field.setText(Integer.toString(myValue));
                    stopCellEditing();
                }
            }
        });

        reduce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myValue > 0) {
                    myValue = myValue - 1;
                    field.setText(Integer.toString(myValue));
                    stopCellEditing();
                }
            }
        });
        field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (field.getText().equals("")) {
                    myValue = 0;
                } else {
                    int val = Integer.parseInt(field.getText());
                    if (val > 0 && val < 100) {
                        myValue = val;
                    } else {
                        myValue = 0;
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
            field.setText(NumUtils.removeSignStr(value.toString(), '%'));
        }
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());

        } else {

            panel.setBackground(table.getBackground());
        }
        this.myValue = NumUtils.removeSignInt(value.toString(), '%');
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        String str = Integer.toString(myValue) + "%";
        return str;
    }
}
