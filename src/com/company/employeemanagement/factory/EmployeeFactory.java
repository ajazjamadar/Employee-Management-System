package com.company.employeemanagement.factory;

import com.company.employeemanagement.model.*;

/**
 * Employee Factory - Open/Closed Principle (OCP)
 * Encapsulates employee creation logic
 * Makes it easy to add new employee types without modifying existing code
 */
public class EmployeeFactory {
    
    /**
     * Employee types enumeration
     */
    public enum EmployeeType {
        PERMANENT,
        CONTRACT,
        MANAGER
    }
    
    /**
     * Create a permanent employee
     */
    public static PermanentEmployee createPermanentEmployee(
            int id, String name, String email, String department,
            double baseSalary, double benefits) {
        return new PermanentEmployee(id, name, email, department, baseSalary, benefits);
    }
    
    /**
     * Create a contract employee
     */
    public static ContractEmployee createContractEmployee(
            int id, String name, String email, String department,
            double hourlyRate, int hoursWorked, int contractDuration) {
        return new ContractEmployee(id, name, email, department, hourlyRate, hoursWorked, contractDuration);
    }
    
    /**
     * Create a manager
     */
    public static Manager createManager(
            int id, String name, String email, String department,
            double baseSalary, double benefits, double bonus) {
        return new Manager(id, name, email, department, baseSalary, benefits, bonus);
    }
    
    /**
     * Get employee type description
     */
    public static String getEmployeeTypeDescription(EmployeeType type) {
        return switch (type) {
            case PERMANENT -> "Permanent Employee";
            case CONTRACT -> "Contract Employee";
            case MANAGER -> "Manager";
        };
    }
}
