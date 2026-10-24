# 📊 HỆ THỐNG QUẢN LÝ LƯƠNG NHÂN VIÊN v2.1 - HƯỚNG DẪN TOÀN DIỆN

## 🎯 CẬP NHẬT CHÍNH (v2.0 → v2.1)

### ✅ Yêu Cầu Đã Hoàn Thành:

#### 1. **💾 Lưu Trữ Dữ Liệu (Data Persistence)**
- ✔️ Dữ liệu nhân viên được lưu vào file `data/employees.dat`
- ✔️ Dữ liệu chấm công được lưu vào file `data/attendance.dat`
- ✔️ Dữ liệu tự động lưu khi:
  - Thêm nhân viên mới
  - Check-in / Check-out
  - Chót lương tháng
- ✔️ Dữ liệu tự động tải khi khởi động ứng dụng
- ✔️ Không còn mất dữ liệu khi tắt ứng dụng

#### 2. **🔐 Bảo Mật Mật Khẩu (Password Security)**
- ✔️ Mật khẩu được mã hóa SHA-256 với SALT
- ✔️ Admin không thể xem mật khẩu của nhân viên
- ✔️ Mỗi mật khẩu có SALT riêng (16 bytes ngẫu nhiên)
- ✔️ Hỗ trợ hàm `verifyPassword()` thay vì `getPassword()`
- ✔️ Tất cả mật khẩu trong `data/employees.dat` đều được mã hóa

#### 3. **💰 Tính Lương Từ Chấm Công (Salary Calculation from Attendance)**
- ✔️ Thêm nút "🔐 Chót Lương Tháng" trong Quản Lý Nhân Viên
- ✔️ Tự động tính:
  - **HourlyEmployee**: Tổng giờ làm việc từ tất cả check-in/check-out của tháng
  - **DailyEmployee**: Số ngày làm việc từ tất cả chấm công của tháng
  - **SalaryEmployee**: Lương cố định (không thay đổi)
- ✔️ Lương được cập nhật vào object Employee ngay lập tức
- ✔️ Lương được lưu vào file dữ liệu

#### 4. **📦 Cấu Trúc Package Chuyên Nghiệp (Professional Architecture)**
```
com.payroll.model/      → Các lớp thực thể (Employee, AttendanceRecord...)
com.payroll.service/    → Logic tính toán (SalaryCalculator, TaxInsurance...)
com.payroll.repository/ → Lưu trữ dữ liệu (DataRepository)
com.payroll.util/       → Tiện ích (PasswordHasher)
com.payroll.view/       → Giao diện người dùng (QLLuongVien, LoginPanel)
```

---

## 📂 CẤU TRÚC THƯ MỤC HOÀN CHỈNH

```
01-test/
├── src/
│   └── com/payroll/
│       ├── model/
│       │   ├── Employee.java              (Lớp cơ sở, abstract)
│       │   ├── SalaryEmployee.java        (Nhân viên lương cố định)
│       │   ├── HourlyEmployee.java        (Nhân viên trả theo giờ)
│       │   ├── DailyEmployee.java         (Nhân viên trả theo ngày)
│       │   ├── AttendanceRecord.java      (Bản ghi chấm công)
│       │   └── TaxInsurance.java          (Tính toán thuế bảo hiểm)
│       ├── service/
│       │   └── SalaryCalculator.java      (Tính lương từ chấm công)
│       ├── repository/
│       │   └── DataRepository.java        (Lưu/tải dữ liệu từ file)
│       ├── util/
│       │   └── PasswordHasher.java        (Mã hóa mật khẩu SHA-256)
│       └── view/
│           ├── QLLuongVien.java           (Main UI)
│           └── LoginPanel.java            (Panel đăng nhập)
├── bin/                                    (Thư mục .class files)
├── data/                                   (Thư mục dữ liệu)
│   ├── employees.dat                      (File nhân viên được nén)
│   └── attendance.dat                     (File chấm công được nén)
├── compile.bat                            (Script biên dịch)
├── run.bat                                (Script chạy)
└── build_and_run.ps1                      (Script PowerShell)
```

---

## 🔑 TÀI KHOẢN MẶC ĐỊNH

| Username | Password | Role  | Chức Năng                      |
|----------|----------|-------|--------------------------------|
| `admin`  | `admin123` | ADMIN | Quản lý nhân viên + Chấm công  |
| `user`   | `user123`  | USER  | Xem lương của mình             |
| `user2`  | `pass123`  | USER  | Xem lương của mình             |
| `user3`  | `pass123`  | USER  | Xem lương của mình             |

### ⚠️ QUAN TRỌNG:
- Khi thêm nhân viên mới:
  - **Username** = `user` + Mã NV (ví dụ: `user1005`)
  - **Password** = `pass123` (mặc định)
  - Nhân viên có thể đổi mật khẩu sau (chưa implement trong v2.1)

