/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anew;

import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.SimpleDoc;

public class OpenCashDrawer {

    PrinterJob printerJob;

    public OpenCashDrawer() {
        PrintService tsp100 = getPrintService("Adobe PDF");
        try {
            DocPrintJob job = tsp100.createPrintJob();
            String openDrawerCommand = ((char) 0x07) + "";
            byte by[] = openDrawerCommand.getBytes();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);
        } catch (PrintException e) {
            System.out.println("Whoa bro. The printer is balls. Check it:");
            Logger.getLogger(OpenCashDrawer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private PrintService getPrintService(String serviceName) {
        PrintService[] ps = PrinterJob.lookupPrintServices();
        for (int i = 0; i < ps.length; i++) {
            if (ps[i].getName().indexOf(serviceName) >= 0) {
                return ps[i];
            }
        }
        System.out.println("Aw SNAP! I like, can't find a printer with "
                + serviceName + " in the name dude.");
        System.exit(1);
        return null;
    }

    public static void main(String[] args) {
        OpenCashDrawer ocd = new OpenCashDrawer();
    }
}
