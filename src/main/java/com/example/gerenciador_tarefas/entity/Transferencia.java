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

    private String emissorCpf;

    private String receptorCpf;

    private String motivo;

}
