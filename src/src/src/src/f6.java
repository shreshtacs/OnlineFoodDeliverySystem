package src;
import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class f6 extends javax.swing.JFrame {
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private JButton jButton1, jButton2, jButton11, viewOrdersButton;
    private JTextArea jTextArea;
    private JLabel backgroundLabel;

    public f6() {
        initComponents();
        setTitle("EMPLOYEE INFORMATION");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "123");
            JOptionPane.showMessageDialog(null, "Connected to Database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
        }
    }
    
    private void initComponents() {
        // Create buttons
        jButton1 = new JButton("Customer options");
        jButton2 = new JButton("View Employee Info");
        jButton11 = new JButton("Customer Data");
        viewOrdersButton = new JButton("View Orders");

        // Create a text area to display information
        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Create the background image
        backgroundLabel = new JLabel();
        ImageIcon mainBgIcon = new ImageIcon("img\\adminOptions.jpg");
        Image mainBgImg = mainBgIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(mainBgImg));
        backgroundLabel.setBounds(0, 0, 900, 600);

        // Set layout
        setLayout(null);

        // Add buttons with updated positions
        jButton1.setBounds(50, 50, 200, 40);
        jButton2.setBounds(50, 100, 200, 40);
        jButton11.setBounds(50, 150, 200, 40);
        viewOrdersButton.setBounds(50, 200, 200, 40); // New button position

        add(jButton1);
        add(jButton2);
        add(jButton11);
        add(viewOrdersButton);

        // Add text area with scroll pane
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setBounds(300, 50, 550, 500);
        add(scrollPane);

        // Add background
        add(backgroundLabel);

        // Set button actions
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
        jButton11.addActionListener(evt -> jButton11ActionPerformed(evt));
        viewOrdersButton.addActionListener(evt -> viewOrdersActionPerformed(evt));

        // Set JFrame properties
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void viewOrdersActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Query to get orders with their items
            String sql = 
                "SELECT o.order_id, o.order_date, o.total_amount, " +
                "oi.item_name, oi.quantity, oi.price " +
                "FROM orders o " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "ORDER BY o.order_date DESC, o.order_id, oi.item_name";
            
            pst = Con.prepareStatement(sql);
            rs = pst.executeQuery();

            StringBuilder orderInfo = new StringBuilder();
            orderInfo.append(String.format("%-10s %-20s %-15s %-20s %-10s %-10s\n",
                "Order ID", "Date", "Total Amount", "Item", "Quantity", "Price"));
            orderInfo.append("=".repeat(85)).append("\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int currentOrderId = -1;

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                
                // Add a blank line between different orders
                if (currentOrderId != -1 && currentOrderId != orderId) {
                    orderInfo.append("\n");
                }
                
                orderInfo.append(String.format("%-10d %-20s ₹%-14.2f %-20s %-10d ₹%-10.2f\n",
                    orderId,
                    dateFormat.format(rs.getTimestamp("order_date")),
                    rs.getDouble("total_amount"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")));
                
                currentOrderId = orderId;
            }

            if (orderInfo.toString().equals("")) {
                jTextArea.setText("No orders found in the database.");
            } else {
                jTextArea.setText(orderInfo.toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Order Data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Navigate to CustomerForm
        new CustomerForm().setVisible(true);
        this.dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // View employee information
        try {
            String sql = "SELECT * FROM EMPLOYEE";
            pst = Con.prepareStatement(sql);
            rs = pst.executeQuery();

            // Create a header for the table
            StringBuilder employeeInfo = new StringBuilder();
            employeeInfo.append(String.format("%-10s %-15s %-15s %-30s %-15s\n", "ID", "First Name", "Last Name",
                    "Email", "Phone"));
            employeeInfo.append("=".repeat(85)).append("\n");

            // Add rows for each employee
            while (rs.next()) {
                employeeInfo.append(String.format(
                        "%-10d %-15s %-15s %-30s %-15s\n",
                        rs.getInt("EMPID"),
                        rs.getString("FNAME"),
                        rs.getString("LNAME"),
                        rs.getString("EMAILID"),
                        rs.getString("PHONENO")));
            }

            // Set formatted data to text area
            jTextArea.setText(employeeInfo.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Employee Data: " + e.getMessage());
        }
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
        // Navigate back to the customer data form
        new CUSTOMER().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new f6().setVisible(true));
    }
}
