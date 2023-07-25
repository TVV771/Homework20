package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public Integer createReport() throws IOException {
        String fileName = "report.json";
        String json = String.valueOf(reportRepository.createReport());
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Report report = new Report();
        report.setFilePath(String.valueOf(path.toAbsolutePath()));
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public ResponseEntity<Resource> getReportById(Integer id) throws IOException {
        String file = "report.json";
        String path = reportRepository.findById(id)
                .map(ReportDTO::fromReport)
                .orElseThrow(IOException::new)
                .getFilePath();
        File newFile = new File(path);
        Resource resource = new PathResource(newFile.getPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }
}