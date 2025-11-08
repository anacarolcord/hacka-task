package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TarefaRepository extends MongoRepository<Tarefa,String> {

    List<Tarefa> findAllByUsuario(Usuario usuario);
    List<Tarefa> findByStatusIn(List<StatusTarefa> status);
}
