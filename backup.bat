@echo off
REM Script để backup/restore database sang/từ SQL format

cd /d "%~dp0"

echo ==========================================
echo DATABASE BACKUP & RESTORE TOOL
echo ==========================================
echo.

if "%1"=="" (
    echo Sử dụng:
    echo   backup.bat export    - Export database to SQL file
    echo   backup.bat import    - Import from SQL file
    echo   backup.bat stats     - Show database statistics
    echo.
    goto :end
)

if /i "%1"=="export" (
    echo.
    echo Dang export database...
    java -cp "bin;lib/*" com.payroll.util.DatabaseExporter export
    echo.
    echo File SQL da duoc tao: data/payroll_dump.sql
    goto :end
)

if /i "%1"=="import" (
    echo.
    echo Dang import database...
    if "%2"=="" (
        java -cp "bin;lib/*" com.payroll.util.DatabaseExporter import
    ) else (
        java -cp "bin;lib/*" com.payroll.util.DatabaseExporter import "%2"
    )
    goto :end
)

if /i "%1"=="stats" (
    echo.
    java -cp "bin;lib/*" com.payroll.util.DatabaseExporter stats
    goto :end
)

echo Unknown command: %1

:end
echo.
pause
