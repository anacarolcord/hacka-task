package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarCard {

    private LocalDateTime data;

    private StatusTarefa status;

    private Tarefa tarefa;

}
