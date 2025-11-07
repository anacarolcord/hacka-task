package com.example.gerenciador_tarefas.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("id: "+id+" não encontrado");
    }
}
