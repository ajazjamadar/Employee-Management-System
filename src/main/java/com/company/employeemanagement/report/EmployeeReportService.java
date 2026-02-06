package com.company.employeemanagement.report;

import com.company.employeemanagement.logging.AppLogger;
import com.company.employeemanagement.model.*;
import com.company.employeemanagement.repository.IEmployeeRepository;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Week 4: Streams, Lambdas, Functional Interfaces & Optional
 * Centralized reporting service using Java 8+ features
 * Uses IEmployeeRepository interface (DIP)
 */
public class EmployeeReportService {
    
    private final IEmployeeRepository repository;
    
    public EmployeeReportService(IEmployeeRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Calculate total payroll using stream and mapToDouble
     */
    public double calculateTotalPayroll() {
        AppLogger.LOGGER.info("Calculating total payroll using streams");
        
        return repository.getAllEmployees()
                .stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
    }
    
    /**
     * Get employees by department using filter
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        AppLogger.LOGGER.info(() -> "Filtering employees by department: " + department);
        
        return repository.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .toList();
    }
    
    /**
     * Get highest paid employee using max and Optional
     */
    public Optional<Employee> getHighestPaidEmployee() {
        AppLogger.LOGGER.info("Finding highest paid employee");
        
        return repository.getAllEmployees()
                .stream()
                .max(Comparator.comparing(Employee::calculateSalary));
    }
    
    /**
     * Get lowest paid employee using min and Optional
     */
    public Optional<Employee> getLowestPaidEmployee() {
        return repository.getAllEmployees()
                .stream()
                .min(Comparator.comparing(Employee::calculateSalary));
    }
    
    /**
     * Get average salary using stream
     */
    public double getAverageSalary() {
        return repository.getAllEmployees()
                .stream()
                .mapToDouble(Employee::calculateSalary)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Count employees by department using groupingBy and counting
     */
    public Map<String, Long> countEmployeesByDepartment() {
        return repository.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));
    }
    
