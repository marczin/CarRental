package com.marcinrosol.carrental.exceptions;


import com.marcinrosol.carrental.exceptions.car.CarAlreadyExistException;
import com.marcinrosol.carrental.exceptions.car.CarIdException;
import com.marcinrosol.carrental.exceptions.car.CarNotFoundException;
import com.marcinrosol.carrental.exceptions.car.response.CarAlreadyExistResponse;
import com.marcinrosol.carrental.exceptions.car.response.CarIdResponse;
import com.marcinrosol.carrental.exceptions.car.response.CarNotFoundResponse;
import com.marcinrosol.carrental.exceptions.rent.RentDateException;
import com.marcinrosol.carrental.exceptions.rent.RentException;
import com.marcinrosol.carrental.exceptions.rent.response.RentDateResponse;
import com.marcinrosol.carrental.exceptions.rent.response.RentResponse;
import com.marcinrosol.carrental.exceptions.user.UserAlreadyExistException;
import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.exceptions.user.response.UserAlreadyExistResponse;
import com.marcinrosol.carrental.exceptions.user.response.UserNotFoundResponse;
import com.marcinrosol.carrental.models.User;
import org.apache.coyote.Response;
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
     * Function handle car already exist exception
     *
     * @param ex      carAlreadyExistException
     * @param request WebRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleCarAlreadyExistException(CarAlreadyExistException ex, WebRequest request) {
        CarAlreadyExistResponse exceptionResponse = new CarAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle user already exist exception
     *
     * @param ex      userAlreadyExistException
     * @param request webRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
        UserAlreadyExistResponse exceptionResponse = new UserAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle user not found exception
     *
     * @param ex      userNotFoundException
     * @param request webRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        UserNotFoundResponse exceptionResponse = new UserNotFoundResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle rent data exception
     *
     * @param ex      rentDateException
     * @param request webRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleRentDateException(RentDateException ex, WebRequest request) {
        RentDateResponse exceptionResponse = new RentDateResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function handle rent exception
     *
     * @param ex      rentException
     * @param request webRequest
     * @return return Exception body with message as json and Bad_Request status
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleRentException(RentException ex, WebRequest request) {
        RentResponse exceptionResponse = new RentResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
