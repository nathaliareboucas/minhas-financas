package com.nathaliareboucas.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;
import com.nathaliareboucas.minhasfinancas.model.repository.LancamentoRepository;
import com.nathaliareboucas.minhasfinancas.service.LancamentoService;
import com.nathaliareboucas.minhasfinancas.util.ObjectUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LancamentoServiceImpl implements LancamentoService{
	
	private final LancamentoRepository repository;

	@Override
	@Transactional
	public LancamentoDTO salvar(LancamentoDTO lancamentoDTO) {
		lancamentoDTO.setStatus(StatusLancamento.PENDENTE.toString());
		lancamentoDTO.setDataCadastro(LocalDate.now());
		return repository.save(lancamentoDTO.toEntity()).toDTO();
	}

	@Override
	@Transactional
	public LancamentoDTO atualizar(Long lancamentoId, LancamentoDTO lancamentoDTO) {
		LancamentoDTO lancamentoExistente = buscarPorId(lancamentoId);
		BeanUtils.copyProperties(lancamentoDTO, lancamentoExistente, ObjectUtil.getNullPropertyNames(lancamentoDTO));
		return repository.save(lancamentoExistente.toEntity()).toDTO();
	}

	@Override
	@Transactional
	public void deletar(Long id) {
		repository.delete(buscarPorId(id).toEntity());
	}

	@Override
	@Transactional(readOnly = true)
	public List<LancamentoDTO> buscar(LancamentoDTO lancamentoFiltroDTO) {
		Example<Lancamento> example = Example.of(lancamentoFiltroDTO.toEntity(), ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example)
				.stream()
				.map(Lancamento::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public void atualizarStatus(LancamentoDTO lancamentoDTO, StatusLancamento status) {
		lancamentoDTO.setStatus(status.toString());
		atualizar(lancamentoDTO.getId(), lancamentoDTO);
	}

	@Override
	public LancamentoDTO buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Recurso não encontrado."))
				.toDTO();
	}

	@Override
	public BigDecimal obterSaldoPorTipoLancamentoUsuario(Long usuarioId, TipoLancamento tipoLancamento) {
		return repository.obterSaldoPorTipoLancamentoUsuario(usuarioId, tipoLancamento);
	}
	
}
