package com.company.employeemanagement.service;

import com.company.employeemanagement.exception.EmployeeNotFoundException;
import com.company.employeemanagement.exception.InvalidEmployeeDataException;
import com.company.employeemanagement.logging.AppLogger;
import com.company.employeemanagement.model.*;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.repository.IEmployeeRepository;
import com.company.employeemanagement.util.SalaryCalculator;
import com.company.employeemanagement.validation.IValidator;
import com.company.employeemanagement.validation.ObjectValidator;
import java.util.List;
import java.util.Optional;

/**
 * Business Logic Layer - Dependency Inversion Principle (DIP)
 * Depends on IEmployeeRepository abstraction, not concrete implementation
 * Handles all business operations for employee management
 * Hides repository details from the controller/menu layer
 */
public class EmployeeService {
    
    private final IEmployeeRepository repository;
    private final IValidator<Object> validator;
    
    /**
     * Constructor with dependency injection (DIP)
     */
    public EmployeeService(IEmployeeRepository repository, IValidator<Object> validator) {
        this.repository = repository;
        this.validator = validator;
    }
    
    /**
     * Default constructor for backward compatibility
     */
    public EmployeeService() {
        this(new EmployeeRepository(), ObjectValidator.getInstance());
    }
    
    /**
     * Constructor with repository only
     */
    public EmployeeService(IEmployeeRepository repository) {
        this(repository, ObjectValidator.getInstance());
    }
    
    /**
     * Add a new employee
     * Single Responsibility: Only handles business logic
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            AppLogger.LOGGER.severe("Attempted to add null employee");
            throw new InvalidEmployeeDataException("Employee cannot be null");
        }
        
        // Validate employee data using validator (DIP)
        try {
            validator.validate(employee);
        } catch (InvalidEmployeeDataException e) {
            AppLogger.LOGGER.warning(() -> "Employee validation failed: " + e.getMessage());
            throw e;
        }
        
        // Check if employee already exists
        if (repository.employeeExists(employee.getEmployeeId())) {
            AppLogger.LOGGER.warning(() -> "Attempted to add duplicate employee ID: " + employee.getEmployeeId());
            throw new InvalidEmployeeDataException("Employee with ID " + employee.getEmployeeId() + " already exists");
        }
        
        repository.addEmployee(employee);
        AppLogger.LOGGER.info(() -> "Employee added successfully: ID=" + employee.getEmployeeId() + ", Name=" + employee.getName());
    }
    
    /**
     * Delete an employee by ID
     */
    public void deleteEmployee(int employeeId) {
        Employee employee = repository.findEmployeeById(employeeId)
                .orElseThrow(() -> {
                    AppLogger.LOGGER.warning(() -> "Attempted to delete non-existent employee ID: " + employeeId);
                    return new EmployeeNotFoundException(employeeId);
                });
        
        repository.removeEmployee(employeeId);
        AppLogger.LOGGER.info(() -> "Employee deleted successfully: ID=" + employeeId + ", Name=" + employee.getName());
    }
    
    /**
     * Search employee by ID (returns Optional)
     */
    public Optional<Employee> searchEmployeeById(int employeeId) {
        Optional<Employee> employee = repository.findEmployeeById(employeeId);
        
        if (employee.isPresent()) {
            AppLogger.LOGGER.info(() -> "Employee found: ID=" + employeeId);
        } else {
            AppLogger.LOGGER.info(() -> "Employee not found: ID=" + employeeId);
        }
        
        return employee;
    }
    
