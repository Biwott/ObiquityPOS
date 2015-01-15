/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprinter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

/**
 *
 * @author Victor
 */
public class NewClass {

    public static void main(String[] args) {
        PageFormat format = new PageFormat();
        Paper paper = new Paper();

        double paperWidth = 3.25;
        double paperHeight = 11.69;
        double leftMargin = 0.19;
        double rightMargin = 0.25;
        double topMargin = 0;
        double bottomMargin = 0.01;

        paper.setSize(paperWidth * 72.0, paperHeight * 72.0);
        paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0,
                (paperWidth - leftMargin - rightMargin) * 72.0,
                (paperHeight - topMargin - bottomMargin) * 72.0);

        format.setPaper(paper);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        Printable printable = new ReceiptPrintTest();
        format = printerJob.validatePage(format);
        printerJob.setPrintable(printable, format);
        boolean ok = printerJob.printDialog();
        if (ok) {
            try {
                printerJob.print(aset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

class ReceiptPrintTest implements Printable {

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        if (pageIndex < 0 || pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        Font font1 = new Font("Arial", Font.PLAIN, 12);
        Font font2 = new Font("Arial", Font.PLAIN, 8);

        g2d.setFont(font1);
        g2d.drawString("1234567890abcdefg", 10, 70);
        g2d.setFont(font2);
        g2d.drawString("\n 1234567890abcdefg", 10, 70);

        g2d.drawRect(50, 0, 100, 50);

        return Printable.PAGE_EXISTS;
    }
}
