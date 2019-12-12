package com.nathaliareboucas.minhasfinancas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
		
		Lancamento entity = Lancamento.builder()
				.id(id)
				.descricao(descricao)
				.mes(mes)
				.ano(ano)
				.valor(valor)
				.dataCadastro(dataCadastro)
				.build();
		
		if (Objects.nonNull(usuarioId))
			entity.setUsuario(Usuario.builder().id(usuarioId).build());
		
		if (Objects.nonNull(tipo))
			entity.setTipo(TipoLancamento.valueOf(tipo));
		
		if (Objects.nonNull(status))
			entity.setStatus(StatusLancamento.valueOf(status));
		
		return entity;
				
	}

}
