package com.example.gerenciador_tarefas.dto.response;


import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;

import java.time.LocalDateTime;

public record FeriasResponse(
        String idFerias,
        String usuarioNome,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {
    public static FeriasResponse fromEntity(Ferias ferias, Usuario usuario){
        return new FeriasResponse(
                ferias.getIdFerias(),
                usuario.getNome(),
                ferias.getDataInicio(),
                ferias.getDataFim()
        );
    }
}
