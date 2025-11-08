package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Comentario;

import java.time.LocalDateTime;

public record ComentarioRequest(
        String mensagem,
        LocalDateTime dataComentario,
        String tarefaId


) {
    public Comentario toEntity(String nome){
        Comentario comentario = new Comentario();

        comentario.setMensagem(this.mensagem);
        comentario.setUsuario(nome);
        comentario.setDataComentario(this.dataComentario);


        return comentario;
    }
}
