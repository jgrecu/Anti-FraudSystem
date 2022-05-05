package antifraud.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity<Object>  handleNullPointerException(NullPointerException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object>  handleBadRequestException(BadRequestException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler({HttpConflictException.class})
    protected ResponseEntity<Object> handleHttpConflictException(HttpConflictException ex, WebRequest request) {
        return ResponseEntity.status(409).build();
    }

    @ExceptionHandler({UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return ResponseEntity.notFound().build();
    }


}
