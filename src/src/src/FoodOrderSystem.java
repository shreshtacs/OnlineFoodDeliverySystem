package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FoodOrderSystem extends JFrame {
    private Map<String, Integer> quantities = new HashMap<>();
    private Map<String, Integer> prices = new HashMap<>();
    private Map<String, String> foodImages = new HashMap<>();
    private JTable receiptTable;
    private JPanel foodPanel;
    private static final int IMG_WIDTH = 150;
    private static final int IMG_HEIGHT = 150;

    public FoodOrderSystem() {
        initializeFoodImages();
        initializePrices();
        initializeUI();
        setTitle("ONLINE FOOD DELIVERY");
    }

    private void initializeFoodImages() {
        foodImages.put("Pizza", "img\\pizza.jpg");
        foodImages.put("Burger", "img\\burger.jpg");
        foodImages.put("Ice Cream", "img\\icecream.jpg");
        foodImages.put("Fried Rice", "img\\FriedRice.jpg");
        foodImages.put("Roti Curry", "img\\roti.jpg");
        foodImages.put("Biryani", "img\\biryani.jpg");
        foodImages.put("Masala Dosa", "img/dosa.jpg");
        foodImages.put("Momos", "img\\momo.jpg");
        foodImages.put("Idly Sambar", "img\\idly.jpg");
    }

    private void initializePrices() {
        prices.put("Biryani", 100);
        prices.put("Idly Sambar", 20);
        prices.put("Momos", 60);
        prices.put("Burger", 100);
        prices.put("Roti Curry", 50);
        prices.put("Pizza", 200);
        prices.put("Ice Cream", 70);
        prices.put("Fried Rice", 60);
        prices.put("Masala Dosa", 40);
        for (String food : prices.keySet()) {
            quantities.put(food, 0);
        }
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Main content panel with background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("img\\FoodSelection.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Styled header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("GOURMET DELIGHTS");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 255, 255));
        headerPanel.add(titleLabel);

        // Styled food panel
        foodPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        foodPanel.setOpaque(false);
        foodPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String food : prices.keySet()) {
            addStylishFoodItem(food);
        }

        // Styled receipt table
        String[] columnNames = { "Item", "Quantity", "Price" };
        receiptTable = new JTable(new DefaultTableModel(columnNames, 0));
        styleTable(receiptTable);

        // Styled buttons
        JPanel buttonPanel = createStylishButtonPanel();

        // Layout assembly
        JScrollPane foodScrollPane = new JScrollPane(foodPanel);
        foodScrollPane.setOpaque(false);
        foodScrollPane.getViewport().setOpaque(false);

        JScrollPane receiptScrollPane = new JScrollPane(receiptTable);
        receiptScrollPane.setPreferredSize(new Dimension(600, 200));
        styleScrollPane(receiptScrollPane);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(foodScrollPane, BorderLayout.CENTER);
        mainPanel.add(receiptScrollPane, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

   

    private void addStylishFoodItem(String foodName) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Use the specific image for each food item
        String imagePath = foodImages.get(foodName);
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                quantities.put(foodName, quantities.get(foodName) + 1);
                updateQuantityLabel(foodName, itemPanel);
            }
        });

        JLabel nameLabel = new JLabel(foodName + " - ₹" + prices.get(foodName));
        styleLabel(nameLabel);

        JLabel quantityLabel = new JLabel("Quantity: 0");
        styleLabel(quantityLabel);
        quantityLabel.setName("quantity-" + foodName);

        itemPanel.add(imageLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemPanel.add(nameLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        itemPanel.add(quantityLabel);

        foodPanel.add(itemPanel);
    }

    private JPanel createStylishButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton calculateButton = createStylishButton("Calculate Bill");
        JButton exitButton = createStylishButton("Exit");

        calculateButton.addActionListener(e -> calculateBill());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(calculateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private JButton createStylishButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 69, 0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setBackground(new Color(255, 255, 255, 200));
        table.setForeground(Color.BLACK);
        table.setGridColor(new Color(100, 100, 100));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Helvetica", Font.BOLD, 14));
        header.setBackground(new Color(50, 50, 50));
        header.setForeground(Color.WHITE);
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2));
    }

    private void updateQuantityLabel(String foodName, JPanel itemPanel) {
        for (Component comp : itemPanel.getComponents()) {
            if (comp instanceof JLabel && comp.getName() != null
                    && comp.getName().equals("quantity-" + foodName)) {
                ((JLabel) comp).setText("Quantity: " + quantities.get(foodName));
                break;
            }
        }
    }

  
    private void calculateBill() {
    try

    {
        DefaultTableModel model = (DefaultTableModel) receiptTable.getModel();
        model.setRowCount(0);

        double total = 0;
        boolean hasItems = false;

        // First check if we have any items to save
        for (String food : quantities.keySet()) {
            int qty = quantities.get(food);
            if (qty > 0) {
                hasItems = true;
                int price = prices.get(food) * qty;
                model.addRow(new Object[] { food, qty, price });
                total += price;
            }
        }

        if (!hasItems) {
            JOptionPane.showMessageDialog(this, "Please select items before calculating bill");
            return;
        }

        double tax = total * 0.18;
        double deliveryCharge = 50;
        double grandTotal = total + tax + deliveryCharge;

        model.addRow(new Object[] { "Tax (18%)", "", tax });
        model.addRow(new Object[] { "Delivery Charge", "", deliveryCharge });
        model.addRow(new Object[] { "Grand Total", "", grandTotal });

        // Now save the order
        saveOrder(grandTotal);

    }catch(
    Exception e)
    {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error calculating bill: " + e.getMessage());
    }
}

