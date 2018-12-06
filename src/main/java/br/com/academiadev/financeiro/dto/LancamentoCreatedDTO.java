package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LancamentoCreatedDTO extends LancamentoDTO {
	@ApiModelProperty(value = "Identificador do usuario", example = "123")
	private Long id_usuario;
	
	@ApiModelProperty(value = "Identificador do lançamento", example = "123")
	private Long id;
	
	@ApiModelProperty(value = "Status", allowableValues = "ABERTO,BAIXADO")
	private String status;
	
	@ApiModelProperty(value = "Data de criação", example = "2018-11-01 11:59")
	private String data_criacao;
}