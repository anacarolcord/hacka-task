package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;

public TarefaResponseDto salvar(TarefaRequestDto dados){
    Tarefa tarefa = new Tarefa();

    tarefa.setNome(dados.getNome());
    tarefa.setDescricao
}

}
