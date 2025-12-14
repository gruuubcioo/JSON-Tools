package pl.put.poznan.transformer.rest;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e) {
        return "Error blad danych  : " + e.getMessage() + "\n"+ " status : " + HttpStatus.BAD_REQUEST.value();
    }

    @ExceptionHandler(Exception.class)
    public String handleIllegalArgument(Exception e) {
        return "Error blad serwera  : " + e.getMessage() + "\n" + "status błędu: " + HttpStatus.BAD_REQUEST.value();
    }


}