package ru.skypro.lessons.springboot.weblibrary.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import static constants.ReportConstants.REPORT;
import static constants.ReportConstants.REPORT_DTO_LIST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReportTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanReportTable() {
        reportRepository.deleteAll();
    }

    @Test
    public void createReport_whenIdReturn_NewReportId() throws Exception {
        String report = objectMapper.writeValueAsString(REPORT_DTO_LIST);
        mockMvc.perform(post("/report/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(report))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void givenReportById_whenIsId_ReturnReport() throws Exception {
        String report = objectMapper.writeValueAsString(REPORT);
        mockMvc.perform(post("/report/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(report))
                .andExpect(status().isOk());
        int id = REPORT.getId();
        mockMvc.perform(get("/report/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void givenNoReportInDatabase_whenGetOnReportList_thenNotFound() throws Exception {
        String report = objectMapper.writeValueAsString(REPORT);
        mockMvc.perform(post("/report/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(report))
                .andExpect(status().isOk());
        mockMvc.perform(get("/report/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}