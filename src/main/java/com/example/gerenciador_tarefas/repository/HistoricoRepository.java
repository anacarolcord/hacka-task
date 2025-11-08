package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Historico;
import com.example.gerenciador_tarefas.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HistoricoRepository extends MongoRepository<Historico, String> {
    Optional<Historico> findByTarefa(Tarefa tarefa);
}
