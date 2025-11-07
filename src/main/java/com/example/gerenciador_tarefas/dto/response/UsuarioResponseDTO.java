package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;

public record UsuarioResponseDTO(
        Long idUser,
        String nome,
        Cargo cargo,
        String cpf,
        String email,
        Boolean ativo,
        Boolean ferias
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getCargo(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getAtivo(),
                usuario.getFerias()
                );
    }
}
