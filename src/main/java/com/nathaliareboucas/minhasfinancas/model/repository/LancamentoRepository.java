package com.nathaliareboucas.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;
import com.nathaliareboucas.minhasfinancas.model.enums.TipoLancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	@Query(value = 
			"select sum(l.valor) from Lancamento l join l.usuario u "
		  + "where u.id = :usuarioId and l.tipo = :tipoLancamento group by u")
	BigDecimal obterSaldoPorTipoLancamentoUsuario(@Param("usuarioId") Long usuarioId, @Param("tipoLancamento") TipoLancamento tipoLancamento);

}
