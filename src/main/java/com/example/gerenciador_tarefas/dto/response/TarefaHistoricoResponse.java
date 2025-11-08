package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;

import java.time.LocalDateTime;

public record TarefaHistoricoResponse(
        String nomeTarefa,
        String nomeUsuario,
        StatusTarefa status,
        LocalDateTime data
) {
}
