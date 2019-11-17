package com.nathaliareboucas.minhasfinancas.service;

import java.util.Optional;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);	
	Usuario salvar(Usuario usuario);	
	void validarEmail(String email);
	Optional<Usuario> buscarPorId(Long id);

}
