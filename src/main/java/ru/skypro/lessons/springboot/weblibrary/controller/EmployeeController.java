package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // метод дабавляет сотрудника в базу
    @PostMapping("employees")
    void addEmployees(@RequestBody EmployeeDTO employee) {
        employeeService.addEmployees(employee);
    }

    // метод возвращает полный список сотрудников
    @GetMapping("employees")
    List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    // метод для изменения данных сотрудника
    @PutMapping("employees")
    void editEmployee(@RequestBody EmployeeDTO employeeDTO) throws ExceptionNoId {
        employeeService.editEmployee(employeeDTO);
    }

    // возвращает сотрудника по id
    @GetMapping("employees/{id}")
    EmployeeDTO getEmployeeToId(@PathVariable int id) throws ExceptionNoId {
        return employeeService.getEmployeeToId(id);
    }

    // возвращает всех сотрудников с должностями
    @GetMapping("employees/full_info")
    List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        return employeeService.getAllEmployeeFullInfo();
    }


    // возвращает сотрудника по id с должностью
    @GetMapping("employees/full_info/{id}")
    EmployeeFullInfo getAllEmployeeToIdFullInfo(@PathVariable int id) throws ExceptionNoId {
        return employeeService.getAllEmployeeToIdFullInfo(id);
    }

    // метод возвращает список сотрудников по позиции
    @GetMapping("employees/position/{position}")
    List<EmployeeDTO> getEmployeeByPositionName(@PathVariable String position) {
        return employeeService.getEmployeeByPositionName(position);
    }

    // метод возвращает информацию о сотрудниках, основываясь на номере страницы.
    @GetMapping("page/{page}")
    List<EmployeeDTO> getEmployeeFromPage(@PathVariable int page) {
        return employeeService.getEmployeeFromPage(page);
    }

    // метод удаляет сотрудника по id
    @DeleteMapping("employees/{id}")
    void deleteEmployeeToId(@PathVariable int id) throws ExceptionNoId {
        employeeService.deleteEmployeeToId(id);
    }

    // метод возвращает сумму зарплат
    @GetMapping("/salary/sum")
    public Integer salarySum() {
        return employeeService.salarySum();
    }

    // метод возвращает среднюю зарплату
    @GetMapping("/salary/avg")
    public Integer salaryAvg() {
        return employeeService.salaryAvg();
    }

    // метод возвращает одного сотрудника с минимальной зарплатой
    @GetMapping("/salary/min")
    public EmployeeDTO employeeMinSalary() {
        return employeeService.employeeMinSalary();
    }

    // метод возвращает список сотркдников с максимальной зарплатой
    @GetMapping("/salary/withHighestSalary")
    public List<EmployeeDTO> withHighestSalary() {
        return employeeService.withHighestSalary();
    }

    // метод возвращает список сотрудников с зарплатой выше передаваемого параметра
    @GetMapping("salary/salaryHigherThan/")
    List<EmployeeDTO> findBySalaryGreaterThan(@RequestParam("salary") int salary) {
        return employeeService.findBySalaryGreaterThan(salary);
    }

}