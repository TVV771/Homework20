package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee,Integer>,
        PagingAndSortingRepository<Employee, Integer> {
    @Query(value = "SELECT e FROM Employee e")
    List<Employee> findAllEmployees();


    @Query(value = "select * from employee order by salary desc ",
            nativeQuery = true)
    List<Employee> withHighestSalary();
    @Query(value = "SELECT * FROM employee WHERE name= :name",
            nativeQuery = true)
    List<Employee> getEmployeesByName(@Param("name") Optional position  );



    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary1. " +
            "FullInfo(e.name , e.salary , p.position) " +
            "FROM Employee e join fetch Position p " +
            "WHERE  id = :id",
            nativeQuery = true)
    List<Employee> getEmployeesFullInfo(@Param("id") int id);

    @Query(value = "SELECT e.name FROM Employee e")
    String getEmployeeByName();

    @Query(value = "SELECT max(e.salary) from Employee e")
    int getMaxSalary();
    @Query(value = "SELECT min (e.salary) from Employee e")
    int getMinSalary();
    @Query(value = "SELECT avg(e.salary) from Employee e")
    int getAvgSalary();

}