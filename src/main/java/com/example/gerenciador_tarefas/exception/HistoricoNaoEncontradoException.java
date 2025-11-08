package com.example.gerenciador_tarefas.exception;

public class HistoricoNaoEncontradoException extends RuntimeException {
    public HistoricoNaoEncontradoException() {
        super("Historico nao encontrado");
    }
}
