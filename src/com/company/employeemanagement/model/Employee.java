package com.company.employeemanagement.model;

import com.company.employeemanagement.annotations.NotNull;

public abstract class Employee {
    private int employeeId;
    
    @NotNull(message = "Employee name is mandatory")
    private String name;
    
    @NotNull(message = "Employee email is mandatory")
    private String email;
    
    @NotNull(message = "Employee department is mandatory")
    private String department;
    
    public Employee() {
    }
    
    public Employee(int employeeId, String name, String email, String department) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public abstract double calculateSalary();
    
    public abstract void displayDetails();
    
    @Override
    public String toString() {
        return "Employee [ID=" + employeeId + ", Name=" + name + 
               ", Email=" + email + ", Department=" + department + "]";
    }
}
