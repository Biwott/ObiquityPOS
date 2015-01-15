/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.panels;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Victor
 */
public class DataTableSource implements JRDataSource {

    private int index = -1;
    private Object[][] data = null;

    public DataTableSource(JTable table) {
        data = getTableData(table);
    }

    public final Object[][] getTableData(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                tableData[i][j] = dtm.getValueAt(i, j);
                System.out.println(tableData[i][j] + ", ");
            }
            System.out.println("");
        }
        return tableData;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < data.length);
    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;
        String fieldName = field.getName();
        if ("prod_code".equals(fieldName)) {
            value = data[index][0];
        } else if ("desc".equals(fieldName)) {
            value = data[index][1];
        } else if ("qty".equals(fieldName)) {
            value = data[index][2];
        } else if ("unit_price".equals(fieldName)) {
            value = data[index][3];
        } else if ("line_total".equals(fieldName)) {
            value = data[index][4];
        }
        return value;
    }
}
