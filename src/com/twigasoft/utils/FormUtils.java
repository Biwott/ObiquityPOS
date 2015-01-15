/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.utils;

import com.twigasoft.dialogs.Inventory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.border.DropShadowBorder;

public class FormUtils {

    public static void setWidthAsPercentages(JTable table,
            double... percentages) {
        final double factor = 10000;
        TableColumnModel model = table.getColumnModel();
        for (int columnIndex = 0; columnIndex < percentages.length; columnIndex++) {
            TableColumn column = model.getColumn(columnIndex);
            column.setPreferredWidth((int) (percentages[columnIndex] * factor));
        }
    }

    public static void validateInteger(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }

    public static void blinkErrorMesage(final JLabel label, final JTextField field, final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                label.setText(message);
                Border b = new JTextField().getBorder();
                field.setBorder(errorBorder());
                try {
                    for (int i = 0; i < 3; i++) {
                        field.setBorder(errorBorder());
                        Thread.sleep(100);
                        field.setBorder(b);
                        Thread.sleep(100);
                    }
                    field.setBorder(b);
                    field.requestFocus();
                } catch (InterruptedException ex) {
                    field.setBorder(b);
                    label.setText("");
                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public static int getTextValue(JTextField field) {
        if (field.getText().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(NumUtils.unformatAmount(field.getText()));
        }
    }

    public static FileInputStream getBlobValue(JTextField field, Class cl) throws FileNotFoundException {
        File file;
        if (field.getText().equals("")) {
            file = new File(cl.getResource("/com/twigasoft/images/noimage.png").getFile());

        } else {
            file = new File(field.getText());
        }

        FileInputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    public static Border shadowBorder() {
        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(false);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(false);
        shadow.setShadowOpacity(0.5f);
        shadow.setCornerSize(12);
        shadow.setShadowSize(8);
        return shadow;
    }

    public static Border shadowBorder2() {
        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(false);
        shadow.setShowRightShadow(false);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(false);
        shadow.setShadowOpacity(0.6f);
        shadow.setCornerSize(15);
        shadow.setShadowSize(10);
        return shadow;
    }

    public static Border LoginBorder() {
        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        shadow.setShadowOpacity(0.5f);
        shadow.setCornerSize(12);
        shadow.setShadowSize(8);
        return shadow;
    }

    public static Border errorBorder() {

        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(new java.awt.Color(255, 0, 0));
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        shadow.setShadowOpacity(0.5f);
        shadow.setCornerSize(10);
        shadow.setShadowSize(8);
        return shadow;
    }

    public static Border btnPanelBorder() {
        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(false);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(false);
        shadow.setShadowOpacity(0.9f);
        shadow.setCornerSize(12);
        shadow.setShadowSize(12);
        return shadow;
    }

    public static Border txtAreaBorder() {
        DropShadowBorder shadow;
        shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(false);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(false);
        shadow.setShadowOpacity(0.6f);
        shadow.setCornerSize(15);
        shadow.setShadowSize(6);
        return shadow;
    }

    public static void showSuccessMessage(final JLabel label, final String message) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    label.setText(message);
                    Thread.sleep(3000);
                    label.setText("");
                } catch (InterruptedException ex) {
                    label.setText("");
                    Logger.getLogger(FormUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }).start();

    }

    public ArrayList getImageIcons() {
        ArrayList icons = new ArrayList();

        Image icon1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/16x16.png"));
        Image icon2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/24x24.png"));
        Image icon3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/32x32.png"));
        Image icon4 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/48x48.png"));
        Image icon5 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/64x64.png"));
        Image icon6 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/twigasoft/icons/128x128.png"));
        icons.add(icon1);
        icons.add(icon2);
        icons.add(icon3);
        icons.add(icon4);
        icons.add(icon5);
        icons.add(icon6);
        return icons;
    }
}
