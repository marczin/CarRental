package com.marcinrosol.carrental.exceptions;


import com.marcinrosol.carrental.exceptions.car.CarAlreadyExistException;
import com.marcinrosol.carrental.exceptions.car.CarIdException;
import com.marcinrosol.carrental.exceptions.car.CarNotFoundException;
import com.marcinrosol.carrental.exceptions.car.response.CarAlreadyExistResponse;
import com.marcinrosol.carrental.exceptions.car.response.CarIdResponse;
import com.marcinrosol.carrental.exceptions.car.response.CarNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice                                   //import problem ??
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    /**
     * Function handle car id exception
     *
     * @param ex      CarIdException object
     * @param request Web request
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleCarIdException(CarIdException ex, WebRequest request) {
        CarIdResponse exceptionResponse = new CarIdResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle car not found exception
     *
     * @param ex      CarNotFoundException
     * @param request Web request
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex, WebRequest request) {
        CarNotFoundResponse exceptionResponse = new CarNotFoundResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle cat arleady exist exception
     *
     * @param ex      carArleadyExistException
     * @param request WebRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleCarAlreadyExistException(CarAlreadyExistException ex, WebRequest request) {
        CarAlreadyExistResponse exceptionResponse = new CarAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
