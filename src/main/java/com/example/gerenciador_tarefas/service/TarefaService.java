package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.CriarTarefaRequest;
import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.CriarTarefaResponse;
import com.example.gerenciador_tarefas.dto.response.HistoricoUsuarioDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.entity.*;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import com.example.gerenciador_tarefas.exception.*;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;

    public CriarTarefaResponse salvarTarefa(CriarTarefaRequest dados) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Iniciando criacao de tarefa, mapeando Tarefa...");
        Tarefa tarefa = dados.toEntity();

        log.info("Tarefa mapeada, persistindo no DB...");
        repository.save(tarefa);

        log.info("Tarefa persistida, finalizando salvamento");

        return CriarTarefaResponse.fromEntity(tarefa);
    }

    @Transactional
    //método que o colaborador atualiza a tarefa(diferentes usuarios alteram partes diferentes de uma tarefa
    public TarefaResponseDto atualizarTarefaColaborador(TarefaRequestDto dados, String idTarefa) {
        //pega o usuario que esta logado
        log.info("Iniciando atualizacao de tarefa pelo Colaborador, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Usuario verificado, buscando tarefa no DB...");
        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(TarefaNaoEncontradaException::new);

        log.info("Tarefa encontrada, validando tarefa...");
        if (!dados.status().equals(StatusTarefa.CONCLUIDA) && dados.tempoEstimado().isPositive()) {
            throw new ErroAtualizarTarefaException();
        }

        log.info("Tarefa validada, verificando se usuario esta ativo...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, mapeando tarefa e historico...");
            tarefa.setStatus(dados.status());
            tarefa.setTempoUtilizado(dados.tempoUtilizado());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);
            tarefa.setHistorico(historico);

            log.info("Tarefa e historico mapeados, persistindo no DB...");
            repository.save(tarefa);

            log.info("Tarefa persistida, finalizando atualizacao");
        } else {
            log.error("Usuario de cpf {} nao esta ativo", usuario.getCpf());
            throw new AcessoNaoAutorizadoException();
        }

        return TarefaResponseDto.fromEntity(tarefa);
    }

    //método que o gestor atualiza a tarefa(diferentes usuarios alteram partes diferentes de uma tarefa
    @Transactional
    public TarefaResponseDto atualizarTarefaGestor(TarefaRequestDto dados, String idTarefa) {
        //pega o usuario que esta logado
        log.info("Iniciando atualizacao de tarefa pelo gestor, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Usuario verificado, buscando tarefa no DB...");
        Tarefa tarefa = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        log.info("Tarefa encontrada, validando usuario...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, mapeando tarefa e historico...");
            tarefa.setNome(dados.nome());
            tarefa.setDescricao(dados.descricao());
            tarefa.setStatus(dados.status());
            tarefa.setTempoEstimado(dados.tempoEstimado());

            AtualizarCard atualizarCard = new AtualizarCard();
            atualizarCard.setData(LocalDateTime.now());
            atualizarCard.setStatus(tarefa.getStatus());

            Historico historico = new Historico();

            historico.setStatusHistorico(atualizarCard);

            log.info("Tarefa e historico mapeados, persistindo no DB...");
            if (usuario.getTarefas() == null) {
                usuario.setTarefas(List.of(tarefa));
            } else {
                List<Tarefa> tarefas = usuario.getTarefas();
                tarefas.add(tarefa);
                usuario.setTarefas(tarefas);
            }

            repository.save(tarefa);
            usuarioRepository.save(usuario);
            log.info("Usuario e tarefas persistidos, finalizando metodo");
        }
        return TarefaResponseDto.fromEntity(tarefa);
    }


    //método que pesquisa todas as tarefas que existem
