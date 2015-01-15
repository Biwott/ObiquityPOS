/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.twigasoft.utils.Database;
import com.twigasoft.utils.NumUtils;
import com.twigasoft.utils.FormUtils;
import com.twigasoft.dialogs.Customers;
import com.twigasoft.properties.SystemInfo;
import com.twigasoft.utils.VerticalLabelUI;
import com.twigasoft.search.CustomerSearch;
import com.twigasoft.search.JSearchTextField;
import com.twigasoft.renderers.QuantityEditor;
import com.twigasoft.renderers.DeleteRenderer;
import com.twigasoft.renderers.QuantityRenderer;
import com.twigasoft.renderers.DescriptionEditor;
import com.twigasoft.renderers.DescriptionRenderer;
import com.twigasoft.renderers.DiscountEditor;
import com.twigasoft.renderers.DiscountRenderer;
import java.util.Properties;
import javax.swing.JFrame;

/**
 *
 * @author Victor
 */
public class MainApp extends javax.swing.JFrame {

    private int empId;
    private int tillNo = 1;
    private int nextRow = 0;
    private String tellerName;
    public JWindow popup = null;
    private HomeTab homeTab = null;
    public boolean editable = true;
    private SettingsTab settings = null;
    private IntelliSearch intelli = null;
    private DefaultTableModel model = null;
    private Properties props;
    Thread th;

