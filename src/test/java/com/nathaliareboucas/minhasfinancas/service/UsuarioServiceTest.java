package com.nathaliareboucas.minhasfinancas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nathaliareboucas.minhasfinancas.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	@Autowired
	UsuarioRepository repository;
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenário
		repository.deleteAll();
		
		// ação
		service.validarEmail("joao@gmail.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenário
		Usuario usuario = Usuario.builder().nome("Maria").email("maria@gmail.com").build();
		repository.save(usuario);
		
		// ação
		service.validarEmail("maria@gmail.com");
	}

}
