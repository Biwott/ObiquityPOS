/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.main;

import com.twigasoft.models.IntelliModel;
import com.twigasoft.utils.FormUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor
 */
public final class IntelliSearch extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel1
     */
    String data;
    IntelliModel model;
    MainApp app;

    public IntelliSearch(String data, MainApp frame) {
        this.app = frame;
        this.data = data;
        model = new IntelliModel("");
        initComponents();
        table.setModel(model);
        refresh(data);
        scrollPane.getViewport().setBackground(Color.WHITE);
        FormUtils.setWidthAsPercentages(table, 0.20, 0.50, 0.30);
    }

    public void refresh(String thisData) {
        model.refresh(thisData);
        if (model.getRowCount() == 0) {
            app.popup.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new JScrollPane();
        table = new JTable();
        label = new JLabel();

        table.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        table.setModel(new DefaultTableModel(
            new Object [][] {
                {data, null, null, null},
                {null, data, null, null},
                {null, null, data, null},
                {null, null, null, data},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setEditingColumn(0);
        table.setEditingRow(0);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(20);
        table.setRowSelectionAllowed(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tableMouseClicked(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                tableMouseReleased(evt);
            }
        });
        scrollPane.setViewportView(table);

        label.setBackground(new Color(1, 119, 164));
        label.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        label.setForeground(new Color(255, 255, 255));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("Search Results...");
        label.setOpaque(true);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        table.setRowSelectionAllowed(true);
    }//GEN-LAST:event_tableMouseClicked

    private void tableMouseReleased(MouseEvent evt) {//GEN-FIRST:event_tableMouseReleased
        if (model.getRowCount() != 0 && app.popup.isVisible()) {
            app.fillTable(String.valueOf(model.getValueAt(table.getSelectedRow(), 0)));
        }
        table.setRowSelectionAllowed(true);
        app.popup.setVisible(false);
    }//GEN-LAST:event_tableMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public JLabel label;
    public JScrollPane scrollPane;
    public JTable table;
    // End of variables declaration//GEN-END:variables
}
