package com.payroll.repository;

import com.payroll.model.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Repository quản lý dữ liệu qua SQLite Database
 * Thay thế hoàn toàn file-based storage
 */
public class DataRepository {
    
    private static final Connection conn = DatabaseConnection.getConnection();
    
    // ==================== EMPLOYEES METHODS ====================
    
    /**
     * Lưu nhân viên vào database
     */
    public static void saveEmployee(Employee employee) {
        String sql = "INSERT OR REPLACE INTO employees (id, name, address, phone, username, password_hash, role, employee_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getAddress());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getUsername());
            pstmt.setString(6, employee.getPasswordHash());
            pstmt.setString(7, employee.getRole());
            pstmt.setString(8, employee.getClass().getSimpleName());
            pstmt.executeUpdate();
            
            // Lưu vào bảng con tương ứng
            if (employee instanceof SalaryEmployee) {
                saveSalaryEmployee((SalaryEmployee) employee);
            } else if (employee instanceof HourlyEmployee) {
                saveHourlyEmployee((HourlyEmployee) employee);
            } else if (employee instanceof DailyEmployee) {
                saveDailyEmployee((DailyEmployee) employee);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lưu nhân viên: " + e.getMessage());
        }
    }
    
    /**
     * Lưu tất cả nhân viên
     */
    public static void saveEmployees(List<Employee> employees) {
        for (Employee emp : employees) {
            saveEmployee(emp);
        }
        System.out.println("✅ Đã lưu " + employees.size() + " nhân viên vào database");
    }
    
    /**
     * Tải tất cả nhân viên từ database
     */
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("employee_type");
                Employee emp = null;
                
                if ("SalaryEmployee".equals(type)) {
                    emp = loadSalaryEmployee(id);
                } else if ("HourlyEmployee".equals(type)) {
                    emp = loadHourlyEmployee(id);
                } else if ("DailyEmployee".equals(type)) {
                    emp = loadDailyEmployee(id);
                }
                
