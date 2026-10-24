# 🧪 HỆ THỐNG QUẢN LÝ LƯƠNG - HƯỚNG DẪN TEST

## ⚡ Quick Start

```powershell
cd System_01
.\compile.bat
.\run.bat
```

Hoặc chọn menu test:
```powershell
.\test.bat
```

---

## 👤 Tài Khoản Test

| Username | Password | Role | Quyền |
|----------|----------|------|-------|
| `admin` | `admin123` | ADMIN | Quản lý toàn bộ |
| `user` | `user123` | USER | Xem lương riêng |
| `user2` | `pass123` | USER | Xem lương riêng |
| `user3` | `pass123` | USER | Xem lương riêng |

---

## 🎯 Test Cases Chính

### 1. **Đăng nhập & Phân quyền**
- [ ] Đăng nhập đúng (admin)
- [ ] Đăng nhập đúng (user)
- [ ] Đăng nhập sai - thấy lỗi
- [ ] Admin thấy tab "Quản Lý Nhân Viên"
- [ ] User thấy tab "Lương của tôi"

### 2. **Quản lý nhân viên** (Admin)
- [ ] Xem danh sách 4 nhân viên
- [ ] Thêm nhân viên mới (ID 1005)
- [ ] Tìm kiếm nhân viên
- [ ] Xem thống kê lương

### 3. **Tính lương**
- [ ] SalaryEmployee: 3,000,000 → Gross 3,000,000
- [ ] HourlyEmployee: 50,000/h × 160h → Gross 8,000,000
- [ ] DailyEmployee: 100,000/day × 20 → Gross 2,000,000
- [ ] Bảo hiểm tính đúng (5% + 1.5%)
- [ ] Thuế PIT tính đúng

### 4. **Chấm công**
- [ ] Check-in thành công
- [ ] Check-out thành công
- [ ] Tính giờ làm việc đúng
- [ ] Xem bảng chấm công

### 5. **Xuất báo cáo**
- [ ] Xuất CSV thành công
- [ ] File CSV mở được bằng Excel
- [ ] Dữ liệu trong CSV chính xác

---

## 🔧 Menu Test Nhanh

Chạy `test.bat` và chọn:

| Lựa chọn | Mục đích |
|---------|---------|
| 1 | Xem thống kê database |
| 2 | Test tạo tài khoản |
| 3 | Reset database |
| 4 | Chạy ứng dụng GUI |
| 5 | Compile lại |

---

## 📊 Test Tự động

```powershell
# Xem dữ liệu trong database
java -cp "bin;lib/*" com.payroll.util.DatabaseStats

# Test tính năng Register
java -cp "bin;lib/*" com.payroll.util.TestRegisterFeature

# Chỉ có 4 nhân viên ban đầu (ID 1001-1004)
```

---

## 📝 Dữ liệu Ban Đầu

| ID | Tên | Username | Lương | Loại |
|----|-----|----------|-------|------|
| 1001 | Nguyễn Hoàng Hiệp | admin | 3,000,000 | Salary |
| 1002 | Ngô Hồng Phúc | user | 2,500,000 | Salary |
| 1003 | Nguyễn Đào Minh Trúc | user2 | 50,000/h | Hourly |
| 1004 | Trần Lê Viết | user3 | 100,000/day | Daily |

---

## 🎨 Tabs Chính

- **👨‍💼 Quản Lý Nhân Viên** - Admin only
- **⏱️ Chấm Công** - Tất cả
- **📊 Bảng Lương Tham Khảo** - Tất cả
- **💳 Lương của tôi** - User only

---

## ✅ Checklist Hoàn Chỉnh

- [ ] Đạt test login/logout
- [ ] Đạt test phân quyền
- [ ] Đạt test CRUD nhân viên
- [ ] Đạt test tính lương
- [ ] Đạt test chấm công
- [ ] Đạt test xuất báo cáo
- [ ] Database lưu dữ liệu đúng
- [ ] Không có lỗi compilation

---

## 🐛 Troubleshoot

| Lỗi | Giải pháp |
|-----|----------|
| Class not found | Chạy `compile.bat` |
| SQLite warning | Bình thường, không ảnh hưởng |
| GUI không hiển thị | Kiểm tra console, thử lại |
| Login fail | Xem bảng tài khoản ở trên |

---

## 📞 Báo Cáo Test

Khi test xong, báo cáo:
1. ✅/❌ Kết quả từng test case
2. 📝 Lỗi nếu có (kèm error message)
3. 💬 Ý kiến cải thiện
4. ⭐ Rating tổng thể

---

**Phiên bản:** 2.2  
**Database:** SQLite (payroll.db)  
**Platform:** Windows + Java 11+
