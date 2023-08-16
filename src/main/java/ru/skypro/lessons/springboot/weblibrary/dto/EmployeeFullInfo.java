package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeFullInfo {
    private String name;
    private int salary;
    private String positionName;
}