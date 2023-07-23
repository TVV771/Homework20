package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.controller.EmployeeController;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;
import ru.skypro.lessons.springboot.weblibrary.service.ReportServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    @Mock
    private ReportRepository reportRepositoryMock;
    @Mock
    private Report reportMock;
    @InjectMocks
    private ReportServiceImpl reportServiceTest;
    private final Position pos = new Position(1, "Java");
    private final Position pos1 = new Position(1, "Java");
    private final Report rep = new Report();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void CreateReport_OK() throws IOException {

        List<ReportDTO> reportDTOS = new ArrayList<>();
        ReportDTO reportDTO = ReportDTO.builder()
                .id(1)
                .name(pos)
                .build();
        reportDTOS.add(reportDTO);
        when(reportRepositoryMock.createReport()).thenReturn(reportDTOS);

        assertEquals(reportRepositoryMock.count(), 0);
        verify(reportRepositoryMock, Mockito.times(1)).createReport();
    }
    @Test
    @ValueSource(ints = {1, 2, 3})
    public void Upload_Ok(int id) {
        ResponseEntity<Report> mockResponse = mock(ResponseEntity.class);
        when(reportRepositoryMock.readReportById(id)).thenReturn(mockResponse);
        when(mockResponse.getBody()).thenReturn(reportMock);
        ResponseEntity<Report> result = reportServiceTest.upload(id);
        assertEquals(mockResponse, result);
        assertEquals(reportMock, result.getBody());
    }



}