                if (emp != null) {
                    employees.add(emp);
                }
            }
            
            if (!employees.isEmpty()) {
                System.out.println("✅ Đã tải " + employees.size() + " nhân viên từ database");
            } else {
                System.out.println("ℹ️ Chưa có nhân viên trong database");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải nhân viên: " + e.getMessage());
        }
        
        return employees;
    }
    
    /**
     * Tìm nhân viên theo ID
     */
    public static Employee findEmployeeById(int id) {
        String sql = "SELECT employee_type FROM employees WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("employee_type");
                
                if ("SalaryEmployee".equals(type)) {
                    return loadSalaryEmployee(id);
                } else if ("HourlyEmployee".equals(type)) {
                    return loadHourlyEmployee(id);
                } else if ("DailyEmployee".equals(type)) {
                    return loadDailyEmployee(id);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tìm nhân viên: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Xóa nhân viên
     */
    public static void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("✅ Đã xóa nhân viên ID: " + id);
        } catch (SQLException e) {
            System.err.println("❌ Lỗi xóa nhân viên: " + e.getMessage());
        }
    }
    
    // ==================== SALARY EMPLOYEE METHODS ====================
    
    private static void saveSalaryEmployee(SalaryEmployee emp) {
        String sql = "INSERT OR REPLACE INTO salary_employees (employee_id, monthly_salary) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getId());
            pstmt.setDouble(2, emp.getMonthlySalary());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lưu salary employee: " + e.getMessage());
        }
    }
    
    private static SalaryEmployee loadSalaryEmployee(int id) {
        String baseSql = "SELECT * FROM employees WHERE id = ?";
        String salarySql = "SELECT monthly_salary FROM salary_employees WHERE employee_id = ?";
        
        try (PreparedStatement pstmt1 = conn.prepareStatement(baseSql)) {
            pstmt1.setInt(1, id);
            ResultSet rs1 = pstmt1.executeQuery();
            
            if (rs1.next()) {
                try (PreparedStatement pstmt2 = conn.prepareStatement(salarySql)) {
                    pstmt2.setInt(1, id);
                    ResultSet rs2 = pstmt2.executeQuery();
                    
                    if (rs2.next()) {
                        SalaryEmployee emp = new SalaryEmployee(
                            rs1.getString("name"),
                            rs1.getString("address"),
                            rs1.getString("phone"),
                            id,
                            rs2.getDouble("monthly_salary"),
                            rs1.getString("username"),
                            "",
                            rs1.getString("role")
                        );
                        emp.setPasswordHash(rs1.getString("password_hash"));
                        return emp;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải salary employee: " + e.getMessage());
        }
        
        return null;
    }
    
    // ==================== HOURLY EMPLOYEE METHODS ====================
    
    private static void saveHourlyEmployee(HourlyEmployee emp) {
        String sql = "INSERT OR REPLACE INTO hourly_employees (employee_id, hourly_rate, hours_worked) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getId());
            pstmt.setDouble(2, emp.getHourlyRate());
            pstmt.setInt(3, emp.getHoursWorked());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lưu hourly employee: " + e.getMessage());
        }
    }
    
    private static HourlyEmployee loadHourlyEmployee(int id) {
        String baseSql = "SELECT * FROM employees WHERE id = ?";
        String hourlySql = "SELECT hourly_rate, hours_worked FROM hourly_employees WHERE employee_id = ?";
        
        try (PreparedStatement pstmt1 = conn.prepareStatement(baseSql)) {
            pstmt1.setInt(1, id);
            ResultSet rs1 = pstmt1.executeQuery();
            
            if (rs1.next()) {
                try (PreparedStatement pstmt2 = conn.prepareStatement(hourlySql)) {
                    pstmt2.setInt(1, id);
                    ResultSet rs2 = pstmt2.executeQuery();
                    
                    if (rs2.next()) {
                        HourlyEmployee emp = new HourlyEmployee(
                            rs1.getString("name"),
                            rs1.getString("address"),
                            rs1.getString("phone"),
                            id,
                            rs2.getDouble("hourly_rate"),
                            rs2.getInt("hours_worked"),
                            rs1.getString("username"),
                            "",
                            rs1.getString("role")
                        );
                        emp.setPasswordHash(rs1.getString("password_hash"));
                        return emp;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải hourly employee: " + e.getMessage());
        }
        
        return null;
    }
    
    // ==================== DAILY EMPLOYEE METHODS ====================
    
    private static void saveDailyEmployee(DailyEmployee emp) {
        String sql = "INSERT OR REPLACE INTO daily_employees (employee_id, daily_rate, days_worked) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getId());
            pstmt.setDouble(2, emp.getDailyRate());
            pstmt.setInt(3, emp.getDaysWorked());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lưu daily employee: " + e.getMessage());
        }
    }
    
    private static DailyEmployee loadDailyEmployee(int id) {
        String baseSql = "SELECT * FROM employees WHERE id = ?";
        String dailySql = "SELECT daily_rate, days_worked FROM daily_employees WHERE employee_id = ?";
        
        try (PreparedStatement pstmt1 = conn.prepareStatement(baseSql)) {
            pstmt1.setInt(1, id);
            ResultSet rs1 = pstmt1.executeQuery();
            
            if (rs1.next()) {
                try (PreparedStatement pstmt2 = conn.prepareStatement(dailySql)) {
                    pstmt2.setInt(1, id);
                    ResultSet rs2 = pstmt2.executeQuery();
                    
                    if (rs2.next()) {
                        DailyEmployee emp = new DailyEmployee(
                            rs1.getString("name"),
                            rs1.getString("address"),
                            rs1.getString("phone"),
                            id,
                            rs2.getDouble("daily_rate"),
                            rs2.getInt("days_worked"),
                            rs1.getString("username"),
                            "",
                            rs1.getString("role")
                        );
                        emp.setPasswordHash(rs1.getString("password_hash"));
                        return emp;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải daily employee: " + e.getMessage());
        }
        
        return null;
    }
    
    // ==================== ATTENDANCE METHODS ====================
    
    /**
     * Lưu attendance record
     */
    public static void saveAttendanceRecord(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_records (employee_id, check_in_time, check_out_time, hours_worked, date) " +
                "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, record.getEmployeeId());
            pstmt.setString(2, record.getCheckInTime() != null ? record.getCheckInTime().toString() : null);
            pstmt.setString(3, record.getCheckOutTime() != null ? record.getCheckOutTime().toString() : null);
            pstmt.setDouble(4, record.getHoursWorked());
            pstmt.setString(5, record.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lưu attendance: " + e.getMessage());
        }
    }
    
    /**
     * Lưu tất cả attendance records
     */
    public static void saveAttendanceRecords(List<AttendanceRecord> records) {
        for (AttendanceRecord record : records) {
            saveAttendanceRecord(record);
        }
        System.out.println("✅ Đã lưu " + records.size() + " bản ghi chấm công vào database");
    }
    
    /**
     * Tải tất cả attendance records
     */
    public static List<AttendanceRecord> loadAttendanceRecords() {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM attendance_records";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord(rs.getInt("employee_id"));
                
                String checkInStr = rs.getString("check_in_time");
                String checkOutStr = rs.getString("check_out_time");
                
                if (checkInStr != null) {
                    record.setCheckInTime(LocalDateTime.parse(checkInStr));
                }
                if (checkOutStr != null) {
                    record.setCheckOutTime(LocalDateTime.parse(checkOutStr));
                }
                
                record.setHoursWorked(rs.getDouble("hours_worked"));
                record.setDate(rs.getString("date"));
                
                records.add(record);
            }
            
            if (!records.isEmpty()) {
                System.out.println("✅ Đã tải " + records.size() + " bản ghi chấm công từ database");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải attendance: " + e.getMessage());
        }
        
        return records;
    }
    
    /**
     * Tải attendance theo ngày
     */
    public static List<AttendanceRecord> loadAttendanceByDate(String date) {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM attendance_records WHERE date = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord(rs.getInt("employee_id"));
                
                String checkInStr = rs.getString("check_in_time");
                String checkOutStr = rs.getString("check_out_time");
                
                if (checkInStr != null) {
                    record.setCheckInTime(LocalDateTime.parse(checkInStr));
                }
                if (checkOutStr != null) {
                    record.setCheckOutTime(LocalDateTime.parse(checkOutStr));
                }
                
                record.setHoursWorked(rs.getDouble("hours_worked"));
                record.setDate(rs.getString("date"));
                
                records.add(record);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi tải attendance theo ngày: " + e.getMessage());
        }
        
        return records;
    }
    
    /**
     * Xóa tất cả dữ liệu
     */
    public static void deleteAllData() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM attendance_records");
            stmt.execute("DELETE FROM salary_employees");
            stmt.execute("DELETE FROM hourly_employees");
            stmt.execute("DELETE FROM daily_employees");
            stmt.execute("DELETE FROM employees");
            System.out.println("✅ Đã xóa tất cả dữ liệu từ database");
        } catch (SQLException e) {
            System.err.println("❌ Lỗi xóa dữ liệu: " + e.getMessage());
        }
    }
}
