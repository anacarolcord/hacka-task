package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.UsarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO request){
        Usuario usuario = request.toEntity();
        usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuario(UsuarioRequestDTO request){
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    public UsuarioGestorResponseDTO criarAdm(UsarioGestorRequestDTO request){
        String senha= new BCryptPasswordEncoder().encode(request.senha());
        Usuario usuarioadm = request.toEntity(senha);
        usuarioRepository.save((usuarioadm));

        return UsuarioGestorResponseDTO.fromEntity(usuarioadm);
    }

    //public UsuarioRequestDTO criarAdmin(UsuarioRequestDTO dto)
}
