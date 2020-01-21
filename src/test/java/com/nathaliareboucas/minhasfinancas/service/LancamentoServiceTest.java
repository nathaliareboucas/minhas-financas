package com.nathaliareboucas.minhasfinancas.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.exceptionHandler.exception.RegraNegocioException;
import com.nathaliareboucas.minhasfinancas.model.entity.Lancamento;
import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;
import com.nathaliareboucas.minhasfinancas.model.enums.StatusLancamento;
import com.nathaliareboucas.minhasfinancas.model.repository.LancamentoRepository;
import com.nathaliareboucas.minhasfinancas.model.repository.LancamentoRepositoryTest;
import com.nathaliareboucas.minhasfinancas.service.impl.LancamentoServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {
	
	@SpyBean
	LancamentoServiceImpl service;
	
	@MockBean
	LancamentoRepository repository;
	
	@Test
	public void deveSalvarUmLancamento() {
		// cenário
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
		lancamentoASalvar.setUsuario(Usuario.builder().id(1L).build());		
		
		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
		lancamentoSalvo.setUsuario(Usuario.builder().id(1L).build());
		Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

		// execução
		LancamentoDTO lancamento = service.salvar(lancamentoASalvar.toDTO());
		
		// verificação
		assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
		assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE.name());
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarQndHouverErroValidacao() {
		// cenário
		LancamentoDTO lancamentoASalvar = LancamentoRepositoryTest.criarLancamento().toDTO();

		// execução e verificação
		Mockito.doThrow(RegraNegocioException.class).when(service).salvar(lancamentoASalvar);
		Assertions.catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);
	}

}
