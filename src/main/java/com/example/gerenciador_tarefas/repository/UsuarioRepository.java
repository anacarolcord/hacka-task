package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, String > {
    UserDetails findByCpf(String cpf);
}
