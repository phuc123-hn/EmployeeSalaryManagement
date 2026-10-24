# ⚡ QUICK REFERENCE - v2.1

## 🎯 MỤC TIÊU CHÍNH (v2.1)

✅ **Data Persistence**: Lưu dữ liệu vào file (`data/*.dat`)
✅ **Password Security**: Mã hóa SHA-256 + SALT
✅ **Auto Salary Calc**: Tính lương từ chấm công tự động
✅ **Package Structure**: Code organization chuyên nghiệp

---

## 📂 FILE QUAN TRỌNG

| File | Chức Năng |
|------|----------|
| `src/com/payroll/view/QLLuongVien.java` | Main UI (Giao diện chính) |
| `src/com/payroll/model/Employee.java` | Base class (có mã hóa mật khẩu) |
| `src/com/payroll/repository/DataRepository.java` | Lưu/tải dữ liệu |
| `src/com/payroll/service/SalaryCalculator.java` | Tính lương từ chấm công ⭐ |
| `src/com/payroll/util/PasswordHasher.java` | Mã hóa SHA-256 |
| `data/employees.dat` | File nhân viên (được nén) |
| `data/attendance.dat` | File chấm công (được nén) |

---

## 🚀 CHẠY NHANH

```powershell
# 1. Vào thư mục
cd "c:\Users\VivoBook\Documents\java\project_JaVa\Quản lý lương nhân viên\01-test"

# 2. Chạy
java -cp bin com.payroll.view.QLLuongVien
```

---

## 🔑 TÀI KHOẢN MẶC ĐỊNH

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | Admin (Quản lý) |
| `user` | `user123` | User (Nhân viên) |

---

## ⭐ TÍNH NĂNG MỚI

### 🔐 Chót Lương Tháng
```
Bước 1: Click [🔐 Chót Lương Tháng]
Bước 2: Chọn Tháng & Năm
Bước 3: Click [Chót Lương]
→ Hệ thống tự động tính giờ/ngày từ chấm công
→ Lương được cập nhật
→ Dữ liệu được lưu
```

### 💾 Lưu Dữ Liệu Tự Động
- Khi thêm nhân viên → lưu `data/employees.dat`
- Khi check-in/out → lưu `data/attendance.dat`
- Khi chót lương → lưu cập nhật
- Khi tắt app → dữ liệu không mất

### 🔐 Mật Khẩu An Toàn
- SHA-256 + SALT (16 bytes)
- Admin không thể xem mật khẩu
- Không thể reverse từ file
- Mỗi mật khẩu có SALT khác nhau

---

## 📊 CẤU TRÚC PACKAGE

```
com.payroll/
├── model/          → Các lớp thực thể
├── service/        → Logic tính toán ⭐ SalaryCalculator
├── repository/     → Lưu/tải file ⭐ DataRepository
├── util/           → Tiện ích ⭐ PasswordHasher
└── view/           → Giao diện người dùng
```

---

## 🔧 BIÊN DỊCH

```powershell
cd src
javac -encoding UTF-8 -d ../bin `
  com/payroll/model/*.java `
  com/payroll/service/*.java `
  com/payroll/repository/*.java `
  com/payroll/util/*.java `
  com/payroll/view/*.java
```

---

## 💰 CÔNG THỨC LƯƠNG NHANH

```
Lương Net = Lương Gross - BHXH(5%) - BHYT(1.5%) - Thuế PIT

Ví dụ:
Gross = 3,000,000
BHXH = 150,000 (5%)
BHYT = 45,000 (1.5%)
Thuế = 142,500
Net = 2,662,500 ✓
```

---

## 🆕 CÓ GÌ MỚI VỚI v2.0?

| Tính Năng | v2.0 | v2.1 |
|-----------|------|------|
| Lưu dữ liệu | ❌ | ✅ |
| Mật khẩu hash | ❌ | ✅ |
| Tính lương tự động | ❌ | ✅ |
| Package structure | ❌ | ✅ |

---

## ❓ TROUBLESHOOTING

| Vấn đề | Giải Pháp |
|-------|----------|
| Không thấy nhân viên | Xóa `data/` folder, chạy lại |
| Lỗi login | Dùng admin/admin123 |
| Lỗi compile UTF-8 | Thêm `-encoding UTF-8` |
| Dữ liệu bị mất | Khôi phục từ `data/*.dat` |

---

**Phiên bản: v2.1 | Trạng thái: ✅ Production Ready**
