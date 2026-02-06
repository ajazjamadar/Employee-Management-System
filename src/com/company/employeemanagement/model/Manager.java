package com.company.employeemanagement.model;

import com.company.employeemanagement.annotations.Min;
import java.util.ArrayList;
import java.util.List;

public class Manager extends PermanentEmployee {
    @Min(value = 0, message = "Bonus cannot be negative")
    private double bonus;
    private final List<Employee> teamMembers;
    
    public Manager() {
        super();
        this.teamMembers = new ArrayList<>();
    }
    
    public Manager(int employeeId, String name, String email, String department, 
                  double baseSalary, double benefits, double bonus) {
        super(employeeId, name, email, department, baseSalary, benefits);
        this.bonus = bonus;
        this.teamMembers = new ArrayList<>();
    }
    
    public double getBonus() {
        return bonus;
    }
    
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    
    public List<Employee> getTeamMembers() {
        return teamMembers;
    }
    
    public void addTeamMember(Employee employee) {
        if (employee != null && !teamMembers.contains(employee)) {
            teamMembers.add(employee);
        }
    }
    
    public void removeTeamMember(Employee employee) {
        teamMembers.remove(employee);
    }
    
    public int getTeamSize() {
        return teamMembers.size();
    }
    
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + bonus;
    }
    
    @Override
    public void displayDetails() {
        System.out.println("=== Manager Details ===");
        System.out.println("Employee ID: " + getEmployeeId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + getDepartment());
        System.out.println("Base Salary: $" + getBaseSalary());
        System.out.println("Benefits: $" + getBenefits());
        System.out.println("Bonus: $" + bonus);
        System.out.println("Total Salary: $" + calculateSalary());
        System.out.println("Team Size: " + teamMembers.size());
    }
    
    @Override
    public String toString() {
        return "Manager [" + super.toString() + 
               ", Bonus=" + bonus + ", Team Size=" + teamMembers.size() + 
               ", Total Salary=" + calculateSalary() + "]";
    }
}
