package whizware.whizware.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import whizware.whizware.dto.BaseResponse;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        List<ObjectError> errors = ex.getAllErrors();

        List<String> data = errors.stream().map(error -> error.getDefaultMessage()).toList();

        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Request not complete")
                        .data(data)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
