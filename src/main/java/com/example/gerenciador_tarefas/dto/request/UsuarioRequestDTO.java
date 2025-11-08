package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Usuario;
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

        @NotBlank(message = "O cpf não pode ser nulo!")
        String cpf,

        @NotBlank(message = "O email não pode ser nulo!")
        String email,
        @NotBlank(message = "A senha não pode ser nulo!")
        String senha,
        Boolean ferias
) {

    public Usuario toEntity(String senha){

        Usuario usuario = new Usuario();

        usuario.setNome(this.nome);
        usuario.setCargo(this.cargo);
        usuario.setCpf(this.cpf);
        usuario.setEmail(this.email);
        usuario.setSenha(senha);
        usuario.setFerias(this.ferias);

        return usuario;

    }

}
