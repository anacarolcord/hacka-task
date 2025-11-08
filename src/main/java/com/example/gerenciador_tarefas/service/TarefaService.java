package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.entity.*;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import com.example.gerenciador_tarefas.exception.AcessoNaoAutorizadoException;
import com.example.gerenciador_tarefas.exception.TarefaNaoEncontradaException;
import com.example.gerenciador_tarefas.exception.UserNotFoundException;
import com.example.gerenciador_tarefas.exception.UsuarioInativoException;
import com.example.gerenciador_tarefas.repository.HistoricoRepository;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoRepository historicoRepository;


    public TarefaResponseDto salvarTarefa(TarefaRequestDto dados, Usuario usuario) {
        if (usuario.getCargo().equals(Cargo.COLABORADOR)) {
            throw new AcessoNaoAutorizadoException();
        }
        Tarefa tarefa = dados.toEntity();

        repository.save(tarefa);

        return TarefaResponseDto.fromEntity(tarefa);
    }

    @Transactional
    public TarefaResponseDto atualizarTarefaColaborador(TarefaRequestDto dados, String idTarefa, Usuario usuario) {
        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(TarefaNaoEncontradaException::new);

        if (usuario.getAtivo() && !usuario.getFerias()) {

            tarefa.setStatus(dados.status());
            tarefa.setTempoUtilizado(dados.tempoUtilizado());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setTarefa(tarefa);
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);

            historicoRepository.save(historico);

        } else throw new AcessoNaoAutorizadoException();


        return TarefaResponseDto.fromEntity(tarefa);

    }

    public TarefaResponseDto atualizarTarefaGestor(TarefaRequestDto dados, String idTarefa, Usuario usuario) {
        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        if (usuario.getAtivo()
                && !usuario.getFerias()) {

            tarefa.setNome(dados.nome());
            tarefa.setDescricao(dados.descricao());
            tarefa.setStatus(dados.status());
            tarefa.setTempoEstimado(dados.tempoEstimado());
            tarefa.setUsuario(dados.usuario());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setTarefa(tarefa);
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);

            historicoRepository.save(historico);

        }
//mandar pro historico
        return TarefaResponseDto.fromEntity(tarefa);
    }

    public TarefaResponseDto atualizaTarefaAdministrador(TarefaRequestDto dados, String idTarefa, Usuario usuario) {

        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());


        if (usuario.getAtivo()
                && !usuario.getFerias()) {

            tarefa.setNome(dados.nome());
            tarefa.setDescricao(dados.descricao());
            tarefa.setStatus(dados.status());
            tarefa.setTempoEstimado(dados.tempoEstimado());
            tarefa.setUsuario(dados.usuario());
            tarefa.setTempoUtilizado(dados.tempoUtilizado());

            AtualizarCard atualizarCard = new AtualizarCard();
            Historico historico = new Historico();

            historico.setTarefa(tarefa);
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            historico.setStatusHistorico(atualizarCard);

            historicoRepository.save(historico);

        }

        //mandar pro historico

        return TarefaResponseDto.fromEntity(tarefa);
    }

    //método que pesquisa todas as tarefas que existem
//método só pode ser feito por gestor e o gestor só pode pesquisar se estiver ativo e não estiver de férias
    public List<TarefaResponseDto> listarTodasGestor(Usuario usuario) {

        List<TarefaResponseDto> todas = new ArrayList<>();

        if (usuario.getAtivo()
                && !usuario.getFerias()) {

            todas = repository.findAll()
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());

        } else throw new AcessoNaoAutorizadoException();

        return todas;
    }

    //método retorna apenas as tarefas do usuario que esta fazendo a requisiçao
    public List<TarefaResponseDto> listarTodasUsuario(Usuario usuario) {

        List<TarefaResponseDto> tarefasPorUsuario = new ArrayList<>();

        if (usuario.getAtivo() && !usuario.getFerias()) {

            tarefasPorUsuario = repository.findAllByUsuario(usuario)
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());


        } else throw new AcessoNaoAutorizadoException();

        return tarefasPorUsuario;
    }

    //método retorna lista de tarefas de um usuario especifico
    //método de gestor!!
    public List<TarefaResponseDto> listarTodasPeloIdUsuario(Usuario usuario, String idUsuario) {

        List<TarefaResponseDto> tarefasPeloIdUsuario = new ArrayList<>();

        if (usuario.getAtivo() && !usuario.getFerias()) {

            Usuario u = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));


            tarefasPeloIdUsuario = repository.findAllByUsuario(usuario)
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());

        } else throw new UsuarioInativoException();

        return tarefasPeloIdUsuario;
    }

    //Método gestor atribui tarefa para um usuario específico
    public TarefaResponseDto atribuirTarefa(String idTarefa, String idUsuario, Usuario usuario) {

        Tarefa atualizada = null;
        if (usuario.getAtivo() && !usuario.getFerias()) {

            Usuario u = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            Tarefa t = repository.findById(idTarefa)
                    .orElseThrow(() -> new TarefaNaoEncontradaException());

            u.getTarefas().add(t);

            atualizada = repository.save(t);

        } else throw new UsuarioInativoException();

        return TarefaResponseDto.fromEntity(atualizada);
    }

    //método usuário atribui uma tarefa a si mesmo
    public TarefaResponseDto pegarTarefa(Usuario usuario, String idTarefa) {



        Tarefa t = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        return atribuirTarefa(idTarefa, usuario.getIdUsuario(), usuario);
    }

    //método de transferir tarefas para usuario
    public void transferirTarefa(String idUsuariorecebe, String idUsuarioenvia) {

        Usuario usuarioEnvia = usuarioRepository.findById(idUsuarioenvia)
                .orElseThrow(() -> new UserNotFoundException(idUsuarioenvia));

        Usuario usuarioRecebe = usuarioRepository.findById(idUsuariorecebe)
                .orElseThrow(() -> new UserNotFoundException(idUsuariorecebe));

        if(usuarioRecebe.getFerias() != null){
            throw new UsuarioInativoException();
        }

        //Usuario que envia deve estar de ferias
        //identifico as tarefas com status em andamento e status pendentes atribuidas a ele

        List<Tarefa> tarefasPendentesEmAndamento = new ArrayList<>();

        if (usuarioEnvia.getFerias()) {

            tarefasPendentesEmAndamento = usuarioEnvia.getTarefas()
                    .stream()
                    .filter(tarefa -> tarefa.getStatus().equals(StatusTarefa.EM_ANDAMENTO) || tarefa.getStatus().equals(StatusTarefa.PENDENTE))
                    .toList();

        } else throw new AcessoNaoAutorizadoException();

        //registrar no historico

        Transferencia transferencia = new Transferencia();

        transferencia.setEmissor(usuarioEnvia);
        transferencia.setReceptor(usuarioRecebe);
        transferencia.setMotivo("Férias");

        Historico historico = new Historico();

        historico.setTransferencia(transferencia);
        List<Tarefa> tarefasRecebe = usuarioRecebe.getTarefas();

        for (Tarefa t : tarefasPendentesEmAndamento) {
            t.setHistorico(historico);
            tarefasRecebe.add(t);
        }

        usuarioRecebe.setTarefas(tarefasPendentesEmAndamento);

        //salvar alteracoes

        usuarioRepository.save(usuarioRecebe);
        usuarioRepository.save(usuarioEnvia);


    }

    //método soft delete deixa a tarefa com status cancelada
    public TarefaResponseDto deletarTarefa(String idTarefa) {
        Tarefa t = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        t.setStatus(StatusTarefa.CANCELADA);

        //registrar historico

        return TarefaResponseDto.fromEntity(t);
    }


}







