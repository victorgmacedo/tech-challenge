package br.com.tech.challenge.exceptions;

import br.com.tech.challenge.domain.ApplicationErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<ApplicationErrorDetail> handleApplicationException(ApplicationException applicationException) {
        var apiErrorMessage = new ApplicationErrorDetail(applicationException.getMessage());
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApplicationErrorDetail> handleNotFoundException(NotFoundException notFoundException) {
        var apiErrorMessage = new ApplicationErrorDetail(notFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorMessage);
    }

}