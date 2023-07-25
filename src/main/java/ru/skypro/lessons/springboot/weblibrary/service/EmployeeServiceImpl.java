package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PagingAndSortingRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PagingAndSortingRepository pagingAndSortingRepository;

    public Collection<EmployeeFullInfo> getAllEmployees() {
        return employeeRepository.findAllEmployeeFullInfo();
    }

    @Override
    public Integer getSalarySum() {
        return getAllEmployees().stream()
                .mapToInt(employee -> IntStream.of(employee.getSalary()).sum())
                .sum();
    }

    @Override
    public EmployeeFullInfo getSalaryMin() {
        return getAllEmployees().stream()
                .min(Comparator.comparingInt(EmployeeFullInfo::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public EmployeeFullInfo getSalaryMax() {
        return getAllEmployees().stream()
                .max(Comparator.comparingInt(EmployeeFullInfo::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Collection<EmployeeFullInfo> getSalaryAboveAverageEmployees() {
        double salaryAverage = getAllEmployees().stream()
                .mapToInt(EmployeeFullInfo::getSalary).average().getAsDouble();
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > salaryAverage)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EmployeeFullInfo> getEmployeesByParamSalary(int paramSalary) {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > paramSalary)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeFullInfo getEmployeeByIdFullInfo(Integer id) {
        return employeeRepository.findByIdFullInfo(id).orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Collection<EmployeeFullInfo> getEmployeesByPosition(Integer position) {
        if (position != null) {
            return employeeRepository.findEmployeeByPosition(position);
        } else
            return getAllEmployees();
    }

    @Override
    public Collection<EmployeeFullInfo> getEmployeesWithHighestSalary() {
        return employeeRepository.findEmployeeWithHighestSalary();
    }

    @Override
    public Collection<EmployeeDTO> getEmployeeWithPage(Integer page) {
        Page<Employee> employeePage = pagingAndSortingRepository.findAll(PageRequest.of(page, 10));
        Collection<Employee> employeeList = employeePage.stream()
                .toList();
        return employeeList.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void createEmployeeFromFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeDTO[] newEmployees = objectMapper.readValue(file.getBytes(), EmployeeDTO[].class);
        for (EmployeeDTO e : newEmployees) {
            createEmployee(e);
        }
    }

    @Override
    public void createEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.save(employeeDTO.toEmployee());
    }

    @Override
    public void updateEmployeeById(Integer id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        employeeDTO.setId(employee.getId());
        employeeRepository.save(employeeDTO.toEmployee());
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        if (id > getAllEmployees().size() || id <= 0) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.deleteById(id);
    }
}