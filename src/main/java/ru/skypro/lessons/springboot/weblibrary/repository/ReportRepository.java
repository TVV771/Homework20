package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Report;

import java.util.List;
@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO(\n " +
            " p.name, \n" +
            " count(e.id), \n " +
            " min(e.salary), \n " +
            " max(e.salary), \n " +
            " avg(e.salary)) \n " +
            " FROM Employee e join fetch Position p \n" +
            " WHERE e.position = p \n" +
            " GROUP BY p.id")
    List<ReportDTO> createReport();
}