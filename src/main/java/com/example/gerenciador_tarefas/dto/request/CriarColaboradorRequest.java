package com.example.gerenciador_tarefas.dto.request;

public record CriarColaboradorRequest(
        String nome,
        String cpf,
        String email,
        String senha
) {
}
