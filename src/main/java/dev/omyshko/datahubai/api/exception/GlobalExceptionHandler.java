package dev.omyshko.datahubai.api.exception;

import dev.omyshko.datahubai.connections.exception.ConnectionNotFoundException;
import dev.omyshko.datahubai.connections.exception.EncryptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling across all controllers and REST endpoints.
 * 
 * Handles specific exceptions like:
 * - {@link ConnectionNotFoundException} - When requested connection is not found (404)
 * - {@link EncryptionException} - When encryption/decryption operations fail (500)
 * 
 * Also provides a fallback handler for any unhandled exceptions.
 * 
 * All exceptions are logged appropriately and converted to {@link ErrorResponse} objects
 * with proper HTTP status codes and user-friendly messages.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConnectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConnectionNotFoundException(ConnectionNotFoundException ex) {
        log.warn("Connection not found: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            "404.not_found.connection",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EncryptionException.class)
    public ResponseEntity<ErrorResponse> handleEncryptionException(EncryptionException ex) {
        log.error("Encryption error occurred", ex);
        ErrorResponse error = new ErrorResponse(
            "500.internal_error.encryption",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException ex) {
        log.warn("Failed to read HTTP message: {}", ex.getMessage());
        ValidationErrorResponse response = new ValidationErrorResponse();
        
        // Check if it's an enum validation error
        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.ValueInstantiationException) {
            String message = cause.getMessage();
            if (message != null && message.contains("ConnectionType")) {
                response.addError("type", "Invalid connection type provided");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            var invalidFormatEx = (com.fasterxml.jackson.databind.exc.InvalidFormatException) cause;
            if (invalidFormatEx.getTargetType() != null && invalidFormatEx.getTargetType().isEnum()) {
                response.addError("type", "Invalid connection type provided");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        
        // Generic JSON parsing error
        response.addError("body", "Invalid request format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ValidationErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Invalid argument provided: {}", ex.getMessage());
        ValidationErrorResponse response = new ValidationErrorResponse();
        
        // Extract field name from message if it's an enum validation error
        if (ex.getMessage().startsWith("Unexpected value")) {
            response.addError("type", "Invalid connection type provided");
        } else {
            response.addError("value", ex.getMessage());
        }
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());
        ValidationErrorResponse response = new ValidationErrorResponse();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            response.addError(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse error = new ErrorResponse(
            "500.internal_error.unexpected",
            "An unexpected error occurred"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 