package com.twigasoft.models;
// new class. This is the table model


import com.twigasoft.utils.Database;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.*;

public class MyTableModel extends AbstractTableModel {

    private Connection conn;
    private Statement stmnt;
    private ResultSet rs;

    private Vector contents;
    private String columnNames[];
    private ResultSetMetaData metadata;
    private int columnCount = 0;

    public MyTableModel(String[] columns, String tblName) {
        refresh(columns, tblName);
    }

    public void refresh(String[] columns, String tblName) {
        conn = Database.getConnection();
        contents = new Vector();
        String sql = "SELECT " + processColumnames(columns) + " FROM " + tblName + ";";
        try {
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(sql);
            metadata = rs.getMetaData();

            columnCount = metadata.getColumnCount();
            while (rs.next()) {
                Vector rowData = new Vector();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.addElement(rs.getObject(i));
                }
                contents.addElement(rowData);
            }//end while
            columnNames = new String[metadata.getColumnCount()];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metadata.getColumnName(i + 1);
            }//end for
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
               
                stmnt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fireTableDataChanged();
    }

    public final String processColumnames(String[] columns) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i != columns.length - 1) {
                s.append(columns[i] + ", ");
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
        if (col == 0) {
            return false;
        }
        return true;
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
