package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee1")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int salary;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee(String name) {
        this.name = name;
    }
}