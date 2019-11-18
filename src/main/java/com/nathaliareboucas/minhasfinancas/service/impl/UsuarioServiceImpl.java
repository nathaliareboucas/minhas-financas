package com.nathaliareboucas.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nathaliareboucas.minhasfinancas.dto.UsuarioDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.AutenticacaoException;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.repository.UsuarioRepository;
import com.nathaliareboucas.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	
	private final UsuarioRepository repository;

	@Override
	public UsuarioDTO autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if (!usuario.isPresent())
			throw new AutenticacaoException("Usuário não encontrado para o email informado.");
		
		if (!usuario.get().getSenha().equals(senha))
			throw new AutenticacaoException("Senha inválida.");
		
		return usuario.get().toDTO();
	}

	@Override
	@Transactional
	public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
		validarEmail(usuarioDTO.getEmail());
		return repository.save(usuarioDTO.toEntity()).toDTO();
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		
		if (existe)
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
	}
	
	@Override
	public UsuarioDTO buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Recurso não encontrado."))
				.toDTO();
	}

}
