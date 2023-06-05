package ru.skypro.lessons.springboot.weblibraryhw.service;

import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;

import java.util.Collection;

public interface EmployeeService {
    Collection<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    Employee updateEmployeeById(int id, Employee employee);

    void deleteEmployeeById(int id);

    Integer getSalarySum();

    Employee getSalaryMin();

    Employee getSalaryMax();

    Collection<Employee> getSalaryAboveAverageEmployees();

    Collection<Employee> getEmployeesByParamSalary(int paramSalary);
}