package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;
import ru.skypro.lessons.springboot.weblibraryhw.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeServiceImpl implements ru.skypro.lessons.springboot.weblibraryhw.service.EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Integer getSalarySum() {
        return employeeRepository.getAllEmployees().stream()
                .mapToInt(employee -> IntStream.of(employee.getSalary()).sum())
                .sum();
    }

    @Override
    public Employee getSalaryMin() {
        return employeeRepository.getAllEmployees().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .get();
    }

    @Override
    public Employee getSalaryMax() {
        return employeeRepository.getAllEmployees().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .get();
    }

    @Override
    public List<Employee> getSalaryAboveAverageEmployees() {
        double salaryAverage = employeeRepository.getAllEmployees().stream()
                .mapToInt(Employee::getSalary).average().getAsDouble();
        return employeeRepository.getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > salaryAverage)
                .collect(Collectors.toList());
    }
}