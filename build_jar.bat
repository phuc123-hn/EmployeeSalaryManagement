@echo off
REM =========================================================================
REM BUILD EXECUTABLE JAR
REM =========================================================================

setlocal enabledelayedexpansion

echo.
echo =========================================================================
echo BUILDING EXECUTABLE JAR FILE
echo =========================================================================
echo.

REM Compile if not already done
if not exist bin (
    echo Compiling project...
    call compile.bat
    if errorlevel 1 (
        echo Compilation failed!
        pause
        exit /b 1
    )
)

REM Create JAR
echo Creating JAR file...
jar cmf MANIFEST.MF payroll-system.jar -C bin .

if errorlevel 1 (
    echo JAR creation failed!
    pause
    exit /b 1
)

echo.
echo =========================================================================
echo ✅ SUCCESS! JAR file created: payroll-system.jar
echo =========================================================================
echo.
echo How to run:
echo   java -jar payroll-system.jar
echo.
echo System Requirements:
echo   - Java 11 or higher
echo   - Windows/Linux/Mac
echo.
pause
