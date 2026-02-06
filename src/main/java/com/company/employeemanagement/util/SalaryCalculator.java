package com.company.employeemanagement.util;

public class SalaryCalculator {
    
    // Tax rates
    private static final double TAX_RATE_LOW = 0.10;      // 10% for salary < 50000
    private static final double TAX_RATE_MEDIUM = 0.20;   // 20% for salary 50000-100000
    private static final double TAX_RATE_HIGH = 0.30;     // 30% for salary > 100000
    
    // Salary thresholds
    private static final double LOW_THRESHOLD = 50000;
    private static final double HIGH_THRESHOLD = 100000;
    
    // Private constructor to prevent instantiation
    private SalaryCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Calculate tax based on gross salary using progressive tax rates
     */
    public static double calculateTax(double grossSalary) {
        if (grossSalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        
        if (grossSalary < LOW_THRESHOLD) {
            return grossSalary * TAX_RATE_LOW;
        } else if (grossSalary <= HIGH_THRESHOLD) {
            return grossSalary * TAX_RATE_MEDIUM;
        } else {
            return grossSalary * TAX_RATE_HIGH;
        }
    }
    
    /**
     * Calculate net salary after tax deduction
     */
    public static double calculateNetSalary(double grossSalary) {
        return grossSalary - calculateTax(grossSalary);
    }
    
    /**
     * Calculate annual salary from monthly salary
     */
    public static double calculateAnnualSalary(double monthlySalary) {
        return monthlySalary * 12;
    }
    
    /**
     * Calculate monthly salary from annual salary
     */
    public static double calculateMonthlySalary(double annualSalary) {
        return annualSalary / 12;
    }
    
    /**
     * Calculate salary with bonus percentage
     */
    public static double calculateSalaryWithBonus(double baseSalary, double bonusPercentage) {
        if (bonusPercentage < 0) {
            throw new IllegalArgumentException("Bonus percentage cannot be negative");
        }
        return baseSalary + (baseSalary * bonusPercentage / 100);
    }
    
    /**
     * Calculate hourly rate from annual salary (assuming 2080 work hours per year)
     */
    public static double calculateHourlyRate(double annualSalary) {
        final int ANNUAL_WORK_HOURS = 2080; // 40 hours/week * 52 weeks
        return annualSalary / ANNUAL_WORK_HOURS;
    }
    
    /**
     * Format salary as currency string
     */
    public static String formatSalary(double salary) {
        return String.format("$%,.2f", salary);
    }
    
    /**
     * Compare two salaries and return the difference
     */
    public static double compareSalaries(double salary1, double salary2) {
        return Math.abs(salary1 - salary2);
    }
    
    /**
     * Calculate percentage increase/decrease between two salaries
     */
    public static double calculateSalaryChangePercentage(double oldSalary, double newSalary) {
        if (oldSalary == 0) {
            throw new IllegalArgumentException("Old salary cannot be zero");
        }
        return ((newSalary - oldSalary) / oldSalary) * 100;
    }
}
