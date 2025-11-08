package com.example.gerenciador_tarefas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<String> handleTarefaNaoEncontrada(TarefaNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AcessoNaoAutorizadoException.class)
    public ResponseEntity<String> handleAcessoNaoAutorizado(AcessoNaoAutorizadoException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<String> handleUsuarioInativo(UsuarioInativoException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(CpfDuplicadoException.class)
    public ResponseEntity<String> handleCpfDuplicado(CpfDuplicadoException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(HistoricoNaoEncontradoException.class)
    public ResponseEntity<String> handleHistoricoNaoEncontrado(HistoricoNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}

