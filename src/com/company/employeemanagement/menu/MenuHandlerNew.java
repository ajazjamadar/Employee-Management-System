package com.company.employeemanagement.menu;

import com.company.employeemanagement.config.AppConfig;
import com.company.employeemanagement.display.IDisplayFormatter;
import com.company.employeemanagement.exception.EmployeeNotFoundException;
import com.company.employeemanagement.exception.InvalidEmployeeDataException;
import com.company.employeemanagement.factory.EmployeeFactory;
import com.company.employeemanagement.io.IInputHandler;
import com.company.employeemanagement.model.*;
import com.company.employeemanagement.report.EmployeeReportService;
import com.company.employeemanagement.service.EmployeeService;
import java.util.List;
import java.util.Optional;

/**
 * Console UI Layer - Single Responsibility Principle (SRP)
 * Handles user interaction through console menu
 * Delegates business operations to EmployeeService
 * Uses dependency injection for all dependencies (DIP)
 */
public class MenuHandlerNew {
    
    private final EmployeeService employeeService;
    private final EmployeeReportService reportService;
    private final IInputHandler inputHandler;
    private final IDisplayFormatter displayFormatter;
    
    /**
     * Constructor with full dependency injection (DIP)
     */
    public MenuHandlerNew(
            EmployeeService employeeService,
            EmployeeReportService reportService,
            IInputHandler inputHandler,
            IDisplayFormatter displayFormatter) {
        this.employeeService = employeeService;
        this.reportService = reportService;
        this.inputHandler = inputHandler;
        this.displayFormatter = displayFormatter;
    }
    
    /**
     * Display main menu and handle user choices
     */
    public void displayMainMenu() {
        boolean running = true;
        
        while (running) {
            printMainMenu();
            int choice = inputHandler.getIntInput();
            
            running = handleMainMenuChoice(choice);
        }
    }
    
