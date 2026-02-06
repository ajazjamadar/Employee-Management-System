package com.company.employeemanagement.test;

import com.company.employeemanagement.interfaces.EmployeeAction;
import com.company.employeemanagement.interfaces.EmployeeCondition;
import com.company.employeemanagement.interfaces.EmployeeTransformer;
import com.company.employeemanagement.model.*;
import com.company.employeemanagement.report.EmployeeReportService;
import com.company.employeemanagement.repository.EmployeeRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Week 4: Comprehensive demonstration of Streams, Lambdas, Functional Interfaces & Optional
 * This class showcases all Week 4 features in action
 */
public class StreamDemoTest {
    
    public static void main(String[] args) {
        // Setup test data
        EmployeeRepository repository = new EmployeeRepository();
        EmployeeReportService reportService = new EmployeeReportService(repository);
        
        loadTestData(repository);
        
        System.out.println("====================================");
        System.out.println("  WEEK 4: STREAMS & LAMBDAS DEMO");
        System.out.println("====================================\n");
        
        // 1. Stream Creation (Different Ways)
        demonstrateStreamCreation(repository);
        
        // 2. Lambda Expressions
        demonstrateLambdas(repository);
        
        // 3. Method References
        demonstrateMethodReferences(repository);
        
        // 4. Functional Interfaces (Built-in)
        demonstrateBuiltInFunctionalInterfaces(repository);
        
        // 5. Custom Functional Interfaces
        demonstrateCustomFunctionalInterfaces(repository);
        
        // 6. Optional (Null Safety)
        demonstrateOptional(repository);
        
        // 7. Stream Operations
        demonstrateStreamOperations(reportService);
        
        // 8. Collectors
        demonstrateCollectors(repository);
        
        // 9. Comparators with Lambdas
        demonstrateComparators(repository);
        
        System.out.println("\n====================================");
        System.out.println("  ALL DEMONSTRATIONS COMPLETE!");
        System.out.println("====================================");
    }
    
    private static void loadTestData(EmployeeRepository repository) {
        repository.addEmployee(new PermanentEmployee(101, "Alice Johnson", "alice@company.com", "Engineering", 75000, 15000));
        repository.addEmployee(new PermanentEmployee(102, "Bob Smith", "bob@company.com", "Marketing", 65000, 12000));
        repository.addEmployee(new PermanentEmployee(103, "Carol White", "carol@company.com", "Engineering", 80000, 18000));
        repository.addEmployee(new ContractEmployee(201, "David Brown", "david@company.com", "IT Support", 50, 160, 12));
        repository.addEmployee(new ContractEmployee(202, "Eve Davis", "eve@company.com", "Design", 60, 150, 6));
        repository.addEmployee(new Manager(301, "Frank Wilson", "frank@company.com", "Engineering", 100000, 25000, 20000));
        repository.addEmployee(new Manager(302, "Grace Lee", "grace@company.com", "Marketing", 95000, 22000, 18000));
    }
    
    private static void demonstrateStreamCreation(EmployeeRepository repository) {
        System.out.println("=== 1. STREAM CREATION ===\n");
        
        // Way 1: From Collection
        System.out.println("Way 1: From Collection (List)");
        long count1 = repository.getAllEmployees().stream().count();
        System.out.println("Employee count: " + count1);
        
        // Way 2: Using Stream.of()
        System.out.println("\nWay 2: Using Stream.of()");
        Stream<String> departments = Stream.of("Engineering", "Marketing", "IT Support");
        departments.forEach(dept -> System.out.println("  - " + dept));
        
        // Way 3: Parallel Stream
        System.out.println("\nWay 3: Parallel Stream for performance");
        long count2 = repository.getAllEmployees().parallelStream().count();
        System.out.println("Employee count (parallel): " + count2);
        
        System.out.println("\n");
    }
    
