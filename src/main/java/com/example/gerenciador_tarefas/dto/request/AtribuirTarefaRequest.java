package com.example.gerenciador_tarefas.dto.request;

public record AtribuirTarefaRequest(
        String idTarefa,
        String idUsuario
) {
}
