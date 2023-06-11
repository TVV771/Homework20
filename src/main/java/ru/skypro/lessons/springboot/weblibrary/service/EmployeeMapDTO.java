package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

public interface EmployeeMapDTO {
    static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
    }

    static Employee toEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }

    static EmployeeFullInfo toEmployeeFullInfo(Employee employee) {
        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo();
        employeeFullInfo.setId(employee.getId());
        employeeFullInfo.setName(employee.getName());
        employeeFullInfo.setSalary(employee.getSalary());
        employeeFullInfo.setPositionName(employee.getPosition().getName());
        return employeeFullInfo;
    }
}