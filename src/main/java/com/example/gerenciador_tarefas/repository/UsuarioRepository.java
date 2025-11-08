package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String > {
    UserDetails findByCpf(String cpf);

    List<Usuario> findByNome(String nome);

    List<Usuario> findByCargo(Cargo cargo);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByAtivo(Boolean ativo);

    List<Usuario> findByNomeAndCargo(String nome, Cargo cargo);

    List<Usuario> findByNomeAndAtivo(String nome, Boolean ativo);

    List<Usuario> findByCargoAndAtivo(Cargo cargo, Boolean ativo);

    List<Usuario> findByNomeAndCargoAndAtivo(String nome, Cargo cargo, Boolean ativo);

}
