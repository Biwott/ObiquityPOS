/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Victor
 */
public class DataUtils {

    public static int getComboValue(JComboBox combo, String table) {
        int value = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE name = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, combo.getSelectedItem().toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
                pst.close();
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return value;
    }

    public int getTaxValue(String code) {

        int value = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT value FROM tbl_tax WHERE code=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, code);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException ex) {

            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return value;
    }

    public static int getComboValue2(JComboBox combo, String table) {
        String[] s = combo.getSelectedItem().toString().split(" ");
        if (s.length < 2) {
            return 0;
        } else {
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql = "SELECT id FROM " + table + " WHERE first_name = ? AND last_name=?";
            int value = 0;
            try {

                conn = Database.getConnection();
                pst = conn.prepareStatement(sql);
                pst.setString(1, s[0]);
                pst.setString(2, s[1]);
                rs = pst.executeQuery();
                while (rs.next()) {
                    value = rs.getInt(1);
                }
            } catch (SQLException e) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    rs.close();
                    pst.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return value;
        }

    }

    public static boolean checkExistance(String table, String column, String field) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE " + column + " = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return result;
    }

    public static boolean deleteRow(String table, String column, String field) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field);
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return result;
    }

    public static boolean deleteRow(String table, String column1, String column2, String field1, String field2) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String sql = "DELETE FROM " + table + " WHERE " + column1 + " = ? AND " + column2 + " =? ";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field1);
            pst.setString(2, field2);
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return result;
    }

    public static int getTableId(String table, String column, String field) {
        int value = -1;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE " + column + " = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return value;
    }

    public static int getTableId(String table, String column1, String field1, String column2, String field2) {
        int value = -1;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE " + column1 + " = ? AND " + column2 + " = ? ";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field1);
            pst.setString(2, field2);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return value;
    }

    public static boolean checkExistance2(String table, String field1, String field2) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE first_name = ? AND last_name=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, field1);
            pst.setString(2, field2);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return result;
    }

    public static String getFieldValue(String field, String table, int id) {

        String value = "";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT " + field + " FROM " + table + " WHERE id=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getString(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public static void fillCombo(JComboBox combo, String table) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT name FROM " + table;
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void fillCombo2(JComboBox combo, String table) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT first_name, last_name FROM " + table;
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString(1) + " " + rs.getString(2));
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static boolean addTableData(String table, String value) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String sql = "INSERT INTO " + table + " VALUES(?,?)";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, 0);
            pst.setString(2, value);
            pst.executeUpdate();
            status = true;
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public static int getReceiptId(int r_no) {
        int value = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM tbl_sales_transactions WHERE receipt_no=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r_no);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: "
                            + ((SQLException) e).getSQLState());

                    System.err.println("Error Code: "
                            + ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32")) {
            return true;
        }

        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55")) {
            return true;
        }

        return false;
    }
}
