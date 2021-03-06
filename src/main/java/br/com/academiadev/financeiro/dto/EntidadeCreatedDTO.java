package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EntidadeCreatedDTO extends EntidadeDTO {
	@ApiModelProperty(value = "Identificador do usuario", example = "123")
	private Long id_usuario;
	
	@ApiModelProperty(value = "Identificador da entidade", example = "123")
	private Long id;
	
	@ApiModelProperty(value = "Status", allowableValues = "ATIVO,INATIVO")
	private String status;
	
	@ApiModelProperty(value = "Data de criação", example = "2018-01-01 00:00:00")
	private String data_criacao;
	
	@ApiModelProperty(value = "Data de alteração", example = "2018-01-01 00:00:00")
	private String data_alteracao;
}
