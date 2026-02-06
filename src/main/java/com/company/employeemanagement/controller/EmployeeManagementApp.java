package com.company.employeemanagement.controller;

import com.company.employeemanagement.display.ConsoleDisplayFormatter;
import com.company.employeemanagement.display.IDisplayFormatter;
import com.company.employeemanagement.io.ConsoleInputHandler;
import com.company.employeemanagement.io.IInputHandler;
import com.company.employeemanagement.menu.MenuHandlerNew;
import com.company.employeemanagement.report.EmployeeReportService;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.repository.IEmployeeRepository;
import com.company.employeemanagement.service.EmployeeService;
import com.company.employeemanagement.validation.IValidator;
import com.company.employeemanagement.validation.ObjectValidator;

/**
 * Main Controller - Entry Point
 * Demonstrates Dependency Inversion Principle (DIP)
 * All dependencies are injected, following SOLID principles
 * 
 * SOLID Principles Applied:
 * - S: Each class has single responsibility
 * - O: System open for extension through interfaces
 * - L: All implementations are substitutable
 * - I: Small, focused interfaces
 * - D: Depends on abstractions, not concretions
 */
public class EmployeeManagementApp {
    
    public static void main(String[] args) {
        // === Dependency Injection Setup (DIP) ===
        
        // 1. Initialize infrastructure dependencies
        IEmployeeRepository repository = new EmployeeRepository();
        IValidator<Object> validator = ObjectValidator.getInstance();
        IInputHandler inputHandler = new ConsoleInputHandler();
        IDisplayFormatter displayFormatter = new ConsoleDisplayFormatter();
        
        // 2. Initialize service layer with injected dependencies
        EmployeeService employeeService = new EmployeeService(repository, validator);
        
        // 3. Initialize report service with shared repository
        EmployeeReportService reportService = new EmployeeReportService(repository);
        
        // 4. Initialize menu handler with all dependencies (DIP)
        MenuHandlerNew menuHandler = new MenuHandlerNew(
                employeeService,
                reportService,
                inputHandler,
                displayFormatter
        );
        
        // 5. Start the application
        try {
            menuHandler.displayMainMenu();
        } finally {
            // Clean up resources
            menuHandler.close();
        }
    }
}