    private static void demonstrateLambdas(EmployeeRepository repository) {
        System.out.println("=== 2. LAMBDA EXPRESSIONS ===\n");
        
        // Lambda with filter
        System.out.println("Lambda in filter - High earners (>100k):");
        repository.getAllEmployees()
                .stream()
                .filter(e -> e.calculateSalary() > 100000)
                .forEach(e -> System.out.println("  " + e.getName() + ": $" + e.calculateSalary()));
        
        // Lambda with sorting
        System.out.println("\nLambda in sorted - By salary descending:");
        repository.getAllEmployees()
                .stream()
                .sorted((e1, e2) -> Double.compare(e2.calculateSalary(), e1.calculateSalary()))
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getName() + ": $" + e.calculateSalary()));
        
        System.out.println("\n");
    }
    
    private static void demonstrateMethodReferences(EmployeeRepository repository) {
        System.out.println("=== 3. METHOD REFERENCES ===\n");
        
        // Static method reference
        System.out.println("Static method reference - System.out::println:");
        repository.getAllEmployees()
                .stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        
        // Instance method reference
        System.out.println("\nInstance method reference - Employee::calculateSalary:");
        repository.getAllEmployees()
                .stream()
                .mapToDouble(Employee::calculateSalary)
                .forEach(salary -> System.out.println("  $" + salary));
        
        // Constructor reference
        System.out.println("\nConstructor reference - ArrayList::new:");
        ArrayList<String> names = repository.getAllEmployees()
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("  Names collected: " + names);
        
        System.out.println("\n");
    }
    
    private static void demonstrateBuiltInFunctionalInterfaces(EmployeeRepository repository) {
        System.out.println("=== 4. BUILT-IN FUNCTIONAL INTERFACES ===\n");
        
        // Predicate<T>
        System.out.println("Predicate - Testing conditions:");
        Predicate<Employee> isHighEarner = e -> e.calculateSalary() > 100000;
        Predicate<Employee> isEngineer = e -> e.getDepartment().equals("Engineering");
        
        long highEarnerCount = repository.getAllEmployees()
                .stream()
                .filter(isHighEarner)
                .count();
        System.out.println("  High earners: " + highEarnerCount);
        
        // Combining predicates
        long engineerHighEarners = repository.getAllEmployees()
                .stream()
                .filter(isHighEarner.and(isEngineer))
                .count();
        System.out.println("  High earning engineers: " + engineerHighEarners);
        
        // Function<T, R>
        System.out.println("\nFunction - Transforming data:");
        Function<Employee, String> toSummary = e -> 
                e.getName() + " ($" + String.format("%.0f", e.calculateSalary()) + ")";
        
        repository.getAllEmployees()
                .stream()
                .map(toSummary)
                .limit(3)
                .forEach(summary -> System.out.println("  " + summary));
        
        // Consumer<T>
        System.out.println("\nConsumer - Performing actions:");
        Consumer<Employee> displayInfo = e -> 
                System.out.println("  Processing: " + e.getName());
        
        repository.getAllEmployees()
                .stream()
                .limit(3)
                .forEach(displayInfo);
        
        System.out.println("\n");
    }
    
    private static void demonstrateCustomFunctionalInterfaces(EmployeeRepository repository) {
        System.out.println("=== 5. CUSTOM FUNCTIONAL INTERFACES ===\n");
        
        // EmployeeCondition
        System.out.println("EmployeeCondition - Custom filtering:");
        EmployeeCondition salaryAbove80k = e -> e.calculateSalary() > 80000;
        EmployeeCondition inEngineering = e -> e.getDepartment().equals("Engineering");
        
        List<Employee> filtered = repository.getAllEmployees()
                .stream()
                .filter(e -> salaryAbove80k.and(inEngineering).test(e))
                .toList();
        
        System.out.println("  Engineers earning > 80k: " + filtered.size());
        filtered.forEach(e -> System.out.println("    - " + e.getName()));
        
        // EmployeeTransformer
        System.out.println("\nEmployeeTransformer - Custom transformation:");
        EmployeeTransformer<String> toEmailDomain = e -> 
                e.getEmail().substring(e.getEmail().indexOf("@") + 1);
        
        Set<String> domains = repository.getAllEmployees()
                .stream()
                .map(e -> toEmailDomain.transform(e))
                .collect(Collectors.toSet());
        
        System.out.println("  Email domains: " + domains);
        
        // EmployeeAction
        System.out.println("\nEmployeeAction - Custom action:");
        EmployeeAction giveRaise = e -> 
                System.out.println("  Raising salary for: " + e.getName());
        
        repository.getAllEmployees()
                .stream()
                .filter(e -> e instanceof PermanentEmployee)
                .limit(2)
                .forEach(e -> giveRaise.perform(e));
        
        System.out.println("\n");
    }
    
    private static void demonstrateOptional(EmployeeRepository repository) {
        System.out.println("=== 6. OPTIONAL (NULL SAFETY) ===\n");
        
        // Finding with Optional
        System.out.println("Optional.findFirst():");
        Optional<Employee> firstEngineer = repository.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment().equals("Engineering"))
                .findFirst();
        
        firstEngineer.ifPresentOrElse(
                e -> System.out.println("  Found: " + e.getName()),
                () -> System.out.println("  No engineer found")
        );
        
        // Optional with orElse
        System.out.println("\nOptional.orElse():");
        Employee emp = repository.findEmployeeById(999)
                .orElse(new PermanentEmployee(0, "Unknown", "unknown@company.com", 
                        "Unknown", 0, 0));
        System.out.println("  Employee: " + emp.getName());
        
        // Optional with orElseThrow
        System.out.println("\nOptional.orElseThrow():");
        try {
            Employee found = repository.findEmployeeById(101)
                    .orElseThrow(() -> new RuntimeException("Not found"));
            System.out.println("  Found: " + found.getName());
        } catch (Exception e) {
            System.out.println("  " + e.getMessage());
        }
        
        // Optional chaining
        System.out.println("\nOptional chaining:");
        String email = repository.findEmployeeById(101)
                .map(Employee::getEmail)
                .map(String::toUpperCase)
                .orElse("NO EMAIL");
        System.out.println("  Email: " + email);
        
        System.out.println("\n");
    }
    
    private static void demonstrateStreamOperations(EmployeeReportService reportService) {
        System.out.println("=== 7. STREAM OPERATIONS ===\n");
        
        // Intermediate operations
        System.out.println("Intermediate: filter, map, sorted, distinct, limit");
        
        System.out.println("\nTerminal: forEach");
        reportService.getTopPaidEmployees(3)
                .forEach(e -> System.out.println("  " + e.getName()));
        
        System.out.println("\nTerminal: collect");
        List<String> names = reportService.getTopPaidEmployees(3)
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("  " + names);
        
        System.out.println("\nTerminal: reduce");
        double totalSalary = reportService.calculateTotalPayroll();
        System.out.println("  Total payroll: $" + String.format("%.2f", totalSalary));
        
        System.out.println("\nTerminal: anyMatch, allMatch, noneMatch");
        boolean hasHighEarner = reportService.hasHighEarner(100000);
        boolean allAbove50k = reportService.allEmployeesAboveSalary(50000);
        System.out.println("  Has high earner (>100k): " + hasHighEarner);
        System.out.println("  All above 50k: " + allAbove50k);
        
        System.out.println("\n");
    }
    
    private static void demonstrateCollectors(EmployeeRepository repository) {
        System.out.println("=== 8. COLLECTORS ===\n");
        
        // toList
        System.out.println("Collectors.toList():");
        List<String> allNames = repository.getAllEmployees()
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("  " + allNames);
        
        // groupingBy
        System.out.println("\nCollectors.groupingBy():");
        Map<String, List<Employee>> byDept = repository.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        byDept.forEach((dept, emps) -> 
                System.out.println("  " + dept + ": " + emps.size() + " employees"));
        
        // counting
        System.out.println("\nCollectors.counting():");
        Map<String, Long> deptCounts = repository.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));
        deptCounts.forEach((dept, count) -> 
                System.out.println("  " + dept + ": " + count));
        
        // summingDouble
        System.out.println("\nCollectors.summingDouble():");
        Map<String, Double> deptSalaries = repository.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summingDouble(Employee::calculateSalary)
                ));
        deptSalaries.forEach((dept, total) -> 
                System.out.println("  " + dept + ": $" + String.format("%.2f", total)));
        
        // partitioningBy
        System.out.println("\nCollectors.partitioningBy():");
        Map<Boolean, List<Employee>> partitioned = repository.getAllEmployees()
                .stream()
                .collect(Collectors.partitioningBy(
                        e -> e.calculateSalary() > 90000
                ));
        System.out.println("  Above 90k: " + partitioned.get(true).size());
        System.out.println("  Below 90k: " + partitioned.get(false).size());
        
        System.out.println("\n");
    }
    
    private static void demonstrateComparators(EmployeeRepository repository) {
        System.out.println("=== 9. COMPARATORS WITH LAMBDAS ===\n");
        
        // Simple comparator
        System.out.println("Comparing by salary:");
        repository.getAllEmployees()
                .stream()
                .sorted((e1, e2) -> Double.compare(e1.calculateSalary(), e2.calculateSalary()))
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getName() + ": $" + e.calculateSalary()));
        
        // Comparator.comparing
        System.out.println("\nComparator.comparing (by name):");
        repository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getName))
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getName()));
        
        // Reversed comparator
        System.out.println("\nComparator.reversed (by salary):");
        repository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::calculateSalary).reversed())
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getName() + ": $" + e.calculateSalary()));
        
        // Chained comparators
        System.out.println("\nChained comparators (department then name):");
        repository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Employee::getName))
                .forEach(e -> System.out.println("  " + e.getDepartment() + " - " + e.getName()));
        
        System.out.println("\n");
    }
}
