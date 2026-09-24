package com.devshowcase.api.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    private ResponseEntity<Map<String,Object>> body(HttpStatus status,String message,Object details){
        Map<String,Object> b=new LinkedHashMap<>(); b.put("timestamp", LocalDateTime.now()); b.put("status",status.value()); b.put("error",status.getReasonPhrase()); b.put("message",message); if(details!=null)b.put("details",details); return ResponseEntity.status(status).body(b);
    }
    @ExceptionHandler(NoSuchElementException.class) ResponseEntity<Map<String,Object>> notFound(NoSuchElementException e){return body(HttpStatus.NOT_FOUND,e.getMessage(),null);}
    @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String,Object>> badRequest(IllegalArgumentException e){return body(HttpStatus.BAD_REQUEST,e.getMessage(),null);}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){
        Map<String,String> errors=new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x->errors.put(x.getField(),x.getDefaultMessage())); return body(HttpStatus.BAD_REQUEST,"Dados inválidos",errors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String,Object>> integrity(DataIntegrityViolationException e){return body(HttpStatus.BAD_REQUEST,"Não foi possível salvar os dados. Verifique campos únicos e relacionamentos.",null);}
    @ExceptionHandler(Exception.class) ResponseEntity<Map<String,Object>> generic(Exception e){return body(HttpStatus.INTERNAL_SERVER_ERROR,"Ocorreu um erro interno na API.",null);}
}
