package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;
import java.time.LocalDate;

public record TarefaResponseDto(
        String idTarefa,
        String nome,
        String descricao,
        LocalDate dataDeAtualizacao,
        StatusTarefa status,
        Duration tempoEstimado,
        Duration tempoUtilizado
) {
    public static TarefaResponseDto fromEntity(Tarefa tarefa){
        return new TarefaResponseDto(tarefa.getIdTarefa(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getDataDeAtualizacao(),
                tarefa.getStatus(),
                tarefa.getTempoEstimado(),
                tarefa.getTempoUtilizado());
    }
}
