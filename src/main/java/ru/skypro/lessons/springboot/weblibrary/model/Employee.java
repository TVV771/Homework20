package ru.skypro.lessons.springboot.weblibraryhw.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private int salary;
}