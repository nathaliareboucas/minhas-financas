package com.nathaliareboucas.minhasfinancas.resouce;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nathaliareboucas.minhasfinancas.dto.LancamentoDTO;
import com.nathaliareboucas.minhasfinancas.event.RecursoCriadoEvent;
import com.nathaliareboucas.minhasfinancas.service.LancamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {
	
	private final LancamentoService service;
	private final ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<LancamentoDTO>> buscarPorFiltro(LancamentoDTO lancamentoDTOFiltro) {
		return ResponseEntity.ok(service.buscar(lancamentoDTOFiltro));
	}
	
	@PostMapping
	public ResponseEntity<LancamentoDTO> salvar(@RequestBody LancamentoDTO lancamentoDTO, HttpServletResponse response) {	
		LancamentoDTO lancamentoSalvoDTO = service.salvar(lancamentoDTO);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvoDTO.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<LancamentoDTO> atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO lancamentoDTO) {
		return ResponseEntity.ok(service.atualizar(id, lancamentoDTO));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable("id") Long id) {
		service.deletar(id);
	}

}
