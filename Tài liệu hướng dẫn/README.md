# 📁 Cấu Trúc Thư Mục Dự Án

```
01-test/
├── 📂 src/                          (Source code - File .java)
│   ├── Employee.java                (Class cha - Lớp cơ sở)
│   ├── SalaryEmployee.java          (Nhân viên lương cố định)
│   ├── HourlyEmployee.java          (Nhân viên trả theo giờ)
│   ├── DailyEmployee.java           (Nhân viên trả theo ngày)
│   ├── TaxInsurance.java            (Tính thuế & bảo hiểm)
│   ├── AttendanceRecord.java        (Quản lý chấm công)
│   ├── LoginPanel.java              (Giao diện đăng nhập)
│   └── QLLuongVien.java             (Chương trình chính)
│
├── 📂 bin/                          (Compiled code - File .class)
│   ├── Employee.class
│   ├── SalaryEmployee.class
│   ├── HourlyEmployee.class
│   ├── DailyEmployee.class
│   ├── TaxInsurance.class
│   ├── AttendanceRecord.class
│   ├── LoginPanel.class
│   ├── QLLuongVien.class
│   └── QLLuongVien$*.class          (Anonymous class)
│
├── 📜 compile.bat                   (Script biên dịch - Windows)
├── 📜 run.bat                       (Script chạy ứng dụng - Windows)
├── 📜 build_and_run.ps1             (PowerShell script)
├── 📜 HUONG_DAN_SU_DUNG.md         (Hướng dẫn sử dụng)
└── 📄 README.md                     (File này)

```

---

## 🚀 Cách Sử Dụng

### Tùy Chọn 1: Chạy File .bat (Dễ nhất)
```batch
# Windows - Double-click vào file:
run.bat
```

### Tùy Chọn 2: Biên Dịch + Chạy (Nếu sửa code)
```batch
# Windows - Chạy:
compile.bat
```

### Tùy Chọn 3: Command Line
```powershell
# Biên dịch:
javac -encoding UTF-8 -d bin src/*.java

# Chạy:
java -cp bin QLLuongVien
```

### Tùy Chọn 4: PowerShell Script
```powershell
# Chạy script tự động biên dịch + chạy:
.\build_and_run.ps1
```

---

## 📋 Cách Hoạt Động

### Biên Dịch (Compile)
```
src/*.java ---[javac -d bin]---> bin/*.class
```

**Command chi tiết:**
```bash
javac -encoding UTF-8 -d bin src/*.java
```

- `-encoding UTF-8`: Hỗ trợ tiếng Việt
- `-d bin`: Output vào thư mục bin
- `src/*.java`: Lấy tất cả file .java từ src

### Chạy (Run)
```
bin/*.class ---[java -cp bin]---> Chạy chương trình
```

**Command chi tiết:**
```bash
java -cp bin QLLuongVien
```

- `-cp bin`: Classpath trỏ vào thư mục bin (nơi có .class)
- `QLLuongVien`: Main class cần chạy

---

## ✅ Lợi Ích Của Cấu Trúc Này

| Lợi Ích | Mô Tả |
|---------|-------|
| **Sạch sẽ** | Tách biệt .java và .class, dễ quản lý |
| **Dễ Maintain** | Chỉnh sửa code trong src/ rồi recompile |
| **Professional** | Cấu trúc chuẩn của dự án Java |
| **Dễ Deploy** | Chỉ cần copy thư mục bin/ khi release |
| **Không Lẫn Lộn** | Không có file .class lẩn quẩn trong src/ |

---

## 🔧 Nếu Có Lỗi

### Lỗi: "javac: command not found"
→ Cần cài đặt Java JDK (không phải JRE)

### Lỗi: "Main class QLLuongVien not found"
→ Đảm bảo thư mục bin chứa QLLuongVien.class

### Lỗi: Ký tự Tiếng Việt không hiển thị
→ Thêm `-encoding UTF-8` khi compile

---

## 📝 Workflow Phát Triển

### Khi Muốn Chỉnh Sửa Code:
```
1. Sửa file trong src/
2. Chạy compile.bat (hoặc command javac)
3. Chạy run.bat (hoặc command java)
```

### Khi Muốn Deploy:
```
1. Chỉnh sửa + Biên dịch xong
2. Copy toàn bộ thư mục 01-test/ (hoặc chỉ bin/)
3. Trên máy khác: chạy run.bat
```

---

## 🎯 Tệp Nào Cần Chỉnh Sửa?

**Chỉnh sửa:**
- ✏️ Các file trong thư mục `src/`

**Không chỉnh sửa:**
- ❌ Các file trong thư mục `bin/` (tự động generate)

---

## 💡 Gợi Ý

- Thêm `.gitignore` để không commit bin/
- Giữ một bản backup của src/
- Comment code tốt để dễ maintenance

---

**Phiên bản**: 2.0.0  
**Cập nhật**: 2024-2025
