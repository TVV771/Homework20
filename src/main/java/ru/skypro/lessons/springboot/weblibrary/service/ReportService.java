package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;

import java.io.IOException;

public interface ReportService {
    public int createReport() throws IOException;

    public ResponseEntity<Report>upload(int id);

    public String saveReportToFile(String reportJson);
}