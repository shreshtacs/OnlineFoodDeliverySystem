package src;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CUSTOMER extends javax.swing.JFrame {
        Connection Con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        private JButton jButton1, jButton2;
        private JPanel jPanel1;
        private JScrollPane jScrollPane1;
        private JTable jTable1;
        private JLabel backgroundLabel;

        public CUSTOMER() {
                initComponents();
                setTitle("CUSTOMER");

                DefaultTableModel DM = (DefaultTableModel) jTable1.getModel();
                DM.setRowCount(0);

                try {
                        String sql = "SELECT * FROM CUSTOMER";
                        Class.forName("oracle.jdbc.OracleDriver");
                        Con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "123");
                        JOptionPane.showMessageDialog(null, "Connected");

                        String colhead[] = {
                                        "FNAME", "LNAME", "CUSTID", "EMAILID", "PWD",
                                        "ADDRESS", "STREET", "PINCODE", "GENDER",
                                        "PHONENO", "ALLERGY"
                        };
                        DM.setColumnIdentifiers(colhead);

                        Statement stmt = Con.createStatement();
                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                                Object[] obj = new Object[colhead.length];
                                for (int i = 0; i < colhead.length; i++) {
                                        obj[i] = rs.getObject(i + 1);
                                }
                                DM.addRow(obj);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Not connected: " + e.getMessage());
                }
        }

        private void initComponents() {
                // Initialize components
                jPanel1 = new JPanel();
                jScrollPane1 = new JScrollPane();
                jTable1 = new JTable();
                jButton1 = new JButton();
                jButton2 = new JButton();
                backgroundLabel = new JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                // Configure the background image
                ImageIcon imgIcon = new ImageIcon("img\\customerData.jpg");
                Image img = imgIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(img));
                backgroundLabel.setBounds(0, 0, 900, 600);

                // Configure the panel
                jPanel1.setLayout(null);

                // Configure the table
                jTable1.setModel(new DefaultTableModel(
                                new Object[][] {},
                                new String[] {}));
                jScrollPane1.setViewportView(jTable1);
                jScrollPane1.setBounds(150, 100, 600, 300);
                jPanel1.add(jScrollPane1);

                // Configure buttons
                jButton1.setText("CUSTOMER DATA");
                jButton1.setFont(new Font("Sylfaen", Font.BOLD, 18));
                jButton1.setBounds(350, 50, 200, 40);
                jPanel1.add(jButton1);

                jButton2.setText("Back");
                jButton2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                jButton2.setBounds(400, 420, 100, 30);
                jButton2.addActionListener(this::jButton2ActionPerformed);
                jPanel1.add(jButton2);

                // Add the background label last to ensure it appears behind other components
                jPanel1.add(backgroundLabel);

                // Configure the frame
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(jPanel1, BorderLayout.CENTER);

                setSize(900, 600);
                setLocationRelativeTo(null); // Center the frame
                setResizable(false);
        }

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
                // Navigate back to the f6 screen
                new f6().setVisible(true);
                this.dispose();
        }

        public static void main(String args[]) {
                java.awt.EventQueue.invokeLater(() -> new CUSTOMER().setVisible(true));
        }
}
