package dev.omyshko.datahubai.api.exception;

import lombok.Data;
import java.util.List;
import java.util.Collections;

@Data
public class ErrorResponse {
    private final List<Error> errors;

    public ErrorResponse(String code, String message) {
        this.errors = Collections.singletonList(new Error(code, message));
    }

    @Data
    public static class Error {
        private final String code;
        private final String message;
    }
} 