package com.company.employeemanagement.config;

/**
 * Application Configuration - Single Responsibility Principle (SRP)
 * Centralized configuration and constants
 * Makes it easy to modify application behavior without changing code
 */
public final class AppConfig {
    
    // Prevent instantiation
    private AppConfig() {
        throw new AssertionError("Cannot instantiate AppConfig");
    }
    
    // ===== Display Constants =====
    public static final String SEPARATOR = "========================================";
    public static final String SUCCESS_PREFIX = "[SUCCESS] ";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String INFO_PREFIX = "[INFO] ";
    
    // ===== Validation Constants =====
    public static final double MIN_BASE_SALARY = 10000.0;
    public static final double MIN_HOURLY_RATE = 15.0;
    public static final int MIN_CONTRACT_DURATION = 1;
    public static final double MIN_BENEFITS = 0.0;
    public static final double MIN_BONUS = 0.0;
    public static final int MIN_HOURS_WORKED = 0;
    
    // ===== Tax Configuration =====
    public static final double TAX_RATE = 0.15; // 15% tax rate
    
    // ===== Menu Options =====
    public static final int MENU_ADD_EMPLOYEE = 1;
    public static final int MENU_DELETE_EMPLOYEE = 2;
    public static final int MENU_SEARCH_BY_ID = 3;
    public static final int MENU_SEARCH_BY_NAME = 4;
    public static final int MENU_SEARCH_BY_DEPARTMENT = 5;
    public static final int MENU_LIST_ALL = 6;
    public static final int MENU_PAYROLL_SUMMARY = 7;
    public static final int MENU_REPORTS = 8;
    public static final int MENU_SAMPLE_DATA = 9;
    public static final int MENU_EXIT = 0;
    
    // ===== Messages =====
    public static final String MSG_EMPLOYEE_ADDED = "Employee added successfully";
    public static final String MSG_EMPLOYEE_DELETED = "Employee deleted successfully";
    public static final String MSG_EMPLOYEE_NOT_FOUND = "Employee not found";
    public static final String MSG_INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String MSG_THANK_YOU = "Thank you for using Employee Management System!";
    
    // ===== Application Info =====
    public static final String APP_NAME = "Employee Management System";
    public static final String APP_VERSION = "2.0";
    public static final String APP_TITLE = "   " + APP_NAME;
}
