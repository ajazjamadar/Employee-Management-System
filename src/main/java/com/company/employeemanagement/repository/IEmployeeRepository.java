package com.company.employeemanagement.repository;

import com.company.employeemanagement.model.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface - Dependency Inversion Principle (DIP)
 * Defines contract for employee data access
 * Allows for different implementations (in-memory, database, etc.)
 */
public interface IEmployeeRepository {
    
    /**
     * Add an employee to the repository
     * @param employee The employee to add
     * @return true if successful, false otherwise
     */
    boolean addEmployee(Employee employee);
    
    /**
     * Remove an employee by ID
     * @param employeeId The ID of the employee to remove
     * @return true if successful, false otherwise
     */
    boolean removeEmployee(int employeeId);
    
    /**
     * Find an employee by ID
     * @param employeeId The ID of the employee
     * @return Optional containing the employee if found
     */
    Optional<Employee> findEmployeeById(int employeeId);
    
    /**
     * Find employees by name (partial match)
     * @param name The name to search for
     * @return List of matching employees
     */
    List<Employee> findEmployeesByName(String name);
    
    /**
     * Find employees by department
     * @param department The department to search for
     * @return List of employees in the department
     */
    List<Employee> findEmployeesByDepartment(String department);
    
    /**
     * Get all employees
     * @return List of all employees
     */
    List<Employee> getAllEmployees();
    
    /**
     * Update an employee's information
     * @param employee The employee with updated information
     * @return true if successful, false otherwise
     */
    boolean updateEmployee(Employee employee);
    
    /**
     * Check if employee exists by ID
     * @param employeeId The ID to check
     * @return true if exists, false otherwise
     */
    boolean employeeExists(int employeeId);
    
    /**
     * Get total number of employees
     * @return The count of employees
     */
    int getEmployeeCount();
    
    /**
     * Clear all employees (for testing purposes)
     */
    void clearAll();
}
