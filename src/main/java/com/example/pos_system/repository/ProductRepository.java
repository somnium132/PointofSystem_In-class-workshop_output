package com.example.pos_system.repository;

import com.example.pos_system.model.Product;
import com.example.pos_system.util.Database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, price, stock FROM products ORDER BY name ASC";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateStock(int productId, int quantityToReduce, Connection conn) throws SQLException {
        String query = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, quantityToReduce);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantityToReduce);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
