package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Comentario;
import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.Duration;

public record AtualizarTarefaColaboradorRequest(
        StatusTarefa statusTarefa,
        Duration tempoUtilizado,
        Comentario comentario
) {
    public Tarefa toEntity(){
        Tarefa tarefa = new Tarefa();

        tarefa.setStatus(this.statusTarefa);
        tarefa.setTempoUtilizado(this.tempoUtilizado);
        tarefa.getComentarios().add(this.comentario);

        return tarefa;
    }
}
