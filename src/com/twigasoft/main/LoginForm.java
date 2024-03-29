/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.main;

import com.twigasoft.utils.Database;
import com.twigasoft.utils.FormUtils;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Victor
 */
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public LoginForm() {

        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 1024) {
            JOptionPane.showMessageDialog(this, "<html><p align=center><b>Low Screen Resolution!</b></p>"
                    + " <p align=center>Try Setting Monitor Resoltion to 1024x768</p> </html>",
                    "Low Resolution", JOptionPane.OK_OPTION);
        }
        initComponents();

        setIconImages(new FormUtils().getImageIcons());
    }
// Compares password provided with the password in the database.

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;
        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }
        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
        return (password_verified);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPasswd = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Obiquity POS - Login Panel");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(800, 521));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel2.setForeground(new java.awt.Color(0, 153, 0));

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/twigasoft/resources/Bundle"); // NOI18N
        btnCancel.setText(bundle.getString("LoginForm.jButton2.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(51, 51, 51));
        btnLogin.setText(bundle.getString("LoginForm.jButton1.text")); // NOI18N
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin)
                .addGap(49, 49, 49)
                .addComponent(btnCancel)
                .addGap(53, 53, 53))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText(bundle.getString("LoginForm.jLabel1.text")); // NOI18N

        txtUname.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtUname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnameActionPerformed(evt);
            }
        });
        txtUname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnameKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText(bundle.getString("LoginForm.jLabel2.text")); // NOI18N

        txtPasswd.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtPasswd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswdKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Please Login");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
        );

        lblError.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 0));
        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/background.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String name = txtUname.getText();
        //check whether the username exist in the database
        if (getPasswordHash(name) != null) {

            //get password hash for the username from database
            String hash = getPasswordHash(name);
            //Retrieve typed password to a local variable
            String password = new String(txtPasswd.getPassword());
            //Compare typed password with hash
            if (checkPassword(password, hash)) {
                //Instatiate the application JFrame and pass access level as an argument.
                MainApp app = new MainApp(getAccessLevel(name));
                int empId = getEmpId(name);
                app.setEmpId(empId);
                app.setTellerName(getTellerName(empId));
                //Close Login Dialog
                this.dispose();
                app.setVisible(true);// Show the main window
                this.setCursor(Cursor.getDefaultCursor());//Change busy cursor to default cursor 
            } else {
                this.setCursor(Cursor.getDefaultCursor());//Change busy cursor to default cursor 
                txtUname.requestFocus();
                dispWrongPasswd();//Indicate wrong password/username 
            }
        } else {
            this.setCursor(Cursor.getDefaultCursor());
            txtUname.requestFocus();
            dispWrongPasswd();//Indicate wrong password/username
        }

    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(0);//Quit
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtUnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnameKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPasswd.requestFocus();//Change focus to next field.
        }
    }//GEN-LAST:event_txtUnameKeyReleased

    private void txtPasswdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswdKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogin.doClick();//Click login buttoen
        }
    }//GEN-LAST:event_txtPasswdKeyReleased
    public String getTellerName(int id) {
        String empName = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT last_name FROM tbl_employees WHERE id = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                empName = rs.getString(1);
            }
        } catch (SQLException ex) {
               Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return empName;
    }

    public void dispWrongPasswd() {
        lblError.setText("Wrong Username/Password!");
        txtPasswd.setText("");
        txtUname.setText("");
        new Thread(new Runnable() {// This anonymous thread creates a new runnable that
            //displays wrong password for 3 seconds
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    lblError.setText("");
                    txtUname.requestFocus();
                } catch (InterruptedException ex) {
                    lblError.setText("");
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public String getPasswordHash(String uName) {
        String hash = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT password FROM tbl_auth_employee_access WHERE u_name=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, uName);
            rs = pst.executeQuery();
            while (rs.next()) {
                hash = rs.getString(1);
            }
        } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return hash;
    }

    public int getEmpId(String uName) {// Returns Employee id.
        int id = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT emp_id FROM tbl_auth_employee_access WHERE u_name=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, uName);
            rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException e) {
                 Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return id;
    }

    public int getAccessLevel(String uName) {
        int level = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT role_level FROM tbl_auth_employee_access WHERE u_name=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, uName);
            rs = pst.executeQuery();
            while (rs.next()) {
                level = rs.getInt(1);
            }
        } catch (SQLException ex) {
             Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return level;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblError;
    private javax.swing.JPasswordField txtPasswd;
    private javax.swing.JTextField txtUname;
    // End of variables declaration//GEN-END:variables
}
