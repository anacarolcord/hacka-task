package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TarefaRepository extends MongoRepository<Tarefa,Long> {
}
