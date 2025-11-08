package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoRepository extends MongoRepository<Historico, String> {
}
