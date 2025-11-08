package com.example.gerenciador_tarefas.exception;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException() {
        super("Tarefa não encontrada, confira os dados");
    }
}
