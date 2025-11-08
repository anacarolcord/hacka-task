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
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public TarefaResponseDto salvarTarefa(TarefaRequestDto dados) {

        Tarefa tarefa = dados.toEntity();

        repository.save(tarefa);

        return TarefaResponseDto.fromEntity(tarefa);
    }

    @Transactional
    //método que o colaborador atualiza a tarefa(diferentes usuarios alteram partes diferentes de uma tarefa
    public TarefaResponseDto atualizarTarefaColaborador(TarefaRequestDto dados, String idTarefa) {
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(TarefaNaoEncontradaException::new);

        if (usuario.getAtivo() && usuario.getFerias()==null) {

            tarefa.setStatus(dados.status());
            tarefa.setTempoUtilizado(dados.tempoUtilizado());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);
            tarefa.setHistorico(historico);

            repository.save(tarefa);


        } else throw new AcessoNaoAutorizadoException();


        return TarefaResponseDto.fromEntity(tarefa);

    }

    public TarefaResponseDto atualizarTarefaGestor(TarefaRequestDto dados, String idTarefa) {
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        if (usuario.getAtivo() && usuario.getFerias()==null) {

            tarefa.setNome(dados.nome());
            tarefa.setDescricao(dados.descricao());
            tarefa.setStatus(dados.status());
            tarefa.setTempoEstimado(dados.tempoEstimado());
            tarefa.setUsuario(dados.usuario());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);

            repository.save(tarefa);

        }
        return TarefaResponseDto.fromEntity(tarefa);
    }



    //método que pesquisa todas as tarefas que existem
//método só pode ser feito por gestor e o gestor só pode pesquisar se estiver ativo e não estiver de férias
    public List<TarefaResponseDto> listarTodasGestor() {
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> todas = new ArrayList<>();

        if (usuario.getAtivo() && usuario.getFerias()==null) {

            todas = repository.findAll()
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());

        } else throw new AcessoNaoAutorizadoException();

        return todas;
    }

    //método retorna apenas as tarefas do usuario que esta fazendo a requisiçao
    public List<TarefaResponseDto> listarTodasUsuario() {

        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> tarefasPorUsuario = new ArrayList<>();

        if (usuario.getAtivo() && usuario.getFerias()==null) {

            tarefasPorUsuario = repository.findAllByUsuario(usuario)
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());


        } else throw new AcessoNaoAutorizadoException();

        return tarefasPorUsuario;
    }

    //método retorna lista de tarefas de um usuario especifico
    //método de gestor!!
    public List<TarefaResponseDto> listarTodasPeloIdUsuario( String idUsuario) {
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> tarefasPeloIdUsuario = new ArrayList<>();

        if (usuario.getAtivo() && usuario.getFerias()==null) {

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
        if (usuario.getAtivo() && usuario.getFerias()==null) {

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
    public TarefaResponseDto pegarTarefa( String idTarefa) {
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();


        Tarefa t = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        return atribuirTarefa(idTarefa, usuario.getIdUsuario(), usuario);
    }

    //método de transferir tarefas entre usuario
    @Transactional
    public void transferirTarefa(String idUsuariorecebe, String idUsuarioenvia) {

        Usuario usuarioEnvia = usuarioRepository.findById(idUsuarioenvia)
                .orElseThrow(() -> new UserNotFoundException(idUsuarioenvia));

        Usuario usuarioRecebe = usuarioRepository.findById(idUsuariorecebe)
                .orElseThrow(() -> new UserNotFoundException(idUsuariorecebe));

        if(usuarioEnvia.getCargo().equals(Cargo.GESTOR)){
            usuarioRecebe.setCargo(Cargo.COLABORADORRESPONSAVEL);
        }

        if(usuarioRecebe.getFerias() != null){
            throw new UsuarioInativoException();
        }

        //Usuario que envia deve estar de ferias
        //identifico as tarefas com status em andamento e status pendentes atribuidas a ele

        List<Tarefa> tarefasPendentesEmAndamento = new ArrayList<>();

        if (usuarioEnvia.getFerias()!=null) {

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
            repository.save(t);
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







