package com.nathaliareboucas.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void verificaExistenciaEmailCadastrado() {
		// cenário
		Usuario usuario = Usuario.builder().nome("João").email("joao@gmail.com").build();
		entityManager.persist(usuario);
		
		// ação
		boolean existe = repository.existsByEmail("joao@gmail.com");
		
		// verificação
		Assertions.assertThat(existe).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// cenário
		
		// ação
		boolean existe = repository.existsByEmail("joao@gmail.com");
		
		// verificação
		Assertions.assertThat(existe).isFalse();
	}
}
