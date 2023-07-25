package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
    Integer createReport() throws IOException;

    ResponseEntity<Resource> getReportById(Integer id) throws IOException;
}