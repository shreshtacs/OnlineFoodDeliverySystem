package src;

import java.sql.*;
import javax.swing.*;
import java.awt.*;


public class f4 extends javax.swing.JFrame {
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public f4() {
        initComponents();
        setTitle("Food Ordering System");
        setSize(900, 600);
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
        // Initialize components
        jPanel1 = new javax.swing.JPanel();
        orderButton = new javax.swing.JButton();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Main panel setup
        jPanel1.setLayout(null);
        jPanel1.setBounds(0, 0, 900, 600);
        jPanel1.setOpaque(false);

        // Order button
        orderButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        orderButton.setText("ORDER NOW");
        orderButton.setBackground(new Color(255, 102, 102));
        orderButton.setForeground(Color.WHITE);
        orderButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        orderButton.addActionListener(evt -> orderButtonActionPerformed(evt));
        jPanel1.add(orderButton);
        orderButton.setBounds(300, 150, 300, 80);

        // Background image
        ImageIcon imgIcon = new ImageIcon("img\\orderNow.jpg");
        Image img = imgIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(img));
        backgroundLabel.setBounds(0, 0, 900, 600);
        jPanel1.add(backgroundLabel);

        getContentPane().add(jPanel1);
    }

    private void orderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new FoodOrderSystem().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new f4().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton orderButton;
    private javax.swing.JLabel backgroundLabel;
}
