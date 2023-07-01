package ru.skypro.lessons.springboot.weblibrary.service.salary;

import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.entity.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.service.employee.EmployeeMapDTO;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final EmployeeRepository employeeRepository;

    public SalaryServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
}