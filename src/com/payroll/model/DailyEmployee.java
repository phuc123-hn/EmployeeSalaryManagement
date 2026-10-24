package com.payroll.model;

public class DailyEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    private double dailyRate;
    private int daysWorked;

    public DailyEmployee() {
    }

    public DailyEmployee(String name, String address, String phone, int id, double dailyRate, int daysWorked) {
        super(name, address, phone, id);
        this.dailyRate = dailyRate >= 0 ? dailyRate : 0;
        this.daysWorked = daysWorked >= 0 ? daysWorked : 0;
    }

    public DailyEmployee(String name, String address, String phone, int id, double dailyRate, int daysWorked, String username, String password, String role) {
        super(name, address, phone, id, username, password, role);
        this.dailyRate = dailyRate >= 0 ? dailyRate : 0;
        this.daysWorked = daysWorked >= 0 ? daysWorked : 0;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate >= 0 ? dailyRate : 0;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked >= 0 ? daysWorked : 0;
    }

    @Override
    public double calculateGrossSalary() {
        return dailyRate * daysWorked;
    }

    @Override
    public double calculateSalary() {
        return dailyRate * daysWorked;
    }

    @Override
    public String toString() {
        return "DailyEmployee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", dailyRate=" + dailyRate +
                ", daysWorked=" + daysWorked +
                ", salary=" + calculateSalary() +
                '}';
    }
}
