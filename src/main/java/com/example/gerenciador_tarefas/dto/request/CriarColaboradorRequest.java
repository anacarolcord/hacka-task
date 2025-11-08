package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;

public record CriarColaboradorRequest(
        String nome,
        String cpf,
        String email,
        String senha
) {

    public Usuario toEntity(String senha){

        return Usuario.builder()
                .nome(this.nome)
                .senha(senha)
                .cargo(Cargo.COLABORADOR)
                .ativo(true)
                .ferias(false)
                .cpf(this.cpf)
                .email(this.email)
                .build();
    }
}
