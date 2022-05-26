package antifraud.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object>  handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleHttpConflictException(HttpConflictException ex) {
        return ResponseEntity.status(409).build();
    }
    @ExceptionHandler
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleUserNotFoundException(UnprocessableEntityException ex) {
        return ResponseEntity.status(422).build();
    }

    @ExceptionHandler
    protected ResponseEntity<Object>  handleException(Exception ex) {
        return ResponseEntity.badRequest().build();
    }


}
