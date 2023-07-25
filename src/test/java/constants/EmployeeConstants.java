package constants;


import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;

import java.util.List;

public class EmployeeConstants {
    public static final Employee EMPLOYEE_1 = new Employee(1, "name1", 15000, new Position(1, "java"));
    public static final Employee EMPLOYEE_2 = new Employee(2, "name2", 25000, new Position(1, "java"));
    public static final Employee EMPLOYEE_3 = new Employee(3, "name3", 30000, new Position(2, "java2"));
    public static final List<Employee> EMPLOYEE_LIST = List.of(
            EMPLOYEE_1,
            EMPLOYEE_2,
            EMPLOYEE_3
    );
    public static final EmployeeDTO EMPLOYEE_DTO_1 = new EmployeeDTO(1, "name1", 15000, new Position(1, "java"));
    public static final EmployeeDTO EMPLOYEE_DTO_2 = new EmployeeDTO(2, "name2", 25000, new Position(1, "java"));
    public static final EmployeeDTO EMPLOYEE_DTO_3 = new EmployeeDTO(3, "name3", 30000, new Position(2, "java2"));
    public static final List<EmployeeDTO> EMPLOYEE_DTO_LIST = List.of(
            EMPLOYEE_DTO_1,
            EMPLOYEE_DTO_2,
            EMPLOYEE_DTO_3
    );
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_1 = new EmployeeFullInfo("name1", 15000, "java");
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_2 = new EmployeeFullInfo("name2", 25000, "java");
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_3 = new EmployeeFullInfo("name3", 30000, "java2");

    public static final List<EmployeeFullInfo> EMPLOYEE_FULL_INFO_LIST = List.of(
            EMPLOYEE_FULL_INFO_1,
            EMPLOYEE_FULL_INFO_2,
            EMPLOYEE_FULL_INFO_3
    );
}