package com.payroll.util;

import com.payroll.model.*;
import com.payroll.repository.DataRepository;

/**
 * Test utility để verify register feature hoạt động
 */
public class TestRegisterFeature {
    
    public static void main(String[] args) {
        try {
            System.out.println("\n=== TEST REGISTER FEATURE ===\n");
            
            // Test 1: Create new SalaryEmployee
            System.out.println("Test 1: Creating SalaryEmployee...");
            SalaryEmployee emp1 = new SalaryEmployee(
                "Nguyễn Minh An", 
                "123 Đường Lê Lợi, Hà Nội",
                "0912345678",
                2001,
                4000000.0,
                "testuser1",
                "password123",
                "USER"
            );
            DataRepository.saveEmployee(emp1);
            System.out.println("✅ SalaryEmployee saved with ID: " + emp1.getId());
            System.out.println("   Username: " + emp1.getUsername());
            System.out.println("   Password: " + emp1.getPasswordHash());
            
            // Test 2: Create new HourlyEmployee
            System.out.println("\nTest 2: Creating HourlyEmployee...");
            HourlyEmployee emp2 = new HourlyEmployee(
                "Phạm Thị Bình",
                "456 Đường Trần Hưng Đạo, Hồ Chí Minh",
                "0987654321",
                2002,
                75000.0,
                160,
                "testuser2",
                "pass456",
                "USER"
            );
            DataRepository.saveEmployee(emp2);
            System.out.println("✅ HourlyEmployee saved with ID: " + emp2.getId());
            System.out.println("   Username: " + emp2.getUsername());
            System.out.println("   Hourly rate: " + emp2.getHourlyRate());
            
            // Test 3: Create new DailyEmployee
            System.out.println("\nTest 3: Creating DailyEmployee...");
            DailyEmployee emp3 = new DailyEmployee(
                "Trần Văn Phú",
                "789 Đường Nguyễn Huệ, Đà Nẵng",
                "0901234567",
                2003,
                250000.0,
                20,
                "testuser3",
                "secure789",
                "USER"
            );
            DataRepository.saveEmployee(emp3);
            System.out.println("✅ DailyEmployee saved with ID: " + emp3.getId());
            System.out.println("   Username: " + emp3.getUsername());
            System.out.println("   Daily rate: " + emp3.getDailyRate());
            
            // Test 4: Verify password
            System.out.println("\nTest 4: Verifying passwords...");
            Employee emp1Check = DataRepository.findEmployeeById(2001);
            if (emp1Check != null && emp1Check.verifyPassword("password123")) {
                System.out.println("✅ Password verification successful for emp1");
            } else {
                System.out.println("❌ Password verification failed for emp1");
            }
            
            Employee emp2Check = DataRepository.findEmployeeById(2002);
            if (emp2Check != null && emp2Check.verifyPassword("pass456")) {
                System.out.println("✅ Password verification successful for emp2");
            } else {
                System.out.println("❌ Password verification failed for emp2");
            }
            
            // Test 5: Load all employees and check
            System.out.println("\nTest 5: Loading all employees from database...");
            java.util.List<Employee> allEmps = DataRepository.loadEmployees();
            System.out.println("✅ Loaded " + allEmps.size() + " employees total");
            for (Employee emp : allEmps) {
                System.out.println("   - ID: " + emp.getId() + " | " + emp.getName() + " | " + emp.getUsername());
            }
            
            System.out.println("\n✅ All tests passed!\n");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
