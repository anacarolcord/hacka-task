package com.example.gerenciador_tarefas.toll;

import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.StatusTarefa;
import com.example.gerenciador_tarefas.service.TarefaService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssistenteIaToll{

    private final TarefaService tarefaService;

    public AssistenteIaToll(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Tool("Retorna todas as tarefas ativas de um usuário, ordenadas por prioridade e data.")
    public List<Tarefa> listarTarefasAtivas(Usuario usuario) {
        // Usando método direto do repository (não DTO) para simplificar
        List<Tarefa> tarefas = tarefaService.listarTodasUsuarioSimples(usuario);
        return tarefaService.ordenarTarefasPorPrioridade(tarefas);
    }

    @Tool("Gera resumo do histórico de tarefas de um usuário agrupado por status.")
    public String gerarHistorico(Usuario usuario) {
        List<Tarefa> tarefas = tarefaService.listarTodasUsuarioSimples(usuario);

        long pendentes = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.PENDENTE).count();
        long andamento = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.EM_ANDAMENTO).count();
        long concluidas = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA).count();
        long canceladas = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.CANCELADA).count();

        return "Histórico de " + usuario.getNome() + ":\n" +
                "Pendentes: " + pendentes + "\n" +
                "Em andamento: " + andamento + "\n" +
                "Concluídas: " + concluidas + "\n" +
                "Canceladas: " + canceladas;
    }
}