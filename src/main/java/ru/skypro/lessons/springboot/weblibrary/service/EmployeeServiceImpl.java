package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeRepository getRepository() {
        return repository;
    }


    @Override
    public void addEmployees(EmployeeDTO employee) {
        repository.save(EmployeeMapDTO.toEmployee(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        repository.findAll().forEach(employeeList::add);
        return employeeList
                .stream()
                .map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId {
        int id = employeeDTO.getId();
        Employee result = repository.findById(id)
                .orElseThrow(()->new ExceptionNoId("Отсутствует сотрудник под данным id"));
        if (!employeeDTO.getName().isBlank()) {
            result.setName(employeeDTO.getName());
        }
        if (employeeDTO.getSalary() > 0) {
            result.setSalary(employeeDTO.getSalary());
        }
        repository.save(result);
    }

    @Override
    public EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId {
        return repository.findById(id)
                .map(EmployeeMapDTO::fromEmployee)
                .orElseThrow(()->new ExceptionNoId("Отсутствует сотрудник под данным id"));
    }

    @Override
    public List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        return repository.getEmployeeFullInfo();
    }

    @Override
    public EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId {
        return repository.findById(id)
                .map(EmployeeMapDTO::toEmployeeFullInfo)
                .orElseThrow(()->new ExceptionNoId("Отсутствует сотрудник под данным id"));
    }

    @Override
    public List<EmployeeDTO> getEmployeeByPositionName(String position) {
        String pos = position.toLowerCase();
        if (!position.isBlank()) {
            return repository.findEmployeeByPosition_Name(pos)
                    .stream()
                    .map(EmployeeMapDTO::fromEmployee)
                    .collect(Collectors.toList());
        } else return getAllEmployee();
    }

    @Override
    public List<EmployeeDTO> getEmployeeFromPage(int page) {
        return repository.findAll(PageRequest.of(page, 10))
                .stream().map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeToId(int id) throws ExceptionNoId {
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else throw new ExceptionNoId("Отсутствует сотрудник под данным id");
    }

    @Override
    public Integer salarySum() {
        return repository.salarySum();
    }

    @Override
    public Integer salaryAvg() {
        return repository.salaryAvg();
    }

    @Override
    public EmployeeDTO employeeMinSalary() {
        return EmployeeMapDTO.fromEmployee(repository.employeeMinSalary());
    }

    @Override
    public List<EmployeeDTO> withHighestSalary() {
        return repository.withHighestSalary()
                .stream()
                .map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findBySalaryGreaterThan(int salary) {
        return null;
    }
}