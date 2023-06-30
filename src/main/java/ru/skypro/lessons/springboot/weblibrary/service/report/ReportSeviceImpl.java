package ru.skypro.lessons.springboot.weblibrary.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;
import ru.skypro.lessons.springboot.weblibrary.entity.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Service
public class ReportSeviceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private Logger logger = LoggerFactory.getLogger(ReportSeviceImpl.class);

    public ReportSeviceImpl(EmployeeRepository employeeRepository, ReportRepository reportRepository) {
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public int downloadFile() throws IOException {
        logger.info("Вызываем метод для вывода отчета, записи его в файл и сохранение данных файла в б/д");
        String fileName = newName();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReportDTO> report = employeeRepository.getReport();
        logger.debug("Получаем данные из б/д сотрудников");
        // Создаем JSON-строку
        String json = objectMapper.writeValueAsString(report);
        // сщздаем и записываем файл
        File file = new File("src/report/" + fileName);
        Files.writeString(file.toPath(), json);
        ReportFile reportFile = new ReportFile(file.getPath());
        reportRepository.save(reportFile);
        logger.debug("Сохраняем данные о файле в б/д с отчетами");
        return reportFile.getId();
    }

    @Override
    public ResponseEntity<Resource> getFile(int id) throws IOException, ExceptionNoId {
        logger.info("Вызываем метод находит и возвращает созданный ранее файл в формате JSON по переданному уникальному идентификатору");
        ReportFile reportFile = reportRepository.findById(id).orElseThrow(()->{
            logger.error("Ошибка ExceptionNoId нет файла под id={}", id);
            return new ExceptionNoId();
        });
        String fileName = reportFile.getPath();
        Resource resource = new ByteArrayResource(Files.readAllBytes(Path.of(fileName)));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

    //    метод для создания нового имени файла
    private String newName() {
        int id = (int) reportRepository.count();
        String newName = "report" + id + ".json";
        logger.debug("Создаем новое имя файла {}", newName);
        return newName;
    }
}