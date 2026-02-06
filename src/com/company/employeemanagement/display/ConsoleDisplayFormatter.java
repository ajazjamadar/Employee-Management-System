package com.company.employeemanagement.display;

import com.company.employeemanagement.model.Employee;
import java.util.List;

/**
 * Console Display Formatter - Single Responsibility Principle (SRP)
 * Handles all console display formatting
 * Encapsulates presentation logic
 */
public class ConsoleDisplayFormatter implements IDisplayFormatter {
    
    private static final String SEPARATOR = "========================================";
    private static final String SUCCESS_PREFIX = "[SUCCESS] ";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String INFO_PREFIX = "[INFO] ";
    
    @Override
    public void displayEmployee(Employee employee) {
        if (employee == null) {
            displayError("Employee is null");
            return;
        }
        employee.displayDetails();
    }
    
    @Override
    public void displayEmployeeList(List<Employee> employees, String emptyMessage) {
        if (employees == null || employees.isEmpty()) {
            displayInfo(emptyMessage);
            return;
        }
        
        System.out.println("\n" + SEPARATOR);
        System.out.println("   SEARCH RESULTS (" + employees.size() + " found)");
        System.out.println(SEPARATOR + "\n");
        
        for (Employee emp : employees) {
            emp.displayDetails();
            System.out.println();
        }
    }
    
    @Override
    public void displaySuccess(String message) {
        System.out.println(SUCCESS_PREFIX + message);
    }
    
    @Override
    public void displayError(String message) {
        System.out.println(ERROR_PREFIX + "Error: " + message);
    }
    
    @Override
    public void displayInfo(String message) {
        System.out.println(INFO_PREFIX + message);
    }
    
    @Override
    public void displayHeader(String title) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("   " + title);
        System.out.println(SEPARATOR);
    }
    
    @Override
    public void displaySeparator() {
        System.out.println(SEPARATOR);
    }
}
