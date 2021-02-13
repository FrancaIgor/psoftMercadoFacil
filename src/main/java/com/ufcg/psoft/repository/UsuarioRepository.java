package com.ufcg.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	Usuario findByLogin(String login);
}
