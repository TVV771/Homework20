package ru.skypro.lessons.springboot.weblibrary.dto;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;



public class FullInfo {
    private String name;
    private Integer salary;
    private String positionName;


    public FullInfo(String name, Integer salary, String positionName) {
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }

    public FullInfo(Employee employee) {
    }

    public static FullInfo fromEmployeeFullInfo(Employee employee) {
        FullInfo fullInfo = new FullInfo(employee);
        fullInfo.setName(employee.getName());
        fullInfo.setSalary(employee.getSalary());
        fullInfo.setPosition(employee.getPosition());

        return fullInfo;
    }

    private void setPosition(Position position) {
    }

    private void setSalary(int salary) {
    }

    private void setName(String name) {

    }
}