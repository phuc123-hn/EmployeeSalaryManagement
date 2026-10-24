═══════════════════════════════════════════════════════════════════════════════
HỆ THỐNG QUẢN LÝ LƯƠNG V2.2 - PAYROLL MANAGEMENT SYSTEM
═══════════════════════════════════════════════════════════════════════════════

🎯 CHỈNH TRONG 30 GIÂY:

1. Giải nén ZIP file này
2. Mở PowerShell/Command Prompt
3. Chạy: java -jar payroll-system.jar
4. ✅ App chạy!

═══════════════════════════════════════════════════════════════════════════════
✅ YÊU CẦU:
═══════════════════════════════════════════════════════════════════════════════

- Java 11+ (kiểm tra: java -version)
  Download: https://www.oracle.com/java/technologies/downloads/

- Windows/Mac/Linux
- 100 MB RAM trống
- 20 MB disk space

═══════════════════════════════════════════════════════════════════════════════
👤 TÀI KHOẢN TEST:
═══════════════════════════════════════════════════════════════════════════════

ADMIN (Quản lý):
  Username: admin
  Password: admin123

USER (Nhân viên):
  Username: user      / Password: user123
  Username: user2     / Password: pass123
  Username: user3     / Password: pass123

═══════════════════════════════════════════════════════════════════════════════
📂 CẤU TRÚC:
═══════════════════════════════════════════════════════════════════════════════

payroll-system.jar    ← Ứng dụng chính (53 KB)
lib/                  ← Libraries (SQLite driver)
data/                 ← Database (payroll.db)
README.txt            ← File này

═══════════════════════════════════════════════════════════════════════════════
🚀 CÁC BƯỚC CHẠY (Chi tiết):
═══════════════════════════════════════════════════════════════════════════════

Windows:
  1. Mở PowerShell (Windows Key + X → PowerShell)
  2. cd vào thư mục chứa payroll-system.jar
  3. Chạy: java -jar payroll-system.jar
  
Mac/Linux:
  1. Mở Terminal
  2. cd vào thư mục
  3. Chạy: java -jar payroll-system.jar

═══════════════════════════════════════════════════════════════════════════════
🎯 TÍNH NĂNG:
═══════════════════════════════════════════════════════════════════════════════

✅ Quản lý nhân viên (Add/Delete/Search)
✅ Tính lương 3 loại: Cố định, Theo giờ, Theo ngày
✅ Bảo hiểm & Thuế PIT tự động
✅ Chấm công Check-in/Check-out
✅ Xuất báo cáo CSV
✅ Phân quyền Admin/User
✅ SQLite Database

═══════════════════════════════════════════════════════════════════════════════
📊 DỮ LIỆU MẪU:
═══════════════════════════════════════════════════════════════════════════════

4 nhân viên mẫu:
- ID 1001: Nguyễn Hoàng Hiệp (Admin, Lương cố định 3,000,000)
- ID 1002: Ngô Hồng Phúc (User, Lương cố định 2,500,000)
- ID 1003: Nguyễn Đào Minh Trúc (User, Trả giờ 50,000/h)
- ID 1004: Trần Lê Viết (User, Trả ngày 100,000/day)

═══════════════════════════════════════════════════════════════════════════════
🔧 TROUBLESHOOT:
═══════════════════════════════════════════════════════════════════════════════

❌ "java is not recognized"
   → Cài Java: https://www.oracle.com/java/technologies/downloads/

❌ "Cannot find main class"
   → Kiểm tra: payroll-system.jar có trong thư mục không?
   → Thử lại: java -jar payroll-system.jar

❌ "Port already in use" (nếu là web)
   → Đóng ứng dụng cũ: taskkill /IM java.exe

❌ GUI không hiển thị
   → Kiểm tra console output
   → Đợi vài giây, có thể cần load libraries

═══════════════════════════════════════════════════════════════════════════════
📝 LƯU Ý:
═══════════════════════════════════════════════════════════════════════════════

✓ Database (payroll.db) sẽ được tạo trong thư mục lib/ tự động
✓ Lần chạy đầu có thể chậm chút (load libraries)
✓ Mỗi người dùng có dữ liệu riêng, không ảnh hưởng lẫn nhau
✓ Tắt app = Dữ liệu được lưu (SQLite)

═══════════════════════════════════════════════════════════════════════════════
❓ CÓ CÂU HỎI?
═══════════════════════════════════════════════════════════════════════════════

Xem hướng dẫn chi tiết: Mở file HUONG_DAN_TEST.txt (nếu có)

Hoặc contact: [Thông tin liên hệ]

═══════════════════════════════════════════════════════════════════════════════

Phiên bản: 2.2
Database: SQLite 3.44.0.0
Java: 11+
Build: 2026-01-01

═══════════════════════════════════════════════════════════════════════════════
