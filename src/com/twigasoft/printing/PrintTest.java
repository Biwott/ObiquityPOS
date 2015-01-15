/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.printing;

/**
 *
 * @author Victor
 */
import java.awt.print.PrinterJob;
import java.io.*;
import javax.print.*;

public class PrintTest {

    public static void main(String[] args) throws IOException {
        //we are going to print "printtest.txt" file which is inside working directory  
        File file = new File("printtest.txt");
        InputStream is = new BufferedInputStream(new FileInputStream(file));

        //Discover the default print service. If you call PrintServiceLookup.lookupPrintServices  
        //then it will return an array of print services available and you can choose a  
        //printer from them  
        PrintService service = getPrintService("Adobe PDF");

        //Doc flavor specifies the output format of the file (Mime type + Char encoding)  
        //You can retrieve doc flavors supported by the printer, like this  
        DocFlavor[] supportedFlavors = service.getSupportedDocFlavors();

        for (int i = 0; i < supportedFlavors.length; i++) {
            System.err.println(supportedFlavors[i]);
        }

        DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_8;

        // Create the print job  
        DocPrintJob job = service.createPrintJob();
        //Create the Doc. You can pass set of attributes(type of PrintRequestAttributeSet) as the   
        //3rd parameter specifying the page setup, orientation, no. of copies, etc instead of null.   
        Doc doc = new SimpleDoc(is, flavor, null);

        //Order to print, (can pass attributes instead of null)  
        try {
            job.print(doc, null);
        } catch (PrintException e) {
            e.printStackTrace();
        }

        //DocPrintJob.print() is not guaranteed to be synchronous. So it's better to wait on completion  
        //of the print job before closing the stream. (See the link below)  
        is.close();
        System.out.println("Printing done....");
    }
    private static PrintService getPrintService(String serviceName) {
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

}
