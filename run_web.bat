@echo off
REM =========================================================================
REM BUILD AND RUN WEB APPLICATION
REM =========================================================================

setlocal enabledelayedexpansion

cls
echo =========================================================================
echo PAYROLL SYSTEM - WEB VERSION
echo =========================================================================
echo.

REM Check if Spring Boot libs exist
if not exist lib\spring-boot-3.1.0.jar (
    echo ERROR: Spring Boot libraries not found!
    echo.
    echo Please download Spring Boot and copy to lib/ folder
    echo Or manually set CLASSPATH to include Spring Boot jars
    echo.
    pause
    exit /b 1
)

REM Compile
echo Compiling project...
del bin\*.class /s /q 2>nul
javac -d bin -cp "lib/*;." src/com/payroll/**/*.java 2>nul
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation done!
echo.
echo =========================================================================
echo STARTING WEB APPLICATION
echo =========================================================================
echo.
echo Access: http://localhost:8080
echo.
echo Press CTRL+C to stop
echo.

REM Run Spring Boot application
java -cp "bin;lib/*" com.payroll.web.PayrollWebApplication

pause
