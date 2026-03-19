package com.gms.employeebe.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notFound(NoSuchElementException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, mostSpecific(ex), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> badRequestValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,Object> body = base(HttpStatus.BAD_REQUEST, "Validation failed", req);
        body.put("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
                .collect(Collectors.toList()));
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> badJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Malformed JSON: " + mostSpecific(ex), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> fallback(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, mostSpecific(ex), req);
    }

    private ResponseEntity<Map<String,Object>> build(HttpStatus s, String msg, HttpServletRequest req){
        return ResponseEntity.status(s).body(base(s, msg, req));
    }
    private Map<String,Object> base(HttpStatus s, String msg, HttpServletRequest req){
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("timestamp", OffsetDateTime.now().toString());
        m.put("status", s.value());
        m.put("error", s.getReasonPhrase());
        m.put("message", msg);
        m.put("path", req.getRequestURI());
        return m;
    }
    private String mostSpecific(Throwable t){ Throwable x=t; while(x.getCause()!=null) x=x.getCause(); return String.valueOf(x.getMessage()); }
}