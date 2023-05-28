package ru.skypro.lessons.springboot.weblibraryhw.repository;

import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> getAllEmployees();
}