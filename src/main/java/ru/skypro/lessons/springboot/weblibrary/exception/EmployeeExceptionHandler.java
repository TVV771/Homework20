package ru.skypro.lessons.springboot.weblibraryhw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class EmployeeExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleIOException(IOException ioException) {
        String message = "Страница не найдена, проверьте адрес URL";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        String message = "Внутренняя ошибка сервера";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception) {
        String message = "Неверный запрос пользователя";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}