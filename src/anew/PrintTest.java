/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anew;

/*
 * PrintTest.java
 *
 * Created on April 24, 2007, 12:19 PM
 */
 

 
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
 
public class PrintTest extends javax.swing.JFrame {
    private ReceiptPrinter receiptPrinter = new ReceiptPrinter();
    private FileOutputStream fos;
     
    public PrintTest() {
        initComponents();
        setPreferredSize(new Dimension(300, 200));
        pack();
        try {
    /*
     * Note: only one of the following should be used at any time
     * although both should be attempted separately
     * attempt to open printer by its Windows printer name
     */
//            fos = new FileOutputStream("Star TSP100 Cutter (TSP143)");
    // attempt to open printer by its USB port name
            fos = new FileOutputStream("USB002");
    // attempt to open LPT1 as a test of procedures only
//            fos = new FileOutputStream("LPT1");
        }
        catch (FileNotFoundException e) {
           JOptionPane.showMessageDialog(this, "Cannot open file\n" + e.getMessage(),
                   "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
     
    private void initComponents() {
        openButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();
        exitButton = new javax.swing.JButton();
 
        getContentPane().setLayout(null);
 
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        openButton.setFont(new java.awt.Font("Tahoma", 1, 11));
        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
 
        getContentPane().add(openButton);
        openButton.setBounds(151, 120, 80, 23);
 
        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);
 
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 220, 92);
 
        exitButton.setFont(new java.awt.Font("Tahoma", 1, 11));
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
 
        getContentPane().add(exitButton);
        exitButton.setBounds(10, 120, 70, 23);
 
        pack();
    }
 
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            fos.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to close printer port",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
        dispose();
    }
 
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // this section attempts to send the BEL character to the printer port
        byte bel = 0x07;
        try {
            fos.write(bel);
            fos.flush();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error trying to write\n" + e.getMessage(),
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    // this section appends the BEL to  the printed message and sends it to the Windows printer
        String msg = "test";
        msg += ((char) 0x07);
        receiptPrinter.printIt(msg);
    // this section displays a hex dump of the printed message
    // note that the BEL is being converted to a box and that
    // is what actually prints on the printer instead of the beep
        for (int i = 0; i < msg.length(); i += 5) {
            for (int j = 0; j < 5; j++) {
                if ((i + j) < msg.length()) {
                    int x = msg.charAt(i + j);
                    display.append(String.format("&#37;02x ", x));
                }
            }
            display.append("   ");
            for (int j = 0; j < 5; j++) {
                if (i + j < msg.length()) {
                    char c = msg.charAt(i + j);
                    display.append(String.format(" %c", c));
                }
            }
            display.append("\n");
        }
    }
     
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintTest().setVisible(true);
            }
        });
    }
     
    private javax.swing.JTextArea display;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
     
}