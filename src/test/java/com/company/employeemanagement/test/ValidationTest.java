package com.company.employeemanagement.test;

import com.company.employeemanagement.exception.InvalidEmployeeDataException;
import com.company.employeemanagement.model.PermanentEmployee;
import com.company.employeemanagement.service.EmployeeService;

/**
 * Quick test to demonstrate Week 3 features:
 * - Exception handling
 * - Validation annotations
 * - Logging
 */
public class ValidationTest {
    
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        
        System.out.println("=== Testing Week 3 Features ===\n");
        
        // Test 1: Valid Employee (should succeed)
        System.out.println("Test 1: Adding valid employee");
        try {
            PermanentEmployee validEmp = new PermanentEmployee(
                101, "John Smith", "john@company.com", "Engineering", 
                60000, 15000
            );
            service.addEmployee(validEmp);
            System.out.println("[SUCCESS] SUCCESS: Valid employee added\n");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] FAILED: " + e.getMessage() + "\n");
        }
        
        // Test 2: Duplicate Employee ID (should fail)
        System.out.println("Test 2: Adding duplicate employee ID");
        try {
            PermanentEmployee duplicateEmp = new PermanentEmployee(
                101, "Jane Doe", "jane@company.com", "Marketing", 
                55000, 12000
            );
            service.addEmployee(duplicateEmp);
            System.out.println("[SUCCESS] SUCCESS: Duplicate employee added\n");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] EXPECTED FAILURE: " + e.getMessage() + "\n");
        }
        
        // Test 3: Low Salary (should fail validation)
        System.out.println("Test 3: Adding employee with low salary");
        try {
            PermanentEmployee lowSalaryEmp = new PermanentEmployee(
                102, "Bob Johnson", "bob@company.com", "IT", 
                5000,  // Below minimum of 10000
                2000
            );
            service.addEmployee(lowSalaryEmp);
            System.out.println("[SUCCESS] SUCCESS: Low salary employee added\n");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] EXPECTED FAILURE: " + e.getMessage() + "\n");
        }
        
        // Test 4: Empty Name (should fail validation)
        System.out.println("Test 4: Adding employee with empty name");
        try {
            PermanentEmployee emptyNameEmp = new PermanentEmployee(
                103, "", "empty@company.com", "Sales", 
                50000, 10000
            );
            service.addEmployee(emptyNameEmp);
            System.out.println("[SUCCESS] SUCCESS: Empty name employee added\n");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] EXPECTED FAILURE: " + e.getMessage() + "\n");
        }
        
        // Test 5: Null Email (should fail validation)
        System.out.println("Test 5: Adding employee with null email");
        try {
            PermanentEmployee nullEmailEmp = new PermanentEmployee(
                104, "Alice Williams", null, "HR", 
                45000, 8000
            );
            service.addEmployee(nullEmailEmp);
            System.out.println("[SUCCESS] SUCCESS: Null email employee added\n");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("[ERROR] EXPECTED FAILURE: " + e.getMessage() + "\n");
        }
        
        // Test 6: Search for existing employee
        System.out.println("Test 6: Search for existing employee");
        try {
            var employee = service.searchEmployeeById(101);
            if (employee.isPresent()) {
                System.out.println("[SUCCESS] SUCCESS: Found employee: " + employee.get().getName() + "\n");
            } else {
                System.out.println("[ERROR] FAILED: Employee not found\n");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] FAILED: " + e.getMessage() + "\n");
        }
        
        // Test 7: Search for non-existing employee
        System.out.println("Test 7: Search for non-existing employee");
        var employee = service.searchEmployeeById(999);
        if (employee.isEmpty()) {
            System.out.println("[ERROR] EXPECTED FAILURE: Employee not found with ID: 999\n");
        } else {
            System.out.println("[SUCCESS] SUCCESS: Found employee: " + employee.get().getName() + "\n");
        }
        
        System.out.println("=== All Tests Completed ===");
        System.out.println("Check the console logs to see logging in action!");
    }
}
