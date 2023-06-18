package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;

    public EmployeeServiceImpl(EmployeeRepository repository, ReportRepository reportRepository) {
        this.employeeRepository = repository;
        this.reportRepository = reportRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    @Override
    public void addEmployees(EmployeeDTO employee) {
        employeeRepository.save(EmployeeMapDTO.toEmployee(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public void editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId {
        int id = employeeDTO.getId();
        Employee result = employeeRepository.findById(id)
                .orElseThrow(ExceptionNoId::new);
        if (!employeeDTO.getName().isBlank()) {
            result.setName(employeeDTO.getName());
        }
        if (employeeDTO.getSalary() > 0) {
            result.setSalary(employeeDTO.getSalary());
        }
        employeeRepository.save(result);
    }

    @Override
    public EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId {
        return employeeRepository.findById(id)
                .map(EmployeeMapDTO::fromEmployee)
                .orElseThrow(ExceptionNoId::new);
    }

    @Override
    public List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        return employeeRepository.getEmployeeFullInfo();
    }

    @Override
    public EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId {
        return employeeRepository.findById(id)
                .map(EmployeeMapDTO::toEmployeeFullInfo)
                .orElseThrow(ExceptionNoId::new);
    }

    @Override
    public List<EmployeeDTO> getEmployeeByPositionName(String position) {
        String pos = position.toLowerCase();
        List<Employee> employeeList = employeeRepository.findEmployeeByPosition_Name(pos);
        if (!position.isBlank()) {
            return EmployeeMapDTO.toEmployeeDTOList(employeeList);
        } else return getAllEmployee();
    }

    @Override
    public List<EmployeeDTO> getEmployeeFromPage(int page) {
        return employeeRepository.findAll(PageRequest.of(page, 10))
                .stream().map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeToId(int id) throws ExceptionNoId {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        } else throw new ExceptionNoId();
    }

    @Override
    public Integer salarySum() {
        return employeeRepository.salarySum();
    }

    @Override
    public Integer salaryAvg() {
        return employeeRepository.salaryAvg();
    }

    @Override
    public EmployeeDTO employeeMinSalary() {
        return EmployeeMapDTO.fromEmployee(employeeRepository.employeeMinSalary());
    }

    @Override
    public List<EmployeeDTO> withHighestSalary() {
        List<Employee> employeeList = employeeRepository.withHighestSalary();
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public List<EmployeeDTO> findBySalaryGreaterThan(int salary) {
        List<Employee> employeeList = employeeRepository.findBySalaryGreaterThan(salary);
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public void uploadAndSaveEmployees(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<EmployeeDTO>> typeReference = new TypeReference<>() {};
        List<EmployeeDTO> employeeDTOList = objectMapper.readValue(file.getBytes(), typeReference);
        employeeDTOList.forEach(this::addEmployees);
    }

    @Override
    public void recReport(String deportmentName) {
    }

}