package com.company.employeemanagement.repository;

import com.company.employeemanagement.model.Employee;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data Storage Layer - In-Memory Repository
 * Implements IEmployeeRepository - Dependency Inversion Principle (DIP)
 * Acts as a fake database for storing employees
 * Will be replaced by JDBC/JPA in future weeks
 */
public class EmployeeRepository implements IEmployeeRepository {
    
    // In-memory storage using List
    private final List<Employee> employeeList;
    
    // In-memory storage using Map for faster lookups by ID
    private final Map<Integer, Employee> employeeMap;
    
    public EmployeeRepository() {
        this.employeeList = new ArrayList<>();
        this.employeeMap = new HashMap<>();
    }
    
    /**
     * Add an employee to the repository
     */
    @Override
    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        // Check if employee ID already exists
        if (employeeMap.containsKey(employee.getEmployeeId())) {
            return false;
        }
        
        employeeList.add(employee);
        employeeMap.put(employee.getEmployeeId(), employee);
        return true;
    }
    
    /**
     * Remove an employee by ID
     */
    @Override
    public boolean removeEmployee(int employeeId) {
        Employee employee = employeeMap.get(employeeId);
        
        if (employee == null) {
            return false;
        }
        
        employeeList.remove(employee);
        employeeMap.remove(employeeId);
        return true;
    }
    
    /**
     * Find an employee by ID (returns Optional)
     */
    @Override
    public Optional<Employee> findEmployeeById(int employeeId) {
        return Optional.ofNullable(employeeMap.get(employeeId));
    }
    
    /**
     * Find employees by name (partial match) using streams
     */
    @Override
    public List<Employee> findEmployeesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchName = name.toLowerCase();
        return employeeList.stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
    }
    
    /**
     * Find employees by department using streams
     */
    @Override
    public List<Employee> findEmployeesByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return employeeList.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }
    
    /**
     * Update an employee's information
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        Employee existingEmployee = employeeMap.get(employee.getEmployeeId());
        
        if (existingEmployee == null) {
            return false;
        }
        
        // Remove old entry and add updated one
        employeeList.remove(existingEmployee);
        employeeList.add(employee);
        employeeMap.put(employee.getEmployeeId(), employee);
        
        return true;
    }
    
    /**
     * Check if employee exists by ID
     */
    @Override
    public boolean employeeExists(int employeeId) {
        return employeeMap.containsKey(employeeId);
    }
    
    /**
     * Get total number of employees
     */
    @Override
    public int getEmployeeCount() {
        return employeeList.size();
    }
    
    /**
     * Clear all employees (for testing purposes)
     */
    @Override
    public void clearAll() {
        employeeList.clear();
        employeeMap.clear();
    }
}
