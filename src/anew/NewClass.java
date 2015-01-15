/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anew;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Victor
 */
public class NewClass {

    String serverAddr;
    int serverPort;
    int threadCnt;

    public NewClass() {
    }

    public void loadParams() {
        Properties props = new Properties();
        InputStream is = null;

        // First try loading from the current directory
        try {
            File f = new File("server.properties");
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
            props.load(is);
        } catch (Exception e) {
        }

        serverAddr = props.getProperty("ServerAddress", "192.168.0.1");
        serverPort = new Integer(props.getProperty("ServerPort", "8080"));
        threadCnt = new Integer(props.getProperty("ThreadCount", "5"));
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

    public void saveParamChangesAsXML() {
        try {
            Properties props = new Properties();
            props.setProperty("ServerAddress", serverAddr);
            props.setProperty("ServerPort", "" + serverPort);
            props.setProperty("ThreadCount", "" + threadCnt);
            File f = new File("server.xml");
            OutputStream out = new FileOutputStream(f);
            props.storeToXML(out, "This is an optional header comment string");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
