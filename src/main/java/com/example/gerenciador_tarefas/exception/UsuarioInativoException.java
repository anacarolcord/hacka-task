package com.example.gerenciador_tarefas.exception;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException() {
        super("Requisição não pode ser realizada, usuário inativo");
    }
}
