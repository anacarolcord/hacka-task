package com.example.gerenciador_tarefas.dto.response;

import com.example.gerenciador_tarefas.entity.Comentario;

import java.time.LocalDateTime;

public record ComentarioResponse(
        String id,
        String mensagem,
        LocalDateTime dataComentario
) {
    public static ComentarioResponse fromEntity(Comentario comentario){
        return new ComentarioResponse(
                comentario.getId(),
                comentario.getMensagem(),
                comentario.getDataComentario()
        );
    }

}
