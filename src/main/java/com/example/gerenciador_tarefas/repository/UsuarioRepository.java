package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String > {
    UserDetails findByCpf(String cpf);

    List<Usuario> findByNome(String nome);

    List<Usuario> findByCargo(Cargo cargo);

    List<Usuario> findByEmail(String email);

    List<Usuario> findByAtivo(Boolean ativo);

}
