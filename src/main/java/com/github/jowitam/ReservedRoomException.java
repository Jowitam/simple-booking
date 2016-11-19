package com.github.jowitam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

/**
 * WYJATEK że pokój jest zarezerwowany
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)//400 jej etykieta
public class ReservedRoomException extends RuntimeException {
    public ReservedRoomException() {
        super("Rezerwacja niedostępna");
    }
}