package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @GetMapping
    public Collection<EmployeeFullInfo> getAllEmployees() {
        logger.debug("Get a list of employees");
        logger.info("Was invoked method to get a list of employees");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/salary/sum")
    public Integer getSalarySum() {
        logger.debug("Get the sum of all salaries");
        logger.info("Was invoked method to get the sum of all salaries");
        return employeeService.getSalarySum();
    }

    @GetMapping("/salary/min")
    public EmployeeFullInfo getSalaryMin() {
        logger.debug("Get the minimum salary for employees");
        logger.info("Was invoked method to get the minimum salary for employees");
        return employeeService.getSalaryMin();
    }

    @GetMapping("/salary/max")
    public EmployeeFullInfo getSalaryMax() {
        logger.debug("Get the maximum salary for employees");
        logger.info("Was invoked method to get the maximum salary for employees");
        return employeeService.getSalaryMax();
    }

    @GetMapping("/high-salary")
    public Collection<EmployeeFullInfo> getSalaryAboveAverageEmployees() {
        logger.debug("Get employees with salaries above the average");
        logger.info("Was invoked method to get employees with salaries above the average");
        return employeeService.getSalaryAboveAverageEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        logger.debug("Get employee with id {}", id);
        logger.info("Was invoked method to get employee with id {}", id);
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/salaryHigherThan")
    public Collection<EmployeeFullInfo> getEmployeesByParamSalary(@RequestParam("paramSalary") int paramSalary) {
        logger.debug("Get employees with salaries above the param {} ", paramSalary);
        logger.info("Was invoked method to get employees with salaries above the average param {} ", paramSalary);
        return employeeService.getEmployeesByParamSalary(paramSalary);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeFullInfo getEmployeeByIdFullInfo(@PathVariable Integer id) {
        logger.debug("Get employee with id {}", id);
        logger.info("Was invoked method to get employee with id {}", id);
        return employeeService.getEmployeeByIdFullInfo(id);
    }

    @GetMapping("/position")
    public Collection<EmployeeFullInfo> getEmployeesByPosition(@RequestParam Optional<Integer> position) {
        logger.debug("Get employees by position {}", position);
        logger.info("Was invoked method to get employees by position {}", position);
        return employeeService.getEmployeesByPosition(position.orElse(null));
    }

    @GetMapping("/withHighestSalary")
    public Collection<EmployeeFullInfo> getEmployeesWithHighestSalary() {
        logger.debug("Get employee with highest salary");
        logger.info("Was invoked method to get the employee with highest salary");
        return employeeService.getEmployeesWithHighestSalary();
    }

    @GetMapping("/page")
    public Collection<EmployeeDTO> getEmployeeWithPage(@RequestParam("page") Integer page) {
        logger.debug("Get a list of employees on the page {}", page);
        logger.info("Was invoked method to get a list of employees on the page {}", page);
        return employeeService.getEmployeeWithPage(page);
    }
}