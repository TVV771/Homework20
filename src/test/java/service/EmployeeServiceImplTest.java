package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PagingAndSortingRepository;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static constants.EmployeeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @Mock
    private PagingAndSortingRepository pagingRepositoryMock;
    @InjectMocks
    private EmployeeServiceImpl out;

    @Test
    public void getAllEmployeesCollectionOfEmployeesReturnAllEmployees() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(EMPLOYEE_FULL_INFO_LIST);
        assertIterableEquals(EMPLOYEE_FULL_INFO_LIST, out.getAllEmployees());
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void createEmployeeNewEmployeeSaveEmployee(Employee employee) {
        when(employeeRepositoryMock.save(employee))
                .thenReturn(employee);
        out.createEmployee(EmployeeDTO.fromEmployee(employee));
        verify(employeeRepositoryMock, times(1)).save(employee);
    }

    @Test
    public void updateEmployeeByIdIsIdUpdateEmployee() {
        when(employeeRepositoryMock.findById(anyInt()))
                .thenReturn(Optional.of(EMPLOYEE_1));
        assertEquals(EMPLOYEE_1, out.getEmployeeById(1));
        out.updateEmployeeById(1, EMPLOYEE_DTO_1);
        verify(employeeRepositoryMock, times(1)).save(EMPLOYEE_DTO_1.toEmployee());
    }

    @Test
    public void getEmployeeByIdIsIdReturnEmployee() {
        when(employeeRepositoryMock.findById(anyInt()))
                .thenReturn(Optional.of(EMPLOYEE_1));
        assertEquals(EMPLOYEE_1, out.getEmployeeById(1));
        verify(employeeRepositoryMock, times(1)).findById(anyInt());
    }

    @Test
    public void getEmployeeByIdNotFoundIdThrowsExceptions() {
        when(employeeRepositoryMock.findById(anyInt()))
                .thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeById(anyInt()));
    }

    @Test
    public void deleteEmployeeByIdIsIdDeleteEmployee() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(EMPLOYEE_FULL_INFO_LIST)
                .thenThrow(EmployeeNotFoundException.class);
        out.deleteEmployeeById(1);
        verify(employeeRepositoryMock, times(1)).deleteById(1);
    }

    @Test
    public void getSalarySumTestSalarySumReturnSalarySum() {
        Integer testSalarySum = EMPLOYEE_LIST.stream()
                .mapToInt(employee -> IntStream.of(employee.getSalary()).sum())
                .sum();
        Integer actualSalarySum = 25000 + 35000 + 40000;
        assertEquals(testSalarySum, actualSalarySum);
    }

    @Test
    public void getSalaryMinTestSalaryMinReturnEmployeeWithSalaryMin() {
        Employee testEmployeeWithSalaryMin = EMPLOYEE_LIST.stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow();
        Employee actualEmployee = EMPLOYEE_1;
        assertEquals(testEmployeeWithSalaryMin, actualEmployee);
    }

    @Test
    public void getSalaryMaxTestSalaryMaxReturnEmployeeWithSalaryMax() {
        Employee testEmployeeWithSalaryMax = EMPLOYEE_LIST.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow();
        Employee actualEmployee = EMPLOYEE_3;
        assertEquals(testEmployeeWithSalaryMax, actualEmployee);
    }

    @Test
    public void getSalaryAboveAverageEmployeesTestAverageReturnListEmployees() {
        double testSalaryAverage = EMPLOYEE_LIST.stream()
                .mapToInt(Employee::getSalary).average().getAsDouble();
        List<Employee> testEmployeesList = EMPLOYEE_LIST.stream()
                .filter(employee -> employee.getSalary() > testSalaryAverage)
                .collect(Collectors.toList());
        List<Employee> actualEmployeesList = List.of(EMPLOYEE_2, EMPLOYEE_3);
        assertIterableEquals(testEmployeesList, actualEmployeesList);
    }

    @Test
    public void getEmployeesByParamSalaryTestParamReturnListEmployees() {
        int testParam = 26000;
        List<Employee> testEmployeesList = EMPLOYEE_LIST.stream()
                .filter(employee -> employee.getSalary() > testParam)
                .collect(Collectors.toList());
        List<Employee> actualEmployeesList = List.of(EMPLOYEE_2, EMPLOYEE_3);
        assertIterableEquals(testEmployeesList, actualEmployeesList);
    }

    @Test
    public void getEmployeesByPositionIsPositionReturnListEmployees() {
        when(employeeRepositoryMock.findEmployeeByPosition(1))
                .thenReturn((List.of(EMPLOYEE_FULL_INFO_1, EMPLOYEE_FULL_INFO_2)));
        assertIterableEquals(List.of(EMPLOYEE_FULL_INFO_1, EMPLOYEE_FULL_INFO_2), out.getEmployeesByPosition(1));
    }

    @Test
    public void getEmployeeWithPageTestPageReturnListEmployeesByPage() {
        Page<Employee> testEmployeeListPage = new PageImpl<>(EMPLOYEE_LIST);
        when(pagingRepositoryMock.findAll(PageRequest.of(0, 10)))
                .thenReturn(testEmployeeListPage);
        List<EmployeeDTO> testEmployeesList = testEmployeeListPage.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        assertEquals(testEmployeesList, out.getEmployeeWithPage(0));
    }

    @Test
    public void createEmployeeFromFileNewFileCreateEmployee() throws IOException {
        String json = "[\n" +
                "  {\n" +
                "    \"id\": 10,\n" +
                "    \"name\": \"Владимир\",\n" +
                "    \"salary\": 105000,\n" +
                "    \"position\": {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Джава\"\n" +
                "    }\n" +
                "  }\n" +
                "]";
        MockMultipartFile file = new MockMultipartFile(
                "employee",
                "employee.json",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                json.getBytes());
        out.createEmployeeFromFile(file);
        verify(employeeRepositoryMock, times(1)).save(any(Employee.class));
    }

    public static Stream<Arguments> provideParamsForTests() {
        Employee employeeCorrect = new Employee(
                1,
                "Nata",
                25000,
                new Position(2, "java"));
        Employee employeeEmpty = new Employee();
        Employee employeeDefault = new Employee(1, "по умолчанию", 0, null);
        return Stream.of(
                Arguments.of(employeeCorrect, "Корректный сотрудник"),
                Arguments.of(employeeEmpty, "Поля сотрудника не заполнены"),
                Arguments.of(employeeDefault, "Сотрудник по умоланию")
        );
    }
}