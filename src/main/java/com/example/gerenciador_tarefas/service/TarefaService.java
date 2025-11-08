package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.exception.AcessoNaoAutorizadoException;
import com.example.gerenciador_tarefas.exception.TarefaNaoEncontradaException;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;


public TarefaResponseDto salvarTarefa(TarefaRequestDto dados, Usuario usuario){
    if(usuario.getCargo().equals(Cargo.COLABORADOR)){
        throw new AcessoNaoAutorizadoException();
    }
     Tarefa tarefa = dados.toEntity();

     repository.save(tarefa);

     return TarefaResponseDto.fromEntity(tarefa);
}

public TarefaResponseDto atualizarTarefa(TarefaRequestDto dados, Long idTarefa, Usuario usuario){
    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(TarefaNaoEncontradaException::new);

    if( usuario.getAtivo() && !usuario.getFerias() && usuario.getCargo().equals(Cargo.COLABORADOR)) {
        tarefa.setStatus(dados.status());
        tarefa.setTempoUtilizado(dados.tempoUtilizado());
    }

    if(usuario.getAtivo()
            && !usuario.getFerias()
            && (usuario.getCargo().equals(Cargo.GESTOR)
            || usuario.getCargo(Cargo.GESTORTEMPORARIO))){

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(dados.status());
        tarefa.setTempoEstimado(dados.tempoEstimado());
        tarefa.setUsuario(dados.usuario());
    }

    if(usuario.getAtivo()
            && !usuario.getFerias()
            && usuario.getCargo().equals(Cargo.ADMIN)){

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(dados.status());
        tarefa.setTempoEstimado(dados.tempoEstimado());
        tarefa.setUsuario(dados.usuario());
        tarefa.setTempoUtilizado(dados.tempoUtilizado());
    }

    return TarefaResponseDto.fromEntity(tarefa);

}

public List<TarefaResponseDto> listarTodas(Usuario usuario){
    if(usuario.getAtivo() && !usuario.getFerias() && usuario.getCargo().equals(Cargo.GESTOR)){
}






}
