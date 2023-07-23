package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.dynalink.linker.LinkerServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.FullInfo;
import ru.skypro.lessons.springboot.weblibrary.controller.EmployeeController;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee.EmployeeBuilder;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;


import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static ru.skypro.lessons.springboot.weblibrary.constants.EmployeeConstants.EMPLOYEE_LIST;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    public static final Position pos = new Position(1, "Java");
    public static final Position pos1 = new Position(1, "Java");
    public static final Report rep = new Report();
    private static EmployeeDTO employeeDto;
    private static FullInfo fullInfo;
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    private static EmployeeController employeeController;
    @InjectMocks
    private EmployeeServiceImpl employeeServiceTest;

    @Test
    void getAllEmployees_Ok() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "name1", 200000, pos, rep));
        employees.add(new Employee(2, "name2", 30000, pos, rep));
        when(employeeRepositoryMock.findAllEmployees()).thenReturn(employees);
        List<EmployeeDTO> employeeDTOs = employeeServiceTest.getAllEmployees();
        assertEquals(employees.size(), employeeDTOs.size());
    }

    @Test
    void withHighestSalary_Ok() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "name1", 200000, pos, rep));
        employees.add(new Employee(2, "name2", 30000, pos, rep));
        when(employeeRepositoryMock.withHighestSalary()).thenReturn(employees);
        List<EmployeeDTO> highestPaidEmployees = employees.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        List<EmployeeDTO> employeeDTOs = employeeServiceTest.withHighestSalary();
        verify(employeeRepositoryMock).withHighestSalary();
        assertEquals(highestPaidEmployees, employeeDTOs);

    }

    @Test
    void employeesPosition_Ok() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "name1", 200000, pos, rep));
        employees.add(new Employee(2, "name2", 30000, pos1, rep));
        when(employeeRepositoryMock.getEmployeesByName(Optional.of(pos))).thenReturn(employees);
        List<Employee> expected = new ArrayList<>();
        expected.add(new Employee(1, "name1", 200000, pos, rep));
        List<EmployeeDTO> employeeDTOList = expected.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        List<EmployeeDTO> result = employeeServiceTest.employeesPosition(Optional.of(pos));
        assertEquals(employeeDTOList.get(0).getName(), result.get(0).getName());
    }

    @Test
    void fullInfo_Ok() {
        int id = 1;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "name1", 200000, pos, rep));
        employees.add(new Employee(2, "name2", 30000, pos1, rep));
        when(employeeRepositoryMock.getEmployeesFullInfo(id)).thenReturn(employees);
        List<FullInfo> fullInfos = employeeServiceTest.fullInfo(id);
        assertEquals(employees.size(), fullInfos.size());
    }

    @Test
    void getEmployeeWithPaging_OK() {
        int pageIndex = 1;
        int pageSize = 10;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "name1", 200000, pos, rep));
        employees.add(new Employee(2, "name2", 30000, pos1, rep));
        Page<Employee> employeePage = new PageImpl<>(employees);
        when(employeeRepositoryMock.findAll(any(Pageable.class)).thenReturn(employeePage));
        List<EmployeeDTO> expectedEmployeeDTOList = employees.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());

        List<EmployeeDTO> actualEmployeeDTOList = employeeServiceTest.getEmployeeWithPaging(pageIndex);

        assertEquals(expectedEmployeeDTOList, actualEmployeeDTOList);
    }

    @Test
    void upload_NO_OK_Exception() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "multipartFile",
                "filename.txt",
                "application/json",
                "{\"id\" \":111\",\"name\": \"name1\", \"salary\": 20000\",\"position:\"JS\"}".getBytes());

        employeeServiceTest.upload(mockMultipartFile);
        assertThrows(IOException.class, () -> employeeServiceTest.upload(mockMultipartFile));
    }


}