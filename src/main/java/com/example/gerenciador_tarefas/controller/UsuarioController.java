package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.CriarColaboradorRequest;
import com.example.gerenciador_tarefas.dto.request.UsuarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import com.example.gerenciador_tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarColaborador(@RequestBody @Valid CriarColaboradorRequest request){
        UsuarioResponseDTO response = usuarioService.criarColaborador(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){

        List<UsuarioResponseDTO> responseDTO = usuarioService.listarUsuario();
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<UsuarioResponseDTO>> pesquisaUsuarios(@RequestParam(required = false)String nome,
                                                                     @RequestParam(required = false)Cargo cargo,
                                                                     @RequestParam(required = false)String email,
                                                                     @RequestParam(required = false)Boolean ativo){

        List<UsuarioResponseDTO> responseDTO = usuarioService.pesquisaUsuarios(nome, cargo, email, ativo);
        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> deletarUsuario(@PathVariable("idUsuario") String idUsuario){

        UsuarioResponseDTO responseDTO = usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("/admin")
    public ResponseEntity <UsuarioGestorResponseDTO> criarGestor(@RequestBody UsuarioGestorRequestDTO dto){
        UsuarioGestorResponseDTO usariogestor=usuarioService.criarAdm(dto);
        return ResponseEntity.status(201).body(usariogestor);
    }





}
