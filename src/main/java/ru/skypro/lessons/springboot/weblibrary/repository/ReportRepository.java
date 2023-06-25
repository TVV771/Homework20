package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.entity.ReportFile;

@Repository
public interface ReportRepository extends JpaRepository<ReportFile,Integer> {

}

