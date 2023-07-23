package ru.skypro.lessons.springboot.weblibrary.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.FullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
@Data
@Service
@Profile("test")
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    public EmployeeRepository employeeRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Value("${app.env}")
    private String test;


    @Override
    public void addEmployee(Employee employee) {

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Was invoked method for getAllEmployees");
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> withHighestSalary() {
        logger.info("Was invoked method for withHighestSalary");
        return employeeRepository.withHighestSalary().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> employeesPosition(Optional position) {
        logger.info("Was invoked method for create employee:{} ",position);
        return employeeRepository.getEmployeesByName(position).stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());

    }

    @Override
    public List<FullInfo> fullInfo(int id) {
        logger.info("Was invoked method for create employee:{} ",id);
        return employeeRepository.getEmployeesFullInfo(id).stream()
                .map(FullInfo::fromEmployeeFullInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeeWithPaging(int pageIndex) {
        try {
            if (pageIndex != 0) {
                logger.info("Was invoked method for create employee:{} ", pageIndex);
                PageRequest employeePage = PageRequest.of(pageIndex, 10);
                Page<Employee> employeeDTOS = employeeRepository.findAll(employeePage);
                return employeeDTOS.stream()
                        .map(EmployeeDTO::fromEmployee).
                        collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error("the value was entered incorrectly:{}", pageIndex,e);
            throw new RuntimeException(e);
        }return null;
    }

    @Override
    public void upload(MultipartFile multipartFile) throws IOException {
        logger.info("Was invoked method for create employee:{} ",multipartFile);
        File file = new File("new.json");
        Files.write(file.toPath(), multipartFile.getBytes());
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.readValue(file, Employee.class);
        employeeRepository.save(employee);
    }

}