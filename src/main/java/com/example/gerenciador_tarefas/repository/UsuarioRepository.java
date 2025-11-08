package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsuarioRepository extends MongoRepository<Usuario, Long > {
    UserDetails findByCpf(String cpf);

    List<Usuario> findByNome(String nome);

    List<Usuario> findByCargo(Cargo cargo);

    List<Usuario> findByEmail(String email);

    List<Usuario> findByAtivo(Boolean ativo);

}
