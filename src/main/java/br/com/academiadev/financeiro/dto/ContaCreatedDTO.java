package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContaCreatedDTO extends ContaDTO {
	@ApiModelProperty(value = "Identificador", example = "123")
	private Integer id;
	
	@ApiModelProperty(value = "Saldo", example = "0.0")
	private Long saldo;
	
	@ApiModelProperty(value = "Data de criação", example = "2018-11-01 11:50")
	private String data_criacao;
}
