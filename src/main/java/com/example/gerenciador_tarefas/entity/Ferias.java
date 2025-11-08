package com.example.gerenciador_tarefas.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ferias {
    @Id
    private String idFerias;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String idUsuarioReceptor;

}

