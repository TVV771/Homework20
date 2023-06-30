package ru.skypro.lessons.springboot.weblibrary.service.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exception.ExceptionNoId;
import ru.skypro.lessons.springboot.weblibrary.entity.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository repository, ReportRepository reportRepository) {
        this.employeeRepository = repository;
        this.reportRepository = reportRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    @Override
    public void addEmployees(EmployeeDTO employee) {
        employeeRepository.save(EmployeeMapDTO.toEmployee(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        logger.info("Вызываем метод для получения списка сотрудников");
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public void editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId {
        logger.info("Вызываем метод для изменения данных сотрудника");
        int id = employeeDTO.getId();
        Employee result = employeeRepository.findById(id)
                .orElseThrow(()->{
                    logger.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
        logger.debug("Вызываем сотрудника с id={} из базы данных", id);
        if (!employeeDTO.getName().isBlank()) {
            result.setName(employeeDTO.getName());
        }
        if (employeeDTO.getSalary() > 0) {
            result.setSalary(employeeDTO.getSalary());
        }
        employeeRepository.save(result);
        logger.debug("Сщхраняем сотрудника с новыми данными в базу данных");
    }

    @Override
    public EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId {
        logger.info("Вызываем сотрудника с id={}", id);
        return employeeRepository.findById(id)
                .map(EmployeeMapDTO::fromEmployee)
                .orElseThrow(()->{
                    logger.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
    }

    @Override
    public List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        logger.info("Вызываем метод для получения списка сотрудников с наименованием отдела");
        return employeeRepository.getEmployeeFullInfo();
    }

    @Override
    public EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId {
        logger.info("Вызываем метод для получения сотрудника по id={} с наименованием отдела", id);
        return employeeRepository.findById(id)
                .map(EmployeeMapDTO::toEmployeeFullInfo)
                .orElseThrow(()->{
                    logger.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
    }

    @Override
    public List<EmployeeDTO> getEmployeeByPositionName(String position) {
        logger.info("Вызываем метод для получения списка сотрудников работующих в отделе {}", position);
        String pos = position.toLowerCase();
        List<Employee> employeeList = employeeRepository.findEmployeeByPosition_Name(pos);
        logger.debug("Получаем список сотрудников работующих в отделе {} из базы данных", position);
        if (!position.isBlank()) {
            return EmployeeMapDTO.toEmployeeDTOList(employeeList);
        } else return getAllEmployee();
    }

    @Override
    public List<EmployeeDTO> getEmployeeFromPage(int page) {
        int sizePage = 10;
        logger.info("Вызываем метод для вывода списка сотрудников из базы данных находящихся на {} листе. Размер листа - {}", page, sizePage);
        return employeeRepository.findAll(PageRequest.of(page, sizePage))
                .stream().map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeToId(int id) throws ExceptionNoId {
        logger.info("Вызываем метод для удаления сотрудника по id {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            logger.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
            throw new  ExceptionNoId();
        }
    }

    @Override
    public void uploadAndSaveEmployees(MultipartFile file) throws IOException {
        logger.info("Вызываем метод для добавления списка сотрудников в базу данных из файла {}", file.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<EmployeeDTO>> typeReference = new TypeReference<>() {
        };
        List<EmployeeDTO> employeeDTOList = objectMapper.readValue(file.getBytes(), typeReference);
        employeeDTOList.forEach(this::addEmployees);
    }

    @Override
    public void recReport(String deportmentName) {

    }
}