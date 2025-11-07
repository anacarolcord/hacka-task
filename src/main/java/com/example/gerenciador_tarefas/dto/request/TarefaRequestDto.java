package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;
import java.time.LocalDate;

public record TarefaRequestDto (
        String nome,
        String descricao,
        LocalDate dataDeAtualizacao,
        StatusTarefa status,
        Duration tempoEstimado,
        Duration tempoUtilizado
){
    TarefaRequestDto toEntity(Tarefa tarefa){

        TarefaRequestDto dto = new TarefaRequestDto(
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getDataDeAtualizacao(),
                tarefa.getStatus(),
                tarefa.getTempoEstimado(),
                tarefa.getTempoUtilizado());

        return dto;
    }
}
