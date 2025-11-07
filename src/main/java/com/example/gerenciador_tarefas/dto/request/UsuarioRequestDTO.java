package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.User;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome não pode ser nulo!")
        String nome,
        @NotNull(message = "O cargo não pode ser nulo!")
        Cargo cargo,
        @CPF
        @NotBlank(message = "O cpf não pode ser nulo!")
        String cpf,
        @Email
        @NotBlank(message = "O email não pode ser nulo!")
        String email,
        @NotBlank(message = "A senha não pode ser nulo!")
        String senha
) {

    public User toEntity(){

        User user = new User();

        user.setNome(this.nome);
        user.setCargo(this.cargo);
        user.setCpf(this.cpf);
        user.setEmail(this.email);
        user.setSenha(this.senha);

        return user;

    }

}
