package com.company.employeemanagement.validation;

import com.company.employeemanagement.exception.InvalidEmployeeDataException;

/**
 * Validator Interface - Interface Segregation Principle (ISP)
 * Defines contract for object validation
 * Allows for different validation strategies
 */
public interface IValidator<T> {
    
    /**
     * Validate an object
     * @param object The object to validate
     * @throws InvalidEmployeeDataException if validation fails
     */
    void validate(T object) throws InvalidEmployeeDataException;
}
