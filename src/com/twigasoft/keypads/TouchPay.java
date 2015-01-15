/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.keypads;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor
 */
public class TouchPay extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public TouchPay() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDot = new javax.swing.JButton();
        btnOne = new javax.swing.JButton();
        btnFour = new javax.swing.JButton();
        btnSeven = new javax.swing.JButton();
        btnAsterisk = new javax.swing.JButton();
        btnTwo = new javax.swing.JButton();
        btnFive = new javax.swing.JButton();
        btnEight = new javax.swing.JButton();
        btnSlash = new javax.swing.JButton();
        btnThree = new javax.swing.JButton();
        btnSix = new javax.swing.JButton();
        btnNine = new javax.swing.JButton();
        btnMinus = new javax.swing.JButton();
        btnPlus = new javax.swing.JButton();
        btnZero = new javax.swing.JButton();
        btnBackspace = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFocusable(false);
        setRequestFocusEnabled(false);

        btnDot.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnDot.setText(".");
        btnDot.setFocusable(false);
        btnDot.setMargin(new java.awt.Insets(2, 14, 14, 14));
        btnDot.setRequestFocusEnabled(false);
        btnDot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDotActionPerformed(evt);
            }
        });

        btnOne.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnOne.setText("1");
        btnOne.setFocusable(false);
        btnOne.setRequestFocusEnabled(false);
        btnOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOneActionPerformed(evt);
            }
        });

        btnFour.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnFour.setText("4");
        btnFour.setFocusable(false);
        btnFour.setRequestFocusEnabled(false);
        btnFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFourActionPerformed(evt);
            }
        });

        btnSeven.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnSeven.setText("7");
        btnSeven.setFocusable(false);
        btnSeven.setRequestFocusEnabled(false);
        btnSeven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSevenActionPerformed(evt);
            }
        });

        btnAsterisk.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnAsterisk.setText("*");
        btnAsterisk.setFocusable(false);
        btnAsterisk.setMargin(new java.awt.Insets(10, 14, 2, 14));
        btnAsterisk.setRequestFocusEnabled(false);
        btnAsterisk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsteriskActionPerformed(evt);
            }
        });

        btnTwo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnTwo.setText("2");
        btnTwo.setFocusable(false);
        btnTwo.setRequestFocusEnabled(false);
        btnTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTwoActionPerformed(evt);
            }
        });

        btnFive.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnFive.setText("5");
        btnFive.setFocusable(false);
        btnFive.setRequestFocusEnabled(false);
        btnFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiveActionPerformed(evt);
            }
        });

        btnEight.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnEight.setText("8");
        btnEight.setFocusable(false);
        btnEight.setRequestFocusEnabled(false);
        btnEight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEightActionPerformed(evt);
            }
        });

        btnSlash.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnSlash.setText("/");
        btnSlash.setFocusable(false);
        btnSlash.setRequestFocusEnabled(false);
        btnSlash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSlashActionPerformed(evt);
            }
        });

        btnThree.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnThree.setText("3");
        btnThree.setFocusable(false);
        btnThree.setRequestFocusEnabled(false);
        btnThree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThreeActionPerformed(evt);
            }
        });

        btnSix.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnSix.setText("6");
        btnSix.setFocusable(false);
        btnSix.setRequestFocusEnabled(false);
        btnSix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSixActionPerformed(evt);
            }
        });

        btnNine.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnNine.setText("9");
        btnNine.setFocusable(false);
        btnNine.setRequestFocusEnabled(false);
        btnNine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNineActionPerformed(evt);
            }
        });

        btnMinus.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnMinus.setText("-");
        btnMinus.setFocusable(false);
        btnMinus.setRequestFocusEnabled(false);
        btnMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusActionPerformed(evt);
            }
        });

        btnPlus.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnPlus.setText("+");
        btnPlus.setFocusable(false);
        btnPlus.setRequestFocusEnabled(false);
        btnPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusActionPerformed(evt);
            }
        });

        btnZero.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnZero.setText("0");
        btnZero.setFocusable(false);
        btnZero.setRequestFocusEnabled(false);
        btnZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZeroActionPerformed(evt);
            }
        });

        btnBackspace.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnBackspace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/backspace.png"))); // NOI18N
        btnBackspace.setFocusable(false);
        btnBackspace.setRequestFocusEnabled(false);
        btnBackspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackspaceActionPerformed(evt);
            }
        });

        btnEnter.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/enter.png"))); // NOI18N
        btnEnter.setFocusable(false);
        btnEnter.setRequestFocusEnabled(false);
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAsterisk, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOne, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFour, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeven, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnZero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSlash, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFive, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnMinus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThree, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                            .addComponent(btnNine, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnPlus, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                    .addComponent(btnBackspace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSlash, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAsterisk, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOne, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThree, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnPlus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFive, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFour, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSix, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnEight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSeven, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNine, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnZero, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(btnBackspace, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNineActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_9);
            robot.keyRelease(KeyEvent.VK_9);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnNineActionPerformed

    private void btnZeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZeroActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_0);
            robot.keyRelease(KeyEvent.VK_0);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnZeroActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEnterActionPerformed

    private void btnFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiveActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_5);
            robot.keyRelease(KeyEvent.VK_5);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFiveActionPerformed

    private void btnAsteriskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsteriskActionPerformed

    }//GEN-LAST:event_btnAsteriskActionPerformed

    private void btnOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOneActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOneActionPerformed

    private void btnDotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDotActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_DECIMAL);
            robot.keyRelease(KeyEvent.VK_DECIMAL);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDotActionPerformed

    private void btnSlashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSlashActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_SLASH);
            robot.keyRelease(KeyEvent.VK_SLASH);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSlashActionPerformed

    private void btnPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_PLUS);
            robot.keyRelease(KeyEvent.VK_PLUS);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPlusActionPerformed

    private void btnTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTwoActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_2);
            robot.keyRelease(KeyEvent.VK_2);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTwoActionPerformed

    private void btnThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThreeActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_3);
            robot.keyRelease(KeyEvent.VK_3);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThreeActionPerformed

    private void btnFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFourActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_4);
            robot.keyRelease(KeyEvent.VK_4);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFourActionPerformed

    private void btnSixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSixActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_6);
            robot.keyRelease(KeyEvent.VK_6);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSixActionPerformed

    private void btnSevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSevenActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_7);
            robot.keyRelease(KeyEvent.VK_7);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSevenActionPerformed

    private void btnEightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEightActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_8);
            robot.keyRelease(KeyEvent.VK_8);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEightActionPerformed

    private void btnBackspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackspaceActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBackspaceActionPerformed

    private void btnMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
        } catch (AWTException ex) {
            Logger.getLogger(TouchMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMinusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsterisk;
    private javax.swing.JButton btnBackspace;
    private javax.swing.JButton btnDot;
    private javax.swing.JButton btnEight;
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnFive;
    private javax.swing.JButton btnFour;
    private javax.swing.JButton btnMinus;
    private javax.swing.JButton btnNine;
    private javax.swing.JButton btnOne;
    private javax.swing.JButton btnPlus;
    private javax.swing.JButton btnSeven;
    private javax.swing.JButton btnSix;
    private javax.swing.JButton btnSlash;
    private javax.swing.JButton btnThree;
    private javax.swing.JButton btnTwo;
    private javax.swing.JButton btnZero;
    // End of variables declaration//GEN-END:variables
}