package com.example.gerenciador_tarefas.entity.enums;

public enum StatusTarefa {
    PENDENTE(1),
    EM_ANDAMENTO(2),
    CONCLUIDA(3),
    CANCELADA(4);

    private final int prioridade;

    StatusTarefa(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getPrioridade() {
        return prioridade;
    }

    @Override
    public String toString() {
        return this.name() + " (Prioridade: " + prioridade + ")";
    }
}
