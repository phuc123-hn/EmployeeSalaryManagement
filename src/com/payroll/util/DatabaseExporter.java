package com.payroll.util;

import java.sql.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility để backup/restore SQLite database sang/từ SQL dump file
 */
public class DatabaseExporter {
    
    private static final String DATABASE_URL = "jdbc:sqlite:data/payroll.db";
    private static final String DUMP_FILE = "data/payroll_dump.sql";
    
    /**
     * Export database sang file SQL
     */
    public static void exportToSQL() {
        System.out.println("📊 Đang export database sang SQL...");
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PrintWriter writer = new PrintWriter(new FileWriter(DUMP_FILE))) {
            
            // Header
            writer.println("-- SQLite Database Dump");
            writer.println("-- Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("-- Database: payroll.db");
            writer.println("-- =====================================================\n");
            
            // Disable foreign keys trước khi restore
            writer.println("PRAGMA foreign_keys = OFF;\n");
            
            // Lấy danh sách tất cả bảng
            String tableQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' ORDER BY name";
            try (Statement stmt = conn.createStatement();
                 ResultSet tableRS = stmt.executeQuery(tableQuery)) {
                
                while (tableRS.next()) {
                    String tableName = tableRS.getString("name");
                    System.out.println("  • Exporting table: " + tableName);
                    
                    // Lấy CREATE TABLE statement
                    try (Statement stmtCreate = conn.createStatement();
                         ResultSet createTableRS = stmtCreate.executeQuery(
                            "SELECT sql FROM sqlite_master WHERE type='table' AND name='" + tableName + "'")) {
                        
                        if (createTableRS.next()) {
                            String createSQL = createTableRS.getString("sql");
                            writer.println("\n-- Table: " + tableName);
                            writer.println("DROP TABLE IF EXISTS " + tableName + ";");
                            writer.println(createSQL + ";\n");
                            
                            // Lấy dữ liệu từ bảng
                            try (Statement stmtData = conn.createStatement();
                                 ResultSet dataRS = stmtData.executeQuery("SELECT * FROM " + tableName)) {
                                
                                ResultSetMetaData metaData = dataRS.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                
                                // Insert statements
                                while (dataRS.next()) {
                                    StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");
                                    StringBuilder values = new StringBuilder(") VALUES (");
                                    
                                    for (int i = 1; i <= columnCount; i++) {
                                        if (i > 1) {
                                            insertSQL.append(", ");
                                            values.append(", ");
                                        }
                                        insertSQL.append(metaData.getColumnName(i));
                                        
                                        Object value = dataRS.getObject(i);
                                        if (value == null) {
                                            values.append("NULL");
                                        } else if (value instanceof String) {
                                            values.append("'").append(escapeSQL((String) value)).append("'");
                                        } else if (value instanceof Timestamp) {
                                            values.append("'").append(value.toString()).append("'");
                                        } else {
                                            values.append(value);
                                        }
                                    }
                                    
                                    values.append(");");
                                    writer.println(insertSQL.append(values).toString());
                                }
                            }
                        }
                    }
                }
            }
            
            // Footer
            writer.println("\n-- =====================================================");
            writer.println("PRAGMA foreign_keys = ON;");
            writer.println("-- Dump completed successfully");
            
            writer.flush();
            System.out.println("\n✅ Export thành công! File: " + DUMP_FILE);
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("❌ Lỗi file I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Import dữ liệu từ SQL dump file
     */
    public static void importFromSQL(String sqlFile) {
        System.out.println("📥 Đang import dữ liệu từ SQL file...");
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
            
            StringBuilder sql = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Bỏ qua comment và dòng trống
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                
                sql.append(line).append(" ");
                
                // Thực thi khi gặp dấu ;
                if (line.endsWith(";")) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sql.toString());
                        System.out.println("  ✓ " + sql.toString().substring(0, Math.min(50, sql.length())) + "...");
                    }
                    sql = new StringBuilder();
                }
            }
            
            System.out.println("\n✅ Import thành công!");
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("❌ Không tìm thấy file: " + sqlFile);
        } catch (IOException e) {
            System.err.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }
    
    /**
     * Escape đặc biệt kí tự trong SQL string
     */
    private static String escapeSQL(String str) {
        return str.replace("'", "''")
                  .replace("\\", "\\\\");
    }
    
    /**
     * Lấy thông tin statistics về database
     */
    public static void showDatabaseStats() {
        System.out.println("\n📈 Database Statistics:");
        System.out.println("==========================================");
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            
            // Đếm nhân viên
            ResultSet empRS = stmt.executeQuery("SELECT COUNT(*) as count FROM employees");
            if (empRS.next()) {
                System.out.println("Total Employees: " + empRS.getInt("count"));
            }
            
            // Đếm attendance
            ResultSet attRS = stmt.executeQuery("SELECT COUNT(*) as count FROM attendance_records");
            if (attRS.next()) {
                System.out.println("Total Attendance Records: " + attRS.getInt("count"));
            }
            
            // File size
            File dbFile = new File("data/payroll.db");
            long sizeInMB = dbFile.length() / (1024 * 1024);
            System.out.println("Database File Size: " + (sizeInMB > 0 ? sizeInMB + " MB" : dbFile.length() + " bytes"));
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Main method để chạy export/import
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite driver not found!");
            return;
        }
        
        if (args.length == 0) {
            System.out.println("Usage: java com.payroll.util.DatabaseExporter [export|import|stats]");
            System.out.println("\nExamples:");
            System.out.println("  export  - Export database to SQL file");
            System.out.println("  import  - Import from default SQL file (data/payroll_dump.sql)");
            System.out.println("  import <file> - Import from specific SQL file");
            System.out.println("  stats   - Show database statistics");
            return;
        }
        
        String command = args[0].toLowerCase();
        
        switch (command) {
            case "export":
                exportToSQL();
                showDatabaseStats();
                break;
            case "import":
                String sqlFile = args.length > 1 ? args[1] : DUMP_FILE;
                importFromSQL(sqlFile);
                showDatabaseStats();
                break;
            case "stats":
                showDatabaseStats();
                break;
            default:
                System.out.println("❌ Unknown command: " + command);
        }
    }
}
