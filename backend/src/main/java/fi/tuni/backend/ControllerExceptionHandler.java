package fi.tuni.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

/**
 * Controller advice for Spring Boot.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Handles exceptions of CannotFindTargetException.
     *
     * @param exception CannotFindTargetException instance.
     * @return          ResponseEntity object.
     */
    @ExceptionHandler(CannotFindTargetException.class)
    public ResponseEntity<ErrorInfo> handleNoTarget(CannotFindTargetException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of DateTimeException.
     *
     * @param exception DateTimeException instance.
     * @return          ResponseEntity object.
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorInfo> handleDateParseException(DateTimeParseException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions of UserNotAdminException.
     *
     * @param exception UserNotAdminException instance.
     * @return          ResponseEntity object.
     */
    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<ErrorInfo> handleUserNotAdmin(UserNotAdminException exception) {
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Class for storing error information.
     */
    class ErrorInfo {

        /**
         * Stores error message.
         */
        private String errorMessage;

        /**
         * Overrides default constructor.
         *
         * @param msg   Error message.
         */
        public ErrorInfo(String msg) {
            this.errorMessage = msg;
        }

        /**
         * Gets error message.
         *
         * @return Error message as String.
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * Sets error message.
         *
         * @param errorMessage  Error message.
         */
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
