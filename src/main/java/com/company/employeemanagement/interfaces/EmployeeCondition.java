package com.company.employeemanagement.interfaces;

import com.company.employeemanagement.model.Employee;

/**
 * Week 4: Custom Functional Interface
 * Represents a condition/predicate for filtering employees
 */
@FunctionalInterface
public interface EmployeeCondition {
    /**
     * Test if an employee satisfies this condition
     * @param employee the employee to test
     * @return true if condition is met, false otherwise
     */
    boolean test(Employee employee);
    
    /**
     * Returns a condition that represents the logical AND of this condition and another
     */
    default EmployeeCondition and(EmployeeCondition other) {
        return (employee) -> this.test(employee) && other.test(employee);
    }
    
    /**
     * Returns a condition that represents the logical OR of this condition and another
     */
    default EmployeeCondition or(EmployeeCondition other) {
        return (employee) -> this.test(employee) || other.test(employee);
    }
    
    /**
     * Returns a condition that represents the logical negation of this condition
     */
    default EmployeeCondition negate() {
        return (employee) -> !this.test(employee);
    }
}
