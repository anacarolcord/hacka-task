package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;
import java.time.LocalDate;

public record AtualizarTarefaColaboradorResponse(
        String idTarefa,
        String nome,
        StatusTarefa statusTarefa,
        Duration tempoUtilizado,
        LocalDate dataDeAtualizacao
) {
    public static AtualizarTarefaColaboradorResponse fromEntity(Tarefa tarefa){
        return new AtualizarTarefaColaboradorResponse(
                tarefa.getIdTarefa(),
                tarefa.getNome(),
                tarefa.getStatus(),
                tarefa.getTempoEstimado(),
                tarefa.getDataDeAtualizacao());
    }
}
