package com.payroll.model;

public class HourlyEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    private double hourlyRate;
    private int hoursWorked;

    public HourlyEmployee() {
    }

    public HourlyEmployee(String name, String address, String phone, int id, double hourlyRate, int hoursWorked) {
        super(name, address, phone, id);
        this.hourlyRate = hourlyRate >= 0 ? hourlyRate : 0;
        this.hoursWorked = hoursWorked >= 0 ? hoursWorked : 0;
    }

    public HourlyEmployee(String name, String address, String phone, int id, double hourlyRate, int hoursWorked, String username, String password, String role) {
        super(name, address, phone, id, username, password, role);
        this.hourlyRate = hourlyRate >= 0 ? hourlyRate : 0;
        this.hoursWorked = hoursWorked >= 0 ? hoursWorked : 0;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate >= 0 ? hourlyRate : 0;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked >= 0 ? hoursWorked : 0;
    }

    @Override
    public double calculateGrossSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", salary=" + calculateSalary() +
                '}';
    }
}
