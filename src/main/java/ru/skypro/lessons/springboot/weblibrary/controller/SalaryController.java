package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.service.salary.SalaryService;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    // метод возвращает сумму зарплат
    @GetMapping("/sum")
    public Integer salarySum() {
        return salaryService.salarySum();
    }

    // метод возвращает среднюю зарплату
    @GetMapping("/avg")
    public Integer salaryAvg() {
        return salaryService.salaryAvg();
    }

    // метод возвращает одного сотрудника с минимальной зарплатой
    @GetMapping("/min")
    public EmployeeDTO employeeMinSalary() {
        return salaryService.employeeMinSalary();
    }

    // метод возвращает список сотркдников с максимальной зарплатой
    @GetMapping("/withHighestSalary")
    public List<EmployeeDTO> withHighestSalary() {
        return salaryService.withHighestSalary();
    }

    // метод возвращает список сотрудников с зарплатой выше передаваемого параметра
    @GetMapping("/salaryHigherThan/")
    List<EmployeeDTO> findBySalaryGreaterThan(@RequestParam("salary") int salary) {
        return salaryService.findBySalaryGreaterThan(salary);
    }
}