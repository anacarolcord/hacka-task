package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;

public record CriarTarefaResponse (
        String id,
        String nome,
        String descricao,
        StatusTarefa status,
        Duration tempoEstimado
)
{
    public static CriarTarefaResponse fromEntity(Tarefa tarefa){
        return new CriarTarefaResponse(tarefa.getIdTarefa(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getTempoEstimado());
    }
}