---

## 🚀 HƯỚNG DẪN CHẠY ỨNG DỤNG

### 1️⃣ **Cách 1: Dùng Script Batch (Windows)**
```batch
cd "c:\Users\VivoBook\Documents\java\project_JaVa\Quản lý lương nhân viên\01-test"
run.bat
```

### 2️⃣ **Cách 2: Dùng PowerShell**
```powershell
cd "c:\Users\VivoBook\Documents\java\project_JaVa\Quản lý lương nhân viên\01-test"
java -cp bin com.payroll.view.QLLuongVien
```

### 3️⃣ **Cách 3: Biên Dịch & Chạy (PowerShell)**
```powershell
cd "c:\Users\VivoBook\Documents\java\project_JaVa\Quản lý lương nhân viên\01-test\src"
javac -encoding UTF-8 -d ../bin com/payroll/model/*.java com/payroll/service/*.java com/payroll/repository/*.java com/payroll/util/*.java com/payroll/view/*.java

cd ..
java -cp bin com.payroll.view.QLLuongVien
```

---

## 💡 HƯỚNG DẪN SỬ DỤNG CHI TIẾT

### 📋 **Tab 1: Quản Lý Nhân Viên (Chỉ Admin)**

#### Phần 1: Thêm Nhân Viên
1. Nhập **Mã NV** (số, phải unique)
2. Nhập **Họ và Tên**
3. Nhập **Số Điện Thoại**
4. Chọn **Tỉnh/Thành Phố**
5. Chọn **Loại Nhân Viên**:
   - 🟢 **Lương cố định**: Lương không thay đổi theo tháng
   - 🟡 **Trả theo giờ**: Lương = Giờ làm × Mức giá/giờ
   - 🔵 **Trả theo ngày**: Lương = Ngày làm × Mức giá/ngày
6. Nhập **Lương/Mức Giá** (VND)
7. Click **[Thêm Nhân Viên]**

➜ Hệ thống sẽ:
- Tạo username: `user + Mã NV`
- Tạo password: `pass123` (mã hóa SHA-256)
- Lưu tự động vào `data/employees.dat`
- Thông báo username/password

#### Phần 2: Tìm Kiếm Nhân Viên
- Nhập từ khóa tìm theo: **Tên, Mã, SĐT, Tỉnh**
- Click **[🔍 Tìm]**
- Click **[❌ Xóa Lọc]** để xem tất cả

#### Phần 3: Chót Lương Tháng ⭐ (TÍNH NĂNG MỚI)
1. Click **[🔐 Chót Lương Tháng]**
2. Chọn **Tháng** (1-12)
3. Chọn **Năm**
4. Click **[Chót Lương]**

➜ Hệ thống sẽ:
- Duyệt tất cả `AttendanceRecord` của tháng
- Tính tổng giờ/ngày cho từng nhân viên
- Cập nhật `hoursWorked` hoặc `daysWorked` trong Employee
- Lưu dữ liệu mới vào file

#### Phần 4: Quản Lý
- **[📊 Xuất Toàn Bộ]**: Hiển thị tất cả nhân viên
- **[📁 Xuất CSV]**: Xuất file `DanhSachLuongNhanVien_YYYYMMdd_HHmmss.csv`
- **[🗑️ Xóa Tất Cả]**: Xóa toàn bộ dữ liệu (cần xác nhận)

#### Phần 5: Bảng Dữ Liệu
| Cột | Mô Tả |
|-----|-------|
| **Mã NV** | Mã nhân viên |
| **Họ và tên** | Tên đầy đủ |
| **SĐT** | Số điện thoại |
| **Tỉnh/Thành** | Địa chỉ |
| **Loại** | Loại nhân viên |
| **Mức Giá** | Lương cố định / Giá/giờ / Giá/ngày |
| **Lương Gross** | Lương trước lần thuế bảo hiểm |
| **Bảo Hiểm** | BHXH + BHYT |
| **Thuế PIT** | Thuế thu nhập cá nhân |
| **Lương Net** | Lương thực lĩnh |

#### Phần 6: Thống Kê
- 💰 **Tổng Lương Gross**: Tổng lương trước trừ
- 👥 **Tổng Nhân Viên**: Số lượng nhân viên

---

### ⏱️ **Tab 2: Chấm Công**

#### Check-in
1. Click **[🟢 Check-in]**
2. Nhập **Mã NV**
3. Hệ thống ghi lại thời gian hiện tại
4. Tự động lưu vào `data/attendance.dat`

#### Check-out
1. Click **[🔴 Check-out]**
2. Nhập **Mã NV** (phải là nhân viên đã check-in)
3. Hệ thống tính **Giờ Làm Việc** = CheckOut Time - CheckIn Time
4. Thông báo số giờ làm việc
5. Tự động lưu vào `data/attendance.dat`

