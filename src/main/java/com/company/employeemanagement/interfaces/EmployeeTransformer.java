package com.company.employeemanagement.interfaces;

import com.company.employeemanagement.model.Employee;

/**
 * Week 4: Custom Functional Interface
 * Transforms an employee into another type
 */
@FunctionalInterface
public interface EmployeeTransformer<R> {
    /**
     * Transform an employee into type R
     * @param employee the employee to transform
     * @return the transformed result
     */
    R transform(Employee employee);
}
