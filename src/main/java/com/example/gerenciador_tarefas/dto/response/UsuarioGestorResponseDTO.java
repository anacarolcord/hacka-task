package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Usuario;

public record UsuarioGestorResponseDTO (String nome, String email){

    public static UsuarioGestorResponseDTO fromEntity(Usuario usuario){
        return new UsuarioGestorResponseDTO(
                usuario.getNome(),
                usuario.getNome()
        );
    }
}
