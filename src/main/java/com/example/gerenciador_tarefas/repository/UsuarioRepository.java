package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String > {
    UserDetails findByCpf(String cpf);

    Page<Usuario> findByNome(String nome, Pageable pageable);

    Page<Usuario> findByCargo(Cargo cargo, Pageable pageable);

    Page<Usuario> findByEmail(String email, Pageable pageable);

    Page<Usuario> findByAtivo(Boolean ativo, Pageable pageable);

}
