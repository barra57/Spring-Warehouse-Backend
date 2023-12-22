package whizware.whizware.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.exception.ConflictException;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.exception.UnauthorizedException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException ex) {
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

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<BaseResponse> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NoContentException.class)
    public final ResponseEntity<BaseResponse> handleNoContentException(NoContentException ex) {
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<BaseResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

}
