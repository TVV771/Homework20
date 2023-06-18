package ru.skypro.lessons.springboot.weblibrary.dto;


public class ReportDTO {
    private int id;
    String deportmentName;
    int numberOfEmployees;
    int maxSalary;
    int minSalary;
    int averageSalary;

    public ReportDTO(String deportmentName, int numberOfEmployees, int maxSalary, int minSalary, int averageSalary) {
        this.deportmentName = deportmentName;
        this.numberOfEmployees = numberOfEmployees;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.averageSalary = averageSalary;
    }

    public ReportDTO() {
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