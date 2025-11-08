package com.example.gerenciador_tarefas.dto.response;

public record HistoricoUsuarioDto(
        String nomeUsuario,
        long pendentes,
        long emAndamento,
        long concluidas,
        long canceladas
) {
}
