package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "historico")
public class Historico {

    private Map<Tarefa, LocalDateTime> statusHistorico;

    private Transferencia transferencia;
}
