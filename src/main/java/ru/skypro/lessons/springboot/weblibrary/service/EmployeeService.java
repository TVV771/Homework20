package ru.skypro.lessons.springboot.weblibraryhw.service;

import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    public Integer getSalarySum ();
    public Employee getSalaryMin ();
    public Employee getSalaryMax ();
    public List<Employee> getSalaryAboveAverageEmployees ();
}