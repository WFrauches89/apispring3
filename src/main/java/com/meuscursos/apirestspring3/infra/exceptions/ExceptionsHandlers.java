package com.meuscursos.apirestspring3.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

@RestControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e){
        var error400 = e.getFieldErrors();
        return ResponseEntity.badRequest().body(error400.stream().map(DadosErrosDetails::new).toList());
    }

    private record DadosErrosDetails (String campo, String descricao) {

        public DadosErrosDetails (FieldError erros) {
            this(erros.getField(),erros.getDefaultMessage());
        }
    }

}
