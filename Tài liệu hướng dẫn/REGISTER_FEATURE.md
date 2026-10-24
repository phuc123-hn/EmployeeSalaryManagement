# 🎉 Tính Năng Tạo Tài Khoản - Hướng Dẫn Sử Dụng

## ✨ Tính Năng Mới

Hệ thống đã được cập nhật với tính năng **Tạo tài khoản mới (Register)**, cho phép người dùng tự tạo tài khoản mà không cần admin.

## 📋 Yêu Cầu Input

Khi tạo tài khoản, người dùng cần cung cấp:

| Trường | Yêu Cầu | Ghi Chú |
|--------|---------|--------|
| **Họ tên** | Bắt buộc | Min 2 ký tự |
| **Địa chỉ** | Tùy chọn | - |
| **Số điện thoại** | Tùy chọn | - |
| **Loại nhân viên** | Bắt buộc | SalaryEmployee / HourlyEmployee / DailyEmployee |
| **Tên đăng nhập** | Bắt buộc | Phải unique (không trùng) |
| **Mật khẩu** | Bắt buộc | Min 6 ký tự |
| **Xác nhận mật khẩu** | Bắt buộc | Phải trùng với mật khẩu |

## 🔐 Validation & Bảo Mật

✅ **Kiểm tra mật khẩu:**
- Tối thiểu 6 ký tự
- Phải khớp khi confirm
- Tự động hash khi lưu database

✅ **Kiểm tra tên đăng nhập:**
- Không được trùng với account hiện có
- Phải là unique trong hệ thống

✅ **Tự động tạo ID:**
- Hệ thống tự cấp ID nhân viên tiếp theo
- Dựa trên highest ID hiện có

## 👥 Các Loại Nhân Viên

### 1. **SalaryEmployee** (Nhân viên lương cố định)
- Lương mặc định: 3.000.000 VND/tháng
- Thích hợp cho: Nhân viên, quản lý

### 2. **HourlyEmployee** (Nhân viên tính theo giờ)
- Lương mặc định: 50.000 VND/giờ
- Giờ làm việc mặc định: 160 giờ/tháng
- Thích hợp cho: Nhân viên thay ca, bán thời gian

### 3. **DailyEmployee** (Nhân viên tính theo ngày)
- Lương mặc định: 100.000 VND/ngày
- Ngày làm việc mặc định: 20 ngày/tháng
- Thích hợp cho: Lao động thời vụ, hợp đồng ngắn hạn

## 🚀 Hướng Dẫn Tạo Tài Khoản

### Step 1: Nhấn "Tạo tài khoản"
- Từ login screen, click nút **"Tạo tài khoản"**
- Cửa sổ RegisterPanel sẽ mở ra

### Step 2: Điền thông tin
```
Họ tên:           [Nguyễn Văn A          ]
Địa chỉ:         [Hà Nội                 ]
Số điện thoại:   [0123456789            ]
Loại nhân viên:  [SalaryEmployee        ]
Tên đăng nhập:   [nguyenvana            ]
Mật khẩu:        [password123          ]
Xác nhận MK:     [password123          ]
```

### Step 3: Nhấn "Tạo tài khoản"
- Hệ thống sẽ validate toàn bộ input
- Nếu lỗi, hiện thông báo lỗi bằng màu đỏ
- Nếu thành công, hiện: `✅ Tạo tài khoản thành công! ID: 1005`

### Step 4: Auto-close
- Cửa sổ tự động đóng sau 2 giây
- Quay về login screen
- Đăng nhập với tài khoản vừa tạo

## 💾 Dữ Liệu Lưu Trữ

Khi tạo tài khoản thành công:

1. **Dữ liệu được lưu vào:**
   - SQLite Database (`data/payroll.db`)
   - Table `employees` (thông tin cơ bản)
   - Table `salary_employees` / `hourly_employees` / `daily_employees` (chi tiết theo loại)

2. **Thông tin được mã hóa:**
   - Password được hash tự động
   - Không lưu plain text password

3. **Metadata tự động:**
   - ID: Tự cấp từ hệ thống
   - Role: Mặc định là "USER"
   - Created date: Timestamp hiện tại

## 🔍 Thí Dụ Thực Tế

### Ví dụ 1: Tạo Salary Employee
```
Họ tên:              Phạm Thị Hương
Địa chỉ:            Tp.HCM
Số điện thoại:      0777123456
Loại nhân viên:     SalaryEmployee
Tên đăng nhập:      pham_thihuong
Mật khẩu:           secure123
```
**Kết quả:**
- ID: 1005
- Lương: 3.000.000 VND/tháng
- Role: USER
- Status: Active

### Ví dụ 2: Tạo Hourly Employee
```
Họ tên:              Trần Văn Tín
Địa chỉ:            Hà Nội
Số điện thoại:      0912123456
Loại nhân viên:     HourlyEmployee
Tên đăng nhập:      tran_vantin
Mật khẩu:           hourly456
```
**Kết quả:**
- ID: 1006
- Lương: 50.000 VND/giờ
- Giờ làm: 160 giờ/tháng
- Role: USER

## ⚠️ Thông Báo Lỗi & Cách Khắc Phục

| Lỗi | Nguyên Nhân | Giải Pháp |
|-----|------------|----------|
| "Vui lòng nhập họ tên!" | Họ tên trống | Nhập họ tên đầy đủ |
| "Vui lòng nhập tên đăng nhập!" | Username trống | Nhập username |
| "Tên đăng nhập đã tồn tại!" | Username trùng | Chọn username khác |
| "Vui lòng nhập mật khẩu!" | Password trống | Nhập mật khẩu |
| "Mật khẩu phải ít nhất 6 ký tự!" | Password quá ngắn | Nhập ít nhất 6 ký tự |
| "Mật khẩu không khớp!" | Confirm password sai | Kiểm tra lại password |

## 📊 Dữ Liệu Mặc Định

Khi tạo account, các giá trị mặc định sẽ được set:

```java
// SalaryEmployee
monthly_salary = 3_000_000.0

// HourlyEmployee
hourly_rate = 50_000.0
hours_worked = 160

// DailyEmployee
daily_rate = 100_000.0
days_worked = 20
```

## 🔄 Tích Hợp với Hệ Thống

### DataRepository Method
```java
// Lưu employee vào database
DataRepository.saveEmployee(newEmployee);

// Auto-lưu vào table tương ứng:
// - salary_employees (nếu SalaryEmployee)
// - hourly_employees (nếu HourlyEmployee)
// - daily_employees (nếu DailyEmployee)
```

### Quy Trình Xử Lý
```
1. Validate input ✓
2. Check username unique ✓
3. Generate next ID ✓
4. Hash password ✓
5. Create Employee object ✓
6. Save to database ✓
7. Add to employees list ✓
8. Display success message ✓
9. Auto-close window ✓
10. Return to login ✓
```

## 🎯 Best Practices

✅ **Do:**
- Sử dụng mật khẩu mạnh (mix chữ hoa, số, ký tự đặc biệt)
- Ghi nhớ username & password
- Nhập địa chỉ đầy đủ

❌ **Don't:**
- Dùng password quá đơn giản (123456, password)
- Chia sẻ account với người khác
- Sử dụng username trùng

## 📝 Lịch Sử Thay Đổi

- **v2.1 (01/01/2026):** Thêm tính năng tạo tài khoản với validation đầy đủ
- **v2.0:** Login với pre-loaded accounts

---

**Liên Hệ:** Báo cáo lỗi hoặc yêu cầu tính năng
