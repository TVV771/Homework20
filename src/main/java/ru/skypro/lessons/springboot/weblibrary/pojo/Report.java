package ru.skypro.lessons.springboot.weblibrary.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "deportment_name")
    String deportmentName;
    @Column(name = "number_of_employees")
    int numberOfEmployees;
    @Column(name = "max_salary")
    int maxSalary;
    @Column(name = "min_salary")
    int minSalary;
    @Column(name = "average_salary")
    int averageSalary;

    public Report(String deportmentName, int numberOfEmployees, int maxSalary, int minSalary, int averageSalary) {
        this.deportmentName = deportmentName;
        this.numberOfEmployees = numberOfEmployees;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.averageSalary = averageSalary;
    }

    public Report() {
    }

    public String getDeportmentName() {
        return deportmentName;
    }

    public void setDeportmentName(String deportmentName) {
        this.deportmentName = deportmentName;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(int averageSalary) {
        this.averageSalary = averageSalary;
    }
}