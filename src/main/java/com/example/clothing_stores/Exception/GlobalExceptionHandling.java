package com.example.clothing_stores.Exception;

import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Enums.ErrorCode;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandling {
    private static final String MIN_ATTRIBUTE= "min";

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingRuntimeException(AppException exception){
        return ResponseEntity.status(exception.errorCode.getStatus())
                .body(ApiResponse.builder()
                        .code(exception.getErrorCode().getCode())
                        .message(exception.getErrorCode().getMessage())
                        .build()
                );
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidationException(MethodArgumentNotValidException exception){
        ErrorCode errorCode = ErrorCode.valueOf(exception.getFieldError().getDefaultMessage());
        Map<String, Object> attributes = null;

        try {
            var contraintViolantion = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = contraintViolantion.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e) {}

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(attributes == null ? errorCode.getMessage() :
                                mapAttribute(errorCode.getMessage(), attributes))
                        .build()
                );
    }

    private String mapAttribute (String message, Map<String, Object> attributes){
        String value = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", value);
    }
}
