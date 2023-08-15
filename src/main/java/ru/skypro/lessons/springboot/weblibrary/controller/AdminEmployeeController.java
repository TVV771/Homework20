package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin/employees")
@RequiredArgsConstructor
public class AdminEmployeeController {
    private final EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @GetMapping("/all")
    public Collection<EmployeeFullInfo> getEmployees() {
        logger.debug("Get a list of employees");
        logger.info("Was invoked method to get a list of employees");
        return employeeService.getAllEmployees();
    }

    @PostMapping("/add")
    public void createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        logger.info("Was invoked method for create employee " + employeeDTO);
        logger.debug("Create employee {}", employeeDTO);
        employeeService.createEmployee(employeeDTO);
    }

    @DeleteMapping("/del/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) {
        logger.debug("Delete employee with id {}", id);
        logger.info("Was invoked method for delete employee with id " + id);
        employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public void updateEmployeeById(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        logger.debug("Update employee with id {}", id);
        logger.info("Was invoked method for update employee with id {} for employee {} ", id, employeeDTO);
        employeeService.updateEmployeeById(id, employeeDTO);
    }

    @PostMapping(value = "/employees/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createEmployeeFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        logger.debug("Create employee in file {} ", file);
        logger.info("Was invoked method for create employee in file {} ", file);
        logger.error("There is no employee in file {} ", file);
        employeeService.createEmployeeFromFile(file);
    }
}