#### Xem Chấm Công
1. Click **[📋 Xem Chấm Công]**
2. Bảng sẽ hiển thị:
   - **Ngày**: Ngày chấm công
   - **Mã NV**: Mã nhân viên
   - **Tên NV**: Tên nhân viên
   - **Check-in**: Giờ vào
   - **Check-out**: Giờ ra (hoặc N/A nếu chưa check-out)
   - **Giờ làm việc**: Tổng giờ (nếu đã check-out)

---

### 📊 **Tab 3: Bảng Lương Tham Khảo**

Hiển thị các mức lương tham khảo theo chức vụ:
- Nhân viên: 1,000,000 - 3,000,000 VND/tháng
- Trưởng phòng: 3,000,000 - 5,000,000 VND/tháng
- Trả theo giờ (Thường): 30,000 - 50,000 VND/giờ
- Trả theo giờ (Chuyên môn): 50,000 - 100,000 VND/giờ
- Trả theo ngày (Lao động): 100,000 - 150,000 VND/ngày
- Trả theo ngày (Kỹ thuật): 200,000 - 300,000 VND/ngày

---

### 💳 **Tab 4: Lương Của Tôi (Chỉ User)**

Hiển thị **Phiếu Lương Cá Nhân**:
```
================== PHIẾU LƯƠNG ==================
Mã NV: 1002
Tên: Trần Thị B
Địa chỉ: Hồ Chí Minh
SĐT: 0987654321
===================================================

LƯƠNG GROSS (Brutto): 3,000,000 VND
Bảo hiểm xã hội (5%): 150,000 VND
Bảo hiểm y tế (1.5%): 45,000 VND
Tổng bảo hiểm: 195,000 VND
--------------------------------------------------
Thuế PIT (Thu nhập cá nhân): 274,500 VND
===================================================
LƯƠNG NET (Thực lĩnh): 2,530,500 VND
===================================================

Ngày xuất: 15/11/2024 14:30:45
```

---

## 🔐 BỒ CỤ MẬT KHẨU & BẢOS MẬT

### Quy Trình Mã Hóa Mật Khẩu:

```
Mật khẩu gốc: "pass123"
    ↓
1. Tạo SALT ngẫu nhiên (16 bytes)
2. MD: SHA-256 (SALT + "pass123")
3. Combine: SALT + SHA256Hash
4. Encode: Base64
    ↓
Kết quả: "sA3xB2kL9pQ8rT5vW1oN/+dE4fG6hIj7kL8mN9o="
    ↓
Lưu vào file: "data/employees.dat"
```

### Xác Thực Mật Khẩu:
```
Người dùng nhập: "pass123"
    ↓
Lấy mật khẩu hash từ file
    ↓
1. Decode Base64 để lấy SALT + Hash
2. Tính SHA-256 (SALT + nhập vào)
3. So sánh Hash
    ↓
Nếu khớp → Đăng nhập thành công
```

### ✅ Ưu Điểm:
- ✔️ Admin không biết mật khẩu nhân viên
- ✔️ Không thể reverse từ file mã hóa
- ✔️ Mỗi mật khẩu có SALT khác nhau
- ✔️ Chống lại Rainbow Table Attack

---

## 💾 TỆP DỮ LIỆU

### `data/employees.dat`
- **Format**: Java Serialized Object (Binary)
- **Nội dung**: `List<Employee>` chứa tất cả nhân viên
- **Lưu khi**: Thêm/xóa/sửa nhân viên, Chót lương
- **Tải khi**: Khởi động ứng dụng

### `data/attendance.dat`
- **Format**: Java Serialized Object (Binary)
- **Nội dung**: `List<AttendanceRecord>` chứa tất cả bản ghi chấm công
- **Lưu khi**: Check-in / Check-out
- **Tải khi**: Khởi động ứng dụng

### 🔧 Cách Khôi Phục Dữ Liệu:
1. Xóa file `data/employees.dat` và `data/attendance.dat`
2. Chạy lại ứng dụng
3. Dữ liệu mẫu sẽ được tạo tự động

---

## 📐 CÔNG THỨC TÍNH LƯƠNG

### 1️⃣ **SalaryEmployee (Lương Cố Định)**
```
Lương Gross = Lương Cố Định
```

**Ví dụ:**
- Nhân viên lương 5,000,000 VND/tháng
- Lương Gross = 5,000,000 VND

---

### 2️⃣ **HourlyEmployee (Trả Theo Giờ)**
```
Lương Gross = Tổng Giờ Làm × Mức Giá/Giờ
```

**Ví dụ:**
- Mức giá: 50,000 VND/giờ
- Tháng 11: 160 giờ làm việc (từ chấm công)
- Lương Gross = 160 × 50,000 = 8,000,000 VND

