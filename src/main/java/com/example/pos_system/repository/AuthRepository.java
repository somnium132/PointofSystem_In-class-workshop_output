package com.example.pos_system.repository;

import com.example.pos_system.util.Database;
import com.example.pos_system.util.PasswordUtil;
import com.example.pos_system.model.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepository {
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean register(String username, String rawPassword) throws SQLException {
        if (usernameExists(username)) {
            return false;
        }
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, PasswordUtil.hash(rawPassword));
            statement.executeUpdate();
            return true;
        }
    }

    public UserAccount authenticate(String username, String rawPassword) throws SQLException {
        String sql = "SELECT id, password_hash FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                if (PasswordUtil.hash(rawPassword).equals(rs.getString("password_hash"))) {
                    return new UserAccount(rs.getInt("id"), username, rs.getString("password_hash"));
                }
                return null;
            }
        }
    }
}

