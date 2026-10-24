@echo off
REM =========================================================================
REM CREATE SHAREABLE ZIP PACKAGE
REM =========================================================================

setlocal enabledelayedexpansion

cls
echo =========================================================================
echo CREATING SHAREABLE ZIP PACKAGE
echo =========================================================================
echo.

REM Step 1: Build JAR
echo Step 1: Building JAR file...
if not exist payroll-system.jar (
    call build_jar.bat
    if errorlevel 1 (
        echo Build failed!
        pause
        exit /b 1
    )
)
echo ✅ JAR file ready

REM Step 2: Create ZIP
echo Step 2: Creating ZIP file...
set zipname=Payroll-System-v2.2.zip

if exist !zipname! (
    del !zipname!
)

REM Using PowerShell to create ZIP (more reliable)
powershell -Command "Compress-Archive -Path 'payroll-system.jar', 'lib', 'data', 'README_SHARE.txt' -DestinationPath '!zipname!' -Force"

if errorlevel 1 (
    echo ZIP creation failed!
    pause
    exit /b 1
)

echo ✅ ZIP file created: !zipname!

REM Step 3: Show file size
for /f %%A in ('dir /b !zipname!') do (
    for /f "tokens=5" %%B in ('dir "!zipname!"') do (
        set size=%%B
    )
)

echo.
echo =========================================================================
echo ✅ SUCCESS! Package ready to share
echo =========================================================================
echo.
echo File: !zipname!
echo.
echo Next steps:
echo 1. Share this ZIP file
echo 2. Recipient extracts it
echo 3. Runs: java -jar payroll-system.jar
echo.
pause
