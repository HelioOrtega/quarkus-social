package io.github.helioortega.quarkussocial.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    public static final String VALIDATION_ERROR = "Validation error";
    private String message;
    private Collection<FieldError> erros;

    public ResponseError(String message, Collection<FieldError> erros) {
        this.message = message;
        this.erros = erros;
    }

    public static <T>ResponseError createFromValidation
            (Set<ConstraintViolation<T>> violations) {

        List<FieldError> errors = violations
                .stream()
                .map(cv -> new FieldError(
                        cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        return new ResponseError(VALIDATION_ERROR, errors);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldError> getErros() {
        return erros;
    }

    public void setErros(Collection<FieldError> erros) {
        this.erros = erros;
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }
}
