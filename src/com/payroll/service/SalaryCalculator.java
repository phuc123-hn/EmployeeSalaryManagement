package com.payroll.service;

import com.payroll.model.Employee;
import com.payroll.model.HourlyEmployee;
import com.payroll.model.DailyEmployee;
import com.payroll.model.AttendanceRecord;
import java.time.LocalDate;
import java.util.List;

/**
 * Service để tính lương dựa trên chấm công
 * Thay thế cách nhập cứng 160 giờ hoặc 22 ngày
 */
public class SalaryCalculator {
    
    /**
     * Tính tổng giờ làm việc trong tháng cho HourlyEmployee
     */
    public static double calculateMonthlyHours(int employeeId, int month, int year, List<AttendanceRecord> records) {
        double totalHours = 0;
        
        for (AttendanceRecord record : records) {
            if (record.getEmployeeId() == employeeId) {
                LocalDate date = LocalDate.parse(record.getDate());
                if (date.getMonthValue() == month && date.getYear() == year) {
                    totalHours += record.getHoursWorked();
                }
            }
        }
        
        return totalHours;
    }
    
    /**
     * Tính tổng ngày làm việc trong tháng cho DailyEmployee
     */
    public static int calculateMonthlyDays(int employeeId, int month, int year, List<AttendanceRecord> records) {
        int totalDays = 0;
        
        for (AttendanceRecord record : records) {
            if (record.getEmployeeId() == employeeId) {
                LocalDate date = LocalDate.parse(record.getDate());
                if (date.getMonthValue() == month && date.getYear() == year 
                    && record.getHoursWorked() > 0) {
                    totalDays++;
                }
            }
        }
        
        return totalDays;
    }
    
    /**
     * Cập nhật giờ/ngày làm việc cho Employee dựa trên chấm công
     */
    public static void updateEmployeeHoursFromAttendance(Employee emp, int month, int year, List<AttendanceRecord> records) {
        if (emp instanceof HourlyEmployee) {
            HourlyEmployee hourly = (HourlyEmployee) emp;
            double hours = calculateMonthlyHours(emp.getId(), month, year, records);
            hourly.setHoursWorked((int) hours);
            System.out.println("✅ Cập nhật giờ làm cho " + emp.getName() + ": " + hours + " giờ");
        } else if (emp instanceof DailyEmployee) {
            DailyEmployee daily = (DailyEmployee) emp;
            int days = calculateMonthlyDays(emp.getId(), month, year, records);
            daily.setDaysWorked(days);
            System.out.println("✅ Cập nhật ngày làm cho " + emp.getName() + ": " + days + " ngày");
        }
    }
}
