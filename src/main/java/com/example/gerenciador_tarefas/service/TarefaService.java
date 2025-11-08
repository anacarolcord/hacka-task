package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.exception.AcessoNaoAutorizadoException;
import com.example.gerenciador_tarefas.exception.TarefaNaoEncontradaException;
import com.example.gerenciador_tarefas.exception.UserNotFoundException;
import com.example.gerenciador_tarefas.exception.UsuarioInativoException;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;


public TarefaResponseDto salvarTarefa(TarefaRequestDto dados){
     Tarefa tarefa = dados.toEntity();

     repository.save(tarefa);

     return TarefaResponseDto.fromEntity(tarefa);
}

public TarefaResponseDto atualizarTarefaColaborador(TarefaRequestDto dados, String idTarefa, Usuario usuario){
    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(TarefaNaoEncontradaException::new);

    if( usuario.getAtivo() && usuario.getFerias()==null) {
        tarefa.setStatus(dados.status());
        tarefa.setTempoUtilizado(dados.tempoUtilizado());
    }else throw new AcessoNaoAutorizadoException();

//mandar pro historico

    return TarefaResponseDto.fromEntity(tarefa);

}

public TarefaResponseDto atualizarTarefaGestor(TarefaRequestDto dados, String idTarefa, Usuario usuario){
    Tarefa tarefa = repository.findById(idTarefa)
            .orElseThrow(()-> new TarefaNaoEncontradaException());

    if(usuario.getAtivo()
            && usuario.getFerias()==null){

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(dados.status());
        tarefa.setTempoEstimado(dados.tempoEstimado());
        tarefa.setUsuario(dados.usuario());
    }
//mandar pro historico
    return TarefaResponseDto.fromEntity(tarefa);
}


//método que pesquisa todas as tarefas que existem
//método só pode ser feito por gestor e o gestor só pode pesquisar se estiver ativo e não estiver de férias
public List<TarefaResponseDto> listarTodasGestor(Usuario usuario) {

    List<TarefaResponseDto> todas = new ArrayList<>();

    if (usuario.getAtivo()
            && usuario.getFerias()==null) {

        todas = repository.findAll()
                .stream()
                .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                .collect(Collectors.toList());

    }else throw new AcessoNaoAutorizadoException();

    return todas;
}

//método retorna apenas as tarefas do usuario que esta fazendo a requisiçao
public List<TarefaResponseDto> listarTodasUsuario(Usuario usuario){

    List <TarefaResponseDto> tarefasPorUsuario = new ArrayList<>();

    if(usuario.getAtivo() && usuario.getFerias()==null ) {

         tarefasPorUsuario = repository.findAllByUsuario(usuario)
                .stream()
                .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                .collect(Collectors.toList());


    }else throw new AcessoNaoAutorizadoException();

    return tarefasPorUsuario;
}

//método retorna lista de tarefas de um usuario especifico
    //método de gestor!!
public List<TarefaResponseDto> listarTodasPeloIdUsuario(Usuario usuario, String idUsuario){

    List<TarefaResponseDto> tarefasPeloIdUsuario = new ArrayList<>();

    if (usuario.getAtivo() && usuario.getFerias()==null) {

        Usuario u = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UserNotFoundException(idUsuario));


        tarefasPeloIdUsuario = repository.findAllByUsuario(usuario)
                .stream()
                .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                .collect(Collectors.toList());

    }else throw new UsuarioInativoException();

    return tarefasPeloIdUsuario;
}


}







