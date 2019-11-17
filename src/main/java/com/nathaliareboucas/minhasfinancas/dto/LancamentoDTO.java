package com.nathaliareboucas.minhasfinancas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
	
	private Long id;
	private String descricao;
	private Integer mes;
	private Integer ano;
	private BigDecimal valor;
	private String tipo;
	private String status;
	private Long usuarioId;
	private LocalDate dataCadastro;
	
	public Lancamento toEntity() {
		
		if (Objects.isNull(usuarioId) || Objects.isNull(tipo) || Objects.isNull(status))
			throw new RegraNegocioException("Usuário, tipo e status do lnaçamento são obrigatórios");

		Usuario usuarioLancamento = Usuario.builder().id(usuarioId).build();
		return Lancamento.builder()
				.id(id)
				.descricao(descricao)
				.mes(mes)
				.ano(ano)
				.valor(valor)
				.tipo(TipoLancamento.valueOf(tipo))
				.status(StatusLancamento.valueOf(status))
				.usuario(usuarioLancamento)
				.dataCadastro(dataCadastro)
				.build();
				
	}

}
