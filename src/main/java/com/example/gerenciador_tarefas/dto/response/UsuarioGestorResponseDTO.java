package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;

public record UsuarioGestorResponseDTO (
        String idUser,
        String nome,
        Cargo cargo,
        String cpf,
        String email,
        Boolean ativo
){

    public static UsuarioGestorResponseDTO fromEntity(Usuario usuario){
        return new UsuarioGestorResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getCargo(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getAtivo()
        );
    }
}
