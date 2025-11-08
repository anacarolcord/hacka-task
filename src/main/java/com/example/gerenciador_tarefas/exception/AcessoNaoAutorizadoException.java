package com.example.gerenciador_tarefas.exception;

public class AcessoNaoAutorizadoException extends RuntimeException {
    public AcessoNaoAutorizadoException() {
        super("Acesso não autorizado para a função, contate o administrador");
    }
}
