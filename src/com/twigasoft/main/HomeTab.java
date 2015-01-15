package com.twigasoft.main;

import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.TabOrientation;
import com.javaswingcomponents.accordion.listener.AccordionEvent;
import com.javaswingcomponents.accordion.listener.AccordionListener;
import com.javaswingcomponents.accordion.plaf.AccordionUI;
import com.javaswingcomponents.accordion.plaf.basic.BasicAccordionUI;
import com.javaswingcomponents.accordion.plaf.basic.BasicHorizontalTabRenderer;
import com.twigasoft.dialogs.Customers;
import com.twigasoft.dialogs.Inventory;
import com.twigasoft.dialogs.Suppliers;
import com.twigasoft.misc.PurchaseInvoice;
import com.twigasoft.misc.PurchaseOrder;
import com.twigasoft.misc.SalesDialog;
import com.twigasoft.settings.AccessControl;
import com.twigasoft.settings.ClockInOut;
import com.twigasoft.settings.EndDay;
import com.twigasoft.settings.PastSales;
import com.twigasoft.settings.StartDay;
import com.twigasoft.settings.StockAdjustments;
import com.twigasoft.settings.StockTransfers;
import com.twigasoft.settings.Voiding;
import com.twigasoft.utils.Database;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeTab extends JPanel {

    private final MainApp parent;
    private final int level;
    ClockInOut dialog = null;

    public HomeTab(MainApp parent, int level) {
        this.level = level;
        this.parent = parent;
        JSCAccordion accordion = new JSCAccordion();
        addAccordionTabs(accordion);
        listenForChanges(accordion);
        changeTabOrientation(accordion);
        changeTheLookAndFeel(accordion);
        customizeLookAndFeel(accordion);
        setLayout(new GridLayout(1, 1, 30, 30));
        add(accordion);

    }

    private void addAccordionTabs(JSCAccordion accordion) {
        if (validateVisibility(getAccessPerm(1, level))) {
            accordion.addTab("Point of Sale", addPOSPanel(getAccessPerm(1, level)));
        }
        if (validateVisibility(getAccessPerm(2, level))) {
            accordion.addTab("Sales", addSalesPanel(getAccessPerm(2, level)));
        }
        if (validateVisibility(getAccessPerm(3, level))) {
            accordion.addTab("Inventory", addInventoryPanel(getAccessPerm(3, level)));
        }
        if (validateVisibility(getAccessPerm(4, level))) {
            accordion.addTab("Purchasing", addPurchasing(getAccessPerm(4, level)));
        }
//        if (validateVisibility(getAccessPerm(5, level))) {
//            accordion.addTab("Employees", addEmployeesPanel(getAccessPerm(5, level)));
//        }
//        if (validateVisibility(getAccessPerm(6, level))) {
//            accordion.addTab("Customers", addCustomersPanel(getAccessPerm(6, level)));
//        }

    }

    public boolean validateVisibility(boolean[] value) {
        int count = 0;
        for (int i = 0; i < value.length; i++) {
            if (value[i] == true) {
                count++;
            }
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean[] getAccessPerm(int group, int level) {
        ArrayList list = getGroupMembers(group);
        boolean[] values = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            values[i] = getTabAccess(Integer.parseInt(list.get(i).toString()), level);

        }
        return values;
    }

    public boolean getTabAccess(int item, int level) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT value FROM tbl_auth_role_assignments WHERE item_id=? AND level_id=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, item);
            pst.setInt(2, level);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(HomeTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public ArrayList getGroupMembers(int group) {
        ArrayList list = new ArrayList();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT id FROM tbl_auth_role_items WHERE role_group=?";
        try {
            conn = Database.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, group);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                rs.close();
                pst.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(HomeTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    /**
     * It can be useful to be notified when changes occur on the accordion. The
     * accordion can notify a listener when a tab is added, selected or removed.
     *
     * @param accordion
     */
    private void listenForChanges(JSCAccordion accordion) {
        accordion.addAccordionListener(new AccordionListener() {

            @Override
            public void accordionChanged(AccordionEvent accordionEvent) {
                //available fields on accordionEvent.

                switch (accordionEvent.getEventType()) {
                    case TAB_ADDED: {
                        //add your logic here to react to a tab being added.
                        break;
                    }
                    case TAB_REMOVED: {
                        //add your logic here to react to a tab being removed.
                        break;
                    }
                    case TAB_SELECTED: {
                        //add your logic here to react to a tab being selected.
                        break;
                    }
                }
            }
        });
    }

    /**
     * You can change the tab orientation to slide either vertically or
     * horizontally.
     *
     * @param accordion
     */
    private void changeTabOrientation(JSCAccordion accordion) {
        //will make the accordion slide from top to bottom
        accordion.setTabOrientation(TabOrientation.VERTICAL);

        //will make the accordion slide from left ro right
        //accordion.setTabOrientation(TabOrientation.HORITZONTAL);
    }

    /**
     * You can change the look and feel of the component by changing its ui. In
     * this example we will change the UI to the DarkSteelUI
     *
     * @param accordion
     */
    private void changeTheLookAndFeel(JSCAccordion accordion) {
        //We create a new instance of the UI
        AccordionUI newUI = BasicAccordionUI.createUI(accordion);
        //We set the UI
        accordion.setUI(newUI);

    }

    /**
     * The easiest way to customize a AccordionUI is to change the default
     * Background Painter, AccordionTabRenderers or tweak values on the
     * currently installed Background Painter, AccordionTabRenderers and UI.
     *
     * @param accordion
     */
    private void customizeLookAndFeel(JSCAccordion accordion) {
        BasicHorizontalTabRenderer tabRenderer = new BasicHorizontalTabRenderer(accordion);
        tabRenderer.setFontColor(Color.RED);
        accordion.setHorizontalAccordionTabRenderer(tabRenderer);

    }

    public JPanel addInventoryPanel(boolean[] visibilty) {
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton btnAddStock = new JButton();
        JPanel panel2 = new JPanel();
        JButton btnCurrStock = new JButton();
        JPanel panel3 = new JPanel();
        JButton btnStockAdj = new JButton();
        JPanel panel4 = new JPanel();
        JButton btnStockTransf = new JButton();
        panel.setMaximumSize(new Dimension(200, 236));
        panel.setMinimumSize(new Dimension(200, 236));
        panel1.setLayout(new GridBagLayout());
        btnAddStock.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnAddStock.setText("ADD STOCK");
        btnAddStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Inventory dialog = new Inventory(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 85;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel1.add(btnAddStock, gridBagConstraints);
        panel2.setLayout(new GridBagLayout());
        btnCurrStock.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnCurrStock.setText("VIEW CURRENT STOCK");
        btnCurrStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnCurrStock, gridBagConstraints);

        panel3.setLayout(new GridBagLayout());

        btnStockAdj.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnStockAdj.setText("STOCK ADJUSTMENTS");
        btnStockAdj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                StockAdjustments dialog = new StockAdjustments(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnStockAdj, gridBagConstraints);

        panel4.setLayout(new GridBagLayout());

        btnStockTransf.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnStockTransf.setText("STOCK TRANSFERS");
        btnStockTransf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                StockTransfers dialog = new StockTransfers(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnStockTransf, gridBagConstraints);
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }

    private JPanel addPOSPanel(boolean[] visibilty) {
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1;
        JPanel panel2;
        JPanel panel3;
        JPanel panel4;
        panel1 = new JPanel();
        JButton btnStartDay = new JButton();
        panel2 = new JPanel();
        JButton btnPastInv = new JButton();
        panel3 = new JPanel();
        JButton btnClock = new JButton();
        panel4 = new JPanel();
        JButton btnEndDay = new JButton();
        panel.setMaximumSize(new Dimension(200, 257));
        panel.setMinimumSize(new Dimension(200, 257));
        panel1.setLayout(new GridBagLayout());
        btnStartDay.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnStartDay.setText("START OF DAY");
        btnStartDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                StartDay dialog = new StartDay(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 67;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel1.add(btnStartDay, gridBagConstraints);

        panel2.setLayout(new GridBagLayout());

        btnPastInv.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnPastInv.setText("REFUND/ SALE VOIDING");
        btnPastInv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Voiding dialog = new Voiding(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnPastInv, gridBagConstraints);

        panel3.setLayout(new GridBagLayout());

        btnClock.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnClock.setText("CLOCK IN/OUT");
        btnClock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (dialog == null) {
                    dialog = new ClockInOut(parent, true);
                    dialog.setLocationRelativeTo(parent);
                    dialog.setVisible(true);
                } else {
                    dialog.setVisible(true);
                }
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 69;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnClock, gridBagConstraints);

        panel4.setLayout(new GridBagLayout());

        btnEndDay.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnEndDay.setText("END OF DAY");
        btnEndDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                EndDay dialog = new EndDay(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnEndDay, gridBagConstraints);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }

    private JPanel addEmployeesPanel(boolean[] visibilty) {

        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        JButton btnAddEmp = new JButton();
        JPanel panel2 = new JPanel();
        JButton btnCheckin = new JButton();
        JPanel panel3 = new JPanel();
        JButton btnAccessControl = new JButton();
        JPanel panel4 = new JPanel();
        JButton btnViewEmp = new JButton();
        panel.setMaximumSize(new Dimension(200, 236));
        panel.setMinimumSize(new Dimension(200, 236));
        panel1.setLayout(new GridBagLayout());
        btnAddEmp.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnAddEmp.setText("VIEW/ADD EMPLOYEES");
        btnAddEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel1.add(btnAddEmp, gridBagConstraints);
        panel2.setLayout(new GridBagLayout());
        btnCheckin.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnCheckin.setText("CHECKIN/ CHECKOUT");
        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 33;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnCheckin, gridBagConstraints);
        panel3.setLayout(new GridBagLayout());
        btnAccessControl.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnAccessControl.setText("USER ACCESS CONTROL");
        btnAccessControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AccessControl dialog = new AccessControl(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnAccessControl, gridBagConstraints);
        panel4.setLayout(new GridBagLayout());
        btnViewEmp.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnViewEmp.setText("CHANGE PASSWORD");
        btnViewEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnViewEmp, gridBagConstraints);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }

    private JPanel addCustomersPanel(boolean[] visibilty) {
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton btnCustPay = new JButton();
        JPanel panel2 = new JPanel();
        JButton btnViewCust = new JButton();
        JPanel panel3 = new JPanel();
        JButton btnMailList = new JButton();
        JPanel panel4 = new JPanel();
        JButton btnAddCust = new JButton();
        panel.setMaximumSize(new Dimension(200, 236));
        panel.setMinimumSize(new Dimension(200, 236));
        panel1.setLayout(new GridBagLayout());
        btnCustPay.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnCustPay.setText("CLIENT PAYMENTS");
        btnCustPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel1.add(btnCustPay, gridBagConstraints);

        panel2.setLayout(new GridBagLayout());

        btnViewCust.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnViewCust.setText("VIEW/ADD CUSTOMERS");
        btnViewCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Customers dialog = new Customers(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnViewCust, gridBagConstraints);

        panel3.setLayout(new GridBagLayout());

        btnMailList.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnMailList.setText("MAILING LIST");
        btnMailList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnMailList, gridBagConstraints);

        panel4.setLayout(new GridBagLayout());

        btnAddCust.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnAddCust.setText("CUSTOMER GROUPS");
        btnAddCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnAddCust, gridBagConstraints);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }

    private JPanel addPurchasing(boolean[] visibilty) {
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton btnPurchOrder = new JButton();
        JPanel panel2 = new JPanel();
        JButton btnSuppPayments = new JButton();
        JPanel panel3 = new JPanel();
        JButton btnAddSupp = new JButton();
        JPanel panel4 = new JPanel();
        JButton btnPurchInv = new JButton();
        panel.setMaximumSize(new Dimension(200, 236));
        panel.setMinimumSize(new Dimension(200, 236));
        panel1.setLayout(new GridBagLayout());
        btnPurchOrder.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnPurchOrder.setText("PURCHASE ORDER");
        btnPurchOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                PurchaseOrder dialog = new PurchaseOrder(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel1.add(btnPurchOrder, gridBagConstraints);
        panel2.setLayout(new GridBagLayout());
        btnSuppPayments.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnSuppPayments.setText("PAYMENTS TO SUPPLIERS");
        btnSuppPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnSuppPayments, gridBagConstraints);

        panel3.setLayout(new GridBagLayout());

        btnAddSupp.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnAddSupp.setText("ADD/MODIFY SUPPLIERS");
        btnAddSupp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Suppliers dialog = new Suppliers(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnAddSupp, gridBagConstraints);

        panel4.setLayout(new GridBagLayout());

        btnPurchInv.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnPurchInv.setText("PURCHASE INVOICING");
        btnPurchInv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                PurchaseInvoice dialog = new PurchaseInvoice(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnPurchInv, gridBagConstraints);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }

    private JPanel addSalesPanel(boolean[] visibilty) {
        GridBagConstraints gridBagConstraints;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton btnInvTransact = new JButton();
        JPanel panel2 = new JPanel();
        JButton btnDebtorPay = new JButton();
        JPanel panel3 = new JPanel();
        JButton btnRefund = new JButton();
        JPanel panel4 = new JPanel();
        JButton btnQuotation = new JButton();

        panel.setMaximumSize(new Dimension(200, 236));
        panel.setMinimumSize(new Dimension(200, 236));

        panel1.setLayout(new GridBagLayout());

        btnInvTransact.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnInvTransact.setText("PAST TRANSACTIONS");
        btnInvTransact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                PastSales dialog = new PastSales(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel1.add(btnInvTransact, gridBagConstraints);

        panel2.setLayout(new GridBagLayout());

        btnDebtorPay.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnDebtorPay.setText("DEBTOR PAYMENTS");
        btnDebtorPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel2.add(btnDebtorPay, gridBagConstraints);

        panel3.setLayout(new GridBagLayout());

        btnRefund.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnRefund.setText("VIEW/ADD CUSTOMERS ");
        btnRefund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Customers dialog = new Customers(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 12, 10);
        panel3.add(btnRefund, gridBagConstraints);

        panel4.setLayout(new GridBagLayout());

        btnQuotation.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnQuotation.setText("QUOTATION/ INVOICING");
        btnQuotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                SalesDialog dialog = new SalesDialog(parent, false);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(11, 10, 11, 10);
        panel4.add(btnQuotation, gridBagConstraints);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(246, 249, 251));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setVisible(visibilty[0]);
        panel2.setVisible(visibilty[1]);
        panel3.setVisible(visibilty[2]);
        panel4.setVisible(visibilty[3]);
        return panel;
    }
}
