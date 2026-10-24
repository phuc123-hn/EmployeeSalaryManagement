# 📦 Database Backup & Restore Guide

## 📋 Overview

Hệ thống cung cấp tools để:
- ✅ **Export** database SQLite (`.db`) sang text SQL format (`.sql`)
- ✅ **Import** dữ liệu từ SQL files
- ✅ **Backup** dữ liệu toàn bộ
- ✅ **Restore** từ backup files

## 🎯 Tại Sao Dùng SQL Format?

| Tiêu Chí | `.dat` | `.db` | `.sql` |
|---------|--------|--------|---------|
| **Text-based** | ❌ | ❌ | ✅ |
| **Human-readable** | ❌ | ❌ | ✅ |
| **Version control** | ❌ | ❌ | ✅ |
| **Easy backup** | ❌ | ⚠️ (needs copy) | ✅ |
| **Cross-platform** | ✅ | ✅ | ✅✅ |
| **Easy restore** | ❌ | ⚠️ (file copy) | ✅ |

## 📁 Cấu Trúc Thư Mục

```
System_01/
├── data/
│   ├── payroll.db              # Active SQLite database
│   ├── payroll_dump.sql        # SQL export file
│   ├── payroll_backup.db       # Database backup
│   └── backup/
│       ├── employees.dat       # Old serialized data
│       └── attendance.dat      # Old serialized data
├── bin/                        # Compiled Java classes
├── backup.bat                  # Windows script
├── backup.ps1                  # PowerShell script
└── ...
```

## 🚀 Cách Sử Dụng

### Windows (`.bat`)

```bash
# Export database to SQL file
backup.bat export

# Import from SQL file
backup.bat import

# Import from specific file
backup.bat import "path/to/file.sql"

# Show statistics
backup.bat stats
```

### PowerShell

```powershell
# Export
.\backup.ps1 export

# Import (from default payroll_dump.sql)
.\backup.ps1 import

# Import from custom file
.\backup.ps1 import "C:\path\to\custom.sql"

# Statistics
.\backup.ps1 stats
```

### Manual (Command Line)

```bash
# Export
java -cp "bin;lib/*" com.payroll.util.DatabaseExporter export

# Import
java -cp "bin;lib/*" com.payroll.util.DatabaseExporter import

# Stats
java -cp "bin;lib/*" com.payroll.util.DatabaseExporter stats
```

## 📊 Export Process

### Ví dụ Output:

```
📊 Đang export database sang SQL...
  • Exporting table: attendance_records
  • Exporting table: daily_employees
  • Exporting table: employees
  • Exporting table: hourly_employees
  • Exporting table: salary_employees

✅ Export thành công! File: data/payroll_dump.sql

📈 Database Statistics:
==========================================
Total Employees: 4
Total Attendance Records: 0
Database File Size: 45056 bytes
```

## 💾 SQL File Format

Mỗi SQL file chứa:
- Header với timestamp
- CREATE TABLE statements
- DROP TABLE IF EXISTS (để tránh duplicate)
- INSERT statements với tất cả dữ liệu
- PRAGMA settings (foreign keys, etc.)

### Ví dụ:

```sql
-- SQLite Database Dump
-- Generated: 2026-01-01 17:19:00
-- Database: payroll.db

PRAGMA foreign_keys = OFF;

-- Table: employees
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (...);
INSERT INTO employees (...) VALUES (...);

-- Table: salary_employees
DROP TABLE IF EXISTS salary_employees;
CREATE TABLE salary_employees (...);
INSERT INTO salary_employees (...) VALUES (...);

PRAGMA foreign_keys = ON;
-- Dump completed successfully
```

## 🔄 Backup Strategy

### Daily Backup

```bash
# Export toàn bộ database
backup.bat export

# Commit SQL file to version control
git add data/payroll_dump.sql
git commit -m "Daily backup: $(date)"
git push
```

### Weekly Full Backup

```bash
# Copy database file
copy data\payroll.db data\payroll_backup_$(date /T).db

# Export SQL
backup.bat export

# Backup to external drive or cloud
```

## 🔁 Restore Process

### From SQL File:

```bash
# Backup current database
copy data\payroll.db data\payroll_backup.db

# Restore from SQL
backup.bat import data/payroll_dump.sql

# Verify
backup.bat stats
```

### If Something Goes Wrong:

```bash
# Restore from backup
copy data\payroll_backup.db data\payroll.db

# Or restore from SQL
del data\payroll.db
backup.bat import data/payroll_dump.sql
```

## 🛡️ Safety Features

✅ **Automatic Backups:**
- Original `payroll.db` backed up before import
- Old `.dat` files kept in `backup/` folder

✅ **Data Integrity:**
- Foreign key constraints enabled
- Transaction support
- NULL handling

✅ **Error Handling:**
- Detailed error messages
- SQL validation
- Connection pooling

## 📈 Performance

| Operation | Time | File Size |
|-----------|------|-----------|
| Export | ~1-2 sec | ~50-100 KB |
| Import | ~1-2 sec | N/A |
| Full backup | ~2-3 sec | Database + SQL |

## 🔍 Monitoring

### View Database Stats:

```bash
backup.bat stats
```

### Output Example:

```
📈 Database Statistics:
==========================================
Total Employees: 4
Total Attendance Records: 0
Database File Size: 45056 bytes
```

## 🚨 Common Issues & Solutions

### Issue: "Cannot find SQLite driver"
**Solution:** Đảm bảo `lib/sqlite-jdbc-3.44.0.0.jar` tồn tại

### Issue: "SQL file is too large"
**Solution:** Bạn có thể compress SQL file bằng gzip

### Issue: "Cannot import - database locked"
**Solution:** 
1. Đóng tất cả instances của ứng dụng
2. Xóa file `data/payroll.db-wal`
3. Thử lại import

## 📝 Version Control Integration

### `.gitignore`

```bash
# Ignore binary database (use SQL instead)
data/payroll.db
data/payroll_backup.db

# Keep SQL files for version control
!data/payroll_dump.sql

# Backup folder
data/backup/
```

### Recommended Workflow:

```bash
# 1. Make changes to application
# 2. Export database
backup.bat export

# 3. Commit SQL dump
git add data/payroll_dump.sql
git commit -m "Update: Added 5 new employees"

# 4. Push to repository
git push
```

## 🔐 Security Notes

⚠️ **Password Hashes:**
- Passwords được hash và lưu an toàn
- Không bao giờ export plain text passwords

⚠️ **SQL Injection Prevention:**
- Sử dụng PreparedStatement
- String escaping được handled tự động

⚠️ **Backup Files:**
- Giữ SQL dumps ở nơi an toàn
- Consider encryption cho sensitive data

## 📞 Support

Có lỗi? Thử:
1. Xóa `payroll.db` và recreate từ SQL
2. Check logs trong terminal
3. Verify SQL file syntax
4. Restore từ backup

---

**Last Updated:** 01/01/2026  
**Version:** 2.1 - SQL Export Edition
