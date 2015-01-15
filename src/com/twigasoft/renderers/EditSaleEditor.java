/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class EditSaleEditor extends AbstractCellEditor implements TableCellEditor {

    JPanel panel;
    JLabel label;
    Object value;

    public EditSaleEditor() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground().brighter().brighter(), 0, getHeight(),
                        getBackground().darker().darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight() / 2);
            }
        };
        label = new JLabel();
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setFont(new java.awt.Font("Tahoma", 1, 12));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (value.equals(" Return")) {
                    value = " Cancel";
                    stopCellEditing();
                } else if (value.equals(" Cancel")) {
                    value = " Return";
                    stopCellEditing();
                }
            }
        });
        panel.setBackground(new java.awt.Color(0, 153, 51));
        panel.setLayout(new BorderLayout());
        panel.add(label);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, int column) {
        if (value != null) {
            label.setText(value.toString());
            if(value.equals(" Return")){
            panel.setBackground(new java.awt.Color(0, 153, 51));
            }else if(value.equals(" Cancel")){
            panel.setBackground(new java.awt.Color(204, 0, 0));
            }
        }
        if (isSelected) {
            table.setBackground(table.getSelectionBackground());
        } else {
            table.setBackground(table.getBackground());
        }
        this.value = value;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }
}
