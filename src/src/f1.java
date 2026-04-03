package src;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class f1 extends javax.swing.JFrame {
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private javax.swing.JPanel welcomePanel;
    private javax.swing.JPanel customerLoginPanel;
    private javax.swing.JPanel adminLoginPanel;

    private javax.swing.JTextField customerEmailField;
    private javax.swing.JPasswordField customerPasswordField;
    private javax.swing.JTextField adminIdField;
    private javax.swing.JPasswordField adminPasswordField;

    private javax.swing.JButton customerLoginButton;
    private javax.swing.JButton adminLoginButton;
    private javax.swing.JButton proceedToCustomerLoginButton;
    private javax.swing.JButton proceedToAdminLoginButton;

    public f1() {
        initComponents();
        setTitle("Online Food Delivery");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "123");
            JOptionPane.showMessageDialog(null, "Connected to database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }

        customerLoginPanel.setVisible(false);
        adminLoginPanel.setVisible(false);
    }

    private void initComponents() {
        welcomePanel = new javax.swing.JPanel();
        customerLoginPanel = new javax.swing.JPanel();
        adminLoginPanel = new javax.swing.JPanel();

        // Customer Login Fields
        customerEmailField = new javax.swing.JTextField();
        customerPasswordField = new javax.swing.JPasswordField();
        customerLoginButton = new javax.swing.JButton();

        // Admin Login Fields
        adminIdField = new javax.swing.JTextField();
        adminPasswordField = new javax.swing.JPasswordField();
        adminLoginButton = new javax.swing.JButton();

        // Navigation Buttons
        proceedToCustomerLoginButton = new javax.swing.JButton();
        proceedToAdminLoginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Welcome Panel
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(0, 0, 900, 600);

        // Set Welcome Panel Background Image
        ImageIcon welcomeIcon = new ImageIcon("img\\entry.jpg");
        Image welcomeImg = welcomeIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        JLabel welcomeBackground = new JLabel(new ImageIcon(welcomeImg));
        welcomeBackground.setBounds(0, 0, 900, 600);

        proceedToCustomerLoginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        proceedToCustomerLoginButton.setText("Customer Login");
        proceedToCustomerLoginButton.setBounds(200, 450, 200, 50);
        proceedToCustomerLoginButton.addActionListener(evt -> showCustomerLoginPanel());
        welcomePanel.add(proceedToCustomerLoginButton);

        proceedToAdminLoginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        proceedToAdminLoginButton.setText("Admin Login");
        proceedToAdminLoginButton.setBounds(500, 450, 200, 50);
        proceedToAdminLoginButton.addActionListener(evt -> showAdminLoginPanel());
        welcomePanel.add(proceedToAdminLoginButton);

        welcomePanel.add(welcomeBackground); // Add background image to welcome panel

        // Customer Login Panel
        customerLoginPanel.setLayout(null);
        customerLoginPanel.setBounds(0, 0, 900, 600);

        // Set Customer Login Panel Background Image
        ImageIcon customerIcon = new ImageIcon("img\\customerLogin.jpg"); // Set your
                                                                                                        // background
                                                                                                        // image path
                                                                                                        // here
        Image customerImg = customerIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        JLabel customerBackground = new JLabel(new ImageIcon(customerImg));
        customerBackground.setBounds(0, 0, 900, 600);

        JLabel customerEmailLabel = new JLabel("Email:");
        customerEmailLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        customerEmailLabel.setBounds(300, 200, 80, 30);
        customerEmailLabel.setBackground(Color.WHITE); // Set background color
        customerEmailLabel.setOpaque(true);
        customerLoginPanel.add(customerEmailLabel);

        customerEmailField.setBounds(400, 200, 200, 30);
        customerEmailField.setBackground(Color.LIGHT_GRAY); // Set background color for fields
        customerLoginPanel.add(customerEmailField);

        JLabel customerPasswordLabel = new JLabel("Password:");
        customerPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        customerPasswordLabel.setBounds(300, 250, 80, 30);
        customerPasswordLabel.setBackground(Color.WHITE); // Set background color
        customerPasswordLabel.setOpaque(true);
        customerLoginPanel.add(customerPasswordLabel);

        customerPasswordField.setBounds(400, 250, 200, 30);
        customerPasswordField.setBackground(Color.LIGHT_GRAY); // Set background color for fields
        customerLoginPanel.add(customerPasswordField);

        customerLoginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        customerLoginButton.setText("Login");
        customerLoginButton.setBounds(400, 300, 100, 30);
        customerLoginButton.addActionListener(evt -> customerLoginActionPerformed());
        customerLoginPanel.add(customerLoginButton);

        customerLoginPanel.add(customerBackground); // Add background image to customer login panel


        adminLoginPanel.setLayout(null);
        adminLoginPanel.setBounds(0, 0, 900, 600);

        // Set Admin Login Panel Background Image
        ImageIcon adminIcon = new ImageIcon("img\\adminLogin.jpg");
        Image adminImg = adminIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        JLabel adminBackground = new JLabel(new ImageIcon(adminImg));
        adminBackground.setBounds(0, 0, 900, 600);

        JLabel adminIdLabel = new JLabel("Admin name:");
        adminIdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        adminIdLabel.setBackground(Color.WHITE);
        adminIdLabel.setOpaque(true);
        adminIdLabel.setBounds(300, 200, 100, 30);
        adminLoginPanel.add(adminIdLabel);

        adminIdField = new JTextField();
        adminIdField.setBackground(Color.LIGHT_GRAY);
        adminIdField.setBounds(400, 200, 200, 30);
        adminLoginPanel.add(adminIdField);

        JLabel adminPasswordLabel = new JLabel("Password:");
        adminPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        adminPasswordLabel.setBackground(Color.WHITE);
        adminPasswordLabel.setOpaque(true);
        adminPasswordLabel.setBounds(300, 250, 100, 30);
        adminLoginPanel.add(adminPasswordLabel);

        adminPasswordField = new JPasswordField();
        adminPasswordField.setBackground(Color.LIGHT_GRAY);
        adminPasswordField.setBounds(400, 250, 200, 30);
        adminLoginPanel.add(adminPasswordField);

        adminLoginButton = new JButton("Login");
        adminLoginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        adminLoginButton.setBounds(400, 300, 100, 30);
        adminLoginButton.addActionListener(evt -> adminLoginActionPerformed());
        adminLoginPanel.add(adminLoginButton);

        // Add background last so it appears behind other components
        adminLoginPanel.add(adminBackground);

        // Add panels to content pane
        getContentPane().setLayout(new CardLayout());
        getContentPane().add(welcomePanel, "welcome");
        getContentPane().add(customerLoginPanel, "customer");
        getContentPane().add(adminLoginPanel, "admin");

        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "welcome");
    }

    private void showCustomerLoginPanel() {
        welcomePanel.setVisible(false);
        customerLoginPanel.setVisible(true);
    }

    private void showAdminLoginPanel() {
        welcomePanel.setVisible(false);
        adminLoginPanel.setVisible(true);
    }

    private void customerLoginActionPerformed() {
        try {
            String email = customerEmailField.getText().trim();
            String password = new String(customerPasswordField.getPassword());

            String sql = "SELECT * FROM customer WHERE emailid = ? AND pwd = ?";

            pst = Con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Customer Login Successful!");
                new f4().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Customer Credentials");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }

    private void adminLoginActionPerformed() {
        String adminId = adminIdField.getText().trim();
        String password = new String(adminPasswordField.getPassword()).trim();

        if (adminId.equals("admin1") && password.equals("cvb")) {
            JOptionPane.showMessageDialog(this, "Admin Login Successful!");
            new f6().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Admin Credentials");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new f1().setVisible(true));
    }
}
