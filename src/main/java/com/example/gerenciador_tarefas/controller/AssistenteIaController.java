package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.service.AssistenteIaService;
import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assistente")
public class AssistenteIaController {

    private final AssistenteIaService assistenteIaService;

    public AssistenteIaController(AssistenteIaService assistenteIaService) {
        this.assistenteIaService = assistenteIaService;
    }

    @PostMapping
    public String askAssistant(@RequestBody String userMessage) {
        Result<String> result = assistenteIaService.handleRequest(userMessage);
        return result.content();
    }
}
