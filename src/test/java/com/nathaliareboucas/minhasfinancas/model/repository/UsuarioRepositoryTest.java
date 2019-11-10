package com.nathaliareboucas.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void verificaExistenciaEmailCadastrado() {
		Usuario usuario = Usuario.builder().nome("João").email("joao@gmail.com").build();
		repository.save(usuario);
		
		boolean existe = repository.existsByEmail("joao@gmail.com");
		Assertions.assertThat(existe).isTrue();
		
	}
}
