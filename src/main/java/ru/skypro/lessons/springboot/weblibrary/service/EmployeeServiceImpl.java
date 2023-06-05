package ru.skypro.lessons.springboot.weblibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibraryhw.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;
import ru.skypro.lessons.springboot.weblibraryhw.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements ru.skypro.lessons.springboot.weblibraryhw.service.EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Collection<Employee> getAllEmployees() {
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
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Employee getSalaryMax() {
        return employeeRepository.getAllEmployees().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Collection<Employee> getSalaryAboveAverageEmployees() {
        double salaryAverage = employeeRepository.getAllEmployees().stream()
                .mapToInt(Employee::getSalary).average().getAsDouble();
        return employeeRepository.getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > salaryAverage)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Employee> getEmployeesByParamSalary(int paramSalary) {
        return employeeRepository.getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > paramSalary)
                .collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    @Override
    public Employee updateEmployeeById(int id, Employee employee) {
        return employeeRepository.updateEmployeeById(id, employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteEmployeeById(id);
    }
}