package ru.skypro.lessons.springboot.weblibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Сотрудник не найден";

    public EmployeeNotFoundException() {
        super(MESSAGE);
    }
}