package com.example.bikeRenting.api.advice;

import com.example.bikeRenting.dto.error.DisplayableErrorDTO;
import com.example.bikeRenting.exception.*;
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
    public DisplayableErrorDTO handleArgumentNotValidException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserAlreadyBlockedException.class)
    public DisplayableErrorDTO handleUserAlreadyBlockedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserNotBlockedException.class)
    public DisplayableErrorDTO handleUserNotBlockedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_FAILURE)
    @ExceptionHandler(UserBlockedException.class)
    public DisplayableErrorDTO handleUserBlockedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StationNotFoundException.class)
    public DisplayableErrorDTO handleStationNotFoundException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StationNotEmptyException.class)
    public DisplayableErrorDTO handleStationNotEmptyException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BikeAlreadyRentedException.class)
    public DisplayableErrorDTO handleBikeAlreadyRentedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BikeBlockedException.class)
    public DisplayableErrorDTO handleBikeBlockedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalfunctionNotFoundException.class)
    public DisplayableErrorDTO handleMalfunctionNotFoundException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BikeNotFoundException.class)
    public DisplayableErrorDTO handleBikeNotFoundException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BikeNotBlockedException.class)
    public DisplayableErrorDTO handleBikeNotBlockedException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(StationIsFullException.class)
    public DisplayableErrorDTO handleBikeIsFullException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_FAILURE)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public DisplayableErrorDTO handleUserAlreadyExistsException(Exception ex) {
        return new DisplayableErrorDTO(ex.getMessage());
    }
}
