package com.jadson.exceptions;

import com.jadson.dto.requests.ErrorLogRequestDTO;
import com.jadson.services.ErrorLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@ControllerAdvice
@AllArgsConstructor
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorLogService errorLogService;
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }


    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleException(BusinessRuleException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", exception.getMessage());
        createErroLog(exception);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", exception.getMessage());
        createErroLog(exception);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private void createErroLog(Exception exception){
        ErrorLogRequestDTO errorLogRequestDTO = new ErrorLogRequestDTO();
        errorLogRequestDTO.setActionIn(exception.getMessage());
        errorLogRequestDTO.setActionOut(exception.getCause().getMessage());
        errorLogRequestDTO.setInclusionDate(Instant.now());
        errorLogService.registerEntity(errorLogRequestDTO);
    }
}
