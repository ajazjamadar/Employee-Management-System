package com.company.employeemanagement.validation;

import com.company.employeemanagement.annotations.Min;
import com.company.employeemanagement.annotations.NotNull;
import com.company.employeemanagement.exception.InvalidEmployeeDataException;
import java.lang.reflect.Field;

/**
 * Annotation-Based Object Validator
 * Implements IValidator - Interface Segregation Principle (ISP)
 * Uses reflection to validate objects based on custom annotations
 */
public class ObjectValidator implements IValidator<Object> {
    
    private static final ObjectValidator INSTANCE = new ObjectValidator();
    
    /**
     * Private constructor for singleton
     */
    private ObjectValidator() {
    }
    
    /**
     * Get singleton instance
     */
    public static ObjectValidator getInstance() {
        return INSTANCE;
    }
    
    /**
     * Static method for backward compatibility with existing code
     * Renamed to avoid conflict with interface method
     */
    public static void validateStatic(Object obj) throws InvalidEmployeeDataException {
        getInstance().validate(obj);
    }
    
    /**
     * Implement IValidator interface method
     */
    @Override
    public void validate(Object obj) throws InvalidEmployeeDataException {
        if (obj == null) {
            throw new InvalidEmployeeDataException("Object cannot be null");
        }
        
        Class<?> clazz = obj.getClass();
        
        // Validate all fields including inherited ones
        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                
                // Check @NotNull annotation
                if (field.isAnnotationPresent(NotNull.class)) {
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    try {
                        Object value = field.get(obj);
                        if (value == null) {
                            throw new InvalidEmployeeDataException(notNull.message());
                        }
                        // For String fields, also check if empty
                        if (value instanceof String && ((String) value).trim().isEmpty()) {
                            throw new InvalidEmployeeDataException(notNull.message());
                        }
                    } catch (InvalidEmployeeDataException e) {
                        throw e; // Re-throw validation exceptions
                    } catch (IllegalAccessException e) {
                        throw new InvalidEmployeeDataException("Unable to validate field: " + field.getName());
                    }
                }
                
                // Check @Min annotation
                if (field.isAnnotationPresent(Min.class)) {
                    Min min = field.getAnnotation(Min.class);
                    try {
                        Object value = field.get(obj);
                        if (value != null) {
                            double numValue = 0;
                            if (value instanceof Number num) {
                                numValue = num.doubleValue();
                            }
                            if (numValue < min.value()) {
                                throw new InvalidEmployeeDataException(min.message());
                            }
                        }
                    } catch (InvalidEmployeeDataException e) {
                        throw e; // Re-throw validation exceptions
                    } catch (IllegalAccessException e) {
                        throw new InvalidEmployeeDataException("Unable to validate field: " + field.getName());
                    }
                }
            }
            
            // Move to parent class
            clazz = clazz.getSuperclass();
        }
    }
}
