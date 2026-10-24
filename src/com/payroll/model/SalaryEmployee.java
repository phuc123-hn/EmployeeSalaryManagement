package com.payroll.model;

public class SalaryEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    private double monthlySalary;

    public SalaryEmployee() {
    }

    public SalaryEmployee(String name, String address, String phone, int id, double monthlySalary) {
        super(name, address, phone, id);
        this.monthlySalary = monthlySalary >= 0 ? monthlySalary : 0;
    }

    public SalaryEmployee(String name, String address, String phone, int id, double monthlySalary, String username, String password, String role) {
        super(name, address, phone, id, username, password, role);
        this.monthlySalary = monthlySalary >= 0 ? monthlySalary : 0;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary >= 0 ? monthlySalary : 0;
    }

    @Override
    public double calculateGrossSalary() {
        return monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "SalaryEmployee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", monthlySalary=" + monthlySalary +
                ", salary=" + calculateSalary() +
                '}';
    }
}
