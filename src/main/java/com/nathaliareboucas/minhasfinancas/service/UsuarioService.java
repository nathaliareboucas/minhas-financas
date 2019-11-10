package com.nathaliareboucas.minhasfinancas.service;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);	
	Usuario salvar(Usuario usuario);	
	void validarEmail(String email);

}
