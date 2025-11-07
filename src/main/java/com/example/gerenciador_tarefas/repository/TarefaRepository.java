package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
