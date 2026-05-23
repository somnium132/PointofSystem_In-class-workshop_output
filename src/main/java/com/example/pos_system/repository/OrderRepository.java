package com.example.pos_system.repository;

import com.example.pos_system.model.Order;
import com.example.pos_system.model.OrderItem;
import com.example.pos_system.util.Database;

import java.sql.*;
import java.util.List;

public class OrderRepository {

    private final ProductRepository productRepository = new ProductRepository();

    public boolean createOrder(Order order, List<OrderItem> items) {
        String insertOrderQuery = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?) RETURNING id";
        String insertItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                int orderId = -1;
                // 1. Insert Order
                try (PreparedStatement pstmt = conn.prepareStatement(insertOrderQuery)) {
                    pstmt.setInt(1, order.getUserId());
                    pstmt.setBigDecimal(2, order.getTotalAmount());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        }
                    }
                }

                if (orderId == -1) {
                    conn.rollback();
                    return false;
                }

                // 2. Insert Items and Update Stock
                try (PreparedStatement pstmt = conn.prepareStatement(insertItemQuery)) {
                    for (OrderItem item : items) {
                        // Update stock first
                        boolean stockUpdated = productRepository.updateStock(item.getProductId(), item.getQuantity(), conn);
                        if (!stockUpdated) {
                            conn.rollback();
                            return false; // Insufficient stock
                        }

                        pstmt.setInt(1, orderId);
                        pstmt.setInt(2, item.getProductId());
                        pstmt.setInt(3, item.getQuantity());
                        pstmt.setBigDecimal(4, item.getSubtotal());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }

                conn.commit(); // Commit transaction
                return true;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
