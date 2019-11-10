package com.nathaliareboucas.minhasfinancas.model.repository;

import java.util.Optional;

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
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		// cenário
		Usuario usuario = criarUsuario();
		
		// ação
		repository.save(usuario);
		
		// verificação
		Assertions.assertThat(usuario.getId()).isNotNull();
		
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		// cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		// ação
		Optional<Usuario> usuarioPesquisado = repository.findByEmail(usuario.getEmail());
		
		// verificação
		Assertions.assertThat(usuarioPesquisado.isPresent()).isTrue();
		
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		// cenário
		
		// ação
		Optional<Usuario> usuarioPesquisado = repository.findByEmail("usuario@email.com");
		
		// verificação
		Assertions.assertThat(usuarioPesquisado.isPresent()).isFalse();
		
	}
	
	public static Usuario criarUsuario() {
		return Usuario.builder().nome("Usuário").email("usuario@email.com").senha("123abc").build();
	}
	
}
