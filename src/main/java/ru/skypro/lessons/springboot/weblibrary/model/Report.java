package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "filePath", columnDefinition = "text")
    private String filePath;

    @Override
    public String toString() {
        return "Отчет: " +
                "номер отчета: " + id +
                ", содержание: " + filePath + '\n';
    }
}