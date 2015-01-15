/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.renderers;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor
 */
public class DeleteEditor extends AbstractCellEditor implements TableCellEditor {

    public DeleteEditor(final DefaultTableModel model, final JTable table) {

    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return false;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, int column) {

        return new JButton( new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/delete.png")));
    }

    @Override
    public Object getCellEditorValue() {
        return new JButton( new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/delete.png")));
    }
}
