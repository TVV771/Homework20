package ru.skypro.lessons.springboot.weblibrary.service.salary;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;

import java.util.List;

public interface SalaryService {
    // метод возвращает сумму зарплат
    Integer salarySum();

    // метод возвращает среднюю зарплату
    Integer salaryAvg();

    // метод возвращает одного сотрудника с минимальной зарплатой
    EmployeeDTO employeeMinSalary();

    // метод возвращает список сотркдников с максимальной зарплатой
    List<EmployeeDTO> withHighestSalary();

    // метод возвращает список сотрудников с зарплатой выше передаваемого параметра
    List<EmployeeDTO> findBySalaryGreaterThan(int salary);
}