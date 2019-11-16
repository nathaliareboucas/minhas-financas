package com.nathaliareboucas.minhasfinancas.dto;

import com.nathaliareboucas.minhasfinancas.model.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	
	public Usuario toEntity() {
		return Usuario.builder().id(this.id).nome(this.nome).email(this.email).senha(this.senha).build();
	}

}
