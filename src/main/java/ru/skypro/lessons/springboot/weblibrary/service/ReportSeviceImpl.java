package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.util.List;

@Service
public class ReportSeviceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;

    public ReportSeviceImpl(EmployeeRepository employeeRepository, ReportRepository reportRepository) {
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportDTO> downloadFile() {
        return employeeRepository.getReport();
    }
}