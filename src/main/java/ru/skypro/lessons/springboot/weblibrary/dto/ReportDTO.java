package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.*;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder(toBuilder = true)

public class ReportDTO {
    public ReportRepository reportRepository;

    private Integer id;
    private Position name;
    private String name1;
    private int countEmployee;
    private int maxSalary;
    private int minSalary;
    private int avgSalary;
    private EmployeeDTO employeeDTO;

    public ReportDTO(Report report) {
    }

    public static ReportDTO fromReport(Report report) {
        ReportDTO reportDTO = new ReportDTO(report);
        reportDTO.setId(report.getId());
        reportDTO.setName1(report.getFilePath());
        reportDTO.getCountEmployee();
        reportDTO.getMaxSalary();
        reportDTO.getMinSalary();
        reportDTO.getAvgSalary();
        return reportDTO;
    }

}