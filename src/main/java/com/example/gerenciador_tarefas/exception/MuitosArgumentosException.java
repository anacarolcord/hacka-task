package com.example.gerenciador_tarefas.exception;

public class MuitosArgumentosException extends RuntimeException {
    public MuitosArgumentosException() {
        super("É permitido buscar apenas um atributo por vez!");
    }
}
