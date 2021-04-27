package com.example.bikeRenting.api.advice;

import com.example.bikeRenting.dto.error.DisplayableErrorDTO;
import com.example.bikeRenting.exception.EntityNotFoundException;
import com.example.bikeRenting.exception.UserAlreadyBlockedException;
import com.example.bikeRenting.exception.UserBlockedException;
import com.example.bikeRenting.exception.UserNotBlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public DisplayableErrorDTO handleArgumentNotValidException(BindException ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserAlreadyBlockedException.class)
    public DisplayableErrorDTO handleUserAlreadyBlockedException(BindException ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserNotBlockedException.class)
    public DisplayableErrorDTO handleUserNotBlockedException(BindException ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_FAILURE)
    @ExceptionHandler(UserBlockedException.class)
    public DisplayableErrorDTO handleUserBlockedException(BindException ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

}
