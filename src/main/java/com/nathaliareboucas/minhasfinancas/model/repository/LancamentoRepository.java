package com.nathaliareboucas.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
