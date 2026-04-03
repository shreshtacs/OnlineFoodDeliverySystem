package src;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class CustomerForm extends javax.swing.JFrame {
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Declare components
    private JTextField jTextFieldCustID, jTextFieldFName, jTextFieldLName, jTextFieldEmail, jTextFieldAddress,
            jTextFieldStreet, jTextFieldPincode, jTextFieldPhoneNo, jTextFieldAllergy;
    private JPasswordField jPasswordFieldPwd;
    private JComboBox<String> jComboBoxGender;
    private JButton jButtonAdd, jButtonDelete, jButtonEdit, jButtonSearch, jButtonBack;
    private JPanel mainPanel;
    private JLabel backgroundLabel;

    public CustomerForm() {
        initComponents();
        setTitle("Customer Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "123");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
        }
    }

    private void initComponents() {
        // Create main panel with null layout for absolute positioning
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 600);

        // Create semi-transparent panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(50, 50, 700, 500);
        formPanel.setBackground(new Color(255, 255, 255, 220));

        // Initialize components
        jTextFieldCustID = new JTextField();
        jTextFieldFName = new JTextField();
        jTextFieldLName = new JTextField();
        jTextFieldEmail = new JTextField();
        jPasswordFieldPwd = new JPasswordField();
        jTextFieldAddress = new JTextField();
        jTextFieldStreet = new JTextField();
        jTextFieldPincode = new JTextField();
        jTextFieldPhoneNo = new JTextField();
        jTextFieldAllergy = new JTextField();
        jComboBoxGender = new JComboBox<>(new String[] { "Male", "Female", "Other" });

        // Initialize buttons with custom styling
        jButtonAdd = createStyledButton("Add Customer", new Color(46, 204, 113));
        jButtonDelete = createStyledButton("Delete Customer", new Color(231, 76, 60));
        jButtonEdit = createStyledButton("Edit Customer", new Color(52, 152, 219));
        jButtonSearch = createStyledButton("Search Customer", new Color(155, 89, 182));
        jButtonBack = createStyledButton("Back", new Color(149, 165, 166));

        // Initialize labels
        JLabel[] labels = createLabels();

        // Set component bounds
        int startY = 30;
        int labelX = 50;
        int fieldX = 200;
        int buttonX = 500;
        int height = 30;
        int gap = 35;

        // Position components
        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(labelX, startY + (i * gap), 100, height);
            formPanel.add(labels[i]);
        }

        // Position text fields and combo box
        jTextFieldCustID.setBounds(fieldX, startY, 150, height);
        jTextFieldFName.setBounds(fieldX, startY + gap, 150, height);
        jTextFieldLName.setBounds(fieldX, startY + (2 * gap), 150, height);
        jTextFieldEmail.setBounds(fieldX, startY + (3 * gap), 150, height);
        jPasswordFieldPwd.setBounds(fieldX, startY + (4 * gap), 150, height);
        jTextFieldAddress.setBounds(fieldX, startY + (5 * gap), 150, height);
        jTextFieldStreet.setBounds(fieldX, startY + (6 * gap), 150, height);
        jTextFieldPincode.setBounds(fieldX, startY + (7 * gap), 150, height);
        jTextFieldPhoneNo.setBounds(fieldX, startY + (8 * gap), 150, height);
        jTextFieldAllergy.setBounds(fieldX, startY + (9 * gap), 150, height);
        jComboBoxGender.setBounds(fieldX, startY + (10 * gap), 150, height);

        // Position buttons
        jButtonAdd.setBounds(buttonX, startY, 150, height);
        jButtonDelete.setBounds(buttonX, startY + gap, 150, height);
        jButtonEdit.setBounds(buttonX, startY + (2 * gap), 150, height);
        jButtonSearch.setBounds(buttonX, startY + (3 * gap), 150, height);
        jButtonBack.setBounds(10, 10, 80, 25);

        // Add components to form panel
        addComponentsToPanel(formPanel);

        // Add form panel to main panel
        mainPanel.add(formPanel);
        mainPanel.add(jButtonBack);

        // Set background image
        try {
            ImageIcon imgIcon = new ImageIcon("img\\customerEditOptions.jpg");
            Image img = imgIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            backgroundLabel = new JLabel(new ImageIcon(img));
            backgroundLabel.setBounds(0, 0, 800, 600);
            mainPanel.add(backgroundLabel);
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        // Set frame properties
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(mainPanel);

        // Add action listeners
        addActionListeners();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        return button;
    }

    private JLabel[] createLabels() {
        String[] labelTexts = { "Customer ID:", "First Name:", "Last Name:", "Email ID:", "Password:",
                "Address:", "Street:", "Pincode:", "Phone No:", "Allergy Info:", "Gender:" };
        JLabel[] labels = new JLabel[labelTexts.length];

        for (int i = 0; i < labelTexts.length; i++) {
            labels[i] = new JLabel(labelTexts[i]);
            labels[i].setFont(new Font("Tahoma", Font.BOLD, 12));
        }

        return labels;
    }

    private void addComponentsToPanel(JPanel panel) {
        panel.add(jTextFieldCustID);
        panel.add(jTextFieldFName);
        panel.add(jTextFieldLName);
        panel.add(jTextFieldEmail);
        panel.add(jPasswordFieldPwd);
        panel.add(jTextFieldAddress);
        panel.add(jTextFieldStreet);
        panel.add(jTextFieldPincode);
        panel.add(jTextFieldPhoneNo);
        panel.add(jTextFieldAllergy);
        panel.add(jComboBoxGender);
        panel.add(jButtonAdd);
        panel.add(jButtonDelete);
        panel.add(jButtonEdit);
        panel.add(jButtonSearch);
    }

    private void addActionListeners() {
        jButtonAdd.addActionListener(evt -> jButtonAddActionPerformed(evt));
        jButtonDelete.addActionListener(evt -> jButtonDeleteActionPerformed(evt));
        jButtonEdit.addActionListener(evt -> jButtonEditActionPerformed(evt));
        jButtonSearch.addActionListener(evt -> jButtonSearchActionPerformed(evt));
        jButtonBack.addActionListener(evt -> jButtonBackActionPerformed(evt));
    }

    private void clearFields() {
        jTextFieldCustID.setText("");
        jTextFieldFName.setText("");
        jTextFieldLName.setText("");
        jTextFieldEmail.setText("");
        jPasswordFieldPwd.setText("");
        jTextFieldAddress.setText("");
        jTextFieldStreet.setText("");
        jTextFieldPincode.setText("");
        jTextFieldPhoneNo.setText("");
        jTextFieldAllergy.setText("");
        jComboBoxGender.setSelectedIndex(0);
    }
    
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {
        // Close the current form
        this.dispose();

        // Open the F6 form
        f6 f6Form = new f6();
        f6Form.setVisible(true);
    }

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Validation
            if (jTextFieldCustID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer ID");
                return;
            }

            String sql = "INSERT INTO CUSTOMER (CUSTID, FNAME, LNAME, EMAILID, PWD, ADDRESS, STREET, PINCODE, GENDER, PHONENO, ALLERGY) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pst = Con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(jTextFieldCustID.getText()));
            pst.setString(2, jTextFieldFName.getText());
            pst.setString(3, jTextFieldLName.getText());
            pst.setString(4, jTextFieldEmail.getText());
            pst.setString(5, new String(jPasswordFieldPwd.getPassword()));
            pst.setString(6, jTextFieldAddress.getText());
            pst.setString(7, jTextFieldStreet.getText());
            pst.setInt(8, Integer.parseInt(jTextFieldPincode.getText()));
            pst.setString(9, jComboBoxGender.getSelectedItem().toString());
            pst.setLong(10, Long.parseLong(jTextFieldPhoneNo.getText()));
            pst.setString(11, jTextFieldAllergy.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer Added Successfully");
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, Pincode, and Phone Number");
        }
    }

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (jTextFieldCustID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer ID to delete");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this customer?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM CUSTOMER WHERE CUSTID = ?";
                pst = Con.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(jTextFieldCustID.getText()));

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Customer Deleted Successfully");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "No customer found with this ID");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Customer ID");
        }
    }

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (jTextFieldCustID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer ID to update");
                return;
            }

            String sql = "UPDATE CUSTOMER SET FNAME=?, LNAME=?, EMAILID=?, PWD=?, ADDRESS=?, " +
                        "STREET=?, PINCODE=?, GENDER=?, PHONENO=?, ALLERGY=? WHERE CUSTID=?";

            pst = Con.prepareStatement(sql);
            pst.setString(1, jTextFieldFName.getText());
            pst.setString(2, jTextFieldLName.getText());
            pst.setString(3, jTextFieldEmail.getText());
            pst.setString(4, new String(jPasswordFieldPwd.getPassword()));
            pst.setString(5, jTextFieldAddress.getText());
            pst.setString(6, jTextFieldStreet.getText());
            pst.setInt(7, Integer.parseInt(jTextFieldPincode.getText()));
            pst.setString(8, jComboBoxGender.getSelectedItem().toString());
            pst.setLong(9, Long.parseLong(jTextFieldPhoneNo.getText()));
            pst.setString(10, jTextFieldAllergy.getText());
            pst.setInt(11, Integer.parseInt(jTextFieldCustID.getText()));

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Customer Updated Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No customer found with this ID");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, Pincode, and Phone Number");
        }
    }

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (jTextFieldCustID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer ID to search");
                return;
            }

            String sql = "SELECT * FROM CUSTOMER WHERE CUSTID = ?";
            pst = Con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(jTextFieldCustID.getText()));
            rs = pst.executeQuery();

            if (rs.next()) {
                jTextFieldFName.setText(rs.getString("FNAME"));
                jTextFieldLName.setText(rs.getString("LNAME"));
                jTextFieldEmail.setText(rs.getString("EMAILID"));
                jPasswordFieldPwd.setText(rs.getString("PWD"));
                jTextFieldAddress.setText(rs.getString("ADDRESS"));
                jTextFieldStreet.setText(rs.getString("STREET"));
                jTextFieldPincode.setText(rs.getString("PINCODE"));
                jComboBoxGender.setSelectedItem(rs.getString("GENDER"));
                jTextFieldPhoneNo.setText(rs.getString("PHONENO"));
                jTextFieldAllergy.setText(rs.getString("ALLERGY"));
            
 } else {
                JOptionPane.showMessageDialog(null, "No Customer Found with ID: " + jTextFieldCustID.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Searching Customer: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new CustomerForm().setVisible(true));
    }
}
