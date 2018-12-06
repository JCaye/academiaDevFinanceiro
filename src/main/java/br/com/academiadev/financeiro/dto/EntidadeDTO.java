package br.com.academiadev.financeiro.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntidadeDTO {
	@ApiModelProperty(value = "Nome", example = "Advocacia S.A.", required = true)
	@NotNull
	private String nome;
	
	@ApiModelProperty(value = "Tipo", allowableValues = "CLIENTE,FORNECEDOR", required = true)
	@NotNull
	private String tipo;
}
