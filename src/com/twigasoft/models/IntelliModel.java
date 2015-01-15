package com.twigasoft.models;
// new class. This is the table model

import com.twigasoft.utils.NumUtils;
import com.twigasoft.utils.Database;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.*;

public final class IntelliModel extends AbstractTableModel {

    private Vector contents;
    private String columnNames[];
    private ResultSetMetaData metadata;
    private int columnCount = 0;

    public IntelliModel(String data) {
        refresh(data);
    }
    public int refresh(String data) {
        int barcode = -1;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        contents = new Vector();
        String sql;
        try {
            sql = "SELECT upc, item_name, trade_price FROM tbl_inventory"
                    + " WHERE item_name LIKE ? OR upc LIKE ?";
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + data + "%");
            pst.setString(2, data + "%");
            rs = pst.executeQuery();
            metadata = rs.getMetaData();
            columnCount = metadata.getColumnCount();
            while (rs.next()) {
                Vector rowData = new Vector();
                rowData.addElement(rs.getObject(1).toString());
                rowData.addElement(rs.getObject(2));
                rowData.addElement(NumUtils.getCurrency()
                        + NumUtils.formatAmount(rs.getObject(3).toString())
                );
                contents.addElement(rowData);
                if (rs.getString(1).equals(data)) {
                    barcode = Integer.parseInt(data);
                } else {
                    barcode = -1;
                }

            }//end while

            columnNames = new String[metadata.getColumnCount()];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metadata.getColumnName(i + 1);
            }//end for

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(IntelliModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        fireTableDataChanged();

        return barcode;
    }

    public final String processColumnames(String[] columns) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i != columns.length - 1) {
                s.append(columns[i]).append(", ");
            } else {
                s.append(columns[i]);
            }

        }
        return s.toString();
    }

    public String processHeaders(String s) {
        if (s.equals("id")) {
            s = "Id #";
        }
        StringBuilder result = new StringBuilder(s.length());
        String[] words = s.split("_");
        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));

        }
        return result.toString();
    }

    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {

        return false;
    }

    @Override
    public String getColumnName(int col) {
        return processHeaders(columnNames[col]);
    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int arow, int col) {
        Vector value = (Vector) contents.elementAt(arow);
        return value.elementAt(col);
    }

    @Override
    public void setValueAt(Object aValue, int aRow, int aCol) {
        Vector dataRow = (Vector) contents.elementAt(aRow);
        dataRow.setElementAt(aValue, aCol);
        fireTableCellUpdated(aRow, aCol);
    }

}
