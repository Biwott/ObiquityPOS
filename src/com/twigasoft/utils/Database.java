/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.utils;

import com.twigasoft.properties.SystemInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class Database {

    public static Connection getConnection() {
        SystemInfo inf = new SystemInfo();
        Properties props = inf.loadParams();
        Connection conn = null;
        String className = props.getProperty("db.classname");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String host = props.getProperty("db.host");
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(host, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }
}
