package fi.tuni.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {
    @ExceptionHandler(CannotFindTargetException.class)
    public ResponseEntity<ErrorInfo> handleNoCustomer(CannotFindTargetException exception) {
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(exception.getMessage()), HttpStatus.NOT_FOUND);
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
