package com.nathaliareboucas.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
