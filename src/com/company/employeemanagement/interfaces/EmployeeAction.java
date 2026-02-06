package com.company.employeemanagement.interfaces;

import com.company.employeemanagement.model.Employee;

/**
 * Week 4: Custom Functional Interface
 * Performs an action on an employee
 */
@FunctionalInterface
public interface EmployeeAction {
    /**
     * Perform an action on an employee
     * @param employee the employee to act upon
     */
    void perform(Employee employee);
}
