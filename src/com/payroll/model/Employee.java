package com.payroll.model;

import com.payroll.util.PasswordHasher;
import java.io.Serializable;

public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected String address;
    protected String phone;
    protected int id;
    protected String role; // ADMIN hoặc USER
    protected String username;
    protected String passwordHash; // Lưu hash, không phải plain text

    public Employee() {
    }

    public Employee(String name, String address, String phone, int id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.id = id;
        this.role = "USER";
    }

    public Employee(String name, String address, String phone, int id, String username, String password, String role) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.id = id;
        this.username = username;
        this.passwordHash = PasswordHasher.hashPassword(password);
        this.role = role;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Verify password thay vì getter
    public boolean verifyPassword(String password) {
        if (passwordHash == null) return false;
        return PasswordHasher.verifyPassword(password, passwordHash);
    }

    public void setPassword(String password) {
        this.passwordHash = PasswordHasher.hashPassword(password);
    }

    // Phương thức trừu tượng
    public abstract double calculateGrossSalary();
    
    public double calculateNetSalary() {
        double grossSalary = calculateGrossSalary();
        return TaxInsurance.getNetSalary(grossSalary);
    }
    
    public double calculateInsurance() {
        return TaxInsurance.calculateInsurance(calculateGrossSalary());
    }
    
    public double calculateTax() {
        return TaxInsurance.calculatePIT(calculateGrossSalary());
    }

    public double calculateSalary() {
        return calculateGrossSalary();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", role='" + role + '\'' +
                ", salary=" + calculateSalary() +
                '}';
    }
}
