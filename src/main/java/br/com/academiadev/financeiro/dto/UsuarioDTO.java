package br.com.academiadev.financeiro.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
	@ApiModelProperty(value = "Nome", required = true)
	@NotNull
	private String nome;
	
	@ApiModelProperty(value = "E-mail", required = true)
	@NotNull
	private String email;
	
	@ApiModelProperty(value = "Status", allowableValues = "ATIVO,INATIVO", required = true)
	@NotNull
	private String status;
}
