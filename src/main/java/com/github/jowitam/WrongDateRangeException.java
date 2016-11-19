package com.github.jowitam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

/**
 * WYJATEK zły zakres dat
 * oraz data podana jest z przeszłości - nie można zarezerwować w stecz
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)//400 jej etykieta
public class WrongDateRangeException extends RuntimeException {
    public WrongDateRangeException(LocalDate from, LocalDate to) {
        super("Niepoprawny zakres dat: " + from + " - " + to);
    }
    public WrongDateRangeException(LocalDate from) {
        super("Data nie może być z przeszłości.");
    }
}