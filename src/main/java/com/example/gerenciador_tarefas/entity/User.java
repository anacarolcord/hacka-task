package com.example.gerenciador_tarefas.entity;

import com.example.gerenciador_tarefas.entity.enums.Cargo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    private String email;
    private String cpf;
    private String senha;
    private Boolean ativo;

}
