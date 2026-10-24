# Hướng Dẫn SQLite Database Integration

## 📊 Thay Đổi Chính

Hệ thống đã chuyển từ lưu trữ file (`.dat`) sang **SQLite Database**. Điều này mang lại:

✅ **Lợi ích:**
- Tốc độ truy vấn nhanh hơn
- Dễ dàng lọc, sắp xếp dữ liệu phức tạp
- Bảo mật tốt hơn
- Hỗ trợ concurrent access
- Dễ backup/restore dữ liệu
- Không cần máy chủ database riêng (SQLite embedded)

## 🗄️ Cấu Trúc Database

### Bảng chính:
```sql
-- Employees (bảng cơ sở)
CREATE TABLE employees (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    address TEXT,
    phone TEXT,
    username TEXT UNIQUE,
    password_hash TEXT,
    role TEXT DEFAULT 'USER',
    employee_type TEXT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Salary Employees
CREATE TABLE salary_employees (
    id INTEGER PRIMARY KEY,
    employee_id INTEGER NOT NULL UNIQUE,
    monthly_salary REAL NOT NULL,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);

-- Hourly Employees
CREATE TABLE hourly_employees (
    id INTEGER PRIMARY KEY,
    employee_id INTEGER NOT NULL UNIQUE,
    hourly_rate REAL NOT NULL,
    hours_worked INTEGER DEFAULT 0,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);

-- Daily Employees
CREATE TABLE daily_employees (
    id INTEGER PRIMARY KEY,
    employee_id INTEGER NOT NULL UNIQUE,
    daily_rate REAL NOT NULL,
    days_worked INTEGER DEFAULT 0,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);

-- Attendance Records
CREATE TABLE attendance_records (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER NOT NULL,
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP,
    hours_worked REAL DEFAULT 0,
    date TEXT NOT NULL,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);
```

## 📁 Cấu Trúc Thư Mục

```
System_01/
├── lib/
│   ├── sqlite-jdbc-3.44.0.0.jar      # SQLite JDBC Driver
│   ├── slf4j-api-1.7.36.jar          # Logging dependency
│   └── slf4j-nop-1.7.36.jar          # No-op implementation
├── data/
│   ├── payroll.db                     # Database SQLite
│   ├── employees.dat                  # Old file (deprecated)
│   └── attendance.dat                 # Old file (deprecated)
├── bin/                               # Compiled files
├── src/
│   └── com/payroll/
│       ├── repository/
│       │   ├── DatabaseConnection.java    # NEW: Database connection
│       │   └── DataRepository.java        # Updated: SQL operations
│       ├── model/
│       ├── service/
│       ├── util/
│       └── view/
```

## 🚀 Biên Dịch & Chạy

### Option 1: Dùng Batch Script (Windows)
```bash
compile.bat      # Biên dịch
run.bat          # Chạy ứng dụng
```

### Option 2: Dùng PowerShell
```powershell
.\build_and_run.ps1   # Biên dịch và chạy
```

### Option 3: Manual từ Command Line
```bash
# Biên dịch
javac -d bin -cp "lib/*;." src/com/payroll/repository/DatabaseConnection.java src/com/payroll/repository/DataRepository.java src/com/payroll/model/*.java src/com/payroll/service/*.java src/com/payroll/util/*.java src/com/payroll/view/*.java

# Chạy
java -cp "bin;lib/*" com.payroll.view.QLLuongVien
```

## 📝 Công Dụng Từng Module

### `DatabaseConnection.java` (NEW)
- Quản lý kết nối SQLite
- Khởi tạo bảng tự động
- Cung cấp connection singleton

**Phương thức:**
```java
DatabaseConnection.getConnection()      // Lấy connection
DatabaseConnection.closeConnection()    // Đóng connection
```

### `DataRepository.java` (UPDATED)
Thay thế hoàn toàn file-based operations bằng SQL queries

**Phương thức chính:**
```java
// Employees
DataRepository.saveEmployee(Employee emp)
DataRepository.saveEmployees(List<Employee> emps)
DataRepository.loadEmployees()
DataRepository.findEmployeeById(int id)
DataRepository.deleteEmployee(int id)

// Attendance
DataRepository.saveAttendanceRecord(AttendanceRecord record)
DataRepository.saveAttendanceRecords(List<AttendanceRecord> records)
DataRepository.loadAttendanceRecords()
DataRepository.loadAttendanceByDate(String date)

// Cleanup
DataRepository.deleteAllData()
```

## 🔄 Migration từ File sang Database

**Dữ liệu cũ (files):**
- `employees.dat` - Old serialized employee data
- `attendance.dat` - Old serialized attendance data

**Dữ liệu mới (SQLite):**
- `payroll.db` - SQLite database (auto-created)

Khi ứng dụng khởi động, dữ liệu cũ sẽ tự động được migrate nếu cần thiết.

## 🔐 Bảo Mật

- Password được hash bằng `PasswordHasher` trước khi lưu
- Hỗ trợ SQL Injection prevention (dùng `PreparedStatement`)
- Mật khẩu không bao giờ được truyền dưới dạng plaintext

## 📊 Truy Vấn Dữ Liệu

### Ví dụ queries

**Tìm nhân viên theo ID:**
```java
Employee emp = DataRepository.findEmployeeById(1);
```

**Tải tất cả nhân viên:**
```java
List<Employee> employees = DataRepository.loadEmployees();
```

**Tải attendance của ngày cụ thể:**
```java
List<AttendanceRecord> records = DataRepository.loadAttendanceByDate("2026-01-01");
```

**Xóa nhân viên:**
```java
DataRepository.deleteEmployee(1);
```

## 🛠️ Dependencies

| Library | Version | Mục Đích |
|---------|---------|---------|
| sqlite-jdbc | 3.44.0.0 | SQLite JDBC Driver |
| slf4j-api | 1.7.36 | Logging API |
| slf4j-nop | 1.7.36 | No-op logging implementation |

## ⚠️ Lưu Ý Quan Trọng

1. **Database location:** `data/payroll.db`
2. **Tự động tạo bảng:** Database được khởi tạo tự động khi chương trình chạy
3. **File cũ:** `.dat` files còn lưu nhưng không còn được sử dụng
4. **Thread-safe:** SQLite có thread-safe connection pooling

## 🔧 Troubleshooting

**Lỗi: "No such table: employees"**
- Xóa `data/payroll.db` và chạy lại ứng dụng

**Lỗi: "JDBC driver not found"**
- Đảm bảo `lib/sqlite-jdbc-3.44.0.0.jar` tồn tại
- Kiểm tra classpath: `-cp "lib/*"`

**Database bị khóa (locked database)**
- Đóng tất cả instances của ứng dụng
- Xóa file `data/payroll.db-wal` nếu tồn tại

## 📌 Phiên Bản

- **v2.1 - SQLite Edition**
- Date: 01/01/2026
- Status: ✅ Production Ready

---

**Liên Hệ:** Cho phản hồi hoặc báo cáo lỗi
