package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.entity.Employee;
import ru.skypro.lessons.springboot.weblibrary.entity.Position;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {
    @Query("select new ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo(e.id, e.name, e.salary, p.name) " +
            "from Employee e left join Position p where e.position.id = p.id")
    List<EmployeeFullInfo> getEmployeeFullInfo();

    @Query("select e from Employee e where e.position.name = ?1")
    List<Employee> findEmployeeByPosition_Name(String name);

    @Query("select sum(salary) from Employee ")
    Integer salarySum();

    @Query("select avg(salary) from Employee")
    Integer salaryAvg();

    @Query(value = "select * from employee " +
            "where salary = (SELECT MIN(salary) FROM employee) " +
            "limit 1",
            nativeQuery = true)
    Employee employeeMinSalary();

    @Query(value = "select e from Employee e where e.salary = (SELECT MAX(salary) FROM Employee)")
    List<Employee> withHighestSalary();

    List<Employee> findBySalaryGreaterThan(int salary);

    @Query("select p from Position p")
    List<Position> findAllPositnion();

    @Transactional
    @Query(value = "select new ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO(p.name, count(e) , max(e.salary) , min(e.salary) , avg (e.salary)) " +
            "from Employee e left join e.position p " +
            "group by p.name")
    List<ReportDTO> getReport();

}