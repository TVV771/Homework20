package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibraryhw.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements ru.skypro.lessons.springboot.weblibraryhw.repository.EmployeeRepository {
    private final List<Employee> employeeList = List.of(
            new Employee("Иван", 12450),
            new Employee("Дмитрий", 14760),
            new Employee("Владимир", 80000),
            new Employee("Галина", 16000));

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }
}