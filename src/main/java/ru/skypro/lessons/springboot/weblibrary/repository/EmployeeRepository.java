package ru.skypro.lessons.springboot.weblibrary.repository;

import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.Collection;

public interface EmployeeRepository {
    Collection<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee updateEmployeeById(int id, Employee employee);

    Employee getEmployeeById(int id);

    void deleteEmployeeById(int id);
}