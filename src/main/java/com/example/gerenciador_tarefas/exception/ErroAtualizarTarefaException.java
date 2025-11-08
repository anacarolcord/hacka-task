package com.example.gerenciador_tarefas.exception;

public class ErroAtualizarTarefaException extends RuntimeException {
    public ErroAtualizarTarefaException() {
        super("Erro ao atualizar tarefa, verifique os dados");
    }
}
