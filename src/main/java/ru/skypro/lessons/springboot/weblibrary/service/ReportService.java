package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    List<ReportDTO> downloadFile();
}