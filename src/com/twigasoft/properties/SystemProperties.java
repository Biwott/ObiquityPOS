package com.twigasoft.properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.SwingUtilities;

/**
 *
 * @author Victor
 */
public class SystemProperties {

    String serverAddr = "Hey";
    int serverPort = 10;
    int threadCnt = 1000;

    public SystemProperties() {
        loadParams();
    }

    public final void loadParams() {
        Properties props = new Properties();
        InputStream is = null;

        // First try loading from the current directory
        try {
            File f = new File(System.getProperty("user.home") + "/my_prop.xml");
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            is = null;
        }

        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream("server.properties");
            }

            // Try loading properties from the file (if found)
            props.loadFromXML(is);
        } catch (Exception e) {
        }

        System.err.println(props.getProperty("ServerAddress"));
        System.err.println(props.getProperty("ServerPort"));
        System.err.println(props.getProperty("ThreadCount"));

    }

    public void saveParamChanges() {
        try {
            Properties props = new Properties();
            props.setProperty("ServerAddress", serverAddr);
            props.setProperty("ServerPort", "" + serverPort);
            props.setProperty("ThreadCount", "" + threadCnt);
            File f = new File("server.properties");
            OutputStream out = new FileOutputStream(f);
            props.store(out, "This is an optional header comment string");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void saveParamChangesAsXML() {
        try {
            Properties props = new Properties();
            props.setProperty("ServerAddress", serverAddr);
            props.setProperty("ServerPort", "" + serverPort);
            props.setProperty("ThreadCount", "" + threadCnt);
            File f = new File(System.getProperty("user.home") + "/my_prop.xml");
            OutputStream out = new FileOutputStream(f);
            props.storeToXML(out, "This is an optional header comment string");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SystemProperties().saveParamChangesAsXML();
            }
        });

    }
}
