package com.nathaliareboucas.minhasfinancas.resouce;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathaliareboucas.minhasfinancas.dto.UsuarioDTO;
import com.nathaliareboucas.minhasfinancas.event.RecursoCriadoEvent;
import com.nathaliareboucas.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioResource {
	
	UsuarioService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioDTO usuarioDTO, HttpServletResponse response) {	
		UsuarioDTO usuarioSalvoDTO = service.salvar(usuarioDTO); 
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvoDTO.getId()));	
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvoDTO);
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<UsuarioDTO> autenticar(@RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.ok(service.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity<BigDecimal> obterSaldo(@PathVariable("id") Long usuarioId) {
		return ResponseEntity.ok(service.obterSaldoUsuario(usuarioId));
	}
	
	@GetMapping("{id}/{tipoLancamento}")
	public ResponseEntity<BigDecimal> obterSaldoPorTipoUsuario(@PathVariable("id") Long usuarioId, @PathVariable String tipoLancamento) {
		return ResponseEntity.ok(service.obterSaldoPorTipoLancamentoUsuario(usuarioId, tipoLancamento));
	}

}
