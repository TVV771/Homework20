package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;

import java.util.List;

public interface EmployeeService {
    // метод дабавляет сотрудника в базу
    void addEmployees(EmployeeDTO employee);

    // метод возвращает полный список сотрудников
    List<EmployeeDTO> getAllEmployee();

    // метод для изменения данных сотрудника
    void editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId;

    // возвращает сотрудника по id
    EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId;

    // возвращает всех сотрудников с должностями
    List<EmployeeFullInfo> getAllEmployeeFullInfo();

    // возвращает сотрудника по id с должностью
    EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId;

    // метод возвращает список сотрудников по позиции
    List<EmployeeDTO> getEmployeeByPositionName(String position);

    // метод возвращает информацию о сотрудниках, основываясь на номере страницы.
    List<EmployeeDTO> getEmployeeFromPage(int page);

    // метод удаляет сотрудника по id
    void deleteEmployeeToId(int id) throws ExceptionNoId;

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