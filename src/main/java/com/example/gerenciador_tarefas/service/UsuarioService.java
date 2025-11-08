package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.exception.UserNotFoundException;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarColaborador(UsuarioRequestDTO request){
        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.senha());

        Usuario usuario = request.toEntity(senhaCriptografada);
        usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuario(){
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    public List<UsuarioResponseDTO> pesquisaUsuarios(String nome, Cargo cargo, String email, Boolean ativo){

        if (nome != null){
            return usuarioRepository.findByNome(nome)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (cargo != null){
            return usuarioRepository.findByCargo(cargo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (email != null){
            return usuarioRepository.findByEmail(email)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (ativo != null){
            return usuarioRepository.findByAtivo(ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
        
    }


    public UsuarioResponseDTO deletarUsuario(Long idUsuario){
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if(usuario.isPresent()){
            usuario.get().setAtivo(false);
            return UsuarioResponseDTO.fromEntity(usuario.get());
        }
        throw new UserNotFoundException(idUsuario);
    }
}
