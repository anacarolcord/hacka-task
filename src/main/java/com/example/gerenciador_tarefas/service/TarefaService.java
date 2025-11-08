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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

public TarefaResponseDto atualizarTarefaColaborador(TarefaRequestDto dados, String idTarefa, Usuario usuario){
    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(TarefaNaoEncontradaException::new);

    if( usuario.getAtivo() && !usuario.getFerias()) {
        tarefa.setStatus(dados.status());
        tarefa.setTempoUtilizado(dados.tempoUtilizado());
    }else throw new AcessoNaoAutorizadoException();



    return TarefaResponseDto.fromEntity(tarefa);

}

public TarefaResponseDto atualizarTarefaGestor(TarefaRequestDto dados, String idTarefa, Usuario usuario){
    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(()-> new TarefaNaoEncontradaException());

    if(usuario.getAtivo()
            && !usuario.getFerias()){

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(dados.status());
        tarefa.setTempoEstimado(dados.tempoEstimado());
        tarefa.setUsuario(dados.usuario());
    }

    return TarefaResponseDto.fromEntity(tarefa);
}

public TarefaResponseDto atualizaTarefaAdministrador(TarefaRequestDto dados, String idTarefa, Usuario usuario){

    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(()-> new TarefaNaoEncontradaException());



    if(usuario.getAtivo()
            && !usuario.getFerias()) {

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(dados.status());
        tarefa.setTempoEstimado(dados.tempoEstimado());
        tarefa.setUsuario(dados.usuario());
        tarefa.setTempoUtilizado(dados.tempoUtilizado());

    }

    return TarefaResponseDto.fromEntity(tarefa);
}

public List<TarefaResponseDto> listarTodas(Usuario usuario) {

    List<TarefaResponseDto> todas = new ArrayList<>();

    if (usuario.getAtivo()
            && !usuario.getFerias()) {

        todas = repository.findAll()
                .stream()
                .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                .collect(Collectors.toList());

    }else throw new AcessoNaoAutorizadoException();

    return todas;
}

public List<TarefaResponseDto> listarTarefaPorUsuario(Usuario usuario, String idUsuario){

    List <TarefaResponseDto> tarefasPorUsuario = new ArrayList<>();

    if(usuario.getAtivo() && !usuario.getFerias() ) {

         tarefasPorUsuario = repository.findAllByIdUsuario(idUsuario)
                .stream()
                .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                .collect(Collectors.toList());


    }




}



}






}
