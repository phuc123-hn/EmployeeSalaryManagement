#!/bin/bash
# PowerShell script để backup/restore database sang/từ SQL format

param(
    [string]$command = "",
    [string]$file = ""
)

Write-Host "===========================================" -ForegroundColor Cyan
Write-Host "DATABASE BACKUP & RESTORE TOOL" -ForegroundColor Cyan
Write-Host "===========================================" -ForegroundColor Cyan
Write-Host ""

$scriptPath = Split-Path -Parent -Path $MyInvocation.MyCommand.Definition
Set-Location $scriptPath

if ([string]::IsNullOrEmpty($command)) {
    Write-Host "Usage:" -ForegroundColor Yellow
    Write-Host "  .\backup.ps1 export    - Export database to SQL file"
    Write-Host "  .\backup.ps1 import    - Import from SQL file (data/payroll_dump.sql)"
    Write-Host "  .\backup.ps1 import <file> - Import from specific SQL file"
    Write-Host "  .\backup.ps1 stats     - Show database statistics"
    exit
}

switch ($command.ToLower()) {
    "export" {
        Write-Host "Dang export database..." -ForegroundColor Yellow
        java -cp "bin;lib/*" com.payroll.util.DatabaseExporter export
        Write-Host ""
        Write-Host "File SQL da duoc tao: data/payroll_dump.sql" -ForegroundColor Green
    }
    
    "import" {
        Write-Host "Dang import database..." -ForegroundColor Yellow
        if ([string]::IsNullOrEmpty($file)) {
            java -cp "bin;lib/*" com.payroll.util.DatabaseExporter import
        } else {
            java -cp "bin;lib/*" com.payroll.util.DatabaseExporter import "$file"
        }
    }
    
    "stats" {
        Write-Host ""
        java -cp "bin;lib/*" com.payroll.util.DatabaseExporter stats
    }
    
    default {
        Write-Host "Unknown command: $command" -ForegroundColor Red
    }
}

Write-Host ""
