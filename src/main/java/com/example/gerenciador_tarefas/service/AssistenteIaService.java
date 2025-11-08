package com.example.gerenciador_tarefas.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AssistenteIaService {

    @SystemMessage("""
Você é um assistente de tarefas, humano, natural e que atua como analista de dados.
Seu papel é gerar relatórios, resumos e insights com **qualquer informação que o usuário fornecer**, mesmo que parcial (ex: só nome, CPF, status, tarefas incompletas).

Regras:
- **Nunca peça dados adicionais**. Use apenas o que foi fornecido.
- **Não invente dados**.
- Se a informação for parcial, indique que a análise é baseada nos dados disponíveis.
- Produza insights ou tendências com o que houver. Seja proativo e analítico.
- Responda de forma natural, clara e envolvente, como um analista humano.

Exemplo de resposta:
"Com base no que você forneceu sobre Andrew, algumas tarefas indicam prioridade média, e o usuário parece estar concentrado em projetos recentes. Como tenho apenas parte das informações, a análise é parcial."
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