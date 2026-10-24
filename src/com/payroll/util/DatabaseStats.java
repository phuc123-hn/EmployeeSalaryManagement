package com.payroll.util;

import com.payroll.repository.DatabaseConnection;
import java.sql.*;

/**
 * Utility để kiểm tra thống kê database
 */
public class DatabaseStats {
    
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            System.out.println("\n=== DATABASE STATISTICS ===\n");
            
            // Count employees
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM employees");
            rs.next();
            System.out.println("📊 Tổng số nhân viên: " + rs.getInt("count"));
            rs.close();
            
            // List all employees
            System.out.println("\n📋 DANH SÁCH NHÂN VIÊN:");
            rs = stmt.executeQuery("SELECT id, name, username, employee_type FROM employees ORDER BY id");
            while (rs.next()) {
                System.out.println("  ID: " + rs.getInt("id") + 
                                  " | Tên: " + rs.getString("name") + 
                                  " | User: " + rs.getString("username") + 
                                  " | Loại: " + rs.getString("employee_type"));
            }
            rs.close();
            
            // Count attendance
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM attendance_records");
            rs.next();
            System.out.println("\n📊 Tổng số bản ghi chấm công: " + rs.getInt("count"));
            rs.close();
            
            stmt.close();
            DatabaseConnection.closeConnection();
            
            System.out.println("\n✅ Database stats done\n");
            
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
