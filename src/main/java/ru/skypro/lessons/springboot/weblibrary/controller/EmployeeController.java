package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.FullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@NoArgsConstructor(force = true)

public class EmployeeController {
    public  final EmployeeService employeeService;




    @GetMapping("/withHighestSalary")
    public List<EmployeeDTO> withHighestSalary() {
        return employeeService.withHighestSalary();
    }

    @GetMapping
    public List<EmployeeDTO> employeesPosition(@RequestParam("position") Optional position) {
        if (position == null) {
            return employeeService.getAllEmployees();
        } else
            return employeeService.employeesPosition(position);
    }

    @GetMapping("{id}/fullInfo")
    public List<FullInfo> fullInfo(@PathVariable Integer id) {
        return employeeService.fullInfo(id);
    }
    @GetMapping( "/page")
    public List<EmployeeDTO> getEmployeeWithPaging(@RequestParam("page") int page) {
        return employeeService.getEmployeeWithPaging(page);
    }
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public  void upload(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.upload(file); }

}