package com.payroll.repository;

import java.sql.*;

/**
 * Quản lý kết nối SQLite Database
 * Tạo bảng tự động và cung cấp connection
 */
public class DatabaseConnection {
    
    private static final String DATABASE_URL = "jdbc:sqlite:data/payroll.db";
    private static Connection connection;
    
    static {
        try {
            // Đảm bảo thư mục data tồn tại
            new java.io.File("data").mkdirs();
            
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            
            // Tạo connection
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("✅ Kết nối SQLite thành công: data/payroll.db");
            
            // Tạo các bảng
            createTables();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite driver không tìm thấy: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối database: " + e.getMessage());
        }
    }
    
    /**
     * Tạo các bảng cần thiết
     */
    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Bảng Employees (base class)
            String employeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "address TEXT," +
                    "phone TEXT," +
                    "username TEXT UNIQUE," +
                    "password_hash TEXT," +
                    "role TEXT DEFAULT 'USER'," +
                    "employee_type TEXT NOT NULL," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(employeesTable);
            
            // Bảng Salary Employees
            String salaryTable = "CREATE TABLE IF NOT EXISTS salary_employees (" +
                    "id INTEGER PRIMARY KEY," +
                    "employee_id INTEGER NOT NULL UNIQUE," +
                    "monthly_salary REAL NOT NULL," +
                    "FOREIGN KEY(employee_id) REFERENCES employees(id) ON DELETE CASCADE" +
                    ")";
            stmt.execute(salaryTable);
            
            // Bảng Hourly Employees
            String hourlyTable = "CREATE TABLE IF NOT EXISTS hourly_employees (" +
                    "id INTEGER PRIMARY KEY," +
                    "employee_id INTEGER NOT NULL UNIQUE," +
                    "hourly_rate REAL NOT NULL," +
                    "hours_worked INTEGER DEFAULT 0," +
                    "FOREIGN KEY(employee_id) REFERENCES employees(id) ON DELETE CASCADE" +
                    ")";
            stmt.execute(hourlyTable);
            
            // Bảng Daily Employees
            String dailyTable = "CREATE TABLE IF NOT EXISTS daily_employees (" +
                    "id INTEGER PRIMARY KEY," +
                    "employee_id INTEGER NOT NULL UNIQUE," +
                    "daily_rate REAL NOT NULL," +
                    "days_worked INTEGER DEFAULT 0," +
                    "FOREIGN KEY(employee_id) REFERENCES employees(id) ON DELETE CASCADE" +
                    ")";
            stmt.execute(dailyTable);
            
            // Bảng Attendance Records
            String attendanceTable = "CREATE TABLE IF NOT EXISTS attendance_records (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "employee_id INTEGER NOT NULL," +
                    "check_in_time TIMESTAMP," +
                    "check_out_time TIMESTAMP," +
                    "hours_worked REAL DEFAULT 0," +
                    "date TEXT NOT NULL," +
                    "FOREIGN KEY(employee_id) REFERENCES employees(id) ON DELETE CASCADE" +
                    ")";
            stmt.execute(attendanceTable);
            
            System.out.println("✅ Các bảng database đã được tạo thành công");
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tạo bảng: " + e.getMessage());
        }
    }
    
    /**
     * Lấy connection hiện tại
     */
    public static Connection getConnection() {
        if (connection == null) {
            System.err.println("❌ Database connection chưa được khởi tạo");
        }
        return connection;
    }
    
    /**
     * Đóng connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Đã đóng kết nối database");
            } catch (SQLException e) {
                System.err.println("❌ Lỗi đóng connection: " + e.getMessage());
            }
        }
    }
}
