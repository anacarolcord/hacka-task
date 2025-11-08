package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;
import java.time.LocalDate;

public record TarefaRequestDto (
        String nome,
        String descricao,
        LocalDate dataDeAtualizacao,
        StatusTarefa status,
        Duration tempoEstimado,
        Duration tempoUtilizado,
        Usuario usuario
){
    public Tarefa toEntity(){
        Tarefa tarefa = new Tarefa();

        tarefa.setNome(this.nome);
        tarefa.setDescricao(this.descricao);
        tarefa.setDataDeAtualizacao(this.dataDeAtualizacao);
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setTempoEstimado(this.tempoEstimado);
        tarefa.setTempoUtilizado(this.tempoUtilizado);

        return tarefa;
    }
}
