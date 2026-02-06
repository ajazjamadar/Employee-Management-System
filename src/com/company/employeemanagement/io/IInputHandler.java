package com.company.employeemanagement.io;

/**
 * Input Handler Interface - Interface Segregation Principle (ISP)
 * Defines contract for user input operations
 * Separates input concerns from business logic
 */
public interface IInputHandler {
    
    /**
     * Get integer input from user
     * @return The integer value entered
     */
    int getIntInput();
    
    /**
     * Get double input from user
     * @return The double value entered
     */
    double getDoubleInput();
    
    /**
     * Get string input from user
     * @return The string value entered
     */
    String getStringInput();
    
    /**
     * Get string input with a prompt
     * @param prompt The prompt to display
     * @return The string value entered
     */
    String getStringInput(String prompt);
    
    /**
     * Close the input handler and release resources
     */
    void close();
}
