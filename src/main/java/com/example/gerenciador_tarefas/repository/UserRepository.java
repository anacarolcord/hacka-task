package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String > {
}
