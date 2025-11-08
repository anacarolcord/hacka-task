package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.CriarColaboradorRequest;
import com.example.gerenciador_tarefas.dto.request.UsuarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.exception.CpfDuplicadoException;
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
        if(usuarioRepository.findByCpf(request.cpf()) != null){
            throw new CpfDuplicadoException(request.cpf());
        }
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

    public Object pesquisaUsuarios(String idUsuario, String nome, Cargo cargo, String email, Boolean ativo) {

        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            return UsuarioResponseDTO.fromEntity(usuario);
        }

        if (email != null) {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));

            return UsuarioResponseDTO.fromEntity(usuario);
        }

        if (nome != null && cargo != null && ativo != null){

            return usuarioRepository.findByNomeAndCargoAndAtivo(nome, cargo, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();

        }

        if (nome != null && cargo != null){

            return usuarioRepository.findByNomeAndCargo(nome, cargo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (nome != null && ativo != null){

            return usuarioRepository.findByNomeAndAtivo(nome, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (cargo != null && ativo != null){

            return usuarioRepository.findByCargoAndAtivo(cargo, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();

        }



        if (nome != null) {
            return usuarioRepository.findByNome(nome)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        if (cargo != null) {
            return usuarioRepository.findByCargo(cargo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }



        if (ativo != null) {
            return usuarioRepository.findByAtivo(ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        return List.of(); // fallback de segurança
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


    public UsuarioGestorResponseDTO criarGestor(UsuarioGestorRequestDTO request){
        if(usuarioRepository.findByCpf(request.cpf()) != null){
            throw new CpfDuplicadoException(request.cpf());
        }
        String senha= new BCryptPasswordEncoder().encode(request.senha());
        Usuario usuarioadm = request.toEntity(senha);
        usuarioRepository.save((usuarioadm));

        return UsuarioGestorResponseDTO.fromEntity(usuarioadm);
    }


}