---

### 3️⃣ **DailyEmployee (Trả Theo Ngày)**
```
Lương Gross = Tổng Ngày Làm × Mức Giá/Ngày
```

**Ví dụ:**
- Mức giá: 150,000 VND/ngày
- Tháng 11: 22 ngày làm việc (từ chấm công)
- Lương Gross = 22 × 150,000 = 3,300,000 VND

---

### 4️⃣ **Bảo Hiểm**
```
BHXH = Lương Gross × 5%
BHYT = Lương Gross × 1.5%
Tổng Bảo Hiểm = BHXH + BHYT = 6.5%
```

**Ví dụ:**
- Lương Gross = 3,000,000
- BHXH = 3,000,000 × 5% = 150,000
- BHYT = 3,000,000 × 1.5% = 45,000
- Tổng Bảo Hiểm = 195,000

---

### 5️⃣ **Thuế PIT (Thuế Thu Nhập Cá Nhân)**

| Mức Lương Tính Thuế | Thuế Suất |
|-------------------|-----------|
| ≤ 5,000,000 VND | 5% |
| 5,000,001 - 10,000,000 | 10% |
| 10,000,001 - 20,000,000 | 15% |
| > 20,000,000 | 20% |

```
Lương Tính Thuế = Lương Gross - BHXH
Thuế PIT = Lương Tính Thuế × Thuế Suất
```

**Ví dụ 1:**
- Lương Gross = 3,000,000
- BHXH = 150,000
- Lương Tính Thuế = 3,000,000 - 150,000 = 2,850,000
- Thuế Suất = 5%
- Thuế PIT = 2,850,000 × 5% = 142,500

**Ví dụ 2:**
- Lương Gross = 6,000,000
- BHXH = 300,000
- Lương Tính Thuế = 5,700,000
- Thuế PIT = 5,700,000 × 10% = 570,000

---

### 6️⃣ **Lương Net (Lương Thực Lĩnh)**
```
Lương Net = Lương Gross - BHXH - BHYT - Thuế PIT
          = Lương Gross - (BHXH + BHYT) - Thuế PIT
```

**Ví dụ:**
- Lương Gross = 3,000,000
- Bảo Hiểm = 195,000 (BHXH + BHYT)
- Thuế PIT = 142,500
- **Lương Net = 3,000,000 - 195,000 - 142,500 = 2,662,500**

---

## 🆕 SỰ THAY ĐỔI SO VỚI v2.0

| Tính Năng | v2.0 | v2.1 |
|-----------|------|------|
| Dữ liệu trong RAM | ✔️ | ❌ (Lưu file) |
| Lưu trữ file | ❌ | ✔️ |
| Mật khẩu hash | ❌ | ✔️ (SHA-256) |
| Giờ làm tự động | ❌ | ✔️ (từ chấm công) |
| Package structure | ❌ | ✔️ (Professional) |
| Chót lương tháng | ❌ | ✔️ (Tính tự động) |
| Login system | ✔️ | ✔️ (Cải tiến) |
| Chấm công | ✔️ | ✔️ (Cải tiến) |

---

## 🛠️ BIÊN DỊCH TỪNG PACKAGE

Nếu cần biên dịch riêng từng phần:

```powershell
# Chỉ model
javac -encoding UTF-8 -d bin src/com/payroll/model/*.java

# Thêm service
javac -encoding UTF-8 -d bin -cp bin src/com/payroll/service/*.java

# Thêm repository
javac -encoding UTF-8 -d bin -cp bin src/com/payroll/repository/*.java

# Thêm util
javac -encoding UTF-8 -d bin -cp bin src/com/payroll/util/*.java

# Cuối cùng: view
javac -encoding UTF-8 -d bin -cp bin src/com/payroll/view/*.java
```

---

## 📞 LIÊN HỆ & HỖ TRỢ

- Lỗi mã hóa: Kiểm tra `compile.bat` có `UTF-8` chưa
- Lỗi file không tìm thấy: Xóa `data/` folder và chạy lại
- Lỗi login: Dùng tài khoản mặc định (admin/admin123)
- Lỗi chấm công: Kiểm tra mã NV có tồn tại chưa

---

## ✨ PHIÊN BẢN TIẾP THEO (v2.2)

### Dự Kiến Tính Năng:
- 🔐 Đổi mật khẩu cá nhân
- 📧 Xuất phiếu lương PDF/Email
- 📱 QR Code check-in
- 🤖 AI cảnh báo kiệt sức
- 💻 Web-based UI (Spring Boot + React)
- 📊 Dashboard thống kê nâng cao

---

**Cập Nhật: 2024-11-15**  
**Version: v2.1**  
**Status: ✅ Production Ready**