    /**
     * Search employees by name
     */
    public List<Employee> searchEmployeesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Search name cannot be empty");
        }
        
        List<Employee> employees = repository.findEmployeesByName(name);
        AppLogger.LOGGER.info(() -> "Search by name '" + name + "' returned " + employees.size() + " results");
        return employees;
    }
    
    /**
     * Search employees by department
     */
    public List<Employee> searchEmployeesByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Department name cannot be empty");
        }
        
        List<Employee> employees = repository.findEmployeesByDepartment(department);
        AppLogger.LOGGER.info(() -> "Search by department '" + department + "' returned " + employees.size() + " results");
        return employees;
    }
    
    /**
     * List all employees
     */
    public List<Employee> listAllEmployees() {
        return repository.getAllEmployees();
    }
    
    /**
     * Display all employees with details
     */
    public void displayAllEmployees() {
        List<Employee> employees = repository.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   ALL EMPLOYEES (" + employees.size() + " total)");
        System.out.println("========================================\n");
        
        for (Employee emp : employees) {
            emp.displayDetails();
            System.out.println();
        }
    }
    
    /**
     * Display employee details by ID
     */
    public void displayEmployeeById(int employeeId) {
        repository.findEmployeeById(employeeId)
                .ifPresentOrElse(
                        Employee::displayDetails,
                        () -> System.out.println("Employee not found with ID: " + employeeId)
                );
    }
    
    /**
     * Get total employee count
     */
    public int getTotalEmployeeCount() {
        return repository.getEmployeeCount();
    }
    
    /**
     * Calculate total company payroll
     */
    public double calculateTotalPayroll() {
        double total = 0;
        for (Employee emp : repository.getAllEmployees()) {
            total += emp.calculateSalary();
        }
        return total;
    }
    
    /**
     * Display payroll summary
     */
    public void displayPayrollSummary() {
        List<Employee> employees = repository.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees to calculate payroll.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   PAYROLL SUMMARY");
        System.out.println("========================================\n");
        
        double totalGross = 0;
        double totalTax = 0;
        
        for (Employee emp : employees) {
            double salary = emp.calculateSalary();
            double tax = SalaryCalculator.calculateTax(salary);
            double net = SalaryCalculator.calculateNetSalary(salary);
            
            System.out.println(emp.getName() + " (ID: " + emp.getEmployeeId() + ")");
            System.out.println("  Gross: " + SalaryCalculator.formatSalary(salary));
            System.out.println("  Tax:   " + SalaryCalculator.formatSalary(tax));
            System.out.println("  Net:   " + SalaryCalculator.formatSalary(net));
            System.out.println();
            
            totalGross += salary;
            totalTax += tax;
        }
        
        System.out.println("----------------------------------------");
        System.out.println("Total Gross Payroll: " + SalaryCalculator.formatSalary(totalGross));
        System.out.println("Total Tax:           " + SalaryCalculator.formatSalary(totalTax));
        System.out.println("Total Net Payroll:   " + SalaryCalculator.formatSalary(totalGross - totalTax));
        System.out.println("========================================\n");
    }
    
    /**
     * Update employee information
     */
    public void updateEmployee(Employee employee) {
        if (employee == null) {
            AppLogger.LOGGER.severe("Attempted to update null employee");
            throw new InvalidEmployeeDataException("Employee cannot be null");
        }
        
        // Validate employee data
        validator.validate(employee);
        
        // Check if employee exists
        if (!repository.employeeExists(employee.getEmployeeId())) {
            AppLogger.LOGGER.warning(() -> "Attempted to update non-existent employee ID: " + employee.getEmployeeId());
            throw new EmployeeNotFoundException(employee.getEmployeeId());
        }
        
        repository.updateEmployee(employee);
        AppLogger.LOGGER.info(() -> "Employee updated successfully: ID=" + employee.getEmployeeId() + ", Name=" + employee.getName());
    }
    
    /**
     * Initialize with sample data (for testing)
     */
    public void loadSampleData() {
        // Permanent Employees
        addEmployee(new PermanentEmployee(101, "John Smith", "john.smith@company.com", 
                                         "Engineering", 60000, 15000));
        addEmployee(new PermanentEmployee(102, "Sarah Johnson", "sarah.j@company.com", 
                                         "Marketing", 55000, 12000));
        
        // Contract Employees
        addEmployee(new ContractEmployee(201, "Michael Brown", "michael.b@company.com", 
                                        "IT Support", 45, 160, 12));
        addEmployee(new ContractEmployee(202, "Emily Davis", "emily.d@company.com", 
                                        "Design", 50, 150, 6));
        
        // Manager
        Manager manager = new Manager(301, "Robert Wilson", "robert.w@company.com", 
                                     "Engineering", 80000, 20000, 15000);
        addEmployee(manager);
        
        System.out.println("[SUCCESS] Sample data loaded successfully.");
    }
}
