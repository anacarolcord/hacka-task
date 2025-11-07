package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;
import java.time.LocalDate;


public record TarefaComUsuarioResponseDto(
        Long idTarefa,
        String nome,
        String descricao,
        LocalDate dataDeAtualizacao,
        StatusTarefa status,
        Duration tempoEstimado,
        Duration tempoUtilizado,
        Usuario usuario

) {
    public static TarefaComUsuarioResponseDto fromEntity(Tarefa tarefa){
        return new TarefaComUsuarioResponseDto(
                tarefa.getIdTarefa(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getDataDeAtualizacao(),
                tarefa.getStatus(),
                tarefa.getTempoEstimado(),
                tarefa.getTempoUtilizado(),
                tarefa.getUsuario());

    }
}


