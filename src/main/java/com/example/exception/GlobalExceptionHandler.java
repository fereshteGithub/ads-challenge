package com.example.exception;




import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<String>> onConstraintValidationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return getErrorsMap(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<String>> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return getErrorsMap(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final Map<String, List<String>> handleGeneralExceptions(Exception ex, Locale locale) {
        List<String> errors = Collections.singletonList(this.messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
        return getErrorsMap(errors);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Map<String, List<String>> handleRuntimeExceptions(RuntimeException ex, Locale locale) {
        List<String> errors = Collections.singletonList(this.messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
        return getErrorsMap(errors);
    }

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public final Map<String, List<ExceptionErrorDto>> handleNotFoundException(NotFoundException ex, Locale locale) {
//        return getErrorsMap(ex,locale);
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Map<String, List<ExceptionErrorDto>> handleBadRequestException(BadRequestException ex, Locale locale) {
//        return getErrorsMap(ex,locale);
//    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", new ArrayList<>(errors));
        return errorResponse;
    }
//   private Map<String, List<ExceptionErrorDto>> getErrorsMap(BusinessException ex, Locale locale) {
//        Map<String, List<ExceptionErrorDto>> errorResponse = new HashMap<>();
//        String message = this.messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale);
//        errorResponse.put("errors", new ArrayList<>(){
//            {
//                add(new ExceptionErrorDto(ex.getCode(), message));
//            }});
//        return errorResponse;
//    }

}
