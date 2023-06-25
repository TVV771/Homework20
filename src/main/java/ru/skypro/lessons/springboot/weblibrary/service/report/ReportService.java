package ru.skypro.lessons.springboot.weblibrary.service.report;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;

import java.io.IOException;

public interface ReportService {
    int downloadFile() throws IOException;
    ResponseEntity<Resource> getFile(int id) throws IOException, ExceptionNoId;
}