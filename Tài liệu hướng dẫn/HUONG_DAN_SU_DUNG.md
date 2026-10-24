# HƯỚNG DẪN SỬ DỤNG HỆ THỐNG QUẢN LÝ LƯƠNG NHÂN VIÊN (Phiên bản 2.0)

## 📋 Thay đổi chính

### 1. Hệ Thống Đăng Nhập & Phân Quyền
- **Admin**: Có quyền quản lý tất cả nhân viên
  - Username: `admin`
  - Password: `admin123`
  - Quyền: Thêm, xóa, sửa nhân viên, xuất báo cáo
  
- **User**: Chỉ xem lương của mình
  - Mặc định: username = `user`, password = `user123`
  - Quyền: Xem lương cá nhân, check-in/check-out

### 2. Tính Toán Thuế & Bảo Hiểm (TaxInsurance)
Mỗi nhân viên có lương được tính theo công thức:
```
Lương Net (Thực lĩnh) = Lương Gross - Bảo Hiểm - Thuế PIT
```

**Chi tiết:**
- **Bảo Hiểm Xã Hội (BHXH)**: 5% lương Gross
- **Bảo Hiểm Y Tế (BHYT)**: 1.5% lương Gross
- **Tổng Bảo Hiểm**: 6.5% lương Gross

- **Thuế Thu Nhập Cá Nhân (PIT)**: Tính theo bậc tiến độ
  - Mức giảm trừ gia cộng: 11 triệu VND/tháng
  - Bậc thuế: 5%, 10%, 15%, 20%, 25%, 30%, 35%

**Ví dụ:**
- Nhân viên có lương Gross: 5,000,000 VND
- Bảo hiểm: 5,000,000 × 6.5% = 325,000 VND
- Lương tính PIT: 5,000,000 - 325,000 - 11,000,000 = -5,325,000 VND (< 0, không phải nộp PIT)
- Lương Net = 5,000,000 - 325,000 = 4,675,000 VND

### 3. Hệ Thống Chấm Công (AttendanceRecord)
Thay thế việc nhập cứng 160 giờ/tháng hoặc 22 ngày/tháng

**Tính năng:**
- ✅ **Check-in**: Ghi nhận thời gian vào làm
- ✅ **Check-out**: Ghi nhận thời gian ra về
- 📊 **Xem Chấm Công**: Xem lịch sử check-in/out

**Cách sử dụng:**
1. Click nút "🟢 Check-in" → Nhập mã nhân viên → Check-in thành công
2. Click nút "🔴 Check-out" → Nhập mã nhân viên → Tính tự động giờ làm việc
3. Click "📋 Xem Chấm Công" → Xem bảng chấm công chi tiết

### 4. Tính Năng Đa Luồng (SwingWorker)
- Tìm kiếm nhân viên sử dụng SwingWorker
- UI không bị block khi tìm kiếm
- Cải thiện trải nghiệm người dùng

### 5. Bảng Lương Chi Tiết
Bảng danh sách hiển thị đầy đủ thông tin:
- Mã NV, Họ tên, SĐT, Tỉnh/Thành
- Loại nhân viên, Mức Giá
- **Lương Gross** (Trước khi trừ)
- **Bảo Hiểm** (BHXH + BHYT)
- **Thuế PIT** (Thu nhập cá nhân)
- **Lương Net** (Thực lĩnh)

---

## 🚀 Hướng Dẫn Chi Tiết

### A. ĐĂNG NHẬP
```
1. Chạy: java QLLuongVien
2. Nhập thông tin đăng nhập
   - Admin: admin / admin123
   - User: user / user123
3. Bấm "Đăng nhập"
```

### B. ADMIN - QUẢN LÝ NHÂN VIÊN (Tab 1)

#### Thêm nhân viên:
1. Nhập Mã NV, Họ tên, SĐT
2. Chọn Tỉnh/Thành, Loại, Mức giá
3. Click "Thêm Nhân Viên"
4. → Hệ thống tự tạo username và password

#### Xem tất cả nhân viên:
- Click "📊 Xuất Toàn Bộ"
- Bảng sẽ hiển thị tất cả + thống kê

#### Tìm kiếm:
- Nhập từ khóa (tên, mã, SĐT, tỉnh)
- Click "🔍 Tìm"
- → Kết quả hiển thị trong bảng

#### Xuất báo cáo:
- Click "📁 Xuất CSV"
- → Tệp CSV tạo ra với tên: `DanhSachLuongNhanVien_yyyyMMdd_HHmmss.csv`
- Nội dung: Danh sách chi tiết + thống kê

#### Xóa tất cả:
- Click "🗑️ Xóa Tất Cả"
- Xác nhận → Xóa hết dữ liệu

### C. CHẤM CÔNG (Tab 2) - All Users

#### Check-in:
1. Click "🟢 Check-in"
2. Nhập mã nhân viên
3. → Thời gian check-in được ghi nhận

