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
public class AuthUtils {

    public static int getComboValue(JComboBox combo, String table) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM " + table + " WHERE name = ?";
        int value = 0;
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, combo.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            rs.close();
            pst.close();
            conn.close();
        }
        return value;
    }
}
