@echo off
cd /d "%~dp0"
echo ==========================================
echo BIEN DICH CHUONG TRINH VOI SQLITE
echo ==========================================
echo.
echo Dang bien dich cac file .java...
echo.

REM Delete old compiled files
if exist bin rmdir /s /q bin
mkdir bin

REM Compile with SQLite JDBC in classpath
javac -encoding UTF-8 -d bin -cp "lib/*;." src/com/payroll/repository/DatabaseConnection.java src/com/payroll/repository/DataRepository.java src/com/payroll/model/*.java src/com/payroll/service/*.java src/com/payroll/util/*.java src/com/payroll/view/*.java src/com/payroll/web/*.java

if %errorlevel% equ 0 (
    echo.
    echo ✅ Bien dich thanh cong!
    echo Cac file .class da duoc luu trong thu muc: bin\
    echo.
    echo Ban co the chay ung dung bang cach:
    echo 1. Double-click vao run.bat
    echo 2. Hoac chay: java -cp "bin;lib/*" com.payroll.view.QLLuongVien
) else (
    echo.
    echo ❌ Co loi trong qua trinh bien dich!
