package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.CriarColaboradorRequest;
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

    public UsuarioResponseDTO criarColaborador(CriarColaboradorRequest request){
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

    public List<UsuarioResponseDTO> pesquisaUsuarios(String idUsuario, String nome, Cargo cargo, String email, Boolean ativo){

        if (idUsuario != null){
            return usuarioRepository.findById(idUsuario)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

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


    public UsuarioResponseDTO deletarUsuario(String idUsuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        throw new UserNotFoundException(idUsuario);
    }



    public UsuarioResponseDTO atualizarSenha(String idUsuario, UsuarioRequestDTO request){
        final Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setSenha(new BCryptPasswordEncoder().encode(request.senha()));
            usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        throw new UserNotFoundException(idUsuario);
    }

    public UsuarioResponseDTO atualizarCargo(String idUsuario, UsuarioRequestDTO request){
        final Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setCargo(request.cargo());
            usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        throw new UserNotFoundException(idUsuario);
    }

    public UsuarioResponseDTO atualizarFerias(String idUsuario, UsuarioRequestDTO request){
        final Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setFerias(request.ferias());
            usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        throw new UserNotFoundException(idUsuario);
    }




}
