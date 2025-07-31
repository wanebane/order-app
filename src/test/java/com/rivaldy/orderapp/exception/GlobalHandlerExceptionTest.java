package com.rivaldy.orderapp.exception;

import com.rivaldy.orderapp.model.response.GeneralErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalHandlerExceptionTest {

    @InjectMocks
    private GlobalHandlerException globalHandlerException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toResponse_ShouldReturnGeneralErrorResponse() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = "Test error message";

        GeneralErrorResponse response = globalHandlerException.toResponse(status, errorMessage);

        assertEquals(status.value(), response.getStatus());
        assertEquals(errorMessage, response.getErrors());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleValidationException_ShouldReturnBadRequest() throws Exception {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError1 = new FieldError("object", "field1", "Error message 1");
        FieldError fieldError2 = new FieldError("object", "field2", "Error message 2");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        ResponseEntity<GeneralErrorResponse> response =
                globalHandlerException.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) response.getBody().getErrors();
        assertEquals(2, errors.size());
        assertTrue(errors.contains("Error message 1"));
        assertTrue(errors.contains("Error message 2"));
    }

    @Test
    void handleInsufficientException_ShouldReturnConflict() {
        String errorMessage = "Insufficient resources";
        InsufficientException ex = new InsufficientException(errorMessage);

        ResponseEntity<GeneralErrorResponse> response =
                globalHandlerException.handleInsufficientException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getErrors());
    }

    @Test
    void handleDataNotFoundException_ShouldReturnNotFound() {
        String errorMessage = "Data not found";
        DataNotFoundException ex = new DataNotFoundException(errorMessage);

        ResponseEntity<GeneralErrorResponse> response =
                globalHandlerException.handleDataNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getErrors());
    }
}