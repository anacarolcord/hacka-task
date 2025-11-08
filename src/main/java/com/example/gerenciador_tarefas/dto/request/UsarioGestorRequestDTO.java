package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Usuario;
import jakarta.validation.constraints.Email;

public record UsarioGestorRequestDTO(String nome, String email, String senha) {

    public  Usuario toEntity(String senha){
        Usuario usuarioadm = new Usuario();

                usuarioadm.setNome(this.nome);
                usuarioadm.setEmail(this.email);
                usuarioadm.setSenha(senha);

                return usuarioadm;

    }


}
