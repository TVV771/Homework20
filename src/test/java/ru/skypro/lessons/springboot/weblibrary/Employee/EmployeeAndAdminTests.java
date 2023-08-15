package ru.skypro.lessons.springboot.weblibrary.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static constants.EmployeeConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
public class EmployeeAndAdminTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanEmployeeTable() {
        employeeRepository.deleteAll();
    }

    @Test
    void givenNoEmployeesInDatabase_whenGetEmployees_thenEmptyJsonArray() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void givenNoEmployeesInDatabase_whenEmployeeAdded_thenItExistsInList() throws Exception {
        String employee = objectMapper.writeValueAsString(EMPLOYEE_DTO_1);

        mockMvc.perform(post("/admin/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONArray().put(employee).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].salary").value(15000))
                .andExpect(jsonPath("$[0].positionName").value("java"));
    }

    @Test
    public void givenId_whenGetExistingEmployee_EmployeeReturned() throws Exception {
        String employee = objectMapper.writeValueAsString(EMPLOYEE_DTO_1);

        mockMvc.perform(post("/admin/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        int id = EMPLOYEE_DTO_1.getId();
        mockMvc.perform(get("/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    void givenNoEmployeeInDatabase_whenEditOnEmptyList_thenNotFound() throws Exception {
        String employee = objectMapper.writeValueAsString(EMPLOYEE_DTO_3);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee));

        mockMvc.perform(put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenThereIsOneEmployeeCreated_whenEmployeeEdited_thenItChangedInDatabase() throws Exception {
        String employee = objectMapper.writeValueAsString(EMPLOYEE_DTO_1);

        mockMvc.perform(post("/admin/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        int id = EMPLOYEE_DTO_1.getId();
        EmployeeDTO updateEmployeeDTO1 = new EmployeeDTO(
                EMPLOYEE_DTO_1.getId(),
                "Sofa",
                EMPLOYEE_DTO_1.getSalary(),
                EMPLOYEE_DTO_1.getPosition()
        );
        String employeeUpdate = objectMapper.writeValueAsString(updateEmployeeDTO1);
        mockMvc.perform(put("/admin/employees/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeUpdate))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Sofa"));
    }

    @Test
    void givenThereIsOneUserCreated_whenUserDeleted_thenUserNotFound() throws Exception {
        String employee = objectMapper.writeValueAsString(EMPLOYEE_1);

        mockMvc.perform(post("/admin/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        int id = EMPLOYEE_1.getId();

        mockMvc.perform(delete("/admin/employees/del/{id}", id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNoUsersInDatabase_whenDeleteOnEmptyList_thenNotFound() throws Exception {
        mockMvc.perform(delete("/employee/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmployeesFromFile_whenNewFile_CreateEmployees() throws Exception {
        Path path = Paths.get("employee.json");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employee.json",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                Files.readAllBytes(path));
        mockMvc.perform(multipart("/admin/employees/employees/upload")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void givenSalarySum_whenSalarySum_ReturnSalarySum() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));

        mockMvc.perform(get("/employee/salary/sum")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(40000));
    }

    @Test
    public void givenSalaryMin_whenSalaryMin_ReturnEmployeeWithSalaryMin() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));

        mockMvc.perform(get("/employee/salary/min")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.salary").value(15000))
                .andExpect(jsonPath("$.positionName").value("java"));
    }

    @Test
    public void givenSalaryMax_whenSalaryMax_ReturnEmployeeWithSalaryMax() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));

        mockMvc.perform(get("/employee/salary/max")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name2"))
                .andExpect(jsonPath("$.salary").value(25000))
                .andExpect(jsonPath("$.positionName").value("java"));
    }

    @Test
    public void givenEmployeesSalaryAboveAverage_whenSalaryAboveAverage_ReturnEmployees() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));
        String employee3 = objectMapper.writeValueAsString(EMPLOYEE_3);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee3));

        mockMvc.perform(get("/employee/high-salary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("name2"))
                .andExpect(jsonPath("$[1].name").value("name3"));
    }

    @Test
    public void givenEmployeesByParamSalary_whenParamSalary_ReturnEmployees() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));
        String employee3 = objectMapper.writeValueAsString(EMPLOYEE_3);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee3));

        mockMvc.perform(get("/employee/salaryHigherThan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paramSalary", String.valueOf(26000)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("name3"));
    }

    @Test
    public void givenEmployeesByPosition_whenIsPosition_ReturnListEmployees() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));
        String employee3 = objectMapper.writeValueAsString(EMPLOYEE_3);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee3));

        mockMvc.perform(get("/employee/position")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("position", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].positionName").value("java"))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[1].positionName").value("java"));
    }

    @Test
    public void givenEmployeeWithPage_whenPage_ReturnListEmployeesByPage() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));

        mockMvc.perform(get("/employee/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void givenEmployeesWithHighestSalary_whenHighestSalary_ReturnEmployees() throws Exception {
        String employee1 = objectMapper.writeValueAsString(EMPLOYEE_1);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee1));
        String employee2 = objectMapper.writeValueAsString(EMPLOYEE_2);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee2));
        String employee3 = objectMapper.writeValueAsString(EMPLOYEE_3);
        mockMvc.perform(post("/admin/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee3));

        mockMvc.perform(get("/employee/withHighestSalary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("name3"));
    }
}