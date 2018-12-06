package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaDTO {
	@ApiModelProperty(value = "Nome", example = "Banco do Brasil", required = true)
	private String nome;
}