#### Check-out:
1. Click "🔴 Check-out"
2. Nhập mã nhân viên
3. → Hệ thống tính tự động giờ làm việc

#### Xem chấm công:
1. Click "📋 Xem Chấm Công"
2. → Bảng hiển thị: Ngày, Mã, Tên, Check-in, Check-out, Giờ

### D. BẢNG LƯƠNG THAM KHẢO (Tab 3) - All Users
Hiển thị các mức lương tham khảo theo chức vụ/loại

### E. LƯƠNG CỦA TÔI (Tab 4) - Users Only
Chi tiết lương của nhân viên đang đăng nhập:
- Mã, Tên, Địa chỉ, SĐT
- Lương Gross
- Chi tiết bảo hiểm (BHXH, BHYT)
- Thuế PIT
- Lương Net (Thực lĩnh)

---

## 📊 CÔNG THỨC TÍNH LƯƠNG

### Công thức chung:
```
Lương Net = Lương Gross - (Bảo hiểm BHXH + Bảo hiểm BHYT) - Thuế PIT
```

### Ví dụ cụ thể:

**Trường hợp 1: Nhân viên lương cố định**
- Nhập: Lương cố định = 3,000,000 VND
- Lương Gross = 3,000,000 VND
- Bảo hiểm: 3,000,000 × 6.5% = 195,000 VND
- Lương tính PIT: 3,000,000 - 195,000 - 11,000,000 = -8,195,000 (< 0)
- **Thuế PIT = 0 VND**
- **Lương Net = 3,000,000 - 195,000 - 0 = 2,805,000 VND**

**Trường hợp 2: Nhân viên trả theo giờ**
- Nhập: Giá 50,000 VND/giờ, 160 giờ/tháng
- Lương Gross = 50,000 × 160 = 8,000,000 VND
- Bảo hiểm: 8,000,000 × 6.5% = 520,000 VND
- Lương tính PIT: 8,000,000 - 520,000 - 11,000,000 = -3,520,000 (< 0)
- **Thuế PIT = 0 VND**
- **Lương Net = 8,000,000 - 520,000 = 7,480,000 VND**

**Trường hợp 3: Nhân viên lương cao**
- Nhập: Lương cố định = 20,000,000 VND
- Lương Gross = 20,000,000 VND
- Bảo hiểm: 20,000,000 × 6.5% = 1,300,000 VND
- Lương tính PIT: 20,000,000 - 1,300,000 - 11,000,000 = 7,700,000 VND
- **Thuế PIT (5% trên 7,700,000) = 385,000 VND**
- **Lương Net = 20,000,000 - 1,300,000 - 385,000 = 18,315,000 VND**

---

## 🔑 TÀI KHOẢN MẪU

```
ADMIN:
- Username: admin
- Password: admin123
- Quyền: Quản lý toàn bộ hệ thống

USER:
- Username: user
- Password: user123
- Quyền: Xem lương cá nhân, chấm công

Dữ liệu mẫu:
1. Nguyễn Văn A (ID 1001) - Lương 5M - ADMIN
2. Trần Thị B (ID 1002) - Lương 3M - USER
3. Lê Văn C (ID 1003) - 50k/giờ - USER
4. Phạm Thị D (ID 1004) - 150k/ngày - USER
```

---

## ✨ CÁC TÍNH NĂNG MỚI ĐƯỢC THÊM

| Tính năng | Mô tả | Loại |
|----------|-------|------|
| LoginPanel | Đăng nhập với phân quyền | Security |
| TaxInsurance | Tính thuế & bảo hiểm | Business Logic |
| AttendanceRecord | Chấm công check-in/out | HR |
| SwingWorker Search | Tìm kiếm không block UI | Performance |
| Enhanced Table | Thêm cột Gross, Insurance, Tax, Net | UI |
| Payslip View | Xem chi tiết lương cá nhân | User Feature |

---

## 🐛 LƯU Ý

1. Dữ liệu hiện lưu trong bộ nhớ (ArrayList) - Sẽ được cập nhật sang Database/JSON trong phiên bản tiếp theo
2. Chấm công được tính theo thời gian thực lập tức
3. Bảo hiểm và thuế tính tự động dựa trên lương Gross
4. Admin cần tạo tài khoản cho User trước khi User có thể đăng nhập

---

## 📝 LỊCH SỬ PHIÊN BẢN

**v1.0**: Quản lý cơ bản nhân viên, xuất CSV
**v2.0**: 
  - ✅ Thêm đăng nhập & phân quyền
  - ✅ Tính thuế & bảo hiểm chi tiết
  - ✅ Hệ thống chấm công check-in/out
  - ✅ Xem lương chi tiết (Gross/Net)
  - ✅ Multi-threading cho tìm kiếm
  - ✅ Phân biệt quyền Admin/User

---

**Năm cập nhật**: 2024-2025
**Phiên bản**: 2.0.0
