package com.company.employeemanagement.model;

import com.company.employeemanagement.annotations.Min;

public class ContractEmployee extends Employee {
    @Min(value = 15, message = "Hourly rate must be >= 15")
    private double hourlyRate;
    
    @Min(value = 0, message = "Hours worked cannot be negative")
    private int hoursWorked;
    
    @Min(value = 1, message = "Contract duration must be at least 1 month")
    private int contractDuration; // in months
    
    public ContractEmployee() {
        super();
    }
    
    public ContractEmployee(int employeeId, String name, String email, 
                          String department, double hourlyRate, int hoursWorked, 
                          int contractDuration) {
        super(employeeId, name, email, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.contractDuration = contractDuration;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    public int getHoursWorked() {
        return hoursWorked;
    }
    
    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
    
    public int getContractDuration() {
        return contractDuration;
    }
    
    public void setContractDuration(int contractDuration) {
        this.contractDuration = contractDuration;
    }
    
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
    
    @Override
    public void displayDetails() {
        System.out.println("=== Contract Employee Details ===");
        System.out.println("Employee ID: " + getEmployeeId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + getDepartment());
        System.out.println("Hourly Rate: $" + hourlyRate);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Contract Duration: " + contractDuration + " months");
        System.out.println("Total Salary: $" + calculateSalary());
    }
    
    @Override
    public String toString() {
        return "ContractEmployee [" + super.toString() + 
               ", Hourly Rate=" + hourlyRate + ", Hours Worked=" + hoursWorked + 
               ", Contract Duration=" + contractDuration + " months" +
               ", Total Salary=" + calculateSalary() + "]";
    }
}