    /**
     * Print main menu options
     */
    private void printMainMenu() {
        displayFormatter.displayHeader(AppConfig.APP_TITLE);
        System.out.println("1. Add Employee");
        System.out.println("2. Delete Employee");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Search Employees by Name");
        System.out.println("5. Search Employees by Department");
        System.out.println("6. List All Employees");
        System.out.println("7. Display Payroll Summary");
        System.out.println("8. Reports & Analytics");
        System.out.println("9. Load Sample Data");
        System.out.println("0. Exit");
        displayFormatter.displaySeparator();
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Handle main menu choice - Single Responsibility
     */
    private boolean handleMainMenuChoice(int choice) {
        switch (choice) {
            case AppConfig.MENU_ADD_EMPLOYEE -> addEmployeeMenu();
            case AppConfig.MENU_DELETE_EMPLOYEE -> deleteEmployeeMenu();
            case AppConfig.MENU_SEARCH_BY_ID -> searchEmployeeByIdMenu();
            case AppConfig.MENU_SEARCH_BY_NAME -> searchEmployeesByNameMenu();
            case AppConfig.MENU_SEARCH_BY_DEPARTMENT -> searchEmployeesByDepartmentMenu();
            case AppConfig.MENU_LIST_ALL -> listAllEmployeesMenu();
            case AppConfig.MENU_PAYROLL_SUMMARY -> displayPayrollSummaryMenu();
            case AppConfig.MENU_REPORTS -> reportsAndAnalyticsMenu();
            case AppConfig.MENU_SAMPLE_DATA -> loadSampleDataMenu();
            case AppConfig.MENU_EXIT -> {
                displayFormatter.displayInfo(AppConfig.MSG_THANK_YOU);
                return false;
            }
            default -> displayFormatter.displayError(AppConfig.MSG_INVALID_CHOICE);
        }
        return true;
    }
    
    /**
     * Add employee sub-menu
     */
    private void addEmployeeMenu() {
        displayFormatter.displayHeader("Add Employee");
        System.out.println("1. Add Permanent Employee");
        System.out.println("2. Add Contract Employee");
        System.out.println("3. Add Manager");
        System.out.print("Enter employee type: ");
        
        int type = inputHandler.getIntInput();
        
        switch (type) {
            case 1 -> addPermanentEmployee();
            case 2 -> addContractEmployee();
            case 3 -> addManager();
            default -> displayFormatter.displayError("Invalid employee type");
        }
    }
    
    /**
     * Add permanent employee - Uses Factory Pattern (OCP)
     */
    private void addPermanentEmployee() {
        try {
            int id = inputHandler.getIntInput();
            
            String name = inputHandler.getStringInput("Name: ");
            String email = inputHandler.getStringInput("Email: ");
            String department = inputHandler.getStringInput("Department: ");
            
            System.out.print("Base Salary: ");
            double baseSalary = inputHandler.getDoubleInput();
            
            System.out.print("Benefits: ");
            double benefits = inputHandler.getDoubleInput();
            
            PermanentEmployee employee = EmployeeFactory.createPermanentEmployee(
                    id, name, email, department, baseSalary, benefits);
            
            employeeService.addEmployee(employee);
            displayFormatter.displaySuccess(AppConfig.MSG_EMPLOYEE_ADDED + ": " + employee.getName());
        } catch (InvalidEmployeeDataException e) {
            displayFormatter.displayError(e.getMessage());
        }
    }
    
    /**
     * Add contract employee - Uses Factory Pattern (OCP)
     */
    private void addContractEmployee() {
        try {
            System.out.print("Employee ID: ");
            int id = inputHandler.getIntInput();
            
            String name = inputHandler.getStringInput("Name: ");
            String email = inputHandler.getStringInput("Email: ");
            String department = inputHandler.getStringInput("Department: ");
            
            System.out.print("Hourly Rate: ");
            double hourlyRate = inputHandler.getDoubleInput();
            
            System.out.print("Hours Worked: ");
            int hoursWorked = inputHandler.getIntInput();
            
            System.out.print("Contract Duration (months): ");
            int contractDuration = inputHandler.getIntInput();
            
            ContractEmployee employee = EmployeeFactory.createContractEmployee(
                    id, name, email, department, hourlyRate, hoursWorked, contractDuration);
            
            employeeService.addEmployee(employee);
            displayFormatter.displaySuccess(AppConfig.MSG_EMPLOYEE_ADDED + ": " + employee.getName());
        } catch (InvalidEmployeeDataException e) {
            displayFormatter.displayError(e.getMessage());
        }
    }
    
    /**
     * Add manager - Uses Factory Pattern (OCP)
     */
    private void addManager() {
        try {
            System.out.print("Employee ID: ");
            int id = inputHandler.getIntInput();
            
            String name = inputHandler.getStringInput("Name: ");
            String email = inputHandler.getStringInput("Email: ");
            String department = inputHandler.getStringInput("Department: ");
            
            System.out.print("Base Salary: ");
            double baseSalary = inputHandler.getDoubleInput();
            
            System.out.print("Benefits: ");
            double benefits = inputHandler.getDoubleInput();
            
            System.out.print("Bonus: ");
            double bonus = inputHandler.getDoubleInput();
            
            Manager manager = EmployeeFactory.createManager(
                    id, name, email, department, baseSalary, benefits, bonus);
            
            employeeService.addEmployee(manager);
            displayFormatter.displaySuccess(AppConfig.MSG_EMPLOYEE_ADDED + ": " + manager.getName());
        } catch (InvalidEmployeeDataException e) {
            displayFormatter.displayError(e.getMessage());
        }
    }
    
    /**
     * Delete employee menu
     */
    private void deleteEmployeeMenu() {
        displayFormatter.displayHeader("Delete Employee");
        System.out.print("Enter Employee ID to delete: ");
        int id = inputHandler.getIntInput();
        
        try {
            employeeService.deleteEmployee(id);
            displayFormatter.displaySuccess(AppConfig.MSG_EMPLOYEE_DELETED);
        } catch (EmployeeNotFoundException e) {
            displayFormatter.displayError(e.getMessage());
        }
    }
    
    /**
     * Search employee by ID menu
     */
    private void searchEmployeeByIdMenu() {
        displayFormatter.displayHeader("Search Employee by ID");
        System.out.print("Enter Employee ID: ");
        int id = inputHandler.getIntInput();
        
        Optional<Employee> employee = employeeService.searchEmployeeById(id);
        
        employee.ifPresentOrElse(
                emp -> {
                    System.out.println();
                    displayFormatter.displayEmployee(emp);
                },
                () -> displayFormatter.displayError(AppConfig.MSG_EMPLOYEE_NOT_FOUND + " with ID: " + id)
        );
    }
    
    /**
     * Search employees by name menu
     */
    private void searchEmployeesByNameMenu() {
        displayFormatter.displayHeader("Search Employees by Name");
        String name = inputHandler.getStringInput("Enter name (partial match): ");
        
        try {
            List<Employee> employees = employeeService.searchEmployeesByName(name);
            displayFormatter.displayEmployeeList(employees, 
                    "No employees found with name containing: " + name);
        } catch (InvalidEmployeeDataException e) {
            displayFormatter.displayError(e.getMessage());
        }
    }
    
    /**
     * Search employees by department menu
     */
    private void searchEmployeesByDepartmentMenu() {
        displayFormatter.displayHeader("Search Employees by Department");
        String department = inputHandler.getStringInput("Enter department: ");
        
        try {
            List<Employee> employees = employeeService.searchEmployeesByDepartment(department);
            displayFormatter.displayEmployeeList(employees, 
                    "No employees found in department: " + department);
        } catch (InvalidEmployeeDataException e) {
            displayFormatter.displayError(e.getMessage());
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
        displayFormatter.displayHeader("Load Sample Data");
        employeeService.loadSampleData();
        displayFormatter.displaySuccess("Sample data loaded successfully");
    }
    
    /**
     * Reports and Analytics Menu
     */
    private void reportsAndAnalyticsMenu() {
        displayFormatter.displayHeader("REPORTS & ANALYTICS");
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
        displayFormatter.displaySeparator();
        System.out.print("Enter your choice: ");
        
        int choice = inputHandler.getIntInput();
        
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
            default -> displayFormatter.displayError(AppConfig.MSG_INVALID_CHOICE);
        }
    }
    
    private void departmentReportMenu() {
        String dept = inputHandler.getStringInput("\nEnter department name: ");
        reportService.displayDepartmentReport(dept);
    }
    
    private void topPaidEmployeesMenu() {
        System.out.print("\nEnter number of top employees to display: ");
        int n = inputHandler.getIntInput();
        
        List<Employee> topEmployees = reportService.getTopPaidEmployees(n);
        System.out.println("\n--- Top " + n + " Highest Paid Employees ---\n");
        topEmployees.forEach(emp -> 
            System.out.println(emp.getName() + " - $" + 
                    String.format("%.2f", emp.calculateSalary()))
        );
    }
    
    private void employeesAboveSalaryMenu() {
        System.out.print("\nEnter salary threshold: ");
        double threshold = inputHandler.getDoubleInput();
        
        List<Employee> employees = reportService.getEmployeesAboveSalary(threshold);
        System.out.println("\n--- Employees with Salary Above $" + 
                String.format("%.2f", threshold) + " ---\n");
        
        if (employees.isEmpty()) {
            displayFormatter.displayInfo("No employees found above this threshold");
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
                () -> displayFormatter.displayInfo("No employees in the system")
        );
    }
    
    private void lowestPaidEmployeeMenu() {
        Optional<Employee> lowest = reportService.getLowestPaidEmployee();
        
        System.out.println("\n--- Lowest Paid Employee ---");
        lowest.ifPresentOrElse(
                emp -> System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary())),
                () -> displayFormatter.displayInfo("No employees in the system")
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
            displayFormatter.displayInfo("No departments found");
        } else {
            departments.forEach(dept -> System.out.println("• " + dept));
        }
    }
    
    /**
     * Close resources
     */
    public void close() {
        if (inputHandler != null) {
            inputHandler.close();
        }
    }
}
