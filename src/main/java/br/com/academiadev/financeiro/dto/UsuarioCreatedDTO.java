package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UsuarioCreatedDTO extends UsuarioDTO {
	@ApiModelProperty(value = "Identificador", example = "123")
	private Integer id;
	
	@ApiModelProperty(value = "Data de criação", example = "2018-01-01 00:00:00")
	private String data_criacao;
	
	@ApiModelProperty(value = "Data de alteração", example = "2018-01-01 00:00:00")
	private String data_alteracao;
}
