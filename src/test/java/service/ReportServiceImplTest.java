package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;
import ru.skypro.lessons.springboot.weblibrary.service.ReportServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static constants.ReportConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportServiceImpl out;

    @Test
    public void createReportIdReturnNewReport() throws IOException {
        String fileName = "report.json";
        String json = String.valueOf(REPORT_DTO_LIST);
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Report report = new Report();
        report.setFilePath(String.valueOf(path.toAbsolutePath()));
        reportRepositoryMock.save(report);
        out.createReport();
        verify(reportRepositoryMock, times(1)).createReport();
    }

    @Test
    public void getReportByIdIsIdReturnReport() throws IOException {
        when(reportRepositoryMock.findById(anyInt()))
                .thenReturn(Optional.of(REPORT));
        assertEquals(REPORT.getFilePath(), out.getReportById(1).getBody().getFile().getPath());
        verify(reportRepositoryMock, times(1)).findById(anyInt());
    }
}