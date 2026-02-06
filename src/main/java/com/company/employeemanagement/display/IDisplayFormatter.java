package com.company.employeemanagement.display;

import com.company.employeemanagement.model.Employee;
import java.util.List;

/**
 * Display Formatter Interface - Interface Segregation Principle (ISP)
 * Defines contract for displaying employee information
 * Separates presentation concerns from business logic
 */
public interface IDisplayFormatter {
    
    /**
     * Display a single employee's details
     * @param employee The employee to display
     */
    void displayEmployee(Employee employee);
    
    /**
     * Display a list of employees
     * @param employees The list of employees to display
     * @param emptyMessage Message to show if list is empty
     */
    void displayEmployeeList(List<Employee> employees, String emptyMessage);
    
    /**
     * Display success message
     * @param message The success message
     */
    void displaySuccess(String message);
    
    /**
     * Display error message
     * @param message The error message
     */
    void displayError(String message);
    
    /**
     * Display informational message
     * @param message The info message
     */
    void displayInfo(String message);
    
    /**
     * Display a header/title
     * @param title The title to display
     */
    void displayHeader(String title);
    
    /**
     * Display a separator line
     */
    void displaySeparator();
}