    public MainApp(int accessLevel) {
        initComponents();//Initialise visual components
        props = new SystemInfo().loadParams();
        setIconImages(new FormUtils().getImageIcons());
        splitMain.getLeftComponent().setMaximumSize(new Dimension(300, 2000));
        homeTab = new HomeTab(this, accessLevel);//Instantiate 1st Side tab
        settings = new SettingsTab(this, accessLevel);//Instantiate 2nd Side tab
        tabMain.addTab("", homeTab);
        tabMain.addTab("", settings);
        tabMain.setIconAt(0, new ImageIcon(getClass().getResource("/com/twigasoft/images/home.png")));// Set home tab icon
        tabMain.setIconAt(1, new ImageIcon(getClass().getResource("/com/twigasoft/images/settings.png")));// Set Settings tab icon 
        lblUnhide.setVisible(false);//Hides the sidebar strip.
        lblChange.setVisible(false);//Hides the Change Label
        lblChangeLabel.setVisible(false);
        setDefaultStartUpProperties();
        setDefaultDynamicProperties();
        model = (DefaultTableModel) tblMain.getModel();//instatiate the main table model.
        FormUtils.setWidthAsPercentages(tblMain, 0.10, 0.36, 0.15, 0.15, 0.06, 0.14, 0.04);
        scrollpane.getViewport().setBackground(Color.WHITE);// Set table background to white...
        if (editable) {
            setTableRenderers();
            tblMain.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getColumn() == 2 || e.getColumn() == 3) {
                        remodifyTable();
                        getTableTotals();
                    }
                }
            });
        } else {
            // removeTableRenderers();
        }
        addComponentListener(new ComponentAdapter() {// If window is moved, hide the search popup.
            @Override
            public void componentMoved(ComponentEvent e) {
                popup.setVisible(false);
            }
        });

        intelli = new IntelliSearch("", this);//Instantiate the search intellisense.
        popup = new JWindow() {
            @Override
            public void setVisible(boolean b) {
                super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
                if (b == false) {
                    nextRow = 0;
                    if (intelli != null) {
                        intelli.table.setRowSelectionAllowed(true);
                    }
                }
            }
        };
        setTillNo(1);
        showTime();// Dislplays time in the lower left corner.
    }

    /**
     * This is a setter method that is used to assign logged in user(staff)
     *
     * @param tellerName
     */
    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
        lblLoggedUser.setText("<html>Logged in As: <b>" + tellerName + "</b></html>");
    }

    /**
     * This setter method sets the staff/employee id.
     *
     * @param empId
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * Sets the workstation id.
     *
     * @param tillNo
     */
    public final void setTillNo(int tillNo) {
        this.tillNo = tillNo;
    }

    /**
     * @return returns employee id.
     */
    public int getEmpId() {
        return empId;
    }

    /**
     *
     * @return gets till/ wokstation id.
     */
    public int getTillNo() {
        return tillNo;
    }

    /**
     *
     * @return gets employee last name.
     */
    public String getTellerName() {
        return tellerName;
    }

    /**
     * These are properties that take effect only during start-up, so they are
     * called once in the constructor.
     */
    public final void setDefaultStartUpProperties() {
        /*Determines whether the sidetab is shown on start-up*/
        if (Boolean.parseBoolean(props.getProperty("sidetab.show"))) {
            hideButtonClicked();
        }
        /*Determines whether the MainApplication start at fullscreen*/
        if (Boolean.parseBoolean(props.getProperty("screen.startfull"))) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    /**
     * These are properties that can affect behavior of the system during
     * runtime, hence it can be called from the constructor and also when
     * changes occur as invokes by system settings.
     */
    public final void setDefaultDynamicProperties() {

        if (Boolean.parseBoolean(props.getProperty("allow.holdrecall"))) {
            btnHold.setEnabled(true);
        } else {
            btnHold.setEnabled(false);
        }
        if (Boolean.parseBoolean(props.getProperty("discount.overall"))) {
            btnAddDisc.setEnabled(true);
            btnSubDisc.setEnabled(true);
            txtDisc.setEnabled(true);
        } else {
            btnAddDisc.setEnabled(false);
            btnSubDisc.setEnabled(false);
            txtDisc.setEnabled(false);
        }
        if (Boolean.parseBoolean(props.getProperty("discount.item"))) {
            tblMain.getColumnModel().getColumn(3).setCellEditor(new DiscountEditor());
            tblMain.getColumnModel().getColumn(3).setCellRenderer(new DiscountRenderer());
        } else {
            tblMain.getColumnModel().getColumn(3).setCellEditor(null);
            tblMain.getColumnModel().getColumn(3).setCellRenderer(null);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        payGroup = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel4 = new javax.swing.JPanel();
        lblObiquity = new javax.swing.JLabel();
        lblHelp = new javax.swing.JLabel();
        txtSearch = new JSearchTextField();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblDate = new javax.swing.JLabel();
        lblLoggedUser = new javax.swing.JLabel();
        desktopPane = new javax.swing.JDesktopPane();
        splitMain = new javax.swing.JSplitPane();
        pnlLeftTab = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        lblHide = new javax.swing.JLabel();
        lblUnhide = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnDrawer = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnHold = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        btnTender = new javax.swing.JButton();
        outerjPanel15 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnAddDisc = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDisc = new javax.swing.JTextField();
        btnSubDisc = new javax.swing.JButton();
        outerjPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblChangeLabel = new javax.swing.JLabel();
        lblChange = new javax.swing.JLabel();
        outerjPanel15a = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtSubTotal = new javax.swing.JTextField();
        txtTax = new javax.swing.JTextField();
        txtTotals = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        outerjPanel17 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCustomer = new CustomerSearch();
        jButton8 = new javax.swing.JButton();
        scrollpane = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                if(column==1||column==2||column==3){
                    return true;
                }
                else return false;
            }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component returnComp=super.prepareRenderer(renderer, row, column);
                Color alternateColor=new Color(240,244,250);
                Color whiteColor=Color.WHITE;
                if(!returnComp.getBackground().equals(getSelectionBackground())){
                    Color bg=(row%2==0?alternateColor:whiteColor);
                    returnComp.setBackground(bg);
                    bg=null;
                }
                return returnComp;
            }

        };
        menuMain = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ObiquityPOS - Point of Sale System");
        setMinimumSize(new java.awt.Dimension(836, 550));

        jToolBar1.setBackground(new java.awt.Color(204, 204, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        lblHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/help.png"))); // NOI18N
        lblHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHelpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHelpMouseExited(evt);
            }
        });

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblObiquity, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(lblHelp)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblObiquity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txtSearch)
                .addGap(4, 4, 4))
        );

        jToolBar1.add(jPanel4);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDate.setForeground(new java.awt.Color(0, 0, 102));

        lblLoggedUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoggedUser, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
            .addComponent(lblDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(lblLoggedUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        desktopPane.setFocusable(false);
        desktopPane.setRequestFocusEnabled(false);

        splitMain.setBackground(new java.awt.Color(204, 204, 255));
        splitMain.setDividerLocation(284);
        splitMain.setDividerSize(1);
        splitMain.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel16.setLayout(new java.awt.BorderLayout());

        tabMain.setBackground(new java.awt.Color(255, 255, 255));
        tabMain.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabMain.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel16.add(tabMain, java.awt.BorderLayout.CENTER);

        lblHide.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblHide.setForeground(new java.awt.Color(102, 102, 102));
        lblHide.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/hide.png"))); // NOI18N
        lblHide.setText("Hide   ");
        lblHide.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(240, 240, 240)));
        lblHide.setOpaque(true);
        lblHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHideMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblHideMouseReleased(evt);
            }
        });
        jPanel16.add(lblHide, java.awt.BorderLayout.PAGE_START);

        lblUnhide.setUI(new VerticalLabelUI(false));
        lblUnhide.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblUnhide.setForeground(new java.awt.Color(102, 102, 102));
        lblUnhide.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUnhide.setText("label");
        lblUnhide.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(240, 240, 240)));
        lblUnhide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUnhideMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblUnhideMouseReleased(evt);
            }
        });
        jPanel16.add(lblUnhide, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout pnlLeftTabLayout = new javax.swing.GroupLayout(pnlLeftTab);
        pnlLeftTab.setLayout(pnlLeftTabLayout);
        pnlLeftTabLayout.setHorizontalGroup(
            pnlLeftTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLeftTabLayout.setVerticalGroup(
            pnlLeftTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        splitMain.setLeftComponent(pnlLeftTab);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(137, 140, 149)));

        btnDrawer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDrawer.setText("<html>Drawer<p align=\"center\">(F5)</p></html>");
        btnDrawer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawerActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNew.setText("<html>New Sale <p align=\"center\">(F3)</p></html>");
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        btnNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNewKeyPressed(evt);
            }
        });

        btnHold.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHold.setText("<html>Hold/Recall<p align=\"center\">(F4)</p></html>");
        btnHold.setEnabled(false);
        btnHold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoldActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(204, 204, 255));
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancel.setText("<html>Cancel<p align =\"center\">(ESC)</p></html>");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHold, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHold, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnDrawer, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTender.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTender.setText("<html>Tender<p align=\"center\">(F10)</p></html>");
        btnTender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTender)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outerjPanel15.setBackground(new java.awt.Color(255, 255, 255));
        outerjPanel15.setBorder(FormUtils.shadowBorder2());

        jPanel14.setBackground(new java.awt.Color(240, 245, 250));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(137, 140, 149)));

        btnAddDisc.setText("+");
        btnAddDisc.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btnAddDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDiscActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Discount:");

        txtDisc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDisc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDisc.setText("0%");
        txtDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscActionPerformed(evt);
            }
        });
        txtDisc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscFocusLost(evt);
            }
        });
        txtDisc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscKeyTyped(evt);
            }
        });

        btnSubDisc.setText("-");
        btnSubDisc.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btnSubDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubDiscActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(1, 1, 1)
                .addComponent(btnSubDisc)
                .addGap(0, 0, 0)
                .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnAddDisc)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSubDisc, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnAddDisc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDisc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout outerjPanel15Layout = new javax.swing.GroupLayout(outerjPanel15);
        outerjPanel15.setLayout(outerjPanel15Layout);
        outerjPanel15Layout.setHorizontalGroup(
            outerjPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outerjPanel15Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        outerjPanel15Layout.setVerticalGroup(
            outerjPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outerjPanel15Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        outerjPanel.setBackground(new java.awt.Color(255, 255, 255));
        outerjPanel.setBorder(FormUtils.shadowBorder2());

        jPanel1.setBackground(new java.awt.Color(240, 245, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(137, 140, 149)));

        lblChangeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblChangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangeLabel.setText("Change:");

        lblChange.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblChange.setForeground(new java.awt.Color(255, 0, 0));
        lblChange.setText("KShs. 0.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblChangeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChange, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblChangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout outerjPanelLayout = new javax.swing.GroupLayout(outerjPanel);
        outerjPanel.setLayout(outerjPanelLayout);
        outerjPanelLayout.setHorizontalGroup(
            outerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        outerjPanelLayout.setVerticalGroup(
            outerjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        outerjPanel15a.setBackground(new java.awt.Color(255, 255, 255));
        outerjPanel15a.setBorder(FormUtils.shadowBorder2());

        jPanel5.setBackground(new java.awt.Color(240, 245, 250));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(137, 140, 149)));

        txtSubTotal.setEditable(false);
        txtSubTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setText("0.00");

        txtTax.setEditable(false);
        txtTax.setHorizontalAlignment(JTextField.RIGHT);
        txtTax.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTax.setText("0.00");

        txtTotals.setEditable(false);
        txtTotals.setHorizontalAlignment(JTextField.RIGHT);
        txtTotals.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTotals.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotals.setText("0.00");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("SubTotal:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tax:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Total:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotals, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtTax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotals, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        javax.swing.GroupLayout outerjPanel15aLayout = new javax.swing.GroupLayout(outerjPanel15a);
        outerjPanel15a.setLayout(outerjPanel15aLayout);
        outerjPanel15aLayout.setHorizontalGroup(
            outerjPanel15aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outerjPanel15aLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        outerjPanel15aLayout.setVerticalGroup(
            outerjPanel15aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        outerjPanel17.setBackground(new java.awt.Color(255, 255, 255));
        outerjPanel17.setBorder(FormUtils.shadowBorder2());

        jPanel13.setBackground(new java.awt.Color(240, 245, 250));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(137, 140, 149)));

        jLabel7.setBackground(new java.awt.Color(45, 93, 181));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("  Customer Search");
        jLabel7.setOpaque(true);

        txtCustomer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustomerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCustomerFocusLost(evt);
            }
        });
        txtCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustomerKeyPressed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setText("New Customer");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCustomer)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 125, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap())
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout outerjPanel17Layout = new javax.swing.GroupLayout(outerjPanel17);
        outerjPanel17.setLayout(outerjPanel17Layout);
        outerjPanel17Layout.setHorizontalGroup(
            outerjPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outerjPanel17Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        outerjPanel17Layout.setVerticalGroup(
            outerjPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outerjPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(outerjPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(outerjPanel15a, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(outerjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(outerjPanel15a, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outerjPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outerjPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outerjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        scrollpane.setBackground(new java.awt.Color(255, 255, 255));

        tblMain.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UPC#", "Product Description", "Qty", "% Disc.", "Tax", "SubTotal ("+com.twigasoft.utils.NumUtils.getCurrency()+")",""
            }
        ));
        tblMain.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblMain.setRowHeight(25);
        tblMain.setShowHorizontalLines(false);
        tblMain.setShowVerticalLines(false);
        tblMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMainMouseClicked(evt);
            }
        });
        tblMain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMainKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblMainKeyReleased(evt);
            }
        });
        scrollpane.setViewportView(tblMain);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        splitMain.setRightComponent(jPanel2);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(splitMain, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitMain, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        desktopPane.setLayer(splitMain, javax.swing.JLayeredPane.DEFAULT_LAYER);

        menuMain.setBackground(new java.awt.Color(255, 255, 255));
        menuMain.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jMenu1.setText("Point of Sale");

        jMenuItem2.setText("Start of Day Procedures");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Sale Refund/ Return/ Voiding");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Staff Clock In/Out");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("End of Day Procedures");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        menuMain.add(jMenu1);

        jMenu2.setText("Sales");

        jMenuItem6.setText("jMenuItem6");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setText("jMenuItem7");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("jMenuItem8");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setText("jMenuItem9");
        jMenu2.add(jMenuItem9);

        menuMain.add(jMenu2);

        jMenu9.setText("Inventory");

        jMenuItem10.setText("jMenuItem10");
        jMenu9.add(jMenuItem10);

        jMenuItem11.setText("jMenuItem11");
        jMenu9.add(jMenuItem11);

        jMenuItem12.setText("jMenuItem12");
        jMenu9.add(jMenuItem12);

        jMenuItem13.setText("jMenuItem13");
        jMenu9.add(jMenuItem13);

        menuMain.add(jMenu9);

        jMenu8.setText("Purchase");

        jMenuItem14.setText("jMenuItem14");
        jMenu8.add(jMenuItem14);

        jMenuItem15.setText("jMenuItem15");
        jMenu8.add(jMenuItem15);

        jMenuItem16.setText("jMenuItem16");
        jMenu8.add(jMenuItem16);

        jMenuItem17.setText("jMenuItem17");
        jMenu8.add(jMenuItem17);

        menuMain.add(jMenu8);

        jMenu7.setText("Customers");

        jMenuItem18.setText("jMenuItem18");
        jMenu7.add(jMenuItem18);

        jMenuItem19.setText("jMenuItem19");
        jMenu7.add(jMenuItem19);

        menuMain.add(jMenu7);

        jMenu6.setText("Empoyees");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");
        jMenu6.add(jCheckBoxMenuItem1);

        jMenuItem20.setText("jMenuItem20");
        jMenu6.add(jMenuItem20);

        menuMain.add(jMenu6);

        jMenu5.setText("Settings");

        jMenuItem21.setText("jMenuItem21");
        jMenu5.add(jMenuItem21);

        jMenuItem22.setText("jMenuItem22");
        jMenu5.add(jMenuItem22);

        menuMain.add(jMenu5);

        jMenu4.setText("Reports");

        jMenuItem23.setText("jMenuItem23");
        jMenu4.add(jMenuItem23);

        jMenuItem24.setText("jMenuItem24");
        jMenu4.add(jMenuItem24);

        menuMain.add(jMenu4);

        jMenu10.setText("Help");

        jMenuItem25.setText("jMenuItem25");
        jMenu10.add(jMenuItem25);

        jMenuItem26.setText("jMenuItem26");
        jMenu10.add(jMenuItem26);

        menuMain.add(jMenu10);

        setJMenuBar(menuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(desktopPane)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTenderActionPerformed
        if (tblMain.getRowCount() > 0) {
            int total = NumUtils.removeSignInt(txtTotals.getText(), ',');
            Pay dialog = new Pay(this, true, total);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "<html><b>NO ITEMS</b> in the Checkout Table!</html>", "Empty Checkout!", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_btnTenderActionPerformed

    private void tblMainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMainKeyPressed
        getTableTotals();
    }//GEN-LAST:event_tblMainKeyPressed

    private void tblMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainMouseClicked

        if (tblMain.getRowCount() > 0) {
            int col = tblMain.columnAtPoint(evt.getPoint());
            popup.setVisible(false);
            //Check whether event originates from the remove row icon.
            if (col == 6 && editable) {
                model.removeRow(tblMain.rowAtPoint(evt.getPoint()));
            }
            getTableTotals();
        } else {
            txtSubTotal.setText("0.00");
            txtTotals.setText("0.00");
            txtTax.setText("0.00");
            txtDisc.setText("0%");
            txtDisc.setEnabled(false);
            btnAddDisc.setEnabled(false);
            btnSubDisc.setEnabled(false);
        }
    }//GEN-LAST:event_tblMainMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        editable = true;
        btnAddDisc.setEnabled(true);
        btnSubDisc.setEnabled(true);
        txtDisc.setEnabled(true);
        btnCancel.setEnabled(true);
        btnCancel.setEnabled(true);
        txtSearch.setEnabled(true);
        tblMain.setEnabled(true);
        btnTender.setEnabled(true);
        btnHold.setEnabled(true);
        model.setRowCount(0);
        txtDisc.setText("0%");
        lblChange.setVisible(false);
        lblChangeLabel.setVisible(false);
        txtTax.setText("0.00");
        txtSubTotal.setText("0.00");
        txtTotals.setText("0.00");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        txtSearch.setText("");
        model.setRowCount(0);
        txtDisc.setText("0%");
        txtTax.setText("0.00");
        txtSubTotal.setText("0.00");
        txtTotals.setText("0.00");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnHoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoldActionPerformed

    }//GEN-LAST:event_btnHoldActionPerformed

    private void btnDrawerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawerActionPerformed
        if (txtSearch.isEditable()) {

        } else {
            btnNew.requestFocus();
        }
    }//GEN-LAST:event_btnDrawerActionPerformed

    private void btnNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNewKeyPressed
        triggerKeyEvents(evt);
    }//GEN-LAST:event_btnNewKeyPressed

    private void tblMainKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMainKeyReleased

    }//GEN-LAST:event_tblMainKeyReleased

    private void btnSubDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubDiscActionPerformed
        if (NumUtils.removeSignInt(txtDisc.getText(), '%') > 0 && tblMain.getRowCount() > 0) {
            txtDisc.setText(NumUtils.appendPerc(Integer.toString((NumUtils.removeSignInt(txtDisc.getText(), '%')) - 1)));
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                model.setValueAt(txtDisc.getText(), i, 3);
            }
        }
    }//GEN-LAST:event_btnSubDiscActionPerformed

    private void txtDiscKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscKeyTyped
        FormUtils.validateInteger(evt);
        if (txtDisc.getText().length() > 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscKeyTyped

    private void txtDiscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscKeyReleased
        if (tblMain.getRowCount() > 0) {
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                model.setValueAt(txtDisc.getText(), i, 3);
            }
        }
    }//GEN-LAST:event_txtDiscKeyReleased

    private void txtDiscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscFocusLost
        if (NumUtils.removeSignInt(txtDisc.getText(), '%') < 0 || NumUtils.removeSignInt(txtDisc.getText(), '%') > 100) {
            txtDisc.setText("0");
        }
        if (tblMain.getRowCount() > 0) {
            String d = Integer.toString(NumUtils.removeSignInt(txtDisc.getText(), '%'));
            txtDisc.setText(NumUtils.appendPerc(d));
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                model.setValueAt(txtDisc.getText(), i, 3);
            }
        }
    }//GEN-LAST:event_txtDiscFocusLost

    private void txtDiscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscFocusGained
        if (tblMain.getRowCount() > 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    txtDisc.selectAll();
                }
            });
            txtDisc.setEditable(true);
            txtDisc.setText(Integer.toString(NumUtils.removeSignInt(txtDisc.getText(), '%')));
        } else {
            txtDisc.setEditable(false);
        }
    }//GEN-LAST:event_txtDiscFocusGained

    private void txtDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscActionPerformed
        if (NumUtils.removeSignInt(txtDisc.getText(), '%') < 0 || NumUtils.removeSignInt(txtDisc.getText(), '%') > 100) {
            txtDisc.setText("0");
        }
        for (int i = 0; i < tblMain.getRowCount(); i++) {
            model.setValueAt(txtDisc.getText(), i, 3);
        }
    }//GEN-LAST:event_txtDiscActionPerformed

    private void btnAddDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDiscActionPerformed
        if (NumUtils.removeSignInt(txtDisc.getText(), '%') < 100 && tblMain.getRowCount() > 0) {
            txtDisc.setText(NumUtils.appendPerc(Integer.toString((NumUtils.removeSignInt(txtDisc.getText(), '%')) + 1)));
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                model.setValueAt(txtDisc.getText(), i, 3);
            }
        }
    }//GEN-LAST:event_btnAddDiscActionPerformed

    private void txtCustomerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerKeyPressed
        triggerKeyEvents(evt);
    }//GEN-LAST:event_txtCustomerKeyPressed

    private void txtCustomerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustomerFocusLost

    }//GEN-LAST:event_txtCustomerFocusLost

    private void txtCustomerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustomerFocusGained

    }//GEN-LAST:event_txtCustomerFocusGained

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Customers dialog = new Customers(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.btnNew.doClick();
        dialog.setVisible(true);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void lblHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideMouseClicked

    }//GEN-LAST:event_lblHideMouseClicked

    private void lblHideMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideMouseReleased
        hideButtonClicked();
    }//GEN-LAST:event_lblHideMouseReleased

    private void lblUnhideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUnhideMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUnhideMouseClicked

    private void lblUnhideMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUnhideMouseReleased
        hideButtonClicked();
    }//GEN-LAST:event_lblUnhideMouseReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        searchKeyRealeased(evt);
        triggerKeyEvents(evt);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        popup.setVisible(false);
        setTableRenderers();
        th.interrupt();
    }//GEN-LAST:event_txtSearchFocusLost

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        tblMain.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    removeTableRenderers();
                } catch (InterruptedException ex) {

                }
            }
        });
        th.start();
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchEntered();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void lblHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHelpMouseEntered
        lblHelp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 0, 2, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_lblHelpMouseEntered

    private void lblHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHelpMouseExited
        lblHelp.setBorder(null);
    }//GEN-LAST:event_lblHelpMouseExited

    private void lblHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHelpMouseClicked

    }//GEN-LAST:event_lblHelpMouseClicked
    public final void hideButtonClicked() {
        if (lblHide.getText().equals("")) {
            splitMain.setDividerLocation(284);
            lblHide.setText("Hide   ");
            lblHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/hide.png")));
            lblHide.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            tabMain.setVisible(true);
            lblUnhide.setVisible(false);

        } else {
            splitMain.setDividerLocation(25);
            lblHide.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
            lblHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/twigasoft/images/unhide.png")));
            tabMain.setVisible(false);
            lblHide.setText("");
            lblUnhide.setText("SIDEBAR MENU");
            lblUnhide.setVisible(true);
        }
    }

    /**
     * This searchEnterd() Method in the first if condition checks whether a
     * search term has been selected in in the intellisense search after which
     * it selects the item, otherwise of not selected the next else if condition
     * searches the entered digits to try and locate the item, and if it fails
     * it gives a notification of the failure.
     *
     */
    public void searchEntered() {
        if (popup.isVisible() && intelli.table.getSelectedRow() != -1) {
            fillTable(String.valueOf(intelli.model.getValueAt(intelli.table.getSelectedRow(), 0)));
            popup.setVisible(false);
        } else if (!txtSearch.getText().equals("")) {
            if (intelli.model.refresh(txtSearch.getText()) == -1) {
                popup.setVisible(false);
                JOptionPane.showMessageDialog(this, "Cannot Find Item!");
                txtSearch.setText("");
            } else {
                fillTable(txtSearch.getText());
            }
        }
    }

    /**
     * The void fillTable() method assumes an entered upc code and it finds the
     * product using the code and when it is succesful it posts the results into
     * the checkout table using an abstract table model.
     *
     * @param value
     */
    public void fillTable(String value) {
        btnCancel.setEnabled(true);
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        try {
            sql = "SELECT item_name, tax_id, trade_price FROM tbl_inventory WHERE upc=?";
            pst = Database.getConnection().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(value));
            rs = pst.executeQuery();
            while (rs.next()) {
                model.insertRow(tblMain.getRowCount(), new Object[]{value, rs.getString(1), 1, NumUtils.appendPerc("0"), getTaxCode(rs.getInt(2)), NumUtils.formatAmount(rs.getString(3))});
            }
            rs.close();
            pst.close();
        } catch (SQLException | NumberFormatException e) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
        }
        getTableTotals();
        txtSearch.setText("");//Clear Search box ready for next search.
        btnAddDisc.setEnabled(true);//Enable General Discount increment
        btnSubDisc.setEnabled(true);//Enable General Discount reduction
        txtDisc.setEnabled(true);//Enable Direct General Discount addition.
    }

    /**
     * getTaxCode method returns the tax code when supplied with its
     * corresponding id. It is only used for checkout table display purposes.
     *
     * @param id
     * @return code
     */
    public String getTaxCode(int id) {
        String code = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT code FROM tbl_tax WHERE id = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                code = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return code;
    }

    /**
     * getTaxPercentage method takes the tax code and searches for its
     * percentage value and returns it. It is used for tax computation purposes.
     *
     * @param code This is the String code of the tax
     * @return It returns an integer representing the tax percentage value
     */
    public int getTaxPerc(String code) {
        int value = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT value FROM tbl_tax WHERE code = ?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, code);
            rs = pst.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }

    /**
     * popUpTable method is responsible for showing the intellisense search
     * popup and it position the popup below the search text field.
     *
     * @param data argument takes the data entered int the text field and feed
     * it into the intellisense.
     */
    public void popUpTable(String data) {
        intelli.refresh(data);
        txtSearch.requestFocus();
        popup.getContentPane().add(intelli);
        popup.pack();
        Point pt = txtSearch.getLocationOnScreen();
        pt.translate(0, txtSearch.getHeight());
        popup.setLocation(pt);
        popup.setVisible(true);
        popup.setFocusable(true);

    }

    /**
     * getTableTotals method computes the sum of items in the checkout table it
     * adds up the totals and taxex. It then gets the subtotal which is usually
     * the total less tax.
     */
    public void getTableTotals() {
        double totals = 0.0;
        double tax = 0.0;
        if (model.getRowCount() > 0) {
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                totals += NumUtils.removeSign(String.valueOf(model.getValueAt(i, 5)), ',');
                tax += (getTaxPerc(String.valueOf(model.getValueAt(i, 4))) * NumUtils.removeSignInt(String.valueOf(model.getValueAt(i, 5)), ',')) / 100;//* NumUtils.removeSignInt(String.valueOf(model.getValueAt(i, 5)), ',');
            }
        }
        txtTax.setText(NumUtils.formatAmount(Double.toString(tax)));
        txtSubTotal.setText(NumUtils.formatAmount(Double.toString(totals - tax)));
        txtTotals.setText(NumUtils.formatAmount(Double.toString(totals)));

    }

    /**
     * showTime method shows time at the bottom of the main window(Status bar).
     * It utilises a simple anonymous thread
     */
    public final void showTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        lblDate.setText(new Date().toString());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    lblDate.setText("");
                }
            }
        }).start();
    }

    /**
     * The blink change method is responsible for showing the balance. It is
     * used by the pay class.
     *
     * @param value this is the change value
     */
    public void blinkChange(final String value) {
        lblChange.setVisible(true);
        lblChangeLabel.setVisible(true);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 4; i++) {
                        lblChange.setText(NumUtils.getCurrency() + value);
                        Thread.sleep(500);
                        lblChange.setText("");
                        Thread.sleep(500);
                        lblChange.setText(NumUtils.getCurrency() + value);
                        btnNew.requestFocus();
                    }
                } catch (InterruptedException ex) {
                    lblChange.setText(NumUtils.getCurrency() + value);
                    btnNew.requestFocus();
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }

    /**
     * This triggerKeyEvents method uses key listeners to determine which
     * fuction key was clicked it then does the appropriate action according to
     * the keyactions.
     *
     * @param evt this is the key event being passed
     */
    public void triggerKeyEvents(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            btnNew.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_F4) {
            btnHold.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_F5) {
            btnDrawer.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnCancel.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_F10) {
            btnTender.doClick();
        }
    }

    /**
     * This searchKeyRaleased method handles key events for the search box. It
     * defines what happens when search box is empty and also when the scroll
     * keys of up and down are pressed. These are related to intellisense.
     *
     * @param evt this paramenter accepts an evnt from the invoker.
     */
    public void searchKeyRealeased(KeyEvent evt) {
        //Hide Intellisense when there is no text in the search.
        if (txtSearch.getText().equals("")) {
            popup.setVisible(false);
        } else if (evt.getKeyCode() != KeyEvent.VK_ENTER) {
            popUpTable(txtSearch.getText());
        }
        if (popup.isVisible()) {
            //If the intelisense contains one value only then select it.
            if (intelli.table.getRowCount() == 1) {
                intelli.table.setRowSelectionAllowed(true);
                intelli.table.changeSelection(0, 0, false, false);
            } else {
                intelli.table.changeSelection(-1, 0, false, false);
            }

        }
        //Scrolling of intellisense table values. It contains key listeners 
        //that define scrolling of intellisense values.
        if (evt.getKeyCode() == KeyEvent.VK_DOWN && popup.isVisible()) {
            intelli.table.setRowSelectionAllowed(true);
            if (nextRow == 0) {
                intelli.table.changeSelection(nextRow, 0, false, false);
                nextRow++;
            } else if (intelli.table.getRowCount() > nextRow && nextRow > 0) {
                intelli.table.changeSelection(nextRow, 0, false, false);
                nextRow++;
            } else {
                intelli.table.changeSelection(intelli.table.getRowCount() - 1, 0, false, false);
                nextRow = intelli.table.getRowCount() - 1;
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP && popup.isVisible()) {
            intelli.table.setRowSelectionAllowed(true);
            if (nextRow > 0) {
                nextRow--;
                intelli.table.changeSelection(nextRow, 0, false, false);
            } else {
                intelli.table.changeSelection(-1, 0, false, false);
            }
        }
    }

    private void remodifyTable() {
        if (tblMain.getRowCount() > 0) {
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT trade_price FROM tbl_inventory WHERE upc = ?";
            for (int i = 0; i < tblMain.getRowCount(); i++) {
                double cost = 0;
                double total = 0;
                double quantity = 0;
                double discount = 0;
                int finalTotal = 0;
                try {
                    conn = Database.getConnection();
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, tblMain.getValueAt(i, 0).toString());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        cost = Integer.parseInt(rs.getString("trade_price"));
                    }

                } catch (NumberFormatException | SQLException e) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    try {
                        rs.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                quantity = Integer.parseInt(tblMain.getValueAt(i, 2).toString());
                discount = NumUtils.removeSignInt(tblMain.getValueAt(i, 3).toString(), '%');
                total = ((100 - discount) / 100) * quantity * cost;
                finalTotal = (int) total;
                tblMain.setValueAt(NumUtils.formatAmount(Integer.toString(finalTotal)), i, 5);
            }
        }
    }

    public final void setTableRenderers() {
        tblMain.getColumnModel().getColumn(1).setCellEditor(new DescriptionEditor());
        tblMain.getColumnModel().getColumn(2).setCellEditor(new QuantityEditor());
        tblMain.getColumnModel().getColumn(1).setCellRenderer(new DescriptionRenderer());
        tblMain.getColumnModel().getColumn(2).setCellRenderer(new QuantityRenderer());

        tblMain.getColumnModel().getColumn(6).setCellRenderer(new DeleteRenderer());
    }

    public final void removeTableRenderers() {
        tblMain.getColumnModel().getColumn(1).setCellEditor(null);
        tblMain.getColumnModel().getColumn(2).setCellEditor(null);
        tblMain.getColumnModel().getColumn(1).setCellRenderer(null);
        tblMain.getColumnModel().getColumn(2).setCellRenderer(null);
        // tblMain.getColumnModel().getColumn(3).setCellEditor(null);
        //tblMain.getColumnModel().getColumn(3).setCellRenderer(null);
        tblMain.getColumnModel().getColumn(6).setCellRenderer(null);
    }

    /**
     * @param args
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
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp(1).setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddDisc;
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnDrawer;
    public javax.swing.JButton btnHold;
    public javax.swing.JButton btnNew;
    public javax.swing.JButton btnSubDisc;
    public javax.swing.JButton btnTender;
    public javax.swing.JDesktopPane desktopPane;
    public javax.swing.JButton jButton8;
    public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JMenu jMenu1;
    public javax.swing.JMenu jMenu10;
    public javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu4;
    public javax.swing.JMenu jMenu5;
    public javax.swing.JMenu jMenu6;
    public javax.swing.JMenu jMenu7;
    public javax.swing.JMenu jMenu8;
    public javax.swing.JMenu jMenu9;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem10;
    public javax.swing.JMenuItem jMenuItem11;
    public javax.swing.JMenuItem jMenuItem12;
    public javax.swing.JMenuItem jMenuItem13;
    public javax.swing.JMenuItem jMenuItem14;
    public javax.swing.JMenuItem jMenuItem15;
    public javax.swing.JMenuItem jMenuItem16;
    public javax.swing.JMenuItem jMenuItem17;
    public javax.swing.JMenuItem jMenuItem18;
    public javax.swing.JMenuItem jMenuItem19;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JMenuItem jMenuItem20;
    public javax.swing.JMenuItem jMenuItem21;
    public javax.swing.JMenuItem jMenuItem22;
    public javax.swing.JMenuItem jMenuItem23;
    public javax.swing.JMenuItem jMenuItem24;
    public javax.swing.JMenuItem jMenuItem25;
    public javax.swing.JMenuItem jMenuItem26;
    public javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JMenuItem jMenuItem5;
    public javax.swing.JMenuItem jMenuItem6;
    public javax.swing.JMenuItem jMenuItem7;
    public javax.swing.JMenuItem jMenuItem8;
    public javax.swing.JMenuItem jMenuItem9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel13;
    public javax.swing.JPanel jPanel14;
    public javax.swing.JPanel jPanel16;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JLabel lblChange;
    public javax.swing.JLabel lblChangeLabel;
    public javax.swing.JLabel lblDate;
    public javax.swing.JLabel lblHelp;
    public javax.swing.JLabel lblHide;
    public javax.swing.JLabel lblLoggedUser;
    public javax.swing.JLabel lblObiquity;
    public javax.swing.JLabel lblUnhide;
    public javax.swing.JMenuBar menuMain;
    public javax.swing.JPanel outerjPanel;
    public javax.swing.JPanel outerjPanel15;
    public javax.swing.JPanel outerjPanel15a;
    public javax.swing.JPanel outerjPanel17;
    public javax.swing.ButtonGroup payGroup;
    public javax.swing.JPanel pnlLeftTab;
    public javax.swing.JScrollPane scrollpane;
    public javax.swing.JSplitPane splitMain;
    public javax.swing.JTabbedPane tabMain;
    public javax.swing.JTable tblMain;
    public javax.swing.JTextField txtCustomer;
    public javax.swing.JTextField txtDisc;
    public javax.swing.JTextField txtSearch;
    public javax.swing.JTextField txtSubTotal;
    public javax.swing.JTextField txtTax;
    public javax.swing.JTextField txtTotals;
    // End of variables declaration//GEN-END:variables

}
