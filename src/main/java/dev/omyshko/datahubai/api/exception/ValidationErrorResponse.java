package dev.omyshko.datahubai.api.exception;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }

    public void addErrorWithCode(String code, String message) {
        errors.add(new ValidationError(code, message, false));
    }

    @Data
    public static class ValidationError {
        private final String code;
        private final String message;

        public ValidationError(String field, String message) {
            this(field, message, true);
        }

        public ValidationError(String code, String message, boolean prependValidationPrefix) {
            this.code = prependValidationPrefix ? "400.validation_error." + code : code;
            this.message = message;
        }
    }
} 