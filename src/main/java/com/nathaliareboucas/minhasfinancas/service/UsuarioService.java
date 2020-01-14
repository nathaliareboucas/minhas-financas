package com.nathaliareboucas.minhasfinancas.service;

import java.math.BigDecimal;

import com.nathaliareboucas.minhasfinancas.dto.UsuarioDTO;

public interface UsuarioService {
	
	UsuarioDTO autenticar(String email, String senha);	
	UsuarioDTO salvar(UsuarioDTO usuario);	
	void validarEmail(String email);
	UsuarioDTO buscarPorId(Long id);
	BigDecimal obterSaldoPorTipoLancamentoUsuario(Long usuarioId, String tipoLancamento);
	BigDecimal obterSaldoUsuario(Long usuarioId);

}
