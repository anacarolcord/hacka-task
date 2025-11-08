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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<UsuarioResponseDTO> listarUsuario(Pageable pageable){
        log.info("Iniciando listagem de todos os usuários!");
        return usuarioRepository.findAll(pageable)
                .map(UsuarioResponseDTO::fromEntity);
    }

    public Page<UsuarioResponseDTO> pesquisaUsuarios(String idUsuario, String nome, Cargo cargo, String email, Boolean ativo, Pageable pageable) {

        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new UserNotFoundException(idUsuario));

            UsuarioResponseDTO dto = UsuarioResponseDTO.fromEntity(usuario);

            return new PageImpl<>(
                    List.of(dto),
                    pageable,
                    1
            );
        }

        if (nome != null){
            return usuarioRepository.findByNome(nome, pageable)
                    .map(UsuarioResponseDTO::fromEntity);
        }

        if (cargo != null){
            return usuarioRepository.findByCargo(cargo, pageable)
                    .map(UsuarioResponseDTO::fromEntity);
        }

        if (email != null){
            return usuarioRepository.findByEmail(email, pageable)
                    .map(UsuarioResponseDTO::fromEntity);
        }

        if (ativo != null){
            return usuarioRepository.findByAtivo(ativo, pageable)
                    .map(UsuarioResponseDTO::fromEntity);
        }

        return usuarioRepository.findAll(pageable)
                .map(UsuarioResponseDTO::fromEntity);


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
