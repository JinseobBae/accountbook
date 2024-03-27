package myhome.accountbook.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BookExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeHandler(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
