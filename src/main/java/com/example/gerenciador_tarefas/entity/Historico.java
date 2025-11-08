package com.example.gerenciador_tarefas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "historico")
public class Historico {

    private Tarefa tarefa;

    private AtualizarCard statusHistorico;

    private Transferencia transferencia;

}
