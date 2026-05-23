package com.example.pos_system;

import com.example.pos_system.util.Database;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");
            Connection conn = Database.getConnection();
            System.out.println("Connected!");
            
            String sql = new String(Files.readAllBytes(Paths.get("pos_system.sql")));
            Statement stmt = conn.createStatement();
            
            System.out.println("Executing SQL script...");
            stmt.execute(sql);
            System.out.println("Database setup complete!");
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
