# 🚀 HƯỚNG DẪN NHANH - CHẠY ỨNG DỤNG

## Cách 1: Chạy File .bat (Dễ nhất) ⭐
```
Double-click vào: run.bat
```

## Cách 2: PowerShell (Biên dịch + Chạy)
```powershell
.\build_and_run.ps1
```

## Cách 3: Command Line (Chuyên nghiệp)
```bash
# Chỉ chạy (nếu đã biên dịch):
java -cp bin QLLuongVien

# Biên dịch lại (nếu sửa code):
javac -encoding UTF-8 -d bin src/*.java
java -cp bin QLLuongVien
```

---

## 📊 CẤUTRÚC THƯ MỤC

```
01-test/
├── src/         ← Chứa tất cả .java (Source code)
├── bin/         ← Chứa tất cả .class (Compiled code - AUTO)
├── run.bat      ← Script chạy (Windows)
├── compile.bat  ← Script biên dịch (Windows)
└── build_and_run.ps1  ← Script PowerShell
```

### 📌 QUI TẮC VÀNG:
- ✏️ **Chỉnh sửa**: File trong thư mục `src/`
- ❌ **Không chỉnh sửa**: Thư mục `bin/` (tự động generate)

---

## 🔑 TÀI KHOẢN TEST

**Admin:**
- Username: `admin`
- Password: `admin123`

**User:**
- Username: `user`
- Password: `user123`

---

## 💡 CÓ LỖI?

| Lỗi | Giải pháp |
|-----|----------|
| "javac not found" | Cài Java JDK (không phải JRE) |
| "Main class not found" | Chạy từ thư mục 01-test |
| Ký tự lạ | File .java UTF-8 (đã cài rồi) |

---

**Version**: 2.0.0  
**Updated**: 2024-2025
