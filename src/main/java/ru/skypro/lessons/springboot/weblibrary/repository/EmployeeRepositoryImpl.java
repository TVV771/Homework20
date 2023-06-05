package ru.skypro.lessons.springboot.weblibraryhw.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibraryhw.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;

import java.util.*;

@Repository
public class EmployeeRepositoryImpl implements ru.skypro.lessons.springboot.weblibraryhw.repository.EmployeeRepository {
    private final Map<Integer, Employee> employees = new HashMap<>();

    {
        employees.put(1, new Employee(1, "Катя", 90000));
        employees.put(2, new Employee(2, "Дима", 102000));
        employees.put(3, new Employee(3, "Олег", 80000));
        employees.put(4, new Employee(4, "Вика", 165000));
    }

    @Override
    public Collection<Employee> getAllEmployees() {
        return new HashMap<>(employees).values();
    }

    public Employee createEmployee(Employee employee) {
        if (employees.containsKey(employee.getId()) || employee.getId() <= 0) {
            throw new EmployeeNotFoundException();
        }
        return employees.put(employee.getId(), employee);
    }

    @Override
    public Employee updateEmployeeById(int id, Employee employee) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException();
        }
        employees.replace(id, getEmployeeById(id), employee);
        return employee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(id);
    }
}