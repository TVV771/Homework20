package ru.skypro.lessons.springboot.weblibrary.pojo;


import jakarta.persistence.*;
import lombok.*;
@Builder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int salary;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_position")
    private Position position;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_report")
    private Report report;




}