package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.FullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public void addEmployee(Employee employee);
    public  List<EmployeeDTO> getAllEmployees();

    public List<EmployeeDTO> withHighestSalary();
    public List<EmployeeDTO> employeesPosition(Optional position);
    public List<FullInfo> fullInfo(int id);
    public List<EmployeeDTO> getEmployeeWithPaging(int pageIndex);
    public void upload(MultipartFile file) throws IOException;

//    List<Employee> getAllEmployee();

//    public List<Employee> showEmployee();
//
//    public Employee createEmployee(int id, String name, int salary, Position position);
//
//    public void editEmployee(int id);
//    public List<Employee> getEmployee(int id);
//    public void deliteEmployee(int id);
//    public List<Employee> getEmployeeSalaryHigher(int salary);
//    public int sumSalary();
//    public int minSalary();
//    public int maxSalary();
//    public List<Employee> highSalary();
}