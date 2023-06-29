package kr.hs.dgsw.fastwash.fastwashserver.global.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> onBusinessException(BusinessException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()),
                HttpStatus.valueOf(exception.getCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> onUncaughtException(RuntimeException exception) {
        log.error(exception);
        exception.printStackTrace();

        return new ResponseEntity<>(new ExceptionResponse("Unknown problem"),
                HttpStatus.valueOf(500));
    }
}
