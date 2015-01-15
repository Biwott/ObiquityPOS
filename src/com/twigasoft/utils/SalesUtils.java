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

/**
 *
 * @author Victor
 */
public class SalesUtils {

    public static int getIndexNo(String column) {
        int index = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT " + column + " FROM tbl_indexes WHERE id=? ";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, 1);
            rs = pst.executeQuery();
            while (rs.next()) {
                index = incrementIndex(rs.getInt(1), column);
            }
        } catch (SQLException e) {
            Logger.getLogger(SalesUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return index;
    }

    public static int incrementIndex(int index, String column) {
        int increment = index + 1;
        Connection conn = null;
        PreparedStatement pst = null;
        String sql = "UPDATE tbl_indexes SET  " + column + "=? WHERE id =?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, increment);
            pst.setInt(2, 1);
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SalesUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return increment;
    }
}
