@echo off
REM =========================================================================
REM SCRIPT CHAY TEST CHIEN DICH
REM =========================================================================

setlocal enabledelayedexpansion

cls
echo =========================================================================
echo HE THONG QUAN LY LUONG - TEST SUITE
echo =========================================================================
echo.
echo Nhom Test Khong Dung GUI:
echo.
echo 1. Database Statistics (kiem tra du lieu)
echo 2. Test Register Feature (kiem tra tao tai khoan)
echo 3. Cleanup va Reset Database
echo 4. Run Main Application (GUI)
echo 5. Compile Project
echo.

set /p choice="Chon (1-5): "

if "%choice%"=="1" goto test_db
if "%choice%"=="2" goto test_register
if "%choice%"=="3" goto cleanup
if "%choice%"=="4" goto run_app
if "%choice%"=="5" goto compile
goto invalid

:test_db
echo.
echo =========================================================================
echo RUNNING: Database Statistics
echo =========================================================================
echo.
java -cp "bin;lib/*" com.payroll.util.DatabaseStats
echo.
pause
goto menu

:test_register
echo.
echo =========================================================================
echo RUNNING: Test Register Feature
echo =========================================================================
echo.
java -cp "bin;lib/*" com.payroll.util.TestRegisterFeature
echo.
pause
goto menu

:cleanup
echo.
echo =========================================================================
echo CLEANUP: Delete and Reset Database
echo =========================================================================
echo.
del data\payroll.db
echo ✅ Deleted payroll.db - Database will reset on next run
echo.
pause
goto menu

:run_app
echo.
echo =========================================================================
echo RUNNING: Main Application (GUI)
echo =========================================================================
echo.
echo Chop ung dung khong khong chay tren nay.
echo Dung cua so Console hoac bam CTRL+C de dung.
echo.
java -cp "bin;lib/*" com.payroll.view.QLLuongVien
goto end

:compile
echo.
echo =========================================================================
echo COMPILING: Project
echo =========================================================================
echo.
call compile.bat
echo.
pause
goto menu

:invalid
echo Invalid choice!
pause
goto menu

:menu
goto start

:end
echo.
echo Done!
exit /b
