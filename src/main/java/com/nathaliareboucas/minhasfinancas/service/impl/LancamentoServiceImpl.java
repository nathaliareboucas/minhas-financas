package com.nathaliareboucas.minhasfinancas.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.repository.LancamentoRepository;
import com.nathaliareboucas.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{
	
	private LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public LancamentoDTO salvar(LancamentoDTO lancamentoDTO) {
		lancamentoDTO.setStatus(StatusLancamento.PENDENTE.toString());
		return repository.save(lancamentoDTO.toEntity()).toDTO();
	}

	@Override
	@Transactional
	public LancamentoDTO atualizar(LancamentoDTO lancamentoDTO) {
		Objects.requireNonNull(lancamentoDTO.getId());
		return repository.save(lancamentoDTO.toEntity()).toDTO();
	}

	@Override
	@Transactional
	public void deletar(LancamentoDTO lancamentoDTO) {
		Objects.requireNonNull(lancamentoDTO.getId());
		repository.delete(lancamentoDTO.toEntity());
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
		atualizar(lancamentoDTO);
	}

	@Override
	public LancamentoDTO buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Recurso não encontrado."))
				.toDTO();
	}

}
