package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.User;
import com.example.gerenciador_tarefas.entity.enums.Cargo;

public record UserResponseDTO(
        Long idUser,
        String nome,
        Cargo cargo,
        String cpf,

        String email,
        Boolean ativo
) {
    public static UserResponseDTO fromEntity(User user){
        return new UserResponseDTO(
                user.getIdUser(),
                user.getNome(),
                user.getCargo(),
                user.getCpf(),
                user.getEmail(),
                user.getAtivo()
                );
    }
}
