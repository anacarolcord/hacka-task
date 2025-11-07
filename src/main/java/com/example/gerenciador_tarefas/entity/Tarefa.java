package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDate;

@Document(collation = "tarefa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarefa {
    @Id
    private Long idTarefa;

    private String nome;

    private String descricao;

    private LocalDate dataDeAtualizacao;

    private StatusTarefa status;

    private Duration tempoEstimado;
    private Duration tempoUtilizado;

    private Usuario usuario;

}