    /**
     * Get total salary by department
     */
    public Map<String, Double> getTotalSalaryByDepartment() {
        return repository.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summingDouble(Employee::calculateSalary)
                ));
    }
    
    /**
     * Get employees with salary above threshold using Predicate
     */
    public List<Employee> getEmployeesAboveSalary(double threshold) {
        Predicate<Employee> salaryFilter = e -> e.calculateSalary() > threshold;
        
        return repository.getAllEmployees()
                .stream()
                .filter(salaryFilter)
                .toList();
    }
    
    /**
     * Get top N highest paid employees using limit
     */
    public List<Employee> getTopPaidEmployees(int n) {
        return repository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::calculateSalary).reversed())
                .limit(n)
                .toList();
    }
    
    /**
     * Get employees sorted by name using method reference
     */
    public List<Employee> getEmployeesSortedByName() {
        return repository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getName))
                .toList();
    }
    
    /**
     * Check if any employee has salary above threshold using anyMatch
     */
    public boolean hasHighEarner(double threshold) {
        return repository.getAllEmployees()
                .stream()
                .anyMatch(e -> e.calculateSalary() > threshold);
    }
    
    /**
     * Check if all employees have salary above threshold using allMatch
     */
    public boolean allEmployeesAboveSalary(double threshold) {
        return repository.getAllEmployees()
                .stream()
                .allMatch(e -> e.calculateSalary() > threshold);
    }
    
    /**
     * Get employees by type using instanceof and filter
     */
    public List<PermanentEmployee> getPermanentEmployees() {
        return repository.getAllEmployees()
                .stream()
                .filter(e -> e instanceof PermanentEmployee)
                .map(e -> (PermanentEmployee) e)
                .toList();
    }
    
    public List<ContractEmployee> getContractEmployees() {
        return repository.getAllEmployees()
                .stream()
                .filter(e -> e instanceof ContractEmployee)
                .map(e -> (ContractEmployee) e)
                .toList();
    }
    
    public List<Manager> getManagers() {
        return repository.getAllEmployees()
                .stream()
                .filter(e -> e instanceof Manager)
                .map(e -> (Manager) e)
                .toList();
    }
    
    /**
     * Get employee names using map and method reference
     */
    public List<String> getAllEmployeeNames() {
        return repository.getAllEmployees()
                .stream()
                .map(Employee::getName)
                .toList();
    }
    
    /**
     * Get employee IDs using mapToInt
     */
    public List<Integer> getAllEmployeeIds() {
        return repository.getAllEmployees()
                .stream()
                .map(Employee::getEmployeeId)
                .toList();
    }
    
    /**
     * Find employee by ID using Optional
     */
    public Optional<Employee> findEmployeeById(int id) {
        return repository.getAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeId() == id)
                .findFirst();
    }
    
    /**
     * Apply function to all employees using Consumer
     */
    public void applyToAllEmployees(Consumer<Employee> action) {
        repository.getAllEmployees()
                .stream()
                .forEach(action);
    }
    
    /**
     * Transform employees using Function
     */
    public <R> List<R> transformEmployees(Function<Employee, R> transformer) {
        return repository.getAllEmployees()
                .stream()
                .map(transformer)
                .toList();
    }
    
    /**
     * Filter employees using custom Predicate
     */
    public List<Employee> filterEmployees(Predicate<Employee> condition) {
        return repository.getAllEmployees()
                .stream()
                .filter(condition)
                .toList();
    }
    
    /**
     * Get salary statistics using summaryStatistics
     */
    public DoubleSummaryStatistics getSalaryStatistics() {
        return repository.getAllEmployees()
                .stream()
                .mapToDouble(Employee::calculateSalary)
                .summaryStatistics();
    }
    
    /**
     * Get distinct departments using distinct
     */
    public List<String> getDistinctDepartments() {
        return repository.getAllEmployees()
                .stream()
                .map(Employee::getDepartment)
                .distinct()
                .sorted()
                .toList();
    }
    
    /**
     * Partition employees by salary threshold
     */
    public Map<Boolean, List<Employee>> partitionBySalary(double threshold) {
        return repository.getAllEmployees()
                .stream()
                .collect(Collectors.partitioningBy(
                        e -> e.calculateSalary() > threshold
                ));
    }
    
    /**
     * Get employees with parallel stream for large datasets
     */
    public long countEmployeesParallel() {
        return repository.getAllEmployees()
                .parallelStream()
                .count();
    }
    
    /**
     * Display comprehensive salary report
     */
    public void displaySalaryReport() {
        System.out.println("\n========================================");
        System.out.println("   COMPREHENSIVE SALARY REPORT");
        System.out.println("========================================\n");
        
        DoubleSummaryStatistics stats = getSalaryStatistics();
        
        System.out.println("Total Employees: " + stats.getCount());
        System.out.println("Total Payroll: $" + String.format("%.2f", stats.getSum()));
        System.out.println("Average Salary: $" + String.format("%.2f", stats.getAverage()));
        System.out.println("Highest Salary: $" + String.format("%.2f", stats.getMax()));
        System.out.println("Lowest Salary: $" + String.format("%.2f", stats.getMin()));
        
        System.out.println("\n--- Highest Paid Employee ---");
        getHighestPaidEmployee().ifPresentOrElse(
                emp -> System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary())),
                () -> System.out.println("No employees found")
        );
        
        System.out.println("\n--- Lowest Paid Employee ---");
        getLowestPaidEmployee().ifPresentOrElse(
                emp -> System.out.println(emp.getName() + " - $" + 
                        String.format("%.2f", emp.calculateSalary())),
                () -> System.out.println("No employees found")
        );
        
        System.out.println("\n--- Employees by Department ---");
        countEmployeesByDepartment().forEach((dept, count) ->
                System.out.println(dept + ": " + count + " employees")
        );
        
        System.out.println("\n--- Total Salary by Department ---");
        getTotalSalaryByDepartment().forEach((dept, total) ->
                System.out.println(dept + ": $" + String.format("%.2f", total))
        );
        
        System.out.println("\n========================================\n");
    }
    
    /**
     * Display department report
     */
    public void displayDepartmentReport(String department) {
        System.out.println("\n========================================");
        System.out.println("   DEPARTMENT REPORT: " + department.toUpperCase());
        System.out.println("========================================\n");
        
        List<Employee> deptEmployees = getEmployeesByDepartment(department);
        
        if (deptEmployees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
            return;
        }
        
        System.out.println("Total Employees: " + deptEmployees.size());
        
        double deptTotal = deptEmployees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
        
        double deptAvg = deptEmployees.stream()
                .mapToDouble(Employee::calculateSalary)
                .average()
                .orElse(0.0);
        
        System.out.println("Total Payroll: $" + String.format("%.2f", deptTotal));
        System.out.println("Average Salary: $" + String.format("%.2f", deptAvg));
        
        System.out.println("\n--- Employees ---");
        deptEmployees.stream()
                .sorted(Comparator.comparing(Employee::calculateSalary).reversed())
                .forEach(emp -> System.out.println(
                        emp.getName() + " (ID: " + emp.getEmployeeId() + 
                        ") - $" + String.format("%.2f", emp.calculateSalary())
                ));
        
        System.out.println("\n========================================\n");
    }
}
