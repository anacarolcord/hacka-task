package com.example.gerenciador_tarefas.dto.request;

import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;

import java.time.LocalDateTime;

public record FeriasRequestDTO(
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {
    public Ferias toEntity(){
        Ferias ferias = new Ferias();

        ferias.setDataInicio(this.dataInicio);
        ferias.setDataFim(this.dataFim);

        return ferias;
    }


}
