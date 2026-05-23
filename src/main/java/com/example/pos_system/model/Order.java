package com.example.pos_system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(int id, int userId, BigDecimal totalAmount) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
