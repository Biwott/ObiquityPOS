/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Victor
 */
public class SystemInfo {

    public SystemInfo() {
    }

    public Properties loadParams() {
        Properties props = new Properties();
        InputStream is = null;
        // First try loading from the user home directory
        try {
            File f = new File(System.getProperty("user.dir") + "\\settings.xml");
            is = new FileInputStream(f);

        } catch (FileNotFoundException e) {
        }

        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream("/com/twigasoft/properties/settings.xml");
            }
            // Try loading properties from the file (if found)
            props.loadFromXML(is);
        } catch (IOException e) {
            Logger.getLogger(SystemInfo.class.getName()).log(Level.SEVERE, null, e);
        }

        return props;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SystemInfo inf = new SystemInfo();
                Properties props = inf.loadParams();
                System.err.println(props.getProperty("db.username"));
                System.err.println(props.getProperty("db.classname"));
                System.err.println(props.getProperty("db.host"));
            }
        });
    }
}