//método só pode ser feito por gestor e o gestor só pode pesquisar se estiver ativo e não estiver de férias
    public List<TarefaResponseDto> listarTodasGestor() {
        //pega o usuario que esta logado
        log.info("Iniciando listagem das tarefas, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> todas = new ArrayList<>();

        log.info("Usuario veficado, validando usuario...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, buscando e mapeando tarefas...");
            todas = repository.findAll()
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());

        } else {
            log.error("Usuario inativo");
            throw new AcessoNaoAutorizadoException();
        }

        log.info("Tarefas encontradas e mapeadas, finalizando listagem");
        return todas;
    }

    //método retorna apenas as tarefas do usuario que esta fazendo a requisiçao
    public List<TarefaResponseDto> listarTodasUsuario() {
        log.info("Iniciando listagem das tarefas do usuario, verificando usuario da requisicao...");
        //pega o usuario que esta logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> tarefasPorUsuario = new ArrayList<>();

        log.info("Usuario verificado, validando usuario...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, buscando e mapeando tarefas...");
            tarefasPorUsuario = repository.findAllByUsuarioCpf(usuario.getCpf())
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());


        } else {
            log.error("Usuario inativo");
            throw new AcessoNaoAutorizadoException();
        }

        log.info("Tarefas encontradas e mapeadas, finalizando listagem");
        return tarefasPorUsuario;
    }

    //método retorna lista de tarefas de um usuario especifico
    //método de gestor!!
    public List<TarefaResponseDto> listarTodasPeloIdUsuario(String idUsuario) {
        //pega o usuario que esta logado
        log.info("Iniciando listagem de tarefas de usuario especifico, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<TarefaResponseDto> tarefasPeloIdUsuario = new ArrayList<>();

        log.info("Usuario verificado, validando usuario...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, buscando no DB...");
            Usuario u = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            log.info("Usuario encontrado, buscando e mapeando tarefas...");
            tarefasPeloIdUsuario = repository.findAllByUsuarioCpf(usuario.getCpf())
                    .stream()
                    .map(tarefa -> TarefaResponseDto.fromEntity(tarefa))
                    .collect(Collectors.toList());

        } else {
            log.error("Usuario inativo");
            throw new UsuarioInativoException();
        }

        log.info("Tarefas encontradas e mapeadas, finalizando listagem");
        return tarefasPeloIdUsuario;
    }

    //Método gestor atribui tarefa para um usuario específico
    @Transactional
    public TarefaResponseDto atribuirTarefa(String idTarefa, String idUsuario) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();
        log.info("Iniciando atribuicao de tarefa para usuario, validando usuario...");
        if (usuario.getAtivo() && usuario.getFerias() == null) {

            log.info("Usuario validado, buscando no DB...");
            Usuario u = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            log.info("Usuario encontrado, buscando tarefa...");
            Tarefa t = repository.findById(idTarefa)
                    .orElseThrow(() -> new TarefaNaoEncontradaException());

            log.info("Tarefa encontrada, atualizando e persistindo no DB...");
            u.getTarefas().add(t);
            t.setDataDeAtualizacao(LocalDate.now());
            t.setUsuarioCpf(u.getCpf());

            repository.save(t);
            usuarioRepository.save(u);
            log.info("Tarefa persistida no DB, finalizando atribuicao");
            return TarefaResponseDto.fromEntity(t);
        } else {
            log.error("Usuario inativo");
            throw new UsuarioInativoException();
        }
    }

    //método usuário atribui uma tarefa a si mesmo
    public TarefaResponseDto pegarTarefa(String idTarefa) {
        //pega o usuario que esta logado
        log.info("Iniciando auto atribuicao de tarefa, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Usuario verificado, buscando tarefa no DB...");
        Tarefa t = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        log.info("Tarefa encontrada, chamando metodo de atribuicao de tarefa...");
        return atribuirTarefa(idTarefa, usuario.getIdUsuario());
    }

    //método de transferir tarefas entre usuario
    @Transactional
    public void transferirTarefa(String idUsuariorecebe, String idUsuarioenvia, String motivo) {
        log.info("Iniciando transferencia de tarefas, buscando usuarioEnvia...");
        Usuario usuarioEnvia = usuarioRepository.findById(idUsuarioenvia)
                .orElseThrow(() -> new UserNotFoundException(idUsuarioenvia));

        log.info("UsuarioEnvia encontrado, buscando usuarioRecebe...");
        Usuario usuarioRecebe = usuarioRepository.findById(idUsuariorecebe)
                .orElseThrow(() -> new UserNotFoundException(idUsuariorecebe));

        log.info("UsuarioRecebe encontrado, validando se o receptor esta de ferias...");
        if (usuarioRecebe.getFerias() != null) {
            log.error("UsuarioRecebe de ferias");
            throw new UsuarioInativoException();
        }

        log.info("UsuarioRecebe verificado, verificando cargos dos usuarios...");
        if (usuarioEnvia.getCargo().equals(Cargo.GESTOR)) {
            usuarioRecebe.setCargo(Cargo.COLABORADORRESPONSAVEL);
        }
        if (usuarioEnvia.getCargo().equals(Cargo.COLABORADORRESPONSAVEL) && usuarioRecebe.getCargo().equals(Cargo.GESTOR)) {
            usuarioEnvia.setCargo(Cargo.COLABORADOR);
        }


        //Usuario que envia deve estar de ferias
        //identifico as tarefas com status em andamento e status pendentes atribuidas a ele

        List<Tarefa> tarefasPendentesEmAndamento = new ArrayList<>();

        log.info("Cargos verificados, validando se o emissor entrara de ferias...");
        if (usuarioEnvia.getFerias() != null) {

            log.info("Emissor entrara de ferias, atribuindo e mapeando tarefas...");
            tarefasPendentesEmAndamento = usuarioEnvia.getTarefas()
                    .stream()
                    .filter(tarefa -> tarefa.getStatus().equals(StatusTarefa.EM_ANDAMENTO) || tarefa.getStatus().equals(StatusTarefa.PENDENTE))
                    .toList();

            usuarioEnvia.setFerias(null);
        } else {
            log.error("Usuario inativo");
            throw new AcessoNaoAutorizadoException();
        }

        //registrar no historico
        log.info("Tarefas mapeadas, atualizando e persistindo historico...");
        Transferencia transferencia = new Transferencia();

        transferencia.setEmissorCpf(usuarioEnvia.getCpf());
        transferencia.setReceptorCpf(usuarioRecebe.getCpf());
        transferencia.setMotivo(motivo);

        Historico historico = new Historico();

        historico.setTransferencia(transferencia);
        List<Tarefa> tarefasRecebe = usuarioRecebe.getTarefas();

        for (Tarefa t : tarefasPendentesEmAndamento) {
            t.setHistorico(historico);
            tarefasRecebe.add(t);
            repository.save(t);
        }
        log.info("Historicos atualizados e persistidos, salvando usuarios...");

        usuarioRecebe.setTarefas(tarefasPendentesEmAndamento);

        //salvar alteracoes

        usuarioRepository.save(usuarioRecebe);
        usuarioRepository.save(usuarioEnvia);
        log.info("Usuarios persistidos, finalizando transferencia de tarefa");
    }

    //método soft delete deixa a tarefa com status cancelada
    public TarefaResponseDto deletarTarefa(String idTarefa) {
        log.info("Iniciando delecao de tarefa, buscando tarefa...");
        Tarefa t = repository.findById(idTarefa)
                .orElseThrow(() -> new TarefaNaoEncontradaException());

        log.info("Tarefa encontrada, cancelando e persistindo tarefa...");
        t.setStatus(StatusTarefa.CANCELADA);
        repository.save(t);

        log.info("Tarefa persistida, finalizando delecao");
        return TarefaResponseDto.fromEntity(t);
    }

    //método que lista tarefas com status pendentes e em andamento
    public List<TarefaResponseDto> listarTarefasPendentesEmAndamento() {
        log.info("Iniciando listagem de tarefas nao concluidas nem canceladas...");
        List<StatusTarefa> statusAtivos = Arrays.asList(
                StatusTarefa.PENDENTE,
                StatusTarefa.EM_ANDAMENTO
        );

        log.info("Retornando tarefas");
        return repository.findByStatusIn(statusAtivos)
                .stream()
                .map(TarefaResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    //método que conta POR USUARIO quantas tarefas estão em cada status
    public HistoricoUsuarioDto gerarHistoricoPorUsuario(String usuarioId) {
        log.info("Iniciando geracao de historio por usuario, buscando usuario no DB...");
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UserNotFoundException(usuarioId));

        log.info("Usuario encontrada, buscando tarefas...");
        var tarefas = repository.findAllByUsuarioCpf(usuario.getCpf());

        log.info("Tarefas encontradas, calculando por status...");
        long pendentes = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.PENDENTE).count();
        long andamento = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.EM_ANDAMENTO).count();
        long concluidas = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA).count();
        long canceladas = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.CANCELADA).count();

        log.info("Calculo completo, finalizando historico");
        return new HistoricoUsuarioDto(
                usuario.getNome(),
                pendentes,
                andamento,
                concluidas,
                canceladas
        );
    }

}








