package com.nathaliareboucas.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="lancamento", schema="financas")
@Getter
@Setter
@EqualsAndHashCode(of="id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "mes")
	private Integer mes;
	
	@Column(name = "ano")
	private Integer ano;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoLancamento tipo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusLancamento status;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	public LancamentoDTO toDTO() {
			
		if(Objects.isNull(usuario) || Objects.isNull(tipo) || Objects.isNull(status))
			throw new RegraNegocioException("Usuário, tipo e status do lançamento são obrigatórios");
		
		return LancamentoDTO.builder()
				.id(this.id)
				.descricao(descricao)
				.mes(mes)
				.ano(ano)
				.valor(valor)
				.tipo(tipo.toString())
				.status(status.toString())
				.usuarioId(usuario.getId())
				.dataCadastro(dataCadastro)
				.build();
	
	}

}
