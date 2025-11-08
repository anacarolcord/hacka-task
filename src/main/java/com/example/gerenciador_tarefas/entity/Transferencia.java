package com.example.gerenciador_tarefas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia {

    private Usuario emissor;

    private Usuario receptor;

    private String motivo;

}
