package com.example.gerenciador_tarefas.exception;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String cpf) {
        super("CPF: " + cpf + " ja existe no sistema");
    }
}
