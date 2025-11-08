package com.example.gerenciador_tarefas.config;

import com.example.gerenciador_tarefas.service.AssistenteIaService;
import com.example.gerenciador_tarefas.toll.AssistenteIaToll;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssistenteIaConfig {

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    @Bean
    public GoogleAiGeminiChatModel googleAiGeminiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(geminiApiKey)
                .modelName(geminiModel)
                .build();
    }

    @Bean
    public AssistenteIaService assistenteAiService(GoogleAiGeminiChatModel model, AssistenteIaToll assistenteIaToll) {
        return AiServices.builder(AssistenteIaService.class)
                .chatModel(model)
                .tools(assistenteIaToll)
                .build();
    }
}
