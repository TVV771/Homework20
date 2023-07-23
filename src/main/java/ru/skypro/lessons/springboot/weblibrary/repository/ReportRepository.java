package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer> {
    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary1.DTO.ReportDTO( " +
            "p.position, countEmployee (e.id), maxSalary (e.salary), minSalary (e.salary), avgSalary (e.salary)) " +
            "FROM Employee e join  fetch Position p " +
            "where e.position = p " +
            "Group by p.id " )
    List<ReportDTO> createReport();
    @Query("SELECT r.filePath FROM Report r WHERE r.id =:id")
    ResponseEntity readReportById(int id);
}