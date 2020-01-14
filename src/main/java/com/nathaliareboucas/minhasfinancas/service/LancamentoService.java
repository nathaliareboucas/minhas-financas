package com.nathaliareboucas.minhasfinancas.service;

import java.math.BigDecimal;
import java.util.List;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;

public interface LancamentoService {
	
	LancamentoDTO salvar(LancamentoDTO lancamento);
	LancamentoDTO atualizar(Long lancamentoId, LancamentoDTO lancamento);
	void deletar(Long id);
	List<LancamentoDTO> buscar(LancamentoDTO lancamentoFiltro);
	void atualizarStatus(LancamentoDTO lancamento, StatusLancamento status);
	LancamentoDTO buscarPorId(Long id);
	BigDecimal obterSaldoPorTipoLancamentoUsuario(Long usuarioId, TipoLancamento tipoLancamento);
	
}
