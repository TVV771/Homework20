package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;

import java.io.IOException;
import java.util.Collection;

public interface EmployeeService {
    Collection<EmployeeFullInfo> getAllEmployees();

    void createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Integer id);

    void updateEmployeeById(Integer id, EmployeeDTO employeeDTO);

    void deleteEmployeeById(Integer id);

    Integer getSalarySum();

    EmployeeFullInfo getSalaryMin();

    EmployeeFullInfo getSalaryMax();

    Collection<EmployeeFullInfo> getSalaryAboveAverageEmployees();

    Collection<EmployeeFullInfo> getEmployeesByParamSalary(int paramSalary);

    EmployeeFullInfo getEmployeeByIdFullInfo(Integer id);

    Collection<EmployeeFullInfo> getEmployeesByPosition(Integer position);

    Collection<EmployeeFullInfo> getEmployeesWithHighestSalary();

    Collection<EmployeeDTO> getEmployeeWithPage(Integer page);

    void createEmployeeFromFile(MultipartFile file) throws IOException;
}