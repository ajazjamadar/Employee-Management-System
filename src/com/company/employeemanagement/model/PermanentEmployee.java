package com.company.employeemanagement.model;

import com.company.employeemanagement.annotations.Min;

public class PermanentEmployee extends Employee {
    @Min(value = 10000, message = "Base salary must be >= 10000")
    private double baseSalary;
    
    @Min(value = 0, message = "Benefits cannot be negative")
    private double benefits;
    
    public PermanentEmployee() {
        super();
    }
    
    public PermanentEmployee(int employeeId, String name, String email, 
                           String department, double baseSalary, double benefits) {
        super(employeeId, name, email, department);
        this.baseSalary = baseSalary;
        this.benefits = benefits;
    }
    
    public double getBaseSalary() {
        return baseSalary;
    }
    
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }
    
    public double getBenefits() {
        return benefits;
    }
    
    public void setBenefits(double benefits) {
        this.benefits = benefits;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary + benefits;
    }
    
    @Override
    public void displayDetails() {
        System.out.println("=== Permanent Employee Details ===");
        System.out.println("Employee ID: " + getEmployeeId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + getDepartment());
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println("Benefits: $" + benefits);
        System.out.println("Total Salary: $" + calculateSalary());
    }
    
    @Override
    public String toString() {
        return "PermanentEmployee [" + super.toString() + 
               ", Base Salary=" + baseSalary + ", Benefits=" + benefits + 
               ", Total Salary=" + calculateSalary() + "]";
    }
}
