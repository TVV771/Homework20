package ru.skypro.lessons.springboot.weblibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleExceptionNoId(ExceptionNoId exceptionNoId) {
        String message = "Нет работника под данным id\n";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleExceptionNoSuchElementException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleExceptionNoSuchElementException(IllegalArgumentException illegalArgumentException) {
        String message = "Указан нуливой id\n";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}