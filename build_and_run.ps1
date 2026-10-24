#!/bin/bash
# Script compile và chạy ứng dụng trên PowerShell với SQLite

Write-Host "===========================================" -ForegroundColor Cyan
Write-Host "HE THONG QUAN LY LUONG NHAN VIEN v2.1 - SQLite" -ForegroundColor Cyan
Write-Host "===========================================" -ForegroundColor Cyan
Write-Host ""

$scriptPath = Split-Path -Parent -Path $MyInvocation.MyCommand.Definition
Set-Location $scriptPath

# Compile with SQLite JDBC
Write-Host "Dang bieu dich..." -ForegroundColor Yellow
if (Test-Path "bin") {
    Remove-Item -Path "bin" -Recurse -Force | Out-Null
}
New-Item -ItemType Directory -Name "bin" -Force | Out-Null

javac -encoding UTF-8 -d bin -cp "lib/*;." src/com/payroll/repository/DatabaseConnection.java src/com/payroll/repository/DataRepository.java src/com/payroll/model/*.java src/com/payroll/service/*.java src/com/payroll/util/*.java src/com/payroll/view/*.java 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Bieu dich thanh cong!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Dang chay ung dung..." -ForegroundColor Yellow
    Write-Host ""
    java -cp "bin;lib/*" com.payroll.view.QLLuongVien
    Write-Host "❌ Co loi trong qua trinh bieu dich!" -ForegroundColor Red
}
