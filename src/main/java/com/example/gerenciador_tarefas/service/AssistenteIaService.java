package com.example.gerenciador_tarefas.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AssistenteIaService {

    @SystemMessage("""
        Você é um assistente de tarefas, útil e direto.
        Só responda perguntas sobre tarefas de usuários.
        Se o usuário perguntar sobre tarefas ativas ou prioridade, use listarTarefasAtivas.
        Se perguntar sobre resumo ou histórico, use gerarHistorico.
    """)
    Result<String> handleRequest(@UserMessage String userMessage);
}


//💡 Explicação:
//
//@AiService → Marca a interface como um serviço de IA gerenciado pelo LangChain4j/Spring.
//
//@SystemMessage → Define as instruções do assistente (“role” do sistema). Aqui você detalha como ele deve se comportar: tarefas, prioridades, status, recomendações.
//
//@UserMessage → Cada mensagem do usuário que você envia será processada pelo modelo Gemini.
//
//O retorno é um Result<String>, que vai conter a resposta do modelo.