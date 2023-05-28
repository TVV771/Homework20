package ru.skypro.lessons.springboot.weblibraryhw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;
import ru.skypro.lessons.springboot.weblibraryhw.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("salary/sum")
    public Integer getSalarySum() {
        return employeeService.getSalarySum();
    }

    @GetMapping("salary/min")
    public Employee getSalaryMin() {
        return employeeService.getSalaryMin();
    }

    @GetMapping("salary/max")
    public Employee getSalaryMax() {
        return employeeService.getSalaryMax();
    }

    @GetMapping("high-salary")
    public List<Employee> getSalaryAboveAverageEmployees() {
        return employeeService.getSalaryAboveAverageEmployees();
    }
}