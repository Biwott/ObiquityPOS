package com.twigasoft.main;

import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.TabOrientation;
import com.javaswingcomponents.accordion.listener.AccordionEvent;
import com.javaswingcomponents.accordion.listener.AccordionListener;
import com.javaswingcomponents.accordion.plaf.AccordionUI;
import com.javaswingcomponents.accordion.plaf.basic.BasicAccordionUI;
import com.javaswingcomponents.accordion.plaf.basic.BasicHorizontalTabRenderer;
import com.twigasoft.dialogs.Categories;
import com.twigasoft.dialogs.Departments;
import com.twigasoft.dialogs.Employees;
import com.twigasoft.dialogs.StoreLocation;
import com.twigasoft.dialogs.Taxes;
import com.twigasoft.settings.AccessControl;
import com.twigasoft.settings.ChangePassword;
import com.twigasoft.settings.CreateAccount;
import com.twigasoft.settings.SystemSettings;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsTab extends JPanel {

    MainApp parent;

    public SettingsTab(MainApp parent, int level) {
        this.parent = parent;
        JSCAccordion accordion = new JSCAccordion();
        howToAddTabs(accordion);
        howToListenForChanges(accordion);
        howToChangeTabOrientation(accordion);
        howToChangeTheLookAndFeel(accordion);
        howToCustomizeALookAndFeel(accordion);
        setLayout(new GridLayout(1, 1, 30, 30));
        add(accordion);
    }

    /**
     * When adding a tab to the accordion, you must supply text for the tab as
     * well as a component that will be used as the content contained for tab.
     * The example below will add five tabs The first tab will contain the text
     * "Tab 1" and a JButton The second tab will contain the text "Tab 2" and a
     * JLabel The third tab will contain the text "Tab 3" and a JTree wrapped in
     * a JScrollpane The fourth tab will contain the text "Tab 4" and an empty
     * JPanel, with opaque = true The fifth tab will contain the text "Tab 5"
     * and an empty JPanel with opaque = false
     *
     * The key thing to note is the effect of adding an opaque or non opaque
     * component to the accordion.
     *
     * @param accordion
     */
    private void howToAddTabs(JSCAccordion accordion) {
        JPanel transparentPanel = new JPanel();
        transparentPanel.setOpaque(false);
        transparentPanel.setBackground(Color.GRAY);

        JPanel opaquePanel = new JPanel();
        opaquePanel.setOpaque(true);
        opaquePanel.setEnabled(false);
        opaquePanel.setBackground(Color.WHITE);
        //        if (validateVisibility(getAccessPerm(5, level))) {
//            accordion.addTab("Employees", addEmployeesPanel(getAccessPerm(5, level)));
//        }
//        if (validateVisibility(getAccessPerm(6, level))) {
//            accordion.addTab("Customers", addCustomersPanel(getAccessPerm(6, level)));
//        }
        accordion.addTab("Employees", addEmployeesPanel());
        accordion.addTab("Stock Settings", addStockSettingsPanel());
        accordion.addTab("Reporting", opaquePanel);
        accordion.addTab("System Settings", addSystemSettingsPanel());
    }

    /**
     * It can be useful to be notified when changes occur on the accordion. The
     * accordion can notify a listener when a tab is added, selected or removed.
     *
     * @param accordion
     */
    private void howToListenForChanges(JSCAccordion accordion) {
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
    private void howToChangeTabOrientation(JSCAccordion accordion) {
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
    private void howToChangeTheLookAndFeel(JSCAccordion accordion) {
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
    private void howToCustomizeALookAndFeel(JSCAccordion accordion) {
        BasicHorizontalTabRenderer tabRenderer = new BasicHorizontalTabRenderer(accordion);
        tabRenderer.setFontColor(Color.RED);
        accordion.setHorizontalAccordionTabRenderer(tabRenderer);

    }

    public JPanel addEmployeesPanel() {
        JPanel panel = new JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        JPanel panel1 = new javax.swing.JPanel();
        final JButton btnEditEmp = new javax.swing.JButton();
        JPanel panel2 = new javax.swing.JPanel();
        final JButton btnAccessCtrl = new javax.swing.JButton();
        JPanel panel3 = new javax.swing.JPanel();
        final JButton btnCreateUsr = new javax.swing.JButton();
        JPanel panel4 = new javax.swing.JPanel();
        final JButton btnStockTransf = new javax.swing.JButton();
        panel.setMaximumSize(new java.awt.Dimension(200, 236));
        panel.setMinimumSize(new java.awt.Dimension(200, 236));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel1.setLayout(new java.awt.GridBagLayout());
        btnEditEmp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditEmp.setText("VIEW/ EDIT EMPLOYEES");
        btnEditEmp.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEmp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Employees dialog = new Employees(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                btnEditEmp.setCursor(Cursor.getDefaultCursor());

            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel1.add(btnEditEmp, gridBagConstraints);
        panel2.setLayout(new java.awt.GridBagLayout());
        btnAccessCtrl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAccessCtrl.setText("USER ACCESS CONTROL");
        btnAccessCtrl.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccessCtrl.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                AccessControl dialog = new AccessControl(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                btnAccessCtrl.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel2.add(btnAccessCtrl, gridBagConstraints);
        panel3.setLayout(new java.awt.GridBagLayout());
        btnCreateUsr.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCreateUsr.setText("CREATE USER ACCOUNT");
        btnCreateUsr.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUsr.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CreateAccount dialog = new CreateAccount(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                btnCreateUsr.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel3.add(btnCreateUsr, gridBagConstraints);
        panel4.setLayout(new java.awt.GridBagLayout());
        btnStockTransf.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnStockTransf.setText("CHANGE PASSWORD");
        btnStockTransf.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockTransf.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ChangePassword dialog = new ChangePassword(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                btnStockTransf.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel4.add(btnStockTransf, gridBagConstraints);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(255, 255, 217));
        return panel;
    }

    public JPanel addSystemSettingsPanel() {

        JPanel panel = new JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        JPanel panel1 = new javax.swing.JPanel();
        final JButton btnHardSetup = new javax.swing.JButton();
        JPanel panel2 = new javax.swing.JPanel();
        final JButton btnCompSetup = new javax.swing.JButton();
        JPanel panel3 = new javax.swing.JPanel();
        final JButton btnImport = new javax.swing.JButton();
        JPanel panel4 = new javax.swing.JPanel();
        final JButton btnPreferences = new javax.swing.JButton();
        panel.setMaximumSize(new java.awt.Dimension(200, 236));
        panel.setMinimumSize(new java.awt.Dimension(200, 236));
        panel1.setLayout(new java.awt.GridBagLayout());
        btnHardSetup.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnHardSetup.setText("HARDWARE SETUP");
        btnHardSetup.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHardSetup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                final SystemSettings dialog = new SystemSettings(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.jTabbedPane1.setSelectedIndex(3);
                dialog.setVisible(true);
                btnHardSetup.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 12, 10);
        panel1.add(btnHardSetup, gridBagConstraints);
        panel2.setLayout(new java.awt.GridBagLayout());
        btnCompSetup.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCompSetup.setText("COMPANY SETUP");
        btnCompSetup.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompSetup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                final SystemSettings dialog = new SystemSettings(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.jTabbedPane1.setSelectedIndex(0);
                dialog.setVisible(true);
                btnCompSetup.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel2.add(btnCompSetup, gridBagConstraints);
        panel3.setLayout(new java.awt.GridBagLayout());
        btnImport.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnImport.setText("IMPORT/ EXPORT");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 12, 10);
        panel3.add(btnImport, gridBagConstraints);
        panel4.setLayout(new java.awt.GridBagLayout());
        btnPreferences.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPreferences.setText("POS PREFERENCES");
        btnPreferences.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreferences.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                final SystemSettings dialog = new SystemSettings(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.jTabbedPane1.setSelectedIndex(4);
                dialog.setVisible(true);
                btnPreferences.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel4.add(btnPreferences, gridBagConstraints);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        return panel;
    }

    public JPanel addStockSettingsPanel() {
        JPanel panel = new JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        JPanel panel1 = new javax.swing.JPanel();
        final JButton btnEditCat = new javax.swing.JButton();
        JPanel panel2 = new javax.swing.JPanel();
        final JButton btnEditDeps = new javax.swing.JButton();
        JPanel panel3 = new javax.swing.JPanel();
        final JButton btnEditLocs = new javax.swing.JButton();
        JPanel panel4 = new javax.swing.JPanel();
        final JButton btnEditTax = new javax.swing.JButton();
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        setMaximumSize(new java.awt.Dimension(200, 236));
        setMinimumSize(new java.awt.Dimension(200, 236));
        panel1.setLayout(new java.awt.GridBagLayout());
        btnEditCat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditCat.setText("ADD/MODIFY CATEGORIES");
        btnEditCat.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCat.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Categories dialog = new Categories(parent, true);
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                btnEditCat.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel1.add(btnEditCat, gridBagConstraints);
        panel2.setLayout(new java.awt.GridBagLayout());
        btnEditDeps.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditDeps.setText("EDIT DEPARTMENTS");
        btnEditDeps.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDeps.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Departments dialog = new Departments(parent, true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                btnEditDeps.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel2.add(btnEditDeps, gridBagConstraints);
        panel3.setLayout(new java.awt.GridBagLayout());
        btnEditLocs.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditLocs.setText("EDIT STORE LOCATIONS");
        btnEditLocs.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLocs.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                StoreLocation dialog = new StoreLocation(parent, true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                btnEditLocs.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel3.add(btnEditLocs, gridBagConstraints);
        panel4.setLayout(new java.awt.GridBagLayout());
        btnEditTax.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditTax.setText("TAX SETTINGS");
        btnEditTax.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTax.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Taxes dialog = new Taxes(parent, true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                btnEditTax.setCursor(Cursor.getDefaultCursor());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 69;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        panel4.add(btnEditTax, gridBagConstraints);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
        );
        panel.setBackground(new java.awt.Color(255, 255, 217));
        return panel;
    }
}
