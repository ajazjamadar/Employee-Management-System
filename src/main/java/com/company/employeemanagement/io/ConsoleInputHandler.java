package com.company.employeemanagement.io;

import java.util.Scanner;

/**
 * Console Input Handler - Single Responsibility Principle (SRP)
 * Handles all console input operations
 * Encapsulates input validation and error handling
 */
public class ConsoleInputHandler implements IInputHandler {
    
    private final Scanner scanner;
    
    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    @Override
    public double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    @Override
    public String getStringInput() {
        return scanner.nextLine().trim();
    }
    
    @Override
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    @Override
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
