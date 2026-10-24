package com.payroll.model;

import java.io.Serializable;

// Class tính toán Thuế và Bảo hiểm
public class TaxInsurance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Hệ số bảo hiểm xã hội (5%)
    private static final double INSURANCE_RATIO = 0.05;
    
    // Hệ số bảo hiểm y tế (1.5%)
    private static final double HEALTH_RATIO = 0.015;
    
    // Biểu lương tính PIT (Thuế thu nhập cá nhân) - Năm 2024
    private static final double[] PIT_RATES = {0.05, 0.10, 0.15, 0.20, 0.25, 0.30, 0.35};
    private static final double[] PIT_BRACKETS = {0, 5_000_000, 10_000_000, 18_000_000, 32_000_000, 52_000_000, 80_000_000};
    
    // Mức giảm trừ gia cộng (GGCT): 11 triệu VND/tháng
    private static final double DEDUCTION = 11_000_000;

    public static double calculateInsurance(double salary) {
        return salary * (INSURANCE_RATIO + HEALTH_RATIO);
    }

    public static double calculateSocialInsurance(double salary) {
        return salary * INSURANCE_RATIO;
    }

    public static double calculateHealthInsurance(double salary) {
        return salary * HEALTH_RATIO;
    }

    public static double calculatePIT(double salary) {
        double taxableIncome = salary - calculateInsurance(salary) - DEDUCTION;
        
        if (taxableIncome <= 0) return 0;

        double tax = 0;
        for (int i = PIT_BRACKETS.length - 1; i >= 0; i--) {
            if (taxableIncome > PIT_BRACKETS[i]) {
                double bracket = taxableIncome - PIT_BRACKETS[i];
                if (i < PIT_BRACKETS.length - 1) {
                    bracket = Math.min(bracket, PIT_BRACKETS[i + 1] - PIT_BRACKETS[i]);
                }
                tax += bracket * PIT_RATES[i];
                break;
            }
        }
        return tax;
    }

    public static double getNetSalary(double salary) {
        return salary - calculateInsurance(salary) - calculatePIT(salary);
    }

    public static String getTaxBreakdown(double salary) {
        double insurance = calculateInsurance(salary);
        double socialIns = calculateSocialInsurance(salary);
        double healthIns = calculateHealthInsurance(salary);
        double pit = calculatePIT(salary);
        double netSalary = getNetSalary(salary);

        return String.format(
            "Lương Gross: %.0f VND\n" +
            "├─ BHXH: %.0f VND\n" +
            "├─ BHYT: %.0f VND\n" +
            "├─ PIT: %.0f VND\n" +
            "└─ Lương Net: %.0f VND",
            salary, socialIns, healthIns, pit, netSalary
        );
    }
}
