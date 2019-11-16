package com.nathaliareboucas.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.AutenticacaoException;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.repository.UsuarioRepository;
import com.nathaliareboucas.minhasfinancas.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	// UsuarioService service;
	@MockBean
	UsuarioRepository repository;
	
	/* @Before
	public void setUp() {
		service = Mockito.spy(UsuarioService.class);
		service = new UsuarioServiceImpl(repository);
	} */
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		// ação
		service.validarEmail("joao@gmail.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		// ação
		service.validarEmail("maria@gmail.com");
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		// cenário
		String email = "teste@email.com";
		String senha = "123abc";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		// ação
		Usuario usuarioAutenticado = service.autenticar(email, senha);
		
		// verificação
		Assertions.assertThat(usuarioAutenticado).isNotNull();
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		// cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));
		
		// verificação
		Assertions.assertThat(exception).isInstanceOf(AutenticacaoException.class).hasMessage("Usuário não encontrado para o email informado.");
		
	}
	
	@Test
	public void deveLancarErroQuandoASenhaNaoBater() {
		// cenário
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123abc"));
		
		// verificação
		Assertions.assertThat(exception).isInstanceOf(AutenticacaoException.class).hasMessage("Senha inválida.");
		
	}
	
	@Test(expected = Test.None.class)
	public void deveSalvarUmUsuario() {
		// cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		// ação
		Usuario usuarioSalvo = service.salvar(new Usuario());
		
		// verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		// cenário
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		// ação
		service.salvar(usuario);
		
		// verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
		
	}

}
