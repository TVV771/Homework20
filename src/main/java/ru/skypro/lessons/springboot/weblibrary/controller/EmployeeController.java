package ru.skypro.lessons.springboot.weblibraryhw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;
import ru.skypro.lessons.springboot.weblibraryhw.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public Collection< Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/salary/sum")
    public Integer getSalarySum() {
        return employeeService.getSalarySum();
    }

    @GetMapping("/salary/min")
    public Employee getSalaryMin() {
        return employeeService.getSalaryMin();
    }

    @GetMapping("/salary/max")
    public Employee getSalaryMax() {
        return employeeService.getSalaryMax();
    }

    @GetMapping("/high-salary")
    public Collection< Employee> getSalaryAboveAverageEmployees() {
        return employeeService.getSalaryAboveAverageEmployees();
    }
    @PostMapping("/employees")
    public Employee createEmployee (@RequestBody Employee employee){

        return employeeService.createEmployee(employee);
    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping ("/employees/salaryHigherThan")
    public Collection< Employee> getEmployeesByParamSalary (@RequestParam ("paramSalary") int paramSalary){
        return employeeService.getEmployeesByParamSalary(paramSalary);
    }
    @PutMapping ("/employees/{id}")
    public Employee updateEmployeeById(@PathVariable int id, @RequestBody Employee employee) {
        return employeeService.updateEmployeeById(id, employee);
    }
    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }
}