private void saveOrder(double totalAmount) {
    Connection localConn = null;
    PreparedStatement orderStmt = null;
    PreparedStatement itemStmt = null;
    ResultSet seqRs = null;
    
    try {
        // Get a fresh connection
        Class.forName("oracle.jdbc.driver.OracleDriver");
        localConn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "123");
        localConn.setAutoCommit(false);  // Start transaction
        
        // Get next order ID
        seqRs = localConn.createStatement().executeQuery("SELECT order_seq.NEXTVAL FROM DUAL");
        if (!seqRs.next()) {
            throw new SQLException("Could not generate order ID");
        }
        int orderId = seqRs.getInt(1);
        System.out.println("Generated Order ID: " + orderId);

        // Insert order
        orderStmt = localConn.prepareStatement("INSERT INTO orders (order_id, total_amount) VALUES (?, ?)");
        orderStmt.setInt(1, orderId);
        orderStmt.setDouble(2, totalAmount);
        int orderResult = orderStmt.executeUpdate();
        System.out.println("Order insert result: " + orderResult);

        // Insert items
        itemStmt = localConn.prepareStatement(
            "INSERT INTO order_items (order_id, item_name, quantity, price) VALUES (?, ?, ?, ?)"
        );

        for (String food : quantities.keySet()) {
            int qty = quantities.get(food);
            if (qty > 0) {
                itemStmt.setInt(1, orderId);
                itemStmt.setString(2, food);
                itemStmt.setInt(3, qty);
                itemStmt.setDouble(4, prices.get(food));
                itemStmt.executeUpdate();
                System.out.println("Inserted item: " + food + ", Quantity: " + qty);
            }
        }

        // Commit transaction
        localConn.commit();
        System.out.println("Transaction committed successfully");

        // Success message and clear cart
        JOptionPane.showMessageDialog(this, "Order #" + orderId + " saved successfully!");
        clearCart();

    } catch (Exception e) {
        System.err.println("Error in saveOrder: " + e.getMessage());
        e.printStackTrace();
        try {
            if (localConn != null) {
                localConn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
    } finally {
        try {
            if (seqRs != null) seqRs.close();
            if (orderStmt != null) orderStmt.close();
            if (itemStmt != null) itemStmt.close();
            if (localConn != null) {
                localConn.setAutoCommit(true);
                localConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void clearCart() {
    try {
        // Clear quantities
        for (String food : quantities.keySet()) {
            quantities.put(food, 0);
        }

        // Update UI labels
        for (Component comp : foodPanel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JLabel && 
                        innerComp.getName() != null && 
                        innerComp.getName().startsWith("quantity-")) {
                        ((JLabel) innerComp).setText("Quantity: 0");
                    }
                }
            }
        }

        // Clear receipt table
        DefaultTableModel model = (DefaultTableModel) receiptTable.getModel();
        model.setRowCount(0);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error clearing cart: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FoodOrderSystem().setVisible(true);
        });
    }
}
