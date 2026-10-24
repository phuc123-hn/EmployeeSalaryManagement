package com.payroll.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

// Class quản lý Check-in/Check-out
public class AttendanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int employeeId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private double hoursWorked;
    private String date;

    public AttendanceRecord(int employeeId) {
        this.employeeId = employeeId;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void checkIn() {
        this.checkInTime = LocalDateTime.now();
    }

    public void checkOut() {
        this.checkOutTime = LocalDateTime.now();
        calculateHoursWorked();
    }

    private void calculateHoursWorked() {
        if (checkInTime != null && checkOutTime != null) {
            long minutes = java.time.temporal.ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
            this.hoursWorked = minutes / 60.0;
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public String getDate() {
        return date;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCheckedIn() {
        return checkInTime != null;
    }

    public boolean isCheckedOut() {
        return checkOutTime != null;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "employeeId=" + employeeId +
                ", date='" + date + '\'' +
                ", checkIn=" + (checkInTime != null ? checkInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "N/A") +
                ", checkOut=" + (checkOutTime != null ? checkOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "N/A") +
                ", hoursWorked=" + String.format("%.2f", hoursWorked) +
                '}';
    }
}
