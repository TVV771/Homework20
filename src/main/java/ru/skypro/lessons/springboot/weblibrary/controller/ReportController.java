package ru.skypro.lessons.springboot.weblibrary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @PostMapping(value = "/")
    public Integer createReport() throws IOException {
        logger.info("Was invoked method for create report ");
        logger.debug("Create report");
        return reportService.createReport();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getReportById(@PathVariable Integer id) throws IOException {
        logger.debug("Get report by id {} ", id);
        logger.info("Was invoked method get report by id {} ", id);
        logger.error("There is no report by id {} ", id);
        return reportService.getReportById(id);
    }
}