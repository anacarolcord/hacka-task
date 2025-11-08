package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;

public record CriarTarefaRequest(
        String nome,
        String descricao,
        Duration tempoEstimado
) {
    public Tarefa toEntity(){
        Tarefa tarefa = new Tarefa();

        tarefa.setNome(this.nome);
        tarefa.setDescricao(this.descricao);
        tarefa.setTempoEstimado(this.tempoEstimado);

        return tarefa;
    }
}
