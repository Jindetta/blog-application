package fi.tuni.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

/**
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(CannotFindTargetException.class)
    public ResponseEntity<ErrorInfo> handleNoTarget(CannotFindTargetException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorInfo> handleDateParseException(DateTimeParseException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<ErrorInfo> handleUserNotAdmin(UserNotAdminException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    class ErrorInfo {
        private String errorMessage;

        public ErrorInfo(String msg) {
            this.errorMessage = msg;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
