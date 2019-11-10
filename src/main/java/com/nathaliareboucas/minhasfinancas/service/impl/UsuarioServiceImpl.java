package com.nathaliareboucas.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nathaliareboucas.minhasfinancas.exception.AutenticacaoException;
import com.nathaliareboucas.minhasfinancas.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.repository.UsuarioRepository;
import com.nathaliareboucas.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if (!usuario.isPresent())
			throw new AutenticacaoException("Usuário não encontrado para o email informado.");
		
		if (usuario.get().getSenha().equals(senha))
			throw new AutenticacaoException("Senha inválida.");
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		
		if (existe)
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
	}

}
