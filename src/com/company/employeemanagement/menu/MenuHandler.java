package com.company.employeemanagement.menu;

import com.company.employeemanagement.exception.EmployeeNotFoundException;
import com.company.employeemanagement.exception.InvalidEmployeeDataException;
import com.company.employeemanagement.model.*;
import com.company.employeemanagement.report.EmployeeReportService;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console UI Layer
 * Handles user interaction through console menu
 * Delegates business operations to EmployeeService
 */
public class MenuHandler {
    
    private final EmployeeService employeeService;
    private final EmployeeReportService reportService;
    private final Scanner scanner;
    
    public MenuHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.reportService = new EmployeeReportService(new EmployeeRepository());
        this.scanner = new Scanner(System.in);
    }
    
    public MenuHandler(EmployeeService employeeService, EmployeeReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display main menu and handle user choices
     */
    public void displayMainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n========================================");
            System.out.println("   EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employees by Name");
            System.out.println("5. Search Employees by Department");
            System.out.println("6. List All Employees");
            System.out.println("7. Display Payroll Summary");
            System.out.println("8. Reports & Analytics (Week 4)");
            System.out.println("9. Load Sample Data");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1 -> addEmployeeMenu();
                case 2 -> deleteEmployeeMenu();
                case 3 -> searchEmployeeByIdMenu();
                case 4 -> searchEmployeesByNameMenu();
                case 5 -> searchEmployeesByDepartmentMenu();
                case 6 -> listAllEmployeesMenu();
                case 7 -> displayPayrollSummaryMenu();
                case 8 -> reportsAndAnalyticsMenu();
                case 9 -> loadSampleDataMenu();
                case 0 -> {
                    running = false;
                    System.out.println("\nThank you for using Employee Management System!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Add employee sub-menu
     */
    private void addEmployeeMenu() {
        System.out.println("\n--- Add Employee ---");
        System.out.println("1. Add Permanent Employee");
        System.out.println("2. Add Contract Employee");
        System.out.println("3. Add Manager");
        System.out.print("Enter employee type: ");
        
        int type = getIntInput();
        
        switch (type) {
            case 1 -> addPermanentEmployee();
            case 2 -> addContractEmployee();
            case 3 -> addManager();
            default -> System.out.println("Invalid employee type.");
        }
    }
    
    /**
     * Add permanent employee
     */
    private void addPermanentEmployee() {
        try {
            System.out.print("Employee ID: ");
            int id = getIntInput();
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Department: ");
            String department = scanner.nextLine();
            
            System.out.print("Base Salary: ");
            double baseSalary = getDoubleInput();
            
            System.out.print("Benefits: ");
            double benefits = getDoubleInput();
            
            PermanentEmployee employee = new PermanentEmployee(id, name, email, department, 
                                                               baseSalary, benefits);
            employeeService.addEmployee(employee);
            System.out.println("[SUCCESS] Employee added successfully: " + employee.getName());
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * Add contract employee
     */
    private void addContractEmployee() {
        try {
            System.out.print("Employee ID: ");
            int id = getIntInput();
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Department: ");
            String department = scanner.nextLine();
            
            System.out.print("Hourly Rate: ");
            double hourlyRate = getDoubleInput();
            
            System.out.print("Hours Worked: ");
            int hoursWorked = getIntInput();
            
            System.out.print("Contract Duration (months): ");
            int contractDuration = getIntInput();
            
            ContractEmployee employee = new ContractEmployee(id, name, email, department, 
                                                             hourlyRate, hoursWorked, contractDuration);
            employeeService.addEmployee(employee);
            System.out.println("[SUCCESS] Employee added successfully: " + employee.getName());
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * Add manager
     */
    private void addManager() {
        try {
            System.out.print("Employee ID: ");
            int id = getIntInput();
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Department: ");
            String department = scanner.nextLine();
            
            System.out.print("Base Salary: ");
            double baseSalary = getDoubleInput();
            
            System.out.print("Benefits: ");
            double benefits = getDoubleInput();
            
            System.out.print("Bonus: ");
            double bonus = getDoubleInput();
            
            Manager manager = new Manager(id, name, email, department, 
                                          baseSalary, benefits, bonus);
            employeeService.addEmployee(manager);
            System.out.println("[SUCCESS] Manager added successfully: " + manager.getName());
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * Delete employee menu
     */
    private void deleteEmployeeMenu() {
        System.out.println("\n--- Delete Employee ---");
        System.out.print("Enter Employee ID to delete: ");
        int id = getIntInput();
        
        try {
            employeeService.deleteEmployee(id);
            System.out.println("[SUCCESS] Employee deleted successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * Search employee by ID menu (using Optional)
     */
    private void searchEmployeeByIdMenu() {
        System.out.println("\n--- Search Employee by ID ---");
        System.out.print("Enter Employee ID: ");
        int id = getIntInput();
        
        Optional<Employee> employee = employeeService.searchEmployeeById(id);
        
        employee.ifPresentOrElse(
                emp -> {
                    System.out.println();
                    emp.displayDetails();
                },
                () -> System.out.println("[ERROR] Employee not found with ID: " + id)
        );
    }
    
    /**
     * Search employees by name menu
     */
    private void searchEmployeesByNameMenu() {
        System.out.println("\n--- Search Employees by Name ---");
        System.out.print("Enter name (partial match): ");
        String name = scanner.nextLine();
        
        try {
            List<Employee> employees = employeeService.searchEmployeesByName(name);
            displayEmployeeList(employees, "No employees found with name containing: " + name);
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * Search employees by department menu
     */
    private void searchEmployeesByDepartmentMenu() {
        System.out.println("\n--- Search Employees by Department ---");
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        try {
            List<Employee> employees = employeeService.searchEmployeesByDepartment(department);
            displayEmployeeList(employees, "No employees found in department: " + department);
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] Error: " + e.getMessage());
        }
    }
    
    /**
     * List all employees menu
     */
    private void listAllEmployeesMenu() {
        employeeService.displayAllEmployees();
    }
    
    /**
     * Display payroll summary menu
     */
    private void displayPayrollSummaryMenu() {
        employeeService.displayPayrollSummary();
    }
    
    /**
     * Load sample data menu
     */
    private void loadSampleDataMenu() {
        System.out.println("\n--- Load Sample Data ---");
        employeeService.loadSampleData();
    }
    
    /**
     * Week 4: Reports and Analytics Menu (Streams & Lambdas)
     */
    private void reportsAndAnalyticsMenu() {
        System.out.println("\n========================================");
        System.out.println("   REPORTS & ANALYTICS (Week 4)");
        System.out.println("========================================");
        System.out.println("1. Comprehensive Salary Report");
        System.out.println("2. Department Report");
        System.out.println("3. Top N Highest Paid Employees");
        System.out.println("4. Employees Above Salary Threshold");
        System.out.println("5. Highest Paid Employee");
        System.out.println("6. Lowest Paid Employee");
        System.out.println("7. Average Salary");
        System.out.println("8. Employees by Type");
        System.out.println("9. Distinct Departments");
        System.out.println("0. Back to Main Menu");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> reportService.displaySalaryReport();
            case 2 -> departmentReportMenu();
            case 3 -> topPaidEmployeesMenu();
            case 4 -> employeesAboveSalaryMenu();
            case 5 -> highestPaidEmployeeMenu();
            case 6 -> lowestPaidEmployeeMenu();
            case 7 -> averageSalaryMenu();
            case 8 -> employeesByTypeMenu();
            case 9 -> distinctDepartmentsMenu();
            case 0 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void departmentReportMenu() {
        System.out.print("\nEnter department name: ");
        String dept = scanner.nextLine();
        reportService.displayDepartmentReport(dept);
    }
    
    private void topPaidEmployeesMenu() {
        System.out.print("\nEnter number of top employees to display: ");
        int n = getIntInput();
        
        List<Employee> topEmployees = reportService.getTopPaidEmployees(n);
        System.out.println("\n--- Top " + n + " Highest Paid Employees ---\n");
        topEmployees.forEach(emp -> 
            System.out.println(emp.getName() + " - $" + 
                    String.format("%.2f", emp.calculateSalary()))
        );
    }
    
    private void employeesAboveSalaryMenu() {
        System.out.print("\nEnter salary threshold: ");
        double threshold = getDoubleInput();
        
        List<Employee> employees = reportService.getEmployeesAboveSalary(threshold);
        System.out.println("\n--- Employees with Salary Above $" + 
                String.format("%.2f", threshold) + " ---\n");
        
        if (employees.isEmpty()) {
            System.out.println("No employees found above this threshold.");
        } else {
            employees.forEach(emp -> 
                System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary()))
            );
        }
    }
    
    private void highestPaidEmployeeMenu() {
        Optional<Employee> highest = reportService.getHighestPaidEmployee();
        
        System.out.println("\n--- Highest Paid Employee ---");
        highest.ifPresentOrElse(
                emp -> System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary())),
                () -> System.out.println("No employees in the system")
        );
    }
    
    private void lowestPaidEmployeeMenu() {
        Optional<Employee> lowest = reportService.getLowestPaidEmployee();
        
        System.out.println("\n--- Lowest Paid Employee ---");
        lowest.ifPresentOrElse(
                emp -> System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary())),
                () -> System.out.println("No employees in the system")
        );
    }
    
    private void averageSalaryMenu() {
        double avg = reportService.getAverageSalary();
        System.out.println("\n--- Average Salary ---");
        System.out.println("$" + String.format("%.2f", avg));
    }
    
    private void employeesByTypeMenu() {
        System.out.println("\n--- Employees by Type ---");
        
        List<PermanentEmployee> permanent = reportService.getPermanentEmployees();
        List<ContractEmployee> contract = reportService.getContractEmployees();
        List<Manager> managers = reportService.getManagers();
        
        System.out.println("Permanent Employees: " + permanent.size());
        System.out.println("Contract Employees: " + contract.size());
        System.out.println("Managers: " + managers.size());
    }
    
    private void distinctDepartmentsMenu() {
        List<String> departments = reportService.getDistinctDepartments();
        
        System.out.println("\n--- Distinct Departments ---");
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            departments.forEach(dept -> System.out.println("• " + dept));
        }
    }
    
    /**
     * Display a list of employees
     */
    private void displayEmployeeList(List<Employee> employees, String emptyMessage) {
        if (employees.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        
        System.out.println("\n--- Search Results (" + employees.size() + " found) ---\n");
        for (Employee emp : employees) {
            emp.displayDetails();
            System.out.println();
        }
    }
    
    /**
     * Get integer input with error handling
     */
    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Get double input with error handling
     */
    private double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Close scanner resources
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
