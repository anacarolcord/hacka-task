package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
