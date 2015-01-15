/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anew;

/**
 *
 * @author Victor
 */
/**
 * com.sailok.util.ReceiptPrinter.java
 * <p>
 * This class provides a means to print a text or graphics message to
 * a receipt printer. It includes an option to pop up a JOptionPane
 * with a configurable message that will cause the program to wait for
 * the print to finish before continuing. It is fully copyrighted and
 * owned by the developer, SailOK Software, Oklahoma City, OK.
 * <p>
 * A license to use, executed by SailOK, is required to use the program
 * in any way. A source code license is also available. If you have a
 * source code license then you may modify the program in any way you
 * desire. Any such modifications, however, will not be covered under
 * any SailOK warranty. The program may not be resold, even if you
 * have made modifications to it.
 * <p>
 * Any other use of this program or any of its parts is unauthorized
 * and is punishable by law.
 * <p>
 * Created on May 2, 2007, 11:58 AM
 */
 

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.Color;
import java.awt.Graphics;
 
/**
 * This class provides a means to print a text or graphics message to
 * a receipt printer. It includes an option to pop up a JOptionPane
 * with a configurable message that will cause the program to wait for
 * the print to finish before continuing.
 * <p>
 * @author Bayless Kirtley
 */
public class ReceiptPrinter implements Printable {
    private JFrame printFrame;
    private String waitMsg;
 
/** Inner class for the actual printed object */
    class PrintFrame extends JFrame {
        PrintFrame(String msg) {
            //setBackground(new Color(255, 255, 255, 0));       
            add(new JLabel(msg));
            pack();
            setVisible(true);
        }
    }
 
    /** Creates a new instance of ReceiptPrinter with a default wait message */
    public ReceiptPrinter() {
        waitMsg = "Wait for the printer to finish\nClick the OK button when done";
    }
     
    /**
     * Creates a new instance of ReceiptPrinter with a wait message.
     *
     * @param   msg     the wait message
     */
    public ReceiptPrinter(String msg) {
        waitMsg = msg;
    }
 
    /**
     * Sends the actual message to the receipt printer - does not wait.
     *
     * @param   msg     the actual message to be printed
     */
    public void printIt(String msg) {
        printIt(msg, false);
    }
     
    /**
     * Sends the actual message to the receipt printer.
     *
     * @param   msg     the actual message to be printed
     * @param   wait    show JOptionPane to wait for print to finish
     */
    public void printIt(String msg, boolean wait) {
        printFrame = new PrintFrame(msg);
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat format = job.defaultPage();
        format.setOrientation(PageFormat.PORTRAIT);
        job.setPrintable(this, format);
        try {
            job.print();
            if (wait)
                JOptionPane.showMessageDialog(null, waitMsg, "Information",
                        JOptionPane.INFORMATION_MESSAGE);
        } catch (PrinterException e) {
            e.printStackTrace();
        }
        printFrame.dispose();
    }
 
    /**
     * Print method required by Printable interface.
     *
     * @param   g       the graphics context
     * @param   format  the page format
     * @param   pagenum the page number requested to print
     * @return  int     flag indicating page existance
     */
    public int print(Graphics g, PageFormat format, int pagenum) {
        if (pagenum > 0) return Printable.NO_SUCH_PAGE;
        g.translate((int) format.getImageableX(),
                (int) format.getImageableY());
        printFrame.paint(g);
        return Printable.PAGE_EXISTS;
    }
}