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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarColaborador(CriarColaboradorRequest request){
        log.info("Iniciando a criação de um usuário!");
        if(usuarioRepository.findByCpf(request.cpf()) != null){
            log.error("CPF: {} ja está cadastrado", request.cpf());
            throw new CpfDuplicadoException(request.cpf());
        }
        log.info("Criptografando a senha!");
        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.senha());

        log.info("Salvando usuário no banco de dados!");
        Usuario usuario = request.toEntity(senhaCriptografada);
        usuarioRepository.save(usuario);

        log.info("Retornando usuário!");
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuario(){
        log.info("Iniciando listagem de todos os usuários!");
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    public Object pesquisaUsuarios(String idUsuario, String nome, Cargo cargo, String email, Boolean ativo) {

        log.info("Retornando um usuario pelo id!");
        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            return UsuarioResponseDTO.fromEntity(usuario);
        }

        log.info("Retornando um usuario pelo email!");
        if (email != null) {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));

            return UsuarioResponseDTO.fromEntity(usuario);
        }

        log.info("Retornando um usuario pelos atributos de nome, cargo e ativo!");
        if (nome != null && cargo != null && ativo != null){

            return usuarioRepository.findByNomeAndCargoAndAtivo(nome, cargo, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();

        }

        log.info("Retornando um usuario pelo nome e pelo cargo!");
        if (nome != null && cargo != null){

            return usuarioRepository.findByNomeAndCargo(nome, cargo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        log.info("Retornando um usuario pelo nome e ativo!");
        if (nome != null && ativo != null){

            return usuarioRepository.findByNomeAndAtivo(nome, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        log.info("Retornando um usuario pelo cargo e ativo!");
        if (cargo != null && ativo != null){

            return usuarioRepository.findByCargoAndAtivo(cargo, ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();

        }

        log.info("Retornando um usuario pelo nome!");
        if (nome != null) {
            return usuarioRepository.findByNome(nome)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        log.info("Retornando um usuario pelo cargo!");
        if (cargo != null) {
            return usuarioRepository.findByCargo(cargo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        log.info("Retornando um usuario pelo id!");
        if (ativo != null) {
            return usuarioRepository.findByAtivo(ativo)
                    .stream()
                    .map(UsuarioResponseDTO::fromEntity)
                    .toList();
        }

        log.info("Fallback de segurança!");
        return List.of();
    }



    public UsuarioResponseDTO deletarUsuario(String idUsuario){
        log.info("Iniciando a deleção de um usuário!");
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        log.info("Verificando a existencia do usuario!");
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setAtivo(false);
            log.info("Salvando o usuário no banco de dados!");
            usuarioRepository.save(usuario);
            log.info("Retornando um usuário");
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        log.info("Usuário não encontrado com o id: " + idUsuario);
        throw new UserNotFoundException(idUsuario);
    }



    public UsuarioResponseDTO atualizarSenha(String idUsuario, UsuarioRequestDTO request){
        log.info("Iniciando a atualização da senha!");
        final Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        log.info("Verificando se o usuário existe!");
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            log.info("Criptografando senha!");
            usuario.setSenha(new BCryptPasswordEncoder().encode(request.senha()));
            log.info("Salvando usuário no banco de dados!");
            usuarioRepository.save(usuario);
            log.info("Retornando usuário!");
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        log.info("Usuário não encontrado com o id: " + idUsuario);
        throw new UserNotFoundException(idUsuario);
    }

    public UsuarioResponseDTO atualizarCargo(String idUsuario, UsuarioRequestDTO request){
        log.info("Iniciando atualização do cargo de um usuário!");
        final Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        log.info("Verificando se o usuário existe!");
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setCargo(request.cargo());
            log.info("Salvando usuário no banco de dados!");
            usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(usuario);
        }
        log.info("Usuário não encontrado com o id: " + idUsuario);
        throw new UserNotFoundException(idUsuario);
    }


    public UsuarioGestorResponseDTO criarGestor(UsuarioGestorRequestDTO request){
        log.info("Iniciando criação do gestor!");
        if(usuarioRepository.findByCpf(request.cpf()) != null){

            log.error("CPF: {} ja está cadastrado!", request.cpf());
            throw new CpfDuplicadoException(request.cpf());
        }
        log.info("Criptografando a senha do usuário!");
        String senha= new BCryptPasswordEncoder().encode(request.senha());
        Usuario usuarioadm = request.toEntity(senha);
        log.error("Salvando usuário no banco de dados!");
        usuarioRepository.save((usuarioadm));

        log.error("Retornando usuário!");
        return UsuarioGestorResponseDTO.fromEntity(usuarioadm);
    }


}
