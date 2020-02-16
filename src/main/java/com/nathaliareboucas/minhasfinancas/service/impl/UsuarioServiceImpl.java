package com.nathaliareboucas.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.dto.UsuarioDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.AutenticacaoException;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;
import com.nathaliareboucas.minhasfinancas.model.repository.UsuarioRepository;
import com.nathaliareboucas.minhasfinancas.service.LancamentoService;
import com.nathaliareboucas.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	
	private final UsuarioRepository repository;
	private final LancamentoService lancamentoService;

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
		Usuario usuario = usuarioDTO.toEntity();
		usuario.setDataCadastro(LocalDate.now());
		return repository.save(usuario).toDTO();
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

	@Override
	public BigDecimal obterSaldoPorTipoLancamentoUsuario(Long usuarioId, String tipoLancamento) {
		UsuarioDTO usuarioDTO = buscarPorId(usuarioId);
		
		try {
			return lancamentoService.obterSaldoPorTipoLancamentoUsuario(usuarioDTO.getId(), TipoLancamento.valueOf(tipoLancamento.toUpperCase()));
		} catch (Exception e) {
			 throw new RegraNegocioException("Tipo de lançamento inválido");
		}
		
	}

	@Override
	public BigDecimal obterSaldoUsuario(Long usuarioId) {		
		List<LancamentoDTO> lancamentosUsuario = lancamentoService.buscar(LancamentoDTO.builder().usuarioId(usuarioId).build());
		
		BigDecimal receita = lancamentosUsuario.stream()
				.filter(lancamento -> lancamento.getTipo().equals(TipoLancamento.RECEITA.name())
						&& lancamento.getStatus().equals(StatusLancamento.EFETIVADO.name()))
				.map(lanc -> lanc.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal despesa = lancamentosUsuario.stream()
				.filter(lancamento -> lancamento.getTipo().equals(TipoLancamento.DESPESA.name())
						&& lancamento.getStatus().equals(StatusLancamento.EFETIVADO.name()))
				.map(lanc -> lanc.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		
		return receita.subtract(despesa);
	}

}
