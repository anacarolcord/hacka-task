package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")


public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarefa;

    @Column(length = 100)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    private LocalDate dataDeAtualizacao;

    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

    private Duration tempoEstimado;
    private Duration tempoUtilizado;
    private String idUser;